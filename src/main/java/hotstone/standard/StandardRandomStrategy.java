package hotstone.standard;

import hotstone.framework.strategies.RandomStrategy;

import java.util.Random;

public class StandardRandomStrategy implements RandomStrategy {
    private final Random random;

    public StandardRandomStrategy() {
        this.random = new Random();
    }

    @Override
    public int nextInt(int bound) {
        return 0;
    }
}

