package hotstone.variants.alphastone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroPowerStrategy;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.standard.StandardHero;

public class AlphaStoneHeroStrategy implements HeroStrategy {
    private final HeroPowerStrategy babyPower = new BabyPower();

    @Override
    public MutableHero getHero(Player player) {
        return new StandardHero(0, 21, "Baby", player, babyPower) {
        };
    }
}
