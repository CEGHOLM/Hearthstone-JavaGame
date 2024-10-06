package hotstone.framework.strategies;

import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;

/**
 * HeroPowerStrategy defines the behavior for using a hero's power in the game.
 * Each hero can have a different power, and this interface allows for
 * implementation of different hero powers in various game variants.
 */
public interface HeroPowerStrategy {
    /**
     * Use the hero's power in the game.
     * @param game The current game context.
     * @param hero The hero using the power.
     */
    void usePower(MutableGame game, MutableHero hero);

    /**
     * Get a description of the hero's power effect.
     * @return A short description of the hero's power effect.
     */
    String getEffectDescription();
}

