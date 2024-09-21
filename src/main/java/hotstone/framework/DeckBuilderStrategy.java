package hotstone.framework;

import java.util.List;

/**
 * DeckBuilderStrategy defines a strategy for building a deck of cards
 * for a given player. Each game variant can implement its own logic
 * for creating and shuffling the deck.
 */
public interface DeckBuilderStrategy {
    /**
     * Build a deck of cards for the specified player.
     * @param player The player for whom the deck is being built.
     * @return A list of cards representing the player's deck.
     */
    List<Card> buildDeck(Player player);
}

