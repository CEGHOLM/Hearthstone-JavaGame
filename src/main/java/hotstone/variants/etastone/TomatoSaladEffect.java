

package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.adapter.MutableGameAdapter;
import wizardhub.EffectWizard;

import java.util.List;

public class TomatoSaladEffect implements Effect {
    private final RandomStrategy randomStrategy;

    public TomatoSaladEffect(RandomStrategy randomStrategy) {
        this.randomStrategy = randomStrategy;
    }

    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Get the minions of the players field
        List<? extends MutableCard> minionsOnField = (List<? extends MutableCard>) game.getField(player);

        if (!minionsOnField.isEmpty()) {
            int randomIndex = randomStrategy.nextInt(minionsOnField.size());
            new EffectWizard(new MutableGameAdapter(game))
                    .forMe()
                    .forMinionAt(randomIndex)
                    .doChangeAttack(+1);
        }
    }

    @Override
    public String getEffectDescription() {
        return "Add +1 attack to random minion";
    }
}

