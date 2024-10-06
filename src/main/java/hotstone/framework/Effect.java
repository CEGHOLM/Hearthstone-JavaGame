package hotstone.framework;

import hotstone.framework.mutability.MutableGame;

public interface Effect {
    void applyEffect(MutableGame game, Player player);
}
