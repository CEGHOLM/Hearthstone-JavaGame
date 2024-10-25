package hotstone.variants.deltastone;

import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.DeckBuilderUtil;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.variants.NullEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeltaStoneDeckBuilderStrategy implements DeckBuilderStrategy {
    @Override
    public List<MutableCard> buildDeck(Player player) {
        // Define the 9 cards
        List<MutableCard> cards = new ArrayList<>();
        cards.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 2, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 1, 2, 1, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 3, 2, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 4, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 7, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 8, 2, player, new NullEffect()));

        // Use utility to build, shuffle and enforce mana constraints
        List<MutableCard> deck = DeckBuilderUtil.buildeShuffledDeck(cards);
        return DeckBuilderUtil.enforceManaConstraints(deck);
    }
}
