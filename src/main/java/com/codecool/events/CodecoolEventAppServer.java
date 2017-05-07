package com.codecool.events;

import static spark.Spark.*;

import com.codecool.events.controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {
    port(8888);
    staticFileLocation("/static");

    get("/event/add", EventController::renderAddForm, new ThymeleafTemplateEngine());

    post("/event/add", EventController::handleAddEditRequest, new ThymeleafTemplateEngine());

    // TODO
    get("/event/:id/edit", EventController::renderEditForm, new ThymeleafTemplateEngine());

    //TODO
    get("/event/:id/delete", EventController::handleDeleteRequest, new ThymeleafTemplateEngine());

    get("/category/:id", EventController::renderEvents, new ThymeleafTemplateEngine());

    get("/", EventController::renderEvents, new ThymeleafTemplateEngine());
  }
}