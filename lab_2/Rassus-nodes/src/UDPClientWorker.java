import java.io.*;
import java.net.*;
import java.net.DatagramPacket;
import java.util.*;


/**
 * Created by teo on 11/14/17.
 */
public class UDPClientWorker implements Runnable {

    private static final int SENDING_DELAY = 1000;

    private DataModel dataModel;
    private List<Integer> ports;
    private DatagramSocket socket;
    private EmulatedSystemClock clock;
    private DataWorker dataWorker;
    // this nodes port
    private Integer nodePort;

    private HashMap<Integer, Integer> valuesHistory = new HashMap<>();
    private Integer currentPacketNumber = 1;
    private List<Packet> notConfirmedPackets = new LinkedList<>();


    public UDPClientWorker(List<Integer> ports, DatagramSocket socket, Integer nodePort, DataModel dataModel) {
        this.dataModel = dataModel;
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
                Long scalarTime = clock.currentTimeMillis();
                valuesHistory.put(currentPacketNumber, currentValue);
                HashMap<Integer, Integer> vectorClone = new HashMap<>(dataModel.getTimeVector());
                Data data = new Data(currentValue, vectorClone, scalarTime);
                dataModel.assignNewData(nodePort, data);
                for (Integer port : ports) {
                    notConfirmedPackets.add(new Packet(currentPacketNumber, data, nodePort, port));
                }
                currentPacketNumber++;

                for(Packet packet : notConfirmedPackets) {
                    sendPacket(packet,packet.getDestinationPort());
                }

                Thread.sleep(SENDING_DELAY);
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet, Integer destination) throws IOException {

        InetAddress address = InetAddress.getByName("localhost");

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(packet);
        oo.close();

        byte[] serializedMessage = bStream.toByteArray();

        System.out.println("===> " + packet.toString());

        DatagramPacket datagramPacket = new DatagramPacket(serializedMessage, serializedMessage.length,
                address, destination);

        socket.send(datagramPacket);
    }

    public void confirmPacket(Packet packet) {
        if (notConfirmedPackets.contains(packet)) {
            notConfirmedPackets.remove(packet);
            dataModel.timeVectorIncrease();
            System.out.println("confirmed: " + packet.toString());
        }
    }

}
