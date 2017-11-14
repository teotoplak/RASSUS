package example;

import java.util.Date;

/**
 * Created by teo on 10/24/17.
 */
public class Measurement {
    private String username;
    private Date time;
    private String parameter;
    private float averageValue;

    public Measurement(String username, String parameter, float averageValue) {
        this.username = username;
        this.time = new Date();
        this.parameter = parameter;
        this.averageValue = averageValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public float getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(float averageValue) {
        this.averageValue = averageValue;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "username='" + username + '\'' +
                ", time=" + time +
                ", parameter='" + parameter + '\'' +
                ", averageValue=" + averageValue +
                '}';
    }
}
