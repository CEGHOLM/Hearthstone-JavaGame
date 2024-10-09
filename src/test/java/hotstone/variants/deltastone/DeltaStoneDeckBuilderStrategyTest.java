package hotstone.variants.deltastone;

import hotstone.framework.Card;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeltaStoneDeckBuilderStrategyTest {
    private DeckBuilderStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new DeltaStoneDeckBuilderStrategy();
    }

    @Test
    public void deckShouldHaveEighteenCards() {
        // Given a deck
        // When I ask for the size of the deck
        List<MutableCard> deck = strategy.buildDeck(Player.FINDUS);
        // Then it should be 18
        assertThat(deck.size(), is(GameConstants.DELTA_DECK_SIZE));
    }

    @Test
    public void firstCardShouldHaveManaCostOne() {
        // Given a deck
        // When I ask for the mana cost of the first card
        List<MutableCard> deck = strategy.buildDeck(Player.FINDUS);
        Card firstCard = deck.get(0);
        // Then it should be 1
        assertThat(firstCard.getManaCost(), is(1));
    }

    @Test
    public void secondCardShouldHaveManaCostTwoOrLess() {
        // Given a deck
        // When I ask for the mana cost of the second card
        List<MutableCard> deck = strategy.buildDeck(Player.FINDUS);
        Card secondCard = deck.get(1);
        // Then it should be 2 or less
        assertThat(secondCard.getManaCost(), lessThanOrEqualTo(2));
    }

    @Test
    public void thirdCardShouldHaveManaCostFourOrLess() {
        // Given a deck
        // When I ask for the mana cost of the third card
        List<MutableCard> deck = strategy.buildDeck(Player.FINDUS);
        Card thirdCard = deck.get(2);
        // Then it should be 4 or less
        assertThat(thirdCard.getManaCost(), lessThanOrEqualTo(4));
    }

    @Test
    public void deckShouldContainEachCardTwice() {
        // Given a deck
        // When I check if every card appears twice
        List<MutableCard> deck = strategy.buildDeck(Player.FINDUS);

        Map<String, Integer> cardCountMap = new HashMap<>();
        for (Card card : deck) {
            String cardName = card.getName();
            cardCountMap.put(cardName, cardCountMap.getOrDefault(cardName, 0) + 1);
        }
        // Then it should be true
        for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
            assertThat("Card " + entry.getKey() + " should appear twice",
                    entry.getValue(), is(2));
        }
    }

    @Test
    public void deckShouldBeShuffledDifferentlyEachTime() {
        // Given two decks
        // When I check that they have been shuffled differently
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();

        List<MutableCard> deck1 = strategy.buildDeck(Player.FINDUS);
        List<MutableCard> deck2 = deckBuilder.buildDeck(Player.FINDUS);

        // Compare the decks beyond the first three cards
        String card1 = deck1.get(3).getName();
        String card2 = deck2.get(3).getName();
        // Then the two cards at index 3 in the two decks, should not be the same
        assertThat(card1, is(not(equalTo(card2))));
    }
}