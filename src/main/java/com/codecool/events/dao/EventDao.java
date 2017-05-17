package com.codecool.events.dao;

import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.sql.SQLException;
import java.util.List;

public interface EventDao {

  void insert(Event event) throws SQLException;

  void update(Event event) throws SQLException;

  Event find(Integer id) throws SQLException;

  void delete(Event event) throws SQLException;

  List<Event> getAllEvents() throws SQLException;

  List<Event> getEventsBy(Category category) throws SQLException;

}
