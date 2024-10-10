package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;

public class NoodleSoupEffect implements Effect {
    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Draw one card for the player if there is cards in the deck
        if (game.getDeckSize(player) > 0) {
            game.drawCard(player);
        }
    }

    @Override
    public String getEffectDescription() {
        return "Draw a card";
    }
}
