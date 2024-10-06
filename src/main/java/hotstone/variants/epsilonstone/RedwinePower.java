package hotstone.variants.epsilonstone;


import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.HeroPowerStrategy;

import java.util.List;
import java.util.Random;

public class RedwinePower implements HeroPowerStrategy {

    @Override
    public void usePower(MutableGame game, MutableHero hero) {
        Player opponent = Player.computeOpponent(hero.getOwner());
        List<? extends Card> opponentMinions = (List<? extends Card>) game.getField(opponent);
        if (!opponentMinions.isEmpty()) {
            // Use the hero's random generator to pick a minion
            Random random = hero.getRandomGenerator();
            int targetIndex = random.nextInt(opponentMinions.size());
            MutableCard target = (MutableCard) opponentMinions.get(targetIndex);
            target.takeDamage(2); // Deal 2 damage
        }
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent minion";
    }
}
