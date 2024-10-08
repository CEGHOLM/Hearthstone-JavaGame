package hotstone.variants.alphastone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;

public class AlphaStoneHeroStrategy implements HeroStrategy {
    private final Effect babyPower = new BabyPower();

    @Override
    public MutableHero getHero(Player player) {
        return new StandardHero(0, 21, GameConstants.BABY_HERO_TYPE, player, babyPower) {
        };
    }
}
