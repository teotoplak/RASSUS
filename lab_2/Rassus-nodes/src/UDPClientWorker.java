import java.io.*;
import java.net.*;
import java.net.DatagramPacket;


/**
 * Created by teo on 11/14/17.
 */
public class UDPClientWorker implements Runnable {

    private int port;
    private Serializable sendingObject;
    private DatagramSocket socket;

    public UDPClientWorker(int port, Serializable sendingObject, DatagramSocket socket) {
        this.port = port;
        this.sendingObject = sendingObject;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            InetAddress address = InetAddress.getByName("localhost");

//            DatagramSocket socket = new SimpleSimulatedDatagramSocket(0.2, 200); //SOCKET

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

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
