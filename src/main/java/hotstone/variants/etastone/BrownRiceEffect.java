package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;

public class BrownRiceEffect implements Effect {
    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Find the opponent
        Player opponent = Player.computeOpponent(player);

        // Deal two damage to opponent hero
        game.changeHeroHealth(opponent, -1);
    }

    @Override
    public String getEffectDescription() {
        return "Deal 1 damage to opponent hero";
    }
}
