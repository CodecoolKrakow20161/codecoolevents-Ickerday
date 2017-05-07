package com.codecool.events.dao.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbConnector {

  Connection connect() throws SQLException {
    Connection conn;
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/database.db");
    } catch (Exception e) {
      throw new SQLException("SQL ERROR: Couldn't connect to database!");
    }
    return conn;
  }

  void closeConnection(Connection conn) throws SQLException {
    conn.close();
  }

}