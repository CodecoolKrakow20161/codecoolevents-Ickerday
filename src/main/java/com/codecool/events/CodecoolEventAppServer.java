package com.codecool.events;

import static spark.Spark.*;

import com.codecool.events.controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {

    port(8888);
    staticFileLocation("/static");

    // TODO
    get("/event/:id/edit", EventController::renderEvents, new ThymeleafTemplateEngine());

    //TODO
    get("/event/:id/delete", EventController::renderEvents, new ThymeleafTemplateEngine());

    // TODO
    get("/category/:id", EventController::renderEvents, new ThymeleafTemplateEngine());

    // TODO
    post("/event/add", EventController::handleAddEditRequest);

    get("/", EventController::renderEvents, new ThymeleafTemplateEngine());
  }
}