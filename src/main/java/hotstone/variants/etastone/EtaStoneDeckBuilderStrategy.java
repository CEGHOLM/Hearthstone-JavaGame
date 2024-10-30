package hotstone.variants.etastone;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.standard.*;
import hotstone.variants.DeckBuilderUtil;
import hotstone.variants.NullEffect;

import java.util.ArrayList;
import java.util.List;

public class EtaStoneDeckBuilderStrategy implements DeckBuilderStrategy {

    @Override
    public List<MutableCard> buildDeck(Player player) {
        List<MutableCard> cards = new ArrayList<>();
        cards.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 1, player, new BrownRiceEffect()));
        cards.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 1, 2, 1, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 2, 2, player, new TomatoSaladEffect(new StandardRandomStrategy())));
        cards.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 3, player, new PokeBowlEffect()));
        cards.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player, new NullEffect()));
        cards.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player, new NoodleSoupEffect()));
        cards.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 5, player, new SpringRollsEffect(new StandardRandomStrategy())));
        cards.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 7, 6, player, new BakedSalmonEffect(new StandardRandomStrategy())));

        // Use utility to build, shuffle and enforce mana constraints
        List<MutableCard> deck = DeckBuilderUtil.buildeShuffledDeck(cards);
        return DeckBuilderUtil.enforceManaConstraints(deck);
    }
}
