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

  private final Connection dbConn;
  private final CategoryDao categoryDao;

  public EventDaoImpl(Connection dbConn) {
    this.dbConn = dbConn;
    categoryDao = new CategoryDaoImpl(dbConn);

  }

  @Override
  public List<Event> getAllEvents() throws SQLException {
    List<Event> eventList = new ArrayList<>();

    try {
      Statement dbStatement = dbConn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Event");

      while (dbQuery.next()) {
        eventList.add(new Event(
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
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get events!");
    }
    return eventList;
  }

  @Override
  public void delete(Event event) throws SQLException {
    try {
      Statement dbStatement = dbConn.createStatement();

      String query = String.format("DELETE FROM Event WHERE id=%d", event.getId());

      dbStatement.execute(query);
      dbStatement.close();
    } catch (SQLException e) {
      throw new SQLException("SQL ERROR: Couldn't delete from database!");
    }
  }

  @Override
  public Event find(Integer id) throws SQLException {
    Event foundEvent = null;

    try {
      Statement dbStatement = dbConn.createStatement();

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
    } catch (Exception e) {
      throw new SQLException("Couldn't found event!");
    }
    return foundEvent;
  }

  @Override
  public List<Event> getEventsBy(Category category) throws SQLException {
    List<Event> foundEventList = new ArrayList<>();

    try {
      Statement dbStatement = dbConn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery(
          "SELECT * FROM Event WHERE categoryID=" + category.getId());

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
    } catch (Exception e) {
      System.out.println("No events in category" + category.toString());
    }
    return foundEventList;
  }

  @Override
  public void update(Event event) throws SQLException {
    try {
      Statement dbStatement = dbConn.createStatement();

      String query = String.format(
          "UPDATE Event SET name='%s', description='%s', date=%d, categoryID=%d, link='%s' WHERE id=%d;",
          event.getName(), event.getDescription(), event.getDate().getTime(),
          event.getCategory().getId(), event.getLink(), event.getId());

      dbStatement.executeUpdate(query);

      dbStatement.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't insert to database!");
    }
  }

  @Override
  public void insert(Event event) throws SQLException {
    try {
      Statement dbStatement = dbConn.createStatement();

      String query = String.format(
          "INSERT INTO Event (name, description, date, categoryID, link) VALUES ('%s', '%s', %d, %d, '%s')",
          event.getName(), event.getDescription(), event.getDate().getTime(),
          event.getCategory().getId(), event.getLink());

      dbStatement.executeUpdate(query);

      dbStatement.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't insert to database!");
    }
  }
}
