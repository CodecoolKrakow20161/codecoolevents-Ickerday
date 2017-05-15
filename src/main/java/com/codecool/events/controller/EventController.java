package com.codecool.events.controller;

import static spark.Spark.halt;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.dao.EventDao;
import com.codecool.events.dao.implementation.CategoryDaoImpl;
import com.codecool.events.dao.implementation.EventDaoImpl;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EventController {

  public ModelAndView renderEvents(Request req, Response res) {
    EventDao eventDao = new EventDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();
    Map<String, Object> params = new HashMap<>();

    try {
      if (req.params().containsKey(":id")) {
        Integer categoryID = Integer.parseInt(req.params(":id"));
        Category chosenCategory = categoryDao.find(categoryID);

        params.put("eventList", eventDao.getEventsBy(chosenCategory));
        params.put("tabActive", categoryID);
      } else {
        params.put("eventList", eventDao.getAllEvents());
        params.put("tabActive", "all");
      }

      params.put("categoryList", categoryDao.getAllCategories());
    } catch (SQLException e) {
      throw halt(400, "Couldn't show events!");
    }

    return new ModelAndView(params, "event/index");
  }

  public ModelAndView renderAddForm(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    Map<String, Object> params = new HashMap<>();

    try {
      params.put("categoryList", categoryDao.getAllCategories());
    } catch (SQLException e) {
      throw halt(400, "Invalid add form!");
    }
    return new ModelAndView(params, "event/add");
  }

  public ModelAndView renderEditForm(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    EventDao eventDao = new EventDaoImpl();
    Map<String, Object> params = new HashMap<>();

    try {
      List<Event> eventList = new ArrayList<>();
      Event editedEvent = eventDao.find(Integer.parseInt(req.params(":id")));
      eventList.add(editedEvent);

      params.put("eventList", eventList);
      params.put("categoryList", categoryDao.getAllCategories());
    } catch (SQLException e) {
      throw halt(400, "Invalid edit request!");
    }
    return new ModelAndView(params, "event/edit");
  }

  public ModelAndView handleAddRequest(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    EventDao eventDao = new EventDaoImpl();

    try {
      if (req.queryParams().contains("event-category")) {
        String eventName = req.queryParams("event-name");
        String eventDescription = req.queryParams("event-description");
        Category eventCategory = categoryDao
            .find(Integer.parseInt(req.queryParams("event-category")));

        String unparsedDate = req.queryParams("event-date") + " " + req.queryParams("event-time");
        Date eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(unparsedDate);

        String eventLink = req.queryParams("event-link");

        eventDao
            .insert(new Event(eventName, eventDescription, eventCategory, eventDate, eventLink));
      } else {
        throw new SQLException("No category provided!");
      }
    } catch (SQLException | ParseException e) {
      throw halt(400, "Information about event incomplete!");
    }

    res.status(201);
    res.redirect("/");
    return renderEvents(req, res);
  }

  public ModelAndView handleEditRequest(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    EventDao eventDao = new EventDaoImpl();

    for (Object str:req.queryParams().toArray()) {
      System.out.println(str.toString());
    }

    try {
      if (req.queryParams().contains("event-category")) {
        Integer eventID = Integer.parseInt(req.queryParams("event-id"));
        String eventName = req.queryParams("event-name");
        String eventDescription = req.queryParams("event-description");
        Category eventCategory = categoryDao
            .find(Integer.parseInt(req.queryParams("event-category")));

        String unparsedDate = req.queryParams("event-date") + " " + req.queryParams("event-time");
        Date eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(unparsedDate);

        String eventLink = req.queryParams("event-link");

        eventDao
            .insert(new Event(eventID, eventName, eventDescription, eventCategory, eventDate, eventLink));
      } else {
        throw new SQLException("No category provided!");
      }
    } catch (SQLException | ParseException e) {
      throw halt(400, "Couldn't update event!");
    }

    res.status(201);
    res.redirect("/");
    return renderEvents(req, res);
  }

  public ModelAndView handleDeleteRequest(Request req, Response res) {
    EventDao eventDao = new EventDaoImpl();

    try {
      if (req.params().containsKey(":id")) {
        Integer eventID = Integer.parseInt(req.params(":id"));

        eventDao.delete(eventDao.find(eventID));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    res.status(201);
    res.redirect("/");
    return renderEvents(req, res);
  }
}
