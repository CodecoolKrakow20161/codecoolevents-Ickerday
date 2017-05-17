package com.codecool.events;

import java.sql.SQLException;

class ShutdownHandler {

  ShutdownHandler(CodecoolEventAppServer eventApp) {
    final Thread mainThread = Thread.currentThread();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
          System.out.println("Closing DB connection...");

          try {
            eventApp.getEventApp().closeConnection();
            System.out.println("Connection closed.\nShutting down...");

            mainThread.join();
          } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
          }
        })
    );
  }
}
