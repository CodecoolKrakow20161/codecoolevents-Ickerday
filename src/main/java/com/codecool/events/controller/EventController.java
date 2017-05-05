package com.codecool.events.controller;

import com.codecool.events.dao.implementation.EventDaoImpl;
import com.codecool.events.model.Event;
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
}
