package example;

import rassus.SensorServerService;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Created by teo on 10/25/17.
 */
public class SensorClient {

  final static String SERVER_NAME = "localhost"; // server name

  public static void main(String[] argv) {
    try {
      new Thread(() -> {
        new Sensor();
      }).start();
      Thread.sleep(5000);
      new Thread(() -> {
        new Sensor();
      }).start();
      Thread.sleep(5000);
      new Thread(() -> {
        new Sensor();
      }).start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


}
