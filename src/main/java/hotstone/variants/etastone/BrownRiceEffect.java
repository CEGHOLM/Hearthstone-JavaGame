package hotstone.variants.etastone;

import hotstone.framework.mutability.MutableGame;
import wizardhub.EffectWizard;

public class BrownRiceEffect {
    private final MutableGame game;

    public BrownRiceEffect(MutableGame game) {
        this.game = game;
    }

    public void apply() {
        new EffectWizard(new MutableGameAdapter(game))
                .forOpponent()
                .forHero()
                .doChangeHealthOrRemove(-1);
    }
}


