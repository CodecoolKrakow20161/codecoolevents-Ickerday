package com.codecool.events.dao.implementation;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.model.Category;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

  @Override
  public Category find(Integer id) {
    Category foundCategory = null;

    try {
      foundCategory = new DbConnector().getCategoryBy(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return foundCategory;
  }

  @Override
  public List<Category> getAllCategories() {
    List<Category> categoryList = null;
    try {
      categoryList = new DbConnector().getCategories();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categoryList;
  }
}
