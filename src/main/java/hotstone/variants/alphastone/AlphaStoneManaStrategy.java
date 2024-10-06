package hotstone.variants.alphastone;

import hotstone.framework.strategies.ManaProductionStrategy;

public class AlphaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int turnNumber) {
        return 3;
    }
}
