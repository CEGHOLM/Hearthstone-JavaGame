package hotstone.variants.betastone;

import hotstone.framework.ManaProductionStrategy;

public class BetaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int turnNumber) {
        return Math.min(turnNumber, 7);
    }

    @Override
    public int getStartMana() {
        return 1;
    }
}
