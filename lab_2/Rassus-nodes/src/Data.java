import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by teo on 11/15/17.
 */
public class Data implements Serializable {

    private Integer value;
    private HashMap<Integer, Integer> timeVector;
    private Long scalarTime;

    public Data(Integer value, HashMap<Integer, Integer> timeVector, Long scalarTime) {
        this.value = value;
        this.timeVector = timeVector;
        this.scalarTime = scalarTime;
    }

    public Integer getValue() {
        return value;
    }

    public HashMap<Integer, Integer> getTimeVector() {
        return timeVector;
    }

    public Long getScalarTime() {
        return scalarTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data)) return false;

        Data data = (Data) o;

        if (value != null ? !value.equals(data.value) : data.value != null) return false;
        if (timeVector != null ? !timeVector.equals(data.timeVector) : data.timeVector != null) return false;
        return scalarTime != null ? scalarTime.equals(data.scalarTime) : data.scalarTime == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (timeVector != null ? timeVector.hashCode() : 0);
        result = 31 * result + (scalarTime != null ? scalarTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "value=" + value +
                ", timeVector=" + timeVector +
                ", scalarTime=" + scalarTime +
                '}';
    }
}
