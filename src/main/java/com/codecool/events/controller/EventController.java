package com.codecool.events.controller;

import com.codecool.events.dao.implementation.CategoryDaoImpl;
import com.codecool.events.dao.implementation.EventDaoImpl;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EventController {

  public static ModelAndView renderEvents(Request req, Response res) {
    EventDaoImpl eventDao = new EventDaoImpl();
    Map<String, List<Event>> params = new HashMap<>();

    params.put("eventList", eventDao.getAllEvents());

    return new ModelAndView(params, "product/index");
  }

  public static Object handleAddEditRequest(Request req, Response res) {
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    EventDaoImpl eventDao = new EventDaoImpl();

    String eventName = req.queryParams("event-name");
    String eventDescription = req.queryParams("event-description");
    Category eventCategory = categoryDao
        .find(Integer.getInteger(req.queryParams("event-category-id")));

    eventDao.upsert(new Event(eventName, eventDescription, eventCategory, new Date()));

    res.status(201);
    res.redirect("/");
    return "";
  }
}
