package hotstone.variants.epsilonstone;


import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;

import java.util.List;

public class RedwinePower implements Effect {

    private RandomStrategy randomStrategy;

    public RedwinePower(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        Player opponent = Player.computeOpponent(player);

        List<? extends Card> opponentMinions = (List<? extends Card>) game.getField(opponent);

        if (!opponentMinions.isEmpty()) {
            // Brug randomStrategy til at vælge en minion
            int targetIndex = randomStrategy.nextInt(opponentMinions.size());
            MutableCard target = (MutableCard) opponentMinions.get(targetIndex);
            target.takeDamage(2);  // Giv 2 skade
        }
    }

    @Override
    public String getEffectDescription() {
        return "Deal 2 damage to opponent minion";
    }
}
