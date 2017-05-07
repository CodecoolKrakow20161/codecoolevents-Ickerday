package com.codecool.events.dao.implementation;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.dao.EventDao;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {

  private final DbConnector dbConn = new DbConnector();

  @Override
  public List<Event> getAllEvents() throws SQLException {
    List<Event> eventList = new ArrayList<>();
    CategoryDao categoryDao = new CategoryDaoImpl();

    try {
      Connection conn = dbConn.connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Event");

      while (dbQuery.next()) {
        eventList.add(new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            categoryDao.find(dbQuery.getInt("categoryID")),
            new Date(Long.parseLong(dbQuery.getString("date"))),
            dbQuery.getString("link")
        ));
      }

      dbQuery.close();
      dbStatement.close();
      dbConn.closeConnection(conn);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get events!");
    }
    return eventList;
  }

  @Override
  public void delete(Event event) throws SQLException {
    try {
      Connection conn = dbConn.connect();
      Statement dbStatement = conn.createStatement();

      String query = "DELETE FROM Event WHERE id=" + event.getId();

      dbStatement.execute(query);
      dbStatement.close();
      dbConn.closeConnection(conn);
    } catch (SQLException e) {
      throw new SQLException("SQL ERROR: Couldn't delete from database!");
    }
  }

  @Override
  public Event find(Integer id) throws SQLException {
    Event foundEvent = null;
    CategoryDao categoryDao = new CategoryDaoImpl();

    try {
      Connection conn = dbConn.connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Event WHERE id=" + id);

      if (dbQuery.next()) {
        foundEvent = new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            categoryDao.find(dbQuery.getInt("categoryID")),
            new Date(dbQuery.getLong("date")),
            dbQuery.getString("link")
        );
      }

      dbQuery.close();
      dbStatement.close();
      dbConn.closeConnection(conn);
    } catch (Exception e) {
      throw new SQLException("Couldn't found event!");
    }
    return foundEvent;
  }

  @Override
  public List<Event> getEventsBy(Category category) throws SQLException {
    List<Event> foundEventList = new ArrayList<>();
    CategoryDao categoryDao = new CategoryDaoImpl();

    try {
      Connection conn = dbConn.connect();
      Statement dbStatement = conn.createStatement();

      ResultSet dbQuery = dbStatement
          .executeQuery("SELECT * FROM Event WHERE categoryID=" + category.getId());

      while (dbQuery.next()) {
        foundEventList.add(new Event(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description"),
            categoryDao.find(dbQuery.getInt("categoryID")),
            new Date(dbQuery.getLong("date")),
            dbQuery.getString("link")
        ));
      }

      dbQuery.close();
      dbStatement.close();
      dbConn.closeConnection(conn);
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("Couldn't found events!");
    }
    return foundEventList;
  }

  @Override
  public void insert(Event event) throws SQLException {
    try {
      Connection conn = dbConn.connect();
      Statement dbStatement = conn.createStatement();



      dbStatement.execute("INSERT OR REPLACE INTO Event (id, name, description, date, categoryID, link)"
          + " VALUES ((SELECT id FROM Event WHERE id=" +
          event.getId() + "), '" +
          event.getName() + "', '" +
          event.getDescription() + "', " +
          event.getDate().getTime() + ", " +
          event.getCategory().getId() + ", '" +
          event.getLink() + "')"
      );

      dbStatement.close();
      dbConn.closeConnection(conn);
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't insert to database!");
    }
  }
}
