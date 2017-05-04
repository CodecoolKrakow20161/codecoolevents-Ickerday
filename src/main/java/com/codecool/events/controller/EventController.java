package com.codecool.events.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class EventController {
    public static ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao

        Map params = new HashMap<>();
        params.put("eventContainer", "Codecool cinema");
        return new ModelAndView(params, "product/index");
    }
}
