package com.codecool.events.dao;

import com.codecool.events.model.Category;
import java.util.List;

public interface CategoryDao {

  Category find(Integer id);

  List<Category> getAllCategories();

}
