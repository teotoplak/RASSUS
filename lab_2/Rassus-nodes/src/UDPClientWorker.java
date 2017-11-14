import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.*;
import java.net.DatagramPacket;


/**
 * Created by teo on 11/14/17.
 */
public class UDPClientWorker implements Runnable {

    private int port;
    private Packet packet;
    private DatagramSocket socket;

    public UDPClientWorker(int port, Packet packet, DatagramSocket socket) {
        this.port = port;
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            String sendString = "Any string...";

            byte[] rcvBuf = new byte[256]; // received bytes

            InetAddress address = null;

            address = InetAddress.getByName("localhost");

            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(packet);
            oo.close();


            byte[] sendBuf = bStream.toByteArray();

            // create a datagram packet for sending data
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length,
                    address, port);

            socket.send(packet);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
