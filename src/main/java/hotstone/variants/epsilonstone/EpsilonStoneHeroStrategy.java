package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.StandardRandomStrategy;

public class EpsilonStoneHeroStrategy implements HeroStrategy {
    private final Effect redwine = new RedwinePower(new StandardRandomStrategy());
    private final Effect pasta = new PastaPower(new StandardRandomStrategy());

    @Override
    public MutableHero getHero(Player player) {
        if (player.equals(Player.FINDUS)) {
            return new StandardHero(3, 21, GameConstants.FRENCH_CHEF_HERO_TYPE, player, redwine) {};
        } else {
            return new StandardHero(3, 21, GameConstants.ITALIAN_CHEF_HERO_TYPE, player, pasta) {};
        }
    }
}
