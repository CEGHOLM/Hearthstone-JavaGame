

package hotstone.variants.etastone;

import hotstone.framework.mutability.MutableGame;
import wizardhub.EffectWizard;

import java.util.Random;

public class TomatoSaladEffect {
    private final MutableGame game;

    public TomatoSaladEffect(MutableGame game) {
        this.game = game;
    }

    public void apply() {
        int randomIndex = new Random().nextInt(game.getMinionCount(0));
        new EffectWizard(new MutableGameAdapter(game))
                .forMe()
                .forMinionAt(randomIndex)
                .doChangeAttack(+1);
    }
}

