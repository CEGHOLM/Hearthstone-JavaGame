package hotstone.variants.epsilonstone;

import hotstone.framework.Hero;
import hotstone.framework.HeroStrategy;
import hotstone.framework.Player;
import hotstone.standard.StandardHero;

public class EpsilonStoneHeroStrategy implements HeroStrategy {
    @Override
    public Hero getHero(Player player) {
        return new StandardHero(3, 21, "Bocuse", player, null) {
        };
    }
}
