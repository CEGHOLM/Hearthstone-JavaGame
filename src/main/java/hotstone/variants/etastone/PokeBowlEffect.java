package hotstone.variants.etastone;

import hotstone.framework.mutability.MutableGame;
import wizardhub.EffectWizard;

public class PokeBowlEffect {
    private final MutableGame game;

    public PokeBowlEffect(MutableGame game) {
        this.game = game;
    }

    public void apply() {
        new EffectWizard(new MutableGameAdapter(game))
                .forMe()
                .forHero()
                .doChangeHealthOrRemove(+2);
    }
}

