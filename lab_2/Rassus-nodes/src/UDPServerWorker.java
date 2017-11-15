import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by teo on 11/14/17.
 */
public class UDPServerWorker implements Runnable {

    private DatagramSocket socket;

    public UDPServerWorker(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            byte[] rcvBuf = new byte[1024]; // received bytes

            while (true) {

                DatagramPacket packet = new DatagramPacket(rcvBuf, rcvBuf.length);

                // receive packet
                socket.receive(packet); //RECVFROM

                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(rcvBuf));
                Packet messageClass = (Packet) iStream.readObject();
                iStream.close();

                System.out.println("server: " + messageClass.toString());

            }

        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
