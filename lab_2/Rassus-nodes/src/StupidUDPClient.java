/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Eengineering and Computing, University of Zagreb.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class StupidUDPClient {

    static final int PORT = 10001; // server port

    public static void main(String args[]) throws IOException {

        Packet sendString = new Packet(10,9);


        InetAddress address = InetAddress.getByName("localhost");

        DatagramSocket socket = new SimpleSimulatedDatagramSocket(0.2, 200); //SOCKET

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(sendString);
        oo.close();

        byte[] serializedMessage = bStream.toByteArray();

        System.out.print("Client sends: ");

        // create a datagram packet for sending data
        DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length,
                address, PORT);

        // send a datagram packet from this socket
        socket.send(packet); //SENDTO
        System.out.print(new String(serializedMessage));
        System.out.println("");



    }
}
