/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Eengineering and Computing, University of Zagreb.
 */
package example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class ServerWorkerPool implements ServerIf {

    private static final int NUMBER_OF_THREADS = 4;
    private static final int MAX_CLIENTS = 10;
    private static int PORT;

    private final AtomicInteger activeConnections;
    private ServerSocket serverSocket;
    private final ExecutorService executor;
    private final AtomicBoolean runningFlag;

    private DataWorker dataWorker;

    public ServerWorkerPool(int port, DataWorker dataWorker) {
        PORT = port;
        this.dataWorker = dataWorker;
        activeConnections = new AtomicInteger(0);
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        runningFlag = new AtomicBoolean(true);
    }

    public static void main(String[] args) {
//        ServerWorkerPool server = new ServerWorkerPool();
//
//        //start all required services and run the mail loop for accepting client requests
//        server.startup();
//
//        //initiate shutdown when startup is finished
//        server.shutdown();
    }

    // Starts all required server services.
    @Override
    public void startup() {
        // create a server socket, bind it to the specified port on the local host
        // and set the max queue length for client requests
        try (ServerSocket serverSocket = new ServerSocket(PORT, MAX_CLIENTS);/*SOCKET->BIND->LISTEN*/) {
            this.serverSocket = serverSocket;

            // set timeout to avoid blocking
            serverSocket.setSoTimeout(500);

            //start the main loop for accepting client requests
            loop();

        } catch (Exception ex) {
            System.err.println("Exception caught when opening or setting the socket: " + ex);
        } finally {
            executor.shutdown();
        } //CLOSE
    }

    // The main loop for accepting client requests.
    @Override
    public void loop() {
        while (runningFlag.get()) {
            try {
                // create a new socket, accept and listen for a connection to be
                // made to this socket
                Socket clientSocket = serverSocket.accept();/*ACCEPT*/
                
                // execute a tcp request handler in a new thread
                Runnable worker = new ServerWorker(clientSocket, runningFlag, activeConnections,dataWorker);
                executor.execute(worker);
                activeConnections.set(activeConnections.get() + 1);
            } catch (SocketTimeoutException ste) {
                // do nothing, check runningFlag flag
            } catch (IOException ex) {
                System.err.println("Exception caught when waiting for a connection: " + ex);
            }
        }
    }

    @Override
    public void shutdown() {
        while (activeConnections.get() > 0) {
            System.out.println("WARNING: There are still active connections");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
        if (activeConnections.get() == 0) {
            System.out.println("Server shutdown.");
        }
    }

    @Override
    public void setRunningFlag(boolean running) {
        this.runningFlag.set(running);
    }

    @Override
    public boolean getRunningFlag() {
        return runningFlag.get();
    }
}