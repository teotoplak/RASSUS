import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by teo on 11/14/17.
 */
public class UDPServerWorker implements Runnable {

    private DataModel dataModel;
    private DatagramSocket socket;
    private UDPClientWorker clientWorker;
    private Integer nodePort;

    public UDPServerWorker(DatagramSocket socket, UDPClientWorker clientWorker, Integer nodePort, DataModel dataModel) {
        this.dataModel = dataModel;
        this.socket = socket;
        this.clientWorker = clientWorker;
        this.nodePort = nodePort;
    }

    @Override
    public void run() {

        try {

            byte[] rcvBuf = new byte[1024]; // received bytes

            while (true) {

                DatagramPacket datagramPacket = new DatagramPacket(rcvBuf, rcvBuf.length);

                // receive packet
                socket.receive(datagramPacket); //RECVFROM

                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(rcvBuf));
                Packet packet = (Packet) iStream.readObject();
                iStream.close();

                // if packet was from this node it was confirmation
                if (packet.getSourcePort() == nodePort) {
                    System.out.println("#### " + packet);
                    clientWorker.confirmPacket(packet);
                } else {
                    System.out.println("<=== " + packet);
                    dataModel.assignNewData(packet.getSourcePort(), packet.getData());

                    // send confirmation
                    clientWorker.sendPacket(packet, packet.getSourcePort());
                }

            }

        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
