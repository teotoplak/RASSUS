/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Engineering and Computing, University of Zagreb.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class StupidUDPServer {
    
    static final int PORT = 10001; // server port

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        
        byte[] rcvBuf = new byte[1024]; // received bytes
        String rcvStr;

        // create a UDP socket and bind it to the specified port on the local
        // host
        DatagramSocket socket = new SimpleSimulatedDatagramSocket(PORT, 0.2, 200); //SOCKET -> BIND

        while (true) {

            DatagramPacket packet = new DatagramPacket(rcvBuf, rcvBuf.length);

            // receive packet
            socket.receive(packet); //RECVFROM

            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(rcvBuf));
            Packet messageClass = (Packet) iStream.readObject();
            iStream.close();

            System.out.println("josko");
            System.out.println(messageClass.toString());
            System.out.println("bisko");


        }
    }
}
