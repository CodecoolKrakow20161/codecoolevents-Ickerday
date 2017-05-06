package com.codecool.events.controller;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.dao.EventDao;
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
    EventDao eventDao = new EventDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();

    Map<String, List> params = new HashMap<>();

    if (req.params().containsKey(":id")) {
      Category chosenCategory = categoryDao.find(Integer.parseInt(req.params(":id")));
      params.put("eventList", eventDao.getEventsBy(chosenCategory));
    } else {
      params.put("eventList", eventDao.getAllEvents());
    }

    params.put("categoryList", categoryDao.getAllCategories());

    return new ModelAndView(params, "product/index");
  }

  public static Object handleAddEditRequest(Request req, Response res) {
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    EventDaoImpl eventDao = new EventDaoImpl();

    String eventName = req.queryParams("event-name");
    String eventDescription = req.queryParams("event-description");
    Category eventCategory = categoryDao
        .find(Integer.getInteger(req.queryParams("event-category-id")));
    Date eventDate = new Date();

    eventDao.upsert(new Event(eventName, eventDescription, eventCategory, eventDate));

    res.status(201);
    res.redirect("/");
    return "";
  }
}
