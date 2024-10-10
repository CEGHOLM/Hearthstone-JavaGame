package hotstone.variants;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;

public class NullEffect implements Effect {
    @Override
    public void applyEffect(MutableGame game, Player player) {
        // Do nothing - Null Object
    }

    @Override
    public String getEffectDescription() {
        return null;
    }
}
