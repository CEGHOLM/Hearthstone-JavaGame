package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;
import hotstone.adapter.MutableGameAdapter;
import wizardhub.EffectWizard;

public class PokeBowlEffect implements Effect {

    @Override
    public void applyEffect(MutableGame game, Player player) {
        new EffectWizard(new MutableGameAdapter(game))
                .forMe()
                .forHero()
                .doChangeHealthOrRemove(+2);
    }

    @Override
    public String getEffectDescription() {
        return "Restore +2 health to hero";
    }
}

