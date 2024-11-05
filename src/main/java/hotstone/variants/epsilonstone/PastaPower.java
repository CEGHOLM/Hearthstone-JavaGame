package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;

import java.util.List;

public class PastaPower implements Effect {

    private RandomStrategy randomStrategy;

    // Use RandomStrategy
    public PastaPower(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        List<? extends Card> friendlyMinions = (List<? extends Card>) game.getField(player);

        if (!friendlyMinions.isEmpty()) {
            // Use randomStrategy to choose a minion
            int targetIndex = randomStrategy.nextInt(friendlyMinions.size());
            MutableCard target = (MutableCard) friendlyMinions.get(targetIndex);
            game.changeMinionAttack(target, 2);  // Increase attack by 2
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add 2 attack to minion";
    }
}
