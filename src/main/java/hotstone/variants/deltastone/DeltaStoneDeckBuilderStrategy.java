package hotstone.variants.deltastone;

import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.StandardCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeltaStoneDeckBuilderStrategy implements DeckBuilderStrategy {
    @Override
    public List<MutableCard> buildDeck(Player player) {
        // Define the 9 cards
        List<MutableCard> cards = new ArrayList<>();
        cards.add(new StandardCard("Brown Rice", 1, 1, 2, player, null));
        cards.add(new StandardCard("French Fries", 1, 2, 1, player, null));
        cards.add(new StandardCard("Green Salad", 2, 2, 3, player, null));
        cards.add(new StandardCard("Tomato Salad", 2, 3, 2, player, null));
        cards.add(new StandardCard("Poke Bowl", 3, 2, 4, player, null));
        cards.add(new StandardCard("Pumpkin Soup", 4, 2, 7, player, null));
        cards.add(new StandardCard("Noodle Soup", 4, 5, 3, player, null));
        cards.add(new StandardCard("Spring Rolls", 5, 3, 7, player, null));
        cards.add(new StandardCard("Baked Salmon", 5, 8, 2, player, null));
        cards.add(new StandardCard("Brown Rice", 1, 1, 2, player, null));
        cards.add(new StandardCard("French Fries", 1, 2, 1, player, null));
        cards.add(new StandardCard("Green Salad", 2, 2, 3, player, null));
        cards.add(new StandardCard("Tomato Salad", 2, 3, 2, player, null));
        cards.add(new StandardCard("Poke Bowl", 3, 2, 4, player, null));
        cards.add(new StandardCard("Pumpkin Soup", 4, 2, 7, player, null));
        cards.add(new StandardCard("Noodle Soup", 4, 5, 3, player, null));
        cards.add(new StandardCard("Spring Rolls", 5, 3, 7, player, null));
        cards.add(new StandardCard("Baked Salmon", 5, 8, 2, player, null));

        // Add every card to the deck twice
        List<MutableCard> deck = new ArrayList<>();
        for (MutableCard card : cards) {
            deck.add(new StandardCard(card.getName(), card.getManaCost(), card.getAttack(), card.getHealth(), card.getOwner(), card.getEffect()));
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        return enforceManaConstraints(deck);
    }

    // Make sure the first 3 cards follow the mana cost specifications
    private List<MutableCard> enforceManaConstraints(List<MutableCard> deck) {
        // find and move a 1 mana card to index 0
        moveCardWithManaCost(deck, 1, 0);

        // find and move a 2 mana card or less to index 1
        moveCardWithManaCost(deck, 2, 1);

        // find and move a 4 mana card or less to index 2
        moveCardWithManaCost(deck, 4, 2);

        return deck;
    }

    // A function to help find and move the cards around based in mana cost
    private void moveCardWithManaCost(List<MutableCard> deck, int maxMana, int position) {
        for (int i = position; i < deck.size(); i++) {
            if (deck.get(i).getManaCost() <= maxMana) {
                Collections.swap(deck, i, position);
                break;
            }
        }
    }
}
