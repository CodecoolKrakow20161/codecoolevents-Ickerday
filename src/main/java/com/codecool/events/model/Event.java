package com.codecool.events.model;

import java.util.Date;

public class Event extends BaseModel {

  private Category category;
  private Date date;

  public Event(Integer id, String name, String description, Category category,
      Date date) {
    super(id, name, description);
    this.category = category;
    this.date = date;
  }

  public Category getCategory() {
    return category;
  }

  public Date getDate() {
    return date;
  }
}
