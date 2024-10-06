package hotstone.variants.zetastone;

import hotstone.framework.*;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;
import hotstone.variants.alphastone.AlphaStoneManaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class TestZetaStone {

    private MutableGame game;

    // Fixture for BetaStone testing
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new ZetaStoneFactory());
    }

    @Test
    public void shouldHavePeddersenAsWinnerIfBothFieldsAreEmptyAfterRoundThree() {
        // Given a game
        // When no one has played a card and 3 rounds have gone by
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();

        // Then winner should be Peddersen
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerIfPeddersensFieldIsEmptyAfterThreeRounds() {
        // Given a game
        // When I ask for the winner after 3 rounds where only Findus has played a card
        MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, cardToPlay, 0);
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();
        // Then the winner should be Findus
        assertThat(winner, is(Player.FINDUS));
    }

    @Test
    public void shouldHavePeddersenAsWinnerIfFindusFieldIsEmptyAfterThreeRounds() {
        // Given a game
        // When I ask for the winner after 3 rounds where only Peddersen has played a card
        game.endTurn();
        MutableCard cardToPlay = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, cardToPlay, 0);
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();
        // Then the winner should be Peddersem
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldWinGameWhenOpponentHealthIsZero() {
        // Given a game
        // when I ask for the winner when Findus health is 0 after round 6

        // Findus plays a card
        MutableCard findusCard = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, findusCard, 0);
        game.endTurn();

        // Peddersen plays a card so neither fields are empty
        MutableCard peddersenCard = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, peddersenCard, 0);

        // Advance the game so the Beta state can be tested
        TestHelper.advanceGameNRounds(game, 6);
        game.getHero(Player.FINDUS).setHealth(0);
        Player winner = game.getWinner();

        // Then the winner should be Peddersen
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerWhenPeddersensHealthIsZero() {
        // Given a game
        // when I ask for the winner when Peddersens health is 0

        // Findus plays a card
        MutableCard findusCard = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, findusCard, 0);
        game.endTurn();

        // Peddersen plays a card so neither fields are empty
        MutableCard peddersenCard = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, peddersenCard, 0);

        // Advance the game so the Beta state can be tested
        TestHelper.advanceGameNRounds(game, 6);

        game.getHero(Player.PEDDERSEN).setHealth(0);
        Player winner = game.getWinner();
        // Then the winner should be Findus
        assertThat(winner, is(Player.FINDUS));
    }
}
