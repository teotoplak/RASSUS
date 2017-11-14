package example;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by teo on 10/25/17.
 */
public class DataWorker {

    private final long startTime;

    public DataWorker() {
        startTime = System.currentTimeMillis();
    }

        public SensorData getSensorData() {
        try {
            long secondsUpTime = (System.currentTimeMillis() - startTime) / 1000;
            int lineNumber = (int) (secondsUpTime % 100 ) + 2;
            String line = Files.readAllLines(Paths.get("measures.txt")).get(lineNumber);
            String[] split = line.split(",");
            if (!split[4].isEmpty()) {
                return new SensorData(Integer.parseInt(split[4]), "SO2");
            }
            if (!split[3].isEmpty()) {
                return new SensorData(Integer.parseInt(split[3]), "NO2");
            }
            if (!split[2].isEmpty()) {
                return new SensorData(Integer.parseInt(split[2]), "CO");
            }
        } catch (Exception ignorable) {
            ignorable.printStackTrace();
            return null;
        }
        return null;
    }

}
