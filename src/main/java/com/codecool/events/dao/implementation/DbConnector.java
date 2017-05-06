package com.codecool.events.dao.implementation;

import com.codecool.events.model.Category;
import com.codecool.events.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DbConnector {

  private Connection connect() throws SQLException {
    Connection conn;
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/database.db");
    } catch (Exception e) {
      throw new SQLException("SQL ERROR: Couldn't connect to database!");
    }
    return conn;
  }

  private void closeConnection(Connection conn) throws SQLException {
    conn.close();
  }

  Event getEventBy(Integer id) throws SQLException {
    Event foundEvent = null;

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Event WHERE id=" + id);

      if (dbQuery.next()) {
        foundEvent = new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            getCategoryBy(dbQuery.getInt("categoryID")),
            new Date(dbQuery.getLong("date"))
        );
      }
    } catch (Exception e) {
      throw new SQLException("Couldn't found event!");
    }
    return foundEvent;
  }

  List<Event> getEventsBy(Category category) throws SQLException {
    List<Event> foundEventList = new ArrayList<>();

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement
          .executeQuery("SELECT * FROM Event WHERE categoryID=" + category.getId());

      while (dbQuery.next()) {
        foundEventList.add(new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            getCategoryBy(dbQuery.getInt("categoryID")),
            new Date(dbQuery.getLong("date"))
        ));
      }
    } catch (Exception e) {
      throw new SQLException("Couldn't found events!");
    }
    return foundEventList;
  }

  Category getCategoryBy(Integer id) throws SQLException {
    Category foundCategory = null;

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Category WHERE id=" + id);

      if (dbQuery.next()) {
        foundCategory = new Category(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description")
        );
      }
    } catch (Exception e) {
      throw new SQLException("Couldn't found category!");
    }
    return foundCategory;
  }

  List<Event> getEvents() throws SQLException {
    List<Event> eventList = new ArrayList<>();

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Event");

      while (dbQuery.next()) {
        eventList.add(new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            getCategoryBy(dbQuery.getInt("categoryID")),
            new Date(Long.parseLong(dbQuery.getString("date")))
        ));
      }

      dbQuery.close();
      dbStatement.close();
      closeConnection(conn);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get events!");
    }
    return eventList;
  }

  List<Category> getCategories() throws SQLException {
    List<Category> categoryList = new ArrayList<>();

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Category");

      while (dbQuery.next()) {
        categoryList.add(new Category(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description")
        ));
      }

      dbQuery.close();
      dbStatement.close();
      closeConnection(conn);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get categories!");
    }
    return categoryList;
  }

  void upsertEvent(Event event) throws SQLException {
    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      dbStatement.execute("INSERT OR REPLACE INTO Event (id, name, description, date, categoryID)"
          + " VALUES ((SELECT id FROM Event WHERE id=" +
          event.getId() + "), '" +
          event.getName() + "', '" +
          event.getDescription() + "', " +
          event.getDate() + ", " +
          event.getCategory().getId() + ")");

      dbStatement.close();
      closeConnection(conn);
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't upsert to database!");
    }
  }

  void deleteEvent(Event event) throws SQLException {
    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      String query = "DELETE FROM Event WHERE id=" + event.getId();

      dbStatement.execute(query);
      dbStatement.close();
    } catch (SQLException e) {
      throw new SQLException("SQL ERROR: Couldn't delete from database!");
    }
  }

// getEventBy + getCategoryBy

// getEvents + getCategories
}