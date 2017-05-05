package com.codecool.events;

import static spark.Spark.*;

import com.codecool.events.controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {
    port(8888);
    staticFileLocation("/public");

    get("/shrug", (req, res) -> "¯\\_(ツ)_/¯");

    try {
      get("/", EventController::renderEvents, new ThymeleafTemplateEngine());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}