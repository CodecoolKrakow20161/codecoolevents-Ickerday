package com.codecool.events.dao;

import com.codecool.events.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

  Category find(Integer id) throws SQLException;

  List<Category> getAllCategories() throws SQLException;

}
