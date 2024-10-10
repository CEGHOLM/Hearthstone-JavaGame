package hotstone.variants.betastone;

import hotstone.framework.strategies.ManaProductionStrategy;

public class BetaStoneManaStrategy implements ManaProductionStrategy {
    @Override
    public int calculateMana(int turnNumber) {
        int mana = (turnNumber/2) +1;
        return mana > 7 ? 7 : mana;
    }
}
