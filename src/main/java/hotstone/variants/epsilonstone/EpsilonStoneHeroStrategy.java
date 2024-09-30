package hotstone.variants.epsilonstone;

import hotstone.framework.Hero;
import hotstone.framework.HeroPowerStrategy;
import hotstone.framework.HeroStrategy;
import hotstone.framework.Player;
import hotstone.standard.StandardHero;

public class EpsilonStoneHeroStrategy implements HeroStrategy {
    private final HeroPowerStrategy redwine = new RedwinePower();
    private final HeroPowerStrategy pasta = new PastaPower();
    @Override
    public Hero getHero(Player player) {
        if (player.equals(Player.FINDUS)) {
            return new StandardHero(3, 21, "Bocuse", player, redwine) {};
        } else {
            return new StandardHero(3, 21, "Bottura", player, pasta) {};
        }
    }
}
