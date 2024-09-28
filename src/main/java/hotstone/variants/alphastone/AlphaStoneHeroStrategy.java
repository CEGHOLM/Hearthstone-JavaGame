package hotstone.variants.alphastone;

import hotstone.framework.*;
import hotstone.standard.StandardHero;

public class AlphaStoneHeroStrategy implements HeroStrategy {
    private final HeroPowerStrategy babyPower = new BabyPower();

    @Override
    public Hero getHero(Player player) {
        return new StandardHero(0, 21, "Baby", player, babyPower) {
        };
    }
}
