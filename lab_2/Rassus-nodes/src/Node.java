import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by teo on 11/13/17.
 */
public class Node {

    private DatagramSocket socket;

    private static final double LOSS_RATE = 0.2;
    private static final int AVG_DELAY = 200;

    public Node(List<Integer> otherPorts, Integer port) throws SocketException {

        DataModel dataModel = new DataModel(otherPorts, port);
        DatagramSocket clientSocket = new SimpleSimulatedDatagramSocket(LOSS_RATE, AVG_DELAY); //SOCKET
        DatagramSocket serverSocket = new SimpleSimulatedDatagramSocket(port, LOSS_RATE, AVG_DELAY);
        UDPClientWorker clientWorker = new UDPClientWorker(otherPorts, clientSocket, port, dataModel);
        UDPServerWorker serverWorker = new UDPServerWorker(serverSocket, clientWorker, port, dataModel);
        Thread serverThread = new Thread(serverWorker);
        serverThread.start();
        Thread clientThread = new Thread(clientWorker);
        clientThread.start();
    }

    public static void main(String[] args) throws IOException {

        int nodesLine = Integer.parseInt(args[0]);
        List<String> lines = Files.readAllLines(Paths.get("nodes.txt"));
        Integer port = -1;
        List<Integer> otherPorts = new LinkedList<>();

        for(int index = 0; index < lines.size(); index++) {
            Integer currentPort = Integer.parseInt(lines.get(index));
            if (index == nodesLine) {
                port = currentPort;
            } else {
                otherPorts.add(currentPort);
            }
        }

        new Node(otherPorts,port);

    }

}
