package hotstone.variants.deltastone;

import hotstone.framework.strategies.ManaProductionStrategy;

public class DeltaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int turnNumber) {
        return 5;
    }
}
