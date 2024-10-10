package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableGame;

public class ChiliPower implements Effect {
    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Find oppponent and deal 2 damage to their hero
        Player opponent = Player.computeOpponent(player);

        game.changeHeroHealth(opponent, -2);
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent hero";
    }
}
