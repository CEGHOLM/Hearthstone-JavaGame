package hotstone.variants;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.standard.StandardCard;

import java.util.*;

public class DeckBuilderUtil {

    // Method to duplicate and shuffle the deck
    public static List<MutableCard> buildeShuffledDeck(List<MutableCard> cards) {
        List<MutableCard> deck = new ArrayList<>();
        for (MutableCard card : cards) {
            // Duplicate each card to simulate adding them twice
            deck.add(new StandardCard(card.getName(), card.getManaCost(), card.getAttack(), card.getHealth(), card.getOwner(), card.getEffect()));
            deck.add(new StandardCard(card.getName(), card.getManaCost(), card.getAttack(), card.getHealth(), card.getOwner(), card.getEffect()));
        }
        // Shuffle the deck
        Collections.shuffle(deck);
        return deck;
    }

    // Make sure the first 3 cards follow the mana cost specifications
    public static List<MutableCard> enforceManaConstraints(List<MutableCard> deck) {
        // find and move a 1 mana card to index 0
        moveCardWithManaCost(deck, 1, 0);

        // find and move a 2 mana card or less to index 1
        moveCardWithManaCost(deck, 2, 1);

        // find and move a 4 mana card or less to index 2
        moveCardWithManaCost(deck, 4, 2);

        return deck;
    }

    // A function to help find and move the cards around based in mana cost
    public static void moveCardWithManaCost(List<MutableCard> deck, int maxMana, int position) {
        for (int i = position; i < deck.size(); i++) {
            if (deck.get(i).getManaCost() <= maxMana) {
                Collections.swap(deck, i, position);
                break;
            }
        }
    }
}
