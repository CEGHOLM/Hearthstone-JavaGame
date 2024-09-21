package hotstone.variants.alphastone;

import hotstone.framework.*;
import hotstone.standard.StandardHero;

public class AlphaStoneHeroStrategy implements HeroStrategy {
    private final HeroPowerStrategy babyPower = new BabyPower();
    private final ManaProductionStrategy manaProductionStrategy;

    public AlphaStoneHeroStrategy(ManaProductionStrategy manaProductionStrategy) {
        this.manaProductionStrategy = manaProductionStrategy;
    }

    @Override
    public Hero getHero(Player player) {
        int startMane = manaProductionStrategy.getStartMana();
        return new StandardHero(startMane, 21, "Baby", player, babyPower) {
        };
    }
}
