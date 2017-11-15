import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by teo on 10/25/17.
 */
public class DataWorker {

    private final long startTime;
    private final EmulatedSystemClock clock;

    public DataWorker(EmulatedSystemClock clock) {
        startTime = clock.currentTimeMillis();
        this.clock = clock;
    }

        public int getSensorData() {
        try {
            long secondsUpTime = (clock.currentTimeMillis() - startTime) / 1000;
            int lineNumber = (int) (secondsUpTime % 100 ) + 2;
            String line = Files.readAllLines(Paths.get("measures.txt")).get(lineNumber);
            String[] split = line.split(",");
            if (!split[2].isEmpty()) {
                // return CO2
                return Integer.parseInt(split[0]);
            } else {
                return -1;
            }
        } catch (Exception ignorable) {
            ignorable.printStackTrace();
            return -1;
        }
    }

}
