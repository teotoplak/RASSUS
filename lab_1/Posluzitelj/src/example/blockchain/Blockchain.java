package example.blockchain;

import example.Measurement;

import java.util.*;

/**
 * Created by teo on 10/24/17.
 */
public class Blockchain {

    private List<Block> blockChain;

    public Blockchain() {
        this.blockChain = new LinkedList<>();
    }

    public boolean append(String username, String parameter, float averageValue) {
        if(peekLast() == null) {
            return this.blockChain.add(new Block(username, parameter, averageValue, null));
        }
        return this.blockChain.add(new Block(username, parameter, averageValue, peekLast().hashCode()));
    }

    public Set<Measurement> getState() {
        Set<Measurement> set = new HashSet<>();
        Set<String> readUsernames = new HashSet<>();
        ListIterator<Block> iterator = blockChain.listIterator(blockChain.size());
        while(iterator.hasPrevious()) {
            Block block = iterator.previous();
            if(!readUsernames.contains(block.getMeasurement().getUsername())) {
                set.add(block.getMeasurement());
                readUsernames.add(block.getMeasurement().getUsername());
            }
        }
        return set;
    }

    private Block peekLast() {
        if(this.blockChain.isEmpty()) {
            return null;
        }
        return this.blockChain.get(blockChain.size() - 1);
    }

    private Block getBlock(int i) {
        return this.blockChain.get(i);
    }

}
