package com.codecool.events.dao.implementation;

import com.codecool.events.dao.CategoryDao;
import com.codecool.events.model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

  private final Connection dbConn;

  public CategoryDaoImpl(Connection dbConn) {
    this.dbConn = dbConn;
  }

  @Override
  public Category find(Integer id) throws SQLException {
    Category foundCategory = null;

    try {
      Statement dbStatement = dbConn.createStatement();

      String query = String.format("SELECT * FROM Category WHERE id=%d", id);

      ResultSet dbQuery = dbStatement.executeQuery(query);

      if (dbQuery.next()) {
        foundCategory = new Category(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description")
        );
      }

      dbQuery.close();
      dbStatement.close();
    } catch (Exception e) {
      throw new SQLException("Couldn't found category!");
    }
    return foundCategory;
  }

  @Override
  public List<Category> getAllCategories() throws SQLException {
    List<Category> categoryList = new ArrayList<>();

    try {
      Statement dbStatement = dbConn.createStatement();

      ResultSet dbQuery = dbStatement.executeQuery("SELECT * FROM Category");

      while (dbQuery.next()) {
        categoryList.add(new Category(
            dbQuery.getInt("id"),
            dbQuery.getString("name"),
            dbQuery.getString("description")
        ));
      }

      dbQuery.close();
      dbStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException("SQL ERROR: Couldn't get categories!");
    }
    return categoryList;
  }

}
