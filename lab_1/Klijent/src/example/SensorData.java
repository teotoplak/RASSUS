package example;

/**
 * Created by teo on 10/25/17.
 */
public class SensorData {

    private int value;
    private String type;

    public SensorData(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "value=" + value +
                ", type='" + type + '\'' +
                '}';
    }
}
