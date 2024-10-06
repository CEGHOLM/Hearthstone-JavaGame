package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.standard.StandardHero;

public class GammaStoneHeroStrategy implements HeroStrategy {
    private final HeroPowerStrategy chiliPower = new ChiliPower();
    private final HeroPowerStrategy sovsPower = new SovsPower();
    @Override
    public MutableHero getHero(Player player) {
        if (player.equals(Player.FINDUS)) {
            return new StandardHero(3, 21, "Bunyasaranand", player, chiliPower) {
            };
        } else {
            return new StandardHero(3, 21, "Meyer", player, sovsPower) {
            };
        }

    }
}
