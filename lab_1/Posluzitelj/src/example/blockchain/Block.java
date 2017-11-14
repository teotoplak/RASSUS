package example.blockchain;

import example.Measurement;

/**
 * Created by teo on 10/24/17.
 */
public class Block {
    private Measurement measurement;
    private Integer previousBlockHash;

    public Block(String username, String parameter, float averageValue, Integer previousBlockHash) {
        this.measurement = new Measurement(username, parameter, averageValue);
        this.previousBlockHash = previousBlockHash;
    }

    public Integer getPreviousBlockHash() {
        return previousBlockHash;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
