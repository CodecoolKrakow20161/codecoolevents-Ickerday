package com.codecool.events.controller;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.dao.EventDao;
import com.codecool.events.dao.implementation.CategoryDaoImpl;
import com.codecool.events.dao.implementation.EventDaoImpl;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
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

    return new ModelAndView(params, "event/index");
  }

  public static ModelAndView renderAddForm(Request req, Response res) {
    CategoryDao categoryDao = new CategoryDaoImpl();
    Map<String, Object> params = new HashMap<>();

    params.put("categoryList", categoryDao.getAllCategories());
    return new ModelAndView(params, "event/add");
  }

  public static ModelAndView handleAddEditRequest(Request req, Response res) {
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    EventDaoImpl eventDao = new EventDaoImpl();

    String eventName = req.queryParams("event-name");
    String eventDescription = req.queryParams("event-description");
    Category eventCategory = categoryDao
        .find(Integer.getInteger(req.queryParams("event-category")));
    Date eventDate = new Date();

    eventDao.upsert(new Event(eventName, eventDescription, eventCategory, eventDate));

    res.status(201);
    res.redirect("/");
    return renderEvents(req, res);
  }
}
