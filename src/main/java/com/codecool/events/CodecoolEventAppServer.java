package com.codecool.events;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import com.codecool.events.controller.EventController;
import com.codecool.events.dao.implementation.DbConnector;
import java.sql.Connection;
import java.sql.SQLException;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

class CodecoolEventAppServer {

  CodecoolEventAppServer eventApp;
  private Connection dbConn;
  private EventController eventController;
  private ThymeleafTemplateEngine templateEngine;

  CodecoolEventAppServer() {
    Integer PORT_NUMBER = getHerokuAssignedPort();
    port(PORT_NUMBER);

    String locationPath = "/static";
    staticFileLocation(locationPath);

    this.eventApp = this;
    this.dbConn = new DbConnector().connect();
    this.eventController = new EventController(dbConn);
    this.templateEngine = new ThymeleafTemplateEngine();

    dispatchRoutes();
  }

  private Integer getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 8888;
  }

  private void dispatchRoutes() {
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

  void closeConnection() throws SQLException {
    this.dbConn.close();
  }

  CodecoolEventAppServer getEventApp() {
    return eventApp;
  }
}