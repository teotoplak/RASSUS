import java.io.*;
import java.net.*;
import java.net.DatagramPacket;
import java.util.List;


/**
 * Created by teo on 11/14/17.
 */
public class UDPClientWorker implements Runnable {

    private static final int SENDING_DELAY = 2000;

    private List<Integer> ports;
    private DatagramSocket socket;
    private EmulatedSystemClock clock;
    private DataWorker dataWorker;
    // this nodes port
    private Integer nodePort;

    public UDPClientWorker(List<Integer> ports, DatagramSocket socket, Integer nodePort) {
        this.ports = ports;
        this.socket = socket;
        this.clock = new EmulatedSystemClock();
        this.dataWorker = new DataWorker(clock);
        this.nodePort = nodePort;
    }

    @Override
    public void run() {
        try {

            while (true) {

                Integer currentValue = dataWorker.getSensorData();
                Packet sendingObject = new Packet(currentValue, nodePort);

                for (Integer port : ports) {

                    InetAddress address = InetAddress.getByName("localhost");

                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    ObjectOutput oo = new ObjectOutputStream(bStream);
                    oo.writeObject(sendingObject);
                    oo.close();

                    byte[] serializedMessage = bStream.toByteArray();

                    System.out.println("Client sending: " + sendingObject.toString());

                    // create a datagram packet for sending data
                    DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length,
                            address, port);

                    // send a datagram packet from this socket
                    socket.send(packet); //SENDTO

                }

                Thread.sleep(SENDING_DELAY);
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
