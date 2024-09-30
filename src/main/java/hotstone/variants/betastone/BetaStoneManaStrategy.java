package hotstone.variants.betastone;

import hotstone.framework.ManaProductionStrategy;

public class BetaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int roundNumber) {
        return Math.min(roundNumber, 7);
    }
}
