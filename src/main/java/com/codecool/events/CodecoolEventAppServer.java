package com.codecool.events;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import com.codecool.events.controller.EventController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {
    Integer PORT_NUMBER = getHerokuAssignedPort();
    port(PORT_NUMBER);

    String locationPath = "/static";
    staticFileLocation(locationPath);

    EventController eventController = new EventController();
    ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();

    path("/event", () -> {

      path("/:id", () -> {

        get("/edit", eventController::renderEditForm, templateEngine);

        get("/delete", eventController::handleDeleteRequest, templateEngine);

        post("/edit", eventController::handleEditRequest, templateEngine);

      });

      get("/add", eventController::renderAddForm, templateEngine);

      post("/add", eventController::handleAddRequest, templateEngine);

    });

    get("/category/:id", eventController::renderEvents, templateEngine);

    get("/", eventController::renderEvents, templateEngine);
  }

  private Integer getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 8888;
  }
}