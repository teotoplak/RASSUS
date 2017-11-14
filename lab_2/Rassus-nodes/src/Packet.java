import java.io.Serializable;

/**
 * Created by teo on 11/14/17.
 */
public class Packet implements Serializable {

    private int packetNumber;
    private int value;

    public Packet(int packetNumber, int value) {
        this.packetNumber = packetNumber;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetNumber=" + packetNumber +
                ", value=" + value +
                '}';
    }
}
