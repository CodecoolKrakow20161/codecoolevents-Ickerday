package com.codecool.events.dao.implementation;

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

  List<Event> getEvents() throws SQLException {
    List<Event> eventList = new ArrayList<>();
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Events");

      while (dbQuery.next()) {
        eventList.add(new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            categoryDao.find(dbQuery.getInt("categoryID")),
            new Date(dbQuery.getLong("Date"))
        ));
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get events!");
    }

    return eventList;
  }

  void upsertEvent(Event event) throws SQLException {
    try {
      Connection conn = connect();
      Statement dbStatement = conn.createStatement();

      dbStatement.execute("INSERT OR REPLACE INTO Events (id, name, description, date, categoryID)"
          + " VALUES ((SELECT id FROM Events WHERE id=" +
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

      String query = "DELETE FROM Events WHERE id=" + event.getId();

      dbStatement.execute(query);
      dbStatement.close();
    } catch (SQLException e) {
      throw new SQLException("SQL ERROR: Couldn't delete from database!");
    }
  }
}
