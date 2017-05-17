package com.codecool.events.dao.implementation;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

  public Connection connect() {
    Connection conn = null;
    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager
          .getConnection(
              "jdbc:postgresql://ec2-54-235-90-107.compute-1.amazonaws.com:5432/dbfp05r7n90dv5?sslmode=require",
              "yvzesyzgsmhwgr",
              "e807a80727e12aad11ad5d0bad985ab818248aa5c49368d9b7ae4a57e05fb20e");
    } catch (Exception e) {
      System.out.println("SQL ERROR: Couldn't connect to database!");
    }
    return conn;
  }
}