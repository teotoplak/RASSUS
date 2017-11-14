package example;

import java.util.Set;

/**
 * Created by teo on 10/24/17.
 */
public class Utilities {

    private static final int EARTH_RADIUS = 6371;

    public static Sensor findClosesSensor(Set<Sensor> sensors, Sensor askedSensor) {
        Sensor closestSensor = null;
        double closestSensorDistance = Double.MAX_VALUE;
        for (Sensor sensor : sensors) {
            if(sensor.getUsername().equals(askedSensor.getUsername())) {
                continue;
            }
            double dlon = sensor.getLongitude() - askedSensor.getLatitude();
            double dlat = sensor.getLatitude() - askedSensor.getLatitude();
            double a = (Math.pow(Math.sin(dlat/2),2)
                    + Math.cos(askedSensor.getLatitude())
                    * Math.cos(sensor.getLatitude()))
                    * Math.pow(Math.sin(dlon/2),2);
            double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
            double distance = EARTH_RADIUS * c;
            if(distance < closestSensorDistance) {
                closestSensor = sensor;
                closestSensorDistance = distance;
            }
        }
        return closestSensor;
    }
}
