package com.codecool.events.model;

import java.util.Date;

public class Event extends BaseModel {

  private Category category;
  private Date date;
  private String link;

  public Event(String name, String description, Category category, Date date) {
    super(name, description);
    this.category = category;
    this.date = date;
  }

  public Event(Integer id, String name, String description, Category category, Date date, String link) {
    super(id, name, description);
    this.category = category;
    this.date = date;
    this.link = link;
  }

  public String getLink() {
    return link;
  }

  public Category getCategory() {
    return category;
  }

  public Date getDate() {
    return date;
  }
}
