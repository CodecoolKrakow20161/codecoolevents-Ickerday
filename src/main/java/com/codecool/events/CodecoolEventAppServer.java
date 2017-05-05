package com.codecool.events;

import static spark.Spark.*;

import com.codecool.events.controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {
    port(8888);
    staticFileLocation("/static");

    get("/shrug", (req, res) -> "¯\\_(ツ)_/¯");

    get("/", EventController::renderEvents, new ThymeleafTemplateEngine());

    post("/article/create", EventController::handleAddEditRequest);


  }
}