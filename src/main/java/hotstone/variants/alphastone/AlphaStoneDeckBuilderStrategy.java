package hotstone.variants.alphastone;

import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.StandardCard;

import java.util.ArrayList;
import java.util.List;

public class AlphaStoneDeckBuilderStrategy implements DeckBuilderStrategy {
    @Override
    public List<MutableCard> buildDeck(Player player) {
        List<MutableCard> deck = new ArrayList<>();

        deck.add(new StandardCard("Tres", 3, 3, 3, player));
        deck.add(new StandardCard("Dos", 2, 2, 2, player));
        deck.add(new StandardCard("Uno", 1, 1, 1, player));
        deck.add(new StandardCard("Cuatro", 2, 3, 1, player));
        deck.add(new StandardCard("Cinco", 3, 5, 1, player));
        deck.add(new StandardCard("Seis", 2, 1, 3, player));
        deck.add(new StandardCard("Siete", 3, 2, 4, player));
        return deck;
    }
}
