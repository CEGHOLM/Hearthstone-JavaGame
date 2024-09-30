package hotstone.variants.deltastone;

import hotstone.framework.ManaProductionStrategy;

public class DeltaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int roundNumber) {
        return 5;
    }
}
