import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by teo on 11/13/17.
 */
public class Node {

    private EmulatedSystemClock clock;
    private DatagramSocket socket;
    private StupidUDPClient client;
    private StupidUDPServer server;

    private static final double LOSS_RATE = 0.2;
    private static final int AVG_DELAY = 200;

    public Node() throws SocketException {

        socket = new SimpleSimulatedDatagramSocket(LOSS_RATE, AVG_DELAY);

    }

    public static void main(String[] args) throws SocketException {

        DatagramSocket socket = new SimpleSimulatedDatagramSocket(0.2, 200); //SOCKET
        UDPServerWorker serverWorker = new UDPServerWorker(10001);
        UDPClientWorker clientWorker = new UDPClientWorker(10001, new Packet(10, 9), socket);
        Thread serverThread = new Thread(serverWorker);
        serverThread.start();
        Thread clientThread = new Thread(clientWorker);
        clientThread.start();

    }


}
