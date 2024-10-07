package hotstone.variants.epsilonstone;

import hotstone.framework.strategies.RandomStrategy;

public class StubRandomStrategy implements RandomStrategy {
    private int fixedValue;

    public StubRandomStrategy(int fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public int nextInt(int bound) {
        return fixedValue;
    }
}
