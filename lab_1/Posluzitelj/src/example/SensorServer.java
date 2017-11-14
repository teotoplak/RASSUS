package example;

import example.blockchain.Blockchain;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by teo on 10/24/17.
 */
@WebService()
public class SensorServer {

    private static Blockchain blockchain;
    private static Set<Sensor> sensors;

    @WebMethod
    public boolean storeMeasurement(String username, String parameter, float averageValue) {
        System.out.println("<==== user: " + username + " value: " + averageValue + " type: " + parameter);
        return blockchain.append(username, parameter, averageValue);
    }

    @WebMethod
    public boolean register(String username, double latitude, double longitude, String IPaddress, int port) {
        Sensor sensor = new Sensor(username, latitude, longitude, IPaddress, port);
        System.out.println("[REGISTERING] " + sensor.toString());
        return sensors.add(sensor);
    }

    @WebMethod
    public String searchNeighbour(String username) {
        Sensor askingSensor = getSensorByUsername(username);
        if(askingSensor == null) {
            return "No existing sensor for that username!";
        }
        Sensor closestSensor = Utilities.findClosesSensor(sensors, askingSensor);
        if(closestSensor == null) {
            return "No closes sensor!";
        }

        System.out.println("====> user: " + username + " assigned neighbour: " + closestSensor.getPort());

         return closestSensor.getPort() + "";
    }

    public static void main(String[] argv) {
        Object implementor = new SensorServer ();
        blockchain = new Blockchain();
        sensors = new HashSet<>();
        String address = "http://localhost:9000/SensorServer";
        Endpoint.publish(address, implementor);

        // write status every 6 sec
        while(true) {
            if(blockchain.getState() != null) {
                System.out.println("[STATE]" + blockchain.getState().toString());
            }
            try { Thread.sleep(6000);} catch (Exception ingorable) { }
        }
    }

    private Sensor getSensorByUsername(String username) {
        for(Sensor sensor : sensors) {
            if(sensor.getUsername().equals(username)) {
                return sensor;
            }
        }
        return null;
    }
}