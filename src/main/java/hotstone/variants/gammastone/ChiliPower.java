package hotstone.variants.gammastone;

import hotstone.framework.*;

public class ChiliPower implements HeroPowerStrategy {
    @Override
    public void usePower(MutableGame game, MutableHero hero) {
        // Find oppponent and deal 2 damage to their hero
        Player opponent = Player.computeOpponent(hero.getOwner());
        MutableHero opponentHero = (MutableHero) game.getHero(opponent);
        opponentHero.takeDamage(2);
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent hero";
    }
}
