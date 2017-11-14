package example;

import rassus.SensorServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Created by teo on 10/25/17.
 */
public class ClientWorker implements Runnable {

    private static final int DELAY = 5000;

    private rassus.SensorServer service;
    private String username;
    private DataWorker dataWorker;

    public ClientWorker(SensorServer service, String username, DataWorker dataWorker) {
        this.service = service;
        this.username = username;
        this.dataWorker = dataWorker;
    }

    @Override
    public void run() {
        Integer neighbourPort = -1;
        try {
            neighbourPort = Integer.parseInt(service.searchNeighbour(username));
        } catch (Exception ignorable) { }

        if(neighbourPort != -1) {
            System.out.println(username + ": [CONNECTING NEIGHBOUR] Neighbour port is: " + neighbourPort);
            try (Socket clientSocket = new Socket("localhost", neighbourPort)) {

                PrintWriter outToNeighbour = new PrintWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream()), true);
                BufferedReader inFromNeighbour = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));

                while(true) {
                    // generate values
                    SensorData sensorData = dataWorker.getSensorData();
                    outToNeighbour.println(username + " asking");
                    String rcvString = inFromNeighbour.readLine();
                    String[] split = rcvString.split(",");
                    SensorData neighbourData = new SensorData(Integer.parseInt(split[1]),split[0]);

                    System.out.println(username + ": <---- " + neighbourData.toString() + " neighbout-port: " + neighbourPort);
                    float value;
                    if(sensorData.getType().equals(neighbourData.getType())) {
                        value = (sensorData.getValue() + neighbourData.getValue()) / 2;
                    } else {
                        value = sensorData.getValue();
                    }

                    service.storeMeasurement(username, sensorData.getType(), value);
                    System.out.println(username + ": ====> " + value);
                    Thread.sleep(DELAY);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }

        } else {
            while (true) {
                SensorData data = dataWorker.getSensorData();
                service.storeMeasurement(username, data.getType(), data.getValue());
                System.out.println(username + ": ====> " + data.toString());
                try {
                    Thread.sleep(DELAY);
                } catch (Exception ex) {}
            }
        }
    }
}
