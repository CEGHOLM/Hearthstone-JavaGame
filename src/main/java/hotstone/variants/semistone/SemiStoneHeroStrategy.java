package hotstone.variants.semistone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.StandardHero;
import hotstone.variants.gammastone.ChiliPower;
import hotstone.variants.gammastone.SovsPower;
import hotstone.variants.epsilonstone.PastaPower;
import hotstone.variants.epsilonstone.RedwinePower;
import hotstone.standard.StandardRandomStrategy;
import hotstone.standard.GameConstants;


public class SemiStoneHeroStrategy implements HeroStrategy {
    private final RandomStrategy randomStrategy;

    public SemiStoneHeroStrategy(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public MutableHero getHero(Player player) {
        int heroIndex = randomStrategy.nextInt(4);

        switch (heroIndex) {
            case 0:
                return new StandardHero(3, 21, GameConstants.THAI_CHEF_HERO_TYPE, player, new ChiliPower());
            case 1:
                return new StandardHero(3, 21, GameConstants.DANISH_CHEF_HERO_TYPE, player, new SovsPower());
            case 2:
                return new StandardHero(3, 21, GameConstants.FRENCH_CHEF_HERO_TYPE, player, new RedwinePower(new StandardRandomStrategy()));
            case 3:
                return new StandardHero(3, 21, GameConstants.ITALIAN_CHEF_HERO_TYPE, player, new PastaPower(new StandardRandomStrategy()));
            default:
                throw new IllegalStateException("Unexpected hero index: " + heroIndex);
        }
    }
}
