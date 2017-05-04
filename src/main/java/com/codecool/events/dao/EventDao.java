package com.codecool.events.dao;

import com.codecool.events.model.Event;
import java.util.List;

public interface EventDao {

  Event find(Integer id);

  List<Event> getAllEvents();

}
