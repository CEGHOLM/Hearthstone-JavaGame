package hotstone.variants.gammastone;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.HeroPowerStrategy;
import hotstone.framework.Player;

public class ChiliPower implements HeroPowerStrategy {
    @Override
    public void usePower(Game game, Hero hero) {
        // Find oppponent and deal 2 damage to their hero
        Player opponent = Player.computeOpponent(hero.getOwner());
        Hero opponentHero = game.getHero(opponent);
        opponentHero.takeDamage(2);
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent hero";
    }
}
