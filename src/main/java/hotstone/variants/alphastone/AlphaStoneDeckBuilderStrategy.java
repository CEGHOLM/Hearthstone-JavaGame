package hotstone.variants.alphastone;

import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.NullEffect;

import java.util.ArrayList;
import java.util.List;

public class AlphaStoneDeckBuilderStrategy implements DeckBuilderStrategy {
    @Override
    public List<MutableCard> buildDeck(Player player) {
        List<MutableCard> deck = new ArrayList<>();

        deck.add(new StandardCard(GameConstants.TRES_CARD, 3, 3, 3, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.DOS_CARD, 2, 2, 2, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.UNO_CARD, 1, 1, 1, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.CUATRO_CARD, 2, 3, 1, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.CINCO_CARD, 3, 5, 1, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.SEIS_CARD, 2, 1, 3, player, new NullEffect()));
        deck.add(new StandardCard(GameConstants.SIETE_CARD, 3, 2, 4, player, new NullEffect()));
        return deck;
    }
}
