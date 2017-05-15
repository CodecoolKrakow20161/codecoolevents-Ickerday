package com.codecool.events.model;

public class Category extends BaseModel {

  public Category(Integer id, String name, String description) {
    super(id, name, description);
  }

  @Override
  public String toString() {
    return "Category{ id=" + this.getId() +
        "name=" + this.getName() +
        "description=" + this.getDescription() +
        "}";
  }
}
