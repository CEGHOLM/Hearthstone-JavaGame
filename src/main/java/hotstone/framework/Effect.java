package hotstone.framework;

import hotstone.framework.mutability.MutableGame;

/** This interface applies the effects of the different cards to the game,
 * and you can get the effect description
 */
public interface Effect {

    /** Apply the effect of the card in the game
     * to the player
     *
     * @param game, the game being played
     * @param player, the player who "receives" the effect
     */
    void applyEffect(MutableGame game, Player player);

    String getEffectDescription();
}
