/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Eengineering and Computing, University of Zagreb.
 */
package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerWorker implements Runnable {

    private final Socket clientSocket;
    private final AtomicBoolean isRunning;
    private final AtomicInteger activeConnections;
    private DataWorker dataWorker;

    public ServerWorker(Socket clientSocket, AtomicBoolean isRunning, AtomicInteger activeConnections,
                        DataWorker dataWorker) {
        this.clientSocket = clientSocket;
        this.isRunning = isRunning;
        this.activeConnections = activeConnections;
        this.dataWorker = dataWorker;
    }

    @Override
    public void run() {
        try (
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream()), true)) {

            String receivedString;

            while ((receivedString = inFromClient.readLine()) != null) {
                System.out.println("Server received: " + receivedString);

                if (receivedString.contains("shutdown")) {
                    isRunning.set(false);
                    activeConnections.set(activeConnections.get() - 1);
                    return;
                }

                SensorData data = dataWorker.getSensorData();
                String stringToSend = data.getType() + "," + data.getValue();

                outToClient.println(stringToSend);//WRITE
                System.out.println("Server sent: " + stringToSend);
            }
            activeConnections.set(activeConnections.get() - 1);
        } catch (IOException ex) {
            System.err.println("Exception caught when trying to read or send data: " + ex);
        }
    }
}
