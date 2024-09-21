package hotstone.variants;

import hotstone.framework.*;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.*;
import hotstone.variants.deltastone.DeltaStoneDeckBuilderStrategy;
import hotstone.variants.deltastone.DeltaStoneManaStrategy;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class TestDeltaStone {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new DeltaStoneManaStrategy(), new AlphaStoneWinnerStrategy(),
                new AlphaStoneHeroStrategy(new DeltaStoneManaStrategy()), new DeltaStoneDeckBuilderStrategy());
    }

    @Test
    public void shouldHaveFiveManaAtTheStartOfTheTurn() {
        // Given a game
        // When I ask for Findus mana at the start of the game
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then it should be 5
        assertThat(mana, is(5));
    }

    @Test
    public void shouldHaveFiveManaAtTheStartOfTheSecondTurn() {
        // Given a game
        // When I ask for Findus mana at the start of the game
        TestHelper.advanceGameNRounds(game, 1);
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then it should be 5
        assertThat(mana, is(5));
    }

    @Test
    public void shouldHaveEighteenCardsInDeck() {
        // Given a game
        // When I ask for the deck size of player Findus
        int deckSize = game.getDeckSize(Player.FINDUS);
        // Then it should be 18
        assertThat(deckSize, is(18));
    }

    @Test
    public void shouldHaveManaCostOneForFirstCardInDeck() {
        // Given a game
        // When I ask for the mana cost of the first card in the deck
        // First we have to draw the card
        TestHelper.advanceGameNRounds(game, 1);
        Card card = game.getCardInHand(Player.FINDUS, 0);
        int manaCost = card.getManaCost();
        // Then mana cost should be 1
        assertThat(manaCost, is(1));
    }

    @Test
    public void shouldHaveManaCostTwoOrLessForSecondCardInDeck() {
        // Given a game
        // When I ask for the mana cost of the second card in the deck
        // First we have to draw the card
        TestHelper.advanceGameNRounds(game, 2);
        Card card = game.getCardInHand(Player.FINDUS, 0);
        int manaCost = card.getManaCost();
        // Then mana cost should be 1
        assertThat(manaCost, lessThanOrEqualTo(2));
    }

    @Test
    public void shouldHaveManaCostFourOrLessForThirdCardInDeck() {
        // Given a game
        // When I ask for the mana cost of the third card in the deck
        // First we have to draw the card
        TestHelper.advanceGameNRounds(game, 3);
        Card card = game.getCardInHand(Player.FINDUS, 0);
        int manaCost = card.getManaCost();
        // Then mana cost should be 1
        assertThat(manaCost, lessThanOrEqualTo(4));
    }

    @Test
    public void shouldContainEachCardTwiceInDeck() {
        // Given a game
        // When I check the whole deck for one of the player
        List<Card> deck = game.getDeck(Player.FINDUS);

        // Map to hold the count of each card name
        Map<String, Integer> cardCountMap = new HashMap<>();

        // Count occurrences of each card in deck
        for (Card card : deck) {
            String cardName = card.getName();
            cardCountMap.put(cardName, cardCountMap.getOrDefault(cardName, 0) + 1);
        }

        // Ensure that each card is present exactly twice
        for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
            assertThat(entry.getValue(), is(2));
        }

    }
}
