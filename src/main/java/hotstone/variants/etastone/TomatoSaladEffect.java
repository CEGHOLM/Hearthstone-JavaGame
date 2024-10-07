package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;

import java.util.List;

public class TomatoSaladEffect implements Effect {
    private final RandomStrategy randomStrategy;

    public TomatoSaladEffect(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Get the players minions on the field
        List<? extends MutableCard> minionsOnField = (List<? extends MutableCard>) game.getField(player);

        // If the field is not empty
        if (!minionsOnField.isEmpty()) {
            // Pick a random minion
            int randomIndex = randomStrategy.nextInt(minionsOnField.size());
            MutableCard minion = minionsOnField.get(randomIndex);

            // Increase the minions attack by 1
            minion.increaseAttack(1);
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add +1 attack to random minion";
    }
}
