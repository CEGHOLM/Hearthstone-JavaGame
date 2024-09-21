package hotstone.variants.alphastone;

import hotstone.framework.Card;
import hotstone.framework.DeckBuilderStrategy;
import hotstone.framework.Player;
import hotstone.standard.StandardCard;

import java.util.ArrayList;
import java.util.List;

public class AlphaStoneDeckBuilderStrategy implements DeckBuilderStrategy {
    @Override
    public List<Card> buildDeck(Player player) {
        List<Card> deck = new ArrayList<>();

        deck.add(new StandardCard("Cuatro", 2, 3, 1, player));
        deck.add(new StandardCard("Cinco", 3, 5, 1, player));
        deck.add(new StandardCard("Seis", 2, 1, 3, player));
        deck.add(new StandardCard("Siete", 3, 2, 4, player));
        return deck;
    }
}
