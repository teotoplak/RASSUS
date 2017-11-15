import java.io.Serializable;

/**
 * Created by teo on 11/14/17.
 */
public class Packet implements Serializable {

    private int packetNumber;
    private Data data;
    private int sourcePort;
    private int destinationPort;

    public Packet(int packetNumber, Data data, int sourcePort, int destinationPort) {
        this.packetNumber = packetNumber;
        this.data = data;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
    }

    @Override
    public String toString() {
        return "{" +
                "packetNum=" + packetNumber +
                ", sourcePort=" + sourcePort +
                ", destinationPort=" + destinationPort +
                '}';
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public Data getData() {
        return data;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Packet)) return false;

        Packet packet = (Packet) o;

        if (packetNumber != packet.packetNumber) return false;
        if (sourcePort != packet.sourcePort) return false;
        if (destinationPort != packet.destinationPort) return false;
        return data != null ? data.equals(packet.data) : packet.data == null;
    }

    @Override
    public int hashCode() {
        int result = packetNumber;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + sourcePort;
        result = 31 * result + destinationPort;
        return result;
    }
}
