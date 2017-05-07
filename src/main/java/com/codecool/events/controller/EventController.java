package com.codecool.events.controller;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.dao.EventDao;
import com.codecool.events.dao.implementation.CategoryDaoImpl;
import com.codecool.events.dao.implementation.EventDaoImpl;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EventController {

  public static ModelAndView renderEvents(Request req, Response res) {
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
      e.printStackTrace();
    }

    return new ModelAndView(params, "event/index");
  }

  public static ModelAndView renderAddForm(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    Map<String, Object> params = new HashMap<>();

    try {
      params.put("categoryList", categoryDao.getAllCategories());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ModelAndView(params, "event/add");
  }

  // TODO
  public static ModelAndView renderEditForm(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    Map<String, Object> params = new HashMap<>();

    try {
      String eventName = req.queryParams("event-name");
      String eventDescription = req.queryParams("event-description");
      Category eventCategory = categoryDao
          .find(Integer.parseInt(req.queryParams("event-category")));
      Date eventDate = new Date();
      String eventLink = req.queryParams("event-link");

      params.put("categoryList", categoryDao.getAllCategories());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ModelAndView(params, "event/add");
  }

  public static ModelAndView handleAddRequest(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    EventDao eventDao = new EventDaoImpl();

    try {
      String eventName = req.queryParams("event-name");
      String eventDescription = req.queryParams("event-description");
      Category eventCategory = categoryDao
          .find(Integer.parseInt(req.queryParams("event-category")));

      String unparsedDate = req.queryParams("event-date") + " " + req.queryParams("event-time");
      Date eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(unparsedDate);

      String eventLink = req.queryParams("event-link");

      eventDao.insert(new Event(eventName, eventDescription, eventCategory, eventDate, eventLink));
    } catch (SQLException | ParseException e) {
      e.printStackTrace();
    }

    res.status(201);
    res.redirect("/");
    return renderEvents(req, res);
  }

  public static ModelAndView handleDeleteRequest(Request req, Response res) {
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
