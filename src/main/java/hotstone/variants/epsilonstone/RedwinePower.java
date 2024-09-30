package hotstone.variants.epsilonstone;


import hotstone.framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedwinePower implements HeroPowerStrategy {

    @Override
    public void usePower(Game game, Hero hero) {
        Player opponent = Player.computeOpponent(hero.getOwner());
        List<Card> opponentMinions = (List<Card>) game.getField(opponent);
        if (!opponentMinions.isEmpty()) {
            // Use the hero's random generator to pick a minion
            Random random = hero.getRandomGenerator();
            int targetIndex = random.nextInt(opponentMinions.size());
            Card target = opponentMinions.get(targetIndex);
            target.takeDamage(2); // Deal 2 damage
        }
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent minion";
    }
}
