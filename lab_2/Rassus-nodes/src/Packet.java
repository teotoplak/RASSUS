import java.io.Serializable;

/**
 * Created by teo on 11/14/17.
 */
public class Packet implements Serializable {

    private int packetNumber;
    private int value;
    private int sourcePort;
    private int destinationPort;

    public Packet(int packetNumber, int value, int sourcePort, int destinationPort) {
        this.packetNumber = packetNumber;
        this.value = value;
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

    public int getValue() {
        return value;
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
        if (value != packet.value) return false;
        if (sourcePort != packet.sourcePort) return false;
        return destinationPort == packet.destinationPort;
    }

    @Override
    public int hashCode() {
        int result = packetNumber;
        result = 31 * result + value;
        result = 31 * result + sourcePort;
        result = 31 * result + destinationPort;
        return result;
    }
}
