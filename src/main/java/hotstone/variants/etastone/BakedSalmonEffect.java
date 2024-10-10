package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;

import java.util.ArrayList;
import java.util.List;

public class BakedSalmonEffect implements Effect {
    private final RandomStrategy randomStrategy;

    public BakedSalmonEffect(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Get opponent and opponent's field
        Player opponent = Player.computeOpponent(player);
        List<? extends MutableCard> minionsOnField = (List<? extends MutableCard>) game.getField(opponent);

        // If the field is not empty
        if (!minionsOnField.isEmpty()) {
            // Pick a random minion
            int randomIndex = randomStrategy.nextInt(minionsOnField.size());
            MutableCard minion = minionsOnField.get(randomIndex);

            // Increase the minions attack by 1
            minion.increaseAttack(2);
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add +2 attack to random opponent minion";
    }
}
