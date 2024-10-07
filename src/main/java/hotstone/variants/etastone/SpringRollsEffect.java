package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;

import java.util.ArrayList;
import java.util.List;

public class SpringRollsEffect implements Effect {
    private final RandomStrategy randomStrategy;

    public SpringRollsEffect(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Get opponent and opponent's field
        Player opponent = Player.computeOpponent(player);
        Iterable<? extends MutableCard> opponentMinions = (Iterable<? extends MutableCard>) game.getField(opponent);

        // Convert the iterable to a list for easier manipulation
        List<MutableCard> minionsOnField = new ArrayList<>();
        opponentMinions.forEach(minionsOnField::add);

        // If the opponent's field is not empty
        if (!minionsOnField.isEmpty()) {
            // Pick a random minion
            int randomIndex = randomStrategy.nextInt(minionsOnField.size());
            MutableCard minionToRemove = minionsOnField.get(randomIndex);

            // Remove the selected minion from the opponent's field
            game.removeMinionFromField(opponent, minionToRemove);
        }
    }

    @Override
    public String getEffectDescription() {
        return "Destroy a random opponent minion";
    }
}
