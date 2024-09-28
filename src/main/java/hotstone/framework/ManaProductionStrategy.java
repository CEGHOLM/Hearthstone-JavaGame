
package hotstone.framework;

public interface ManaProductionStrategy {
    /**
     * Decides the amount of mana given to a player
     * at the start of a turn, based on the number of turns the player have had
     *
     * @param turnNumber The number of the actual turn.
     * @return The amount of mana given to the player.
     */
    int calculateMana(int turnNumber);
}

