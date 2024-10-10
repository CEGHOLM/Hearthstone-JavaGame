package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;

public class GammaStoneHeroStrategy implements HeroStrategy {
    private final Effect chiliPower = new ChiliPower();
    private final Effect sovsPower = new SovsPower();
    @Override
    public MutableHero getHero(Player player) {
        if (player.equals(Player.FINDUS)) {
            return new StandardHero(3, GameConstants.HERO_MAX_HEALTH, "Bunyasaranand", player, chiliPower) {
            };
        } else {
            return new StandardHero(3, GameConstants.HERO_MAX_HEALTH, "Meyer", player, sovsPower) {
            };
        }

    }
}
