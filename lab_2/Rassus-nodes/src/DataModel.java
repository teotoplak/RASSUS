import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by teo on 11/15/17.
 */
public class DataModel {

    private Set<PortDataPair> dataHistory;
    private HashMap<Integer, Integer> timeVector;
    private Integer nodePort;

    public DataModel(List<Integer> foreignPorts, Integer nodePort) {
        this.nodePort = nodePort;
        // init time vector
        timeVector = new HashMap<>();
        for (Integer port : foreignPorts) {
            timeVector.put(port, 0);
        }
        timeVector.put(nodePort, 0);
        dataHistory = new HashSet<>();

        Thread stateThread = new Thread(new State());
        stateThread.start();
    }

    private void timeVectorAssignNew(HashMap<Integer, Integer> foreignTimeVector) {
        for (Map.Entry<Integer, Integer> entry : foreignTimeVector.entrySet()) {
            if (entry.getKey().equals(nodePort)) {
                continue;
            }
            if (entry.getValue() > timeVector.get(entry.getKey())) {
                timeVector.put(entry.getKey(), entry.getValue());
            }
        }
        timeVectorIncrease();
    }

    public void timeVectorIncrease() {
        timeVector.put(nodePort, timeVector.get(nodePort) + 1);
    }

    public HashMap<Integer, Integer> getTimeVector() {
        return timeVector;
    }

    public void assignNewData(Integer sourcePort, Data data) {
        dataHistory.add(new PortDataPair(sourcePort, data));
        timeVectorAssignNew(data.getTimeVector());
    }

    public void writeDataState() {
        Set<PortDataPair> dataHistory = this.dataHistory;


        // vectors
        List<PortDataPair> list = new ArrayList<>();
        list.addAll(dataHistory);
        Collections.sort(list);

        System.out.println("---------- VECTOR -------------");
        for (PortDataPair portDataPair : list) {
            System.out.println(portDataPair.getPort() + " " + portDataPair.getData().getTimeVector() + " " + portDataPair.getData().getValue());
        }
        System.out.println("-----------------------");


        List<PortScalar> scalarList = new ArrayList<>();
        for (PortDataPair pair : dataHistory) {
            scalarList.add(new PortScalar(pair.getPort(), pair.getData().getScalarTime(), pair.getData().getValue()));
        }
        Collections.sort(scalarList);
        System.out.println("---------- SCALAR -------------");
        for (PortScalar portScalar : scalarList) {
            System.out.println(portScalar.getPort() + " " + portScalar.getTime() + " " + portScalar.getValue());
        }
        System.out.println("-----------------------");

    }

    private void clearHistory() {
        dataHistory = new HashSet<>();
    }

    class State implements Runnable {
        @Override
        public void run() {
            while (true) {
                writeDataState();
                clearHistory();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class PortDataPair implements Comparable<PortDataPair>{
        Integer port;
        Data data;

        public PortDataPair(Integer port, Data data)  {
            this.port = port;
            this.data = data;
        }

        public Integer getPort() {
            return port;
        }

        public Data getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PortDataPair)) return false;

            PortDataPair that = (PortDataPair) o;

            if (!port.equals(that.port)) return false;
            return data.equals(that.data);
        }

        @Override
        public int hashCode() {
            int result = port.hashCode();
            result = 31 * result + data.hashCode();
            return result;
        }

        @Override
        public int compareTo(PortDataPair o) {
            HashMap<Integer, Integer> firstVector = this.data.getTimeVector();
            HashMap<Integer, Integer> secondVector = o.getData().getTimeVector();
            boolean foundIt = false;
            for (Map.Entry<Integer, Integer> entry : firstVector.entrySet()) {
                if(entry.getValue() < secondVector.get(entry.getKey())) {
                    foundIt = true;
                } else if(entry.getValue() <= secondVector.get(entry.getKey())) {
                    // nothing
                } else {
                    return -1;
                }
            }
            return foundIt ? 1 : 0;
        }

        @Override
        public String toString() {
            return "PortDataPair{" +
                    "port=" + port +
                    ", data=" + data +
                    '}';
        }
    }

    class PortScalar implements Comparable<PortScalar> {
        private Integer port;
        private Long time;
        private Integer value;

        public PortScalar(Integer port, Long time, Integer value) {
            this.port = port;
            this.time = time;
            this.value = value;
        }

        @Override
        public int compareTo(PortScalar o) {
            return o.time.compareTo(time);
        }

        public Integer getPort() {
            return port;
        }

        public Long getTime() {
            return time;
        }

        public Integer getValue() {
            return value;
        }
    }


}
