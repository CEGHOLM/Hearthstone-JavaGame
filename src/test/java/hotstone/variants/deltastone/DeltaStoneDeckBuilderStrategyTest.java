package hotstone.variants.deltastone;

import hotstone.framework.Card;
import hotstone.framework.DeckBuilderStrategy;
import hotstone.framework.Player;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeltaStoneDeckBuilderStrategyTest {

    @Test
    public void deckShouldHaveEighteenCards() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();
        List<Card> deck = deckBuilder.buildDeck(Player.FINDUS);
        assertThat(deck.size(), is(18));
    }

    @Test
    public void firstCardShouldHaveManaCostOne() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();
        List<Card> deck = deckBuilder.buildDeck(Player.FINDUS);
        Card firstCard = deck.get(0);
        assertThat(firstCard.getManaCost(), is(1));
    }

    @Test
    public void secondCardShouldHaveManaCostTwoOrLess() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();
        List<Card> deck = deckBuilder.buildDeck(Player.FINDUS);
        Card secondCard = deck.get(1);
        assertThat(secondCard.getManaCost(), lessThanOrEqualTo(2));
    }

    @Test
    public void thirdCardShouldHaveManaCostFourOrLess() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();
        List<Card> deck = deckBuilder.buildDeck(Player.FINDUS);
        Card thirdCard = deck.get(2);
        assertThat(thirdCard.getManaCost(), lessThanOrEqualTo(4));
    }

    @Test
    public void deckShouldContainEachCardTwice() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();
        List<Card> deck = deckBuilder.buildDeck(Player.FINDUS);

        Map<String, Integer> cardCountMap = new HashMap<>();
        for (Card card : deck) {
            String cardName = card.getName();
            cardCountMap.put(cardName, cardCountMap.getOrDefault(cardName, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
            assertThat("Card " + entry.getKey() + " should appear twice",
                    entry.getValue(), is(2));
        }
    }

    @Test
    public void deckShouldBeShuffledDifferentlyEachTime() {
        DeckBuilderStrategy deckBuilder = new DeltaStoneDeckBuilderStrategy();

        List<Card> deck1 = deckBuilder.buildDeck(Player.FINDUS);
        List<Card> deck2 = deckBuilder.buildDeck(Player.FINDUS);

        // Compare the decks beyond the first three cards
        List<Card> subDeck1 = deck1.subList(3, deck1.size());
        List<Card> subDeck2 = deck2.subList(3, deck2.size());

        assertThat(subDeck1, is(not(equalTo(subDeck2))));
    }
}