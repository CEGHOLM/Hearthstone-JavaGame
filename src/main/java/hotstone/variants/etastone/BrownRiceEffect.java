package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;
import hotstone.adapter.MutableGameAdapter;
import wizardhub.EffectWizard;

public class BrownRiceEffect implements Effect {

    @Override
    public void applyEffect(MutableGame game, Player player) {
        new EffectWizard(new MutableGameAdapter(game))
                .forOpponent()
                .forHero()
                .doChangeHealthOrRemove(-1);
    }

    @Override
    public String getEffectDescription() {
        return "Deal 1 damage to opponent hero";
    }
}


