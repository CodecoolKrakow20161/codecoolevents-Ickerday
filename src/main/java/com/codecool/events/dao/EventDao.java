package com.codecool.events.dao;

import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import java.util.List;

public interface EventDao {

  void upsert(Event event);

  Event find(Integer id);

  void delete(Event event);

  List<Event> getAllEvents();

  List<Event> getEventsBy(Category category);

}
