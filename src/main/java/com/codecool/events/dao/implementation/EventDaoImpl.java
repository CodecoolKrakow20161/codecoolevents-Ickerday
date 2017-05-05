package com.codecool.events.dao.implementation;

import com.codecool.events.dao.EventDao;
import com.codecool.events.model.Event;
import java.sql.SQLException;
import java.util.List;

public class EventDaoImpl implements EventDao {

  @Override
  public void upsert(Event event) {
    try {
      new DbConnector().upsertEvent(event);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Event find(Integer id) {
    return null;
  }

  @Override
  public void delete(Event event) {
    try {
      new DbConnector().deleteEvent(event);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Event> getAllEvents() {
    List<Event> eventList = null;
    try {
      eventList = new DbConnector().getEvents();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return eventList;
  }
}
