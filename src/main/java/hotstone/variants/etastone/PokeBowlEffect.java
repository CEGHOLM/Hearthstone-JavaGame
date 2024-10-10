package hotstone.variants.etastone;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;

public class PokeBowlEffect implements Effect {
    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Add to health to the hero
        game.changeHeroHealth(player, 2);
    }

    @Override
    public String getEffectDescription() {
        return "Restore +2 health to hero";
    }
}
