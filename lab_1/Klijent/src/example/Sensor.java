package example;

import rassus.SensorServerService;

import java.util.Random;

/**
 * Created by teo on 10/25/17.
 */
public class Sensor {

    private rassus.SensorServer service;
    private String username;
    private double longitude;
    private double latitude;
    private int port;

    private DataWorker dataWorker;

    public Sensor() {
        startGeneratingData();
        registerSensor();
        startSendingDataToServer();
        startServer();
    }

    private void registerSensor() {

        service = new SensorServerService().getSensorServerPort();

        // username generate
        Random rand = new Random();
        int randomNum = rand.nextInt(1000) + 1;
        username = randomNum + "";

        // longitude generate
        double randomDouble = rand.nextInt(13);
        longitude = 15.87 + (randomNum / 100);

        // latitude generate
        randomNum = rand.nextInt(10);
        latitude = 45.75 + (randomNum / 100);

        // generate port
        randomNum = rand.nextInt(100);
        port = 10002 + randomNum;

        service.register(username, latitude, longitude, "localhost", port);

    }

    private void startGeneratingData() {
        dataWorker = new DataWorker();
    }

    private void startSendingDataToServer() {
        ClientWorker worker = new ClientWorker(service, username, dataWorker);
        Thread thread = new Thread(worker);
        thread.start();
    }

    private void startServer() {
        ServerWorkerPool server = new ServerWorkerPool(port, dataWorker);
        server.startup();
        server.shutdown();
    }

}
