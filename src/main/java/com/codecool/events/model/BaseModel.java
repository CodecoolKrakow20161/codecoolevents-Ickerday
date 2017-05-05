package com.codecool.events.model;

class BaseModel {

  private Integer id;
  private String name;
  private String description;

  BaseModel() {
  }

  BaseModel(String name) {
  }

  BaseModel(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "BaseModel{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }

  public String getDescription() {
    return description;
  }
}