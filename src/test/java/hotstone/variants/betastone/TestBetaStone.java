package hotstone.variants.betastone;

import hotstone.framework.*;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.AlphaStoneDeckBuilderStrategy;
import hotstone.variants.alphastone.AlphaStoneHeroStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class TestBetaStone {
    private Game game;

    // Fixture for BetaStone testing
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new BetaStoneFactory());
    }

    @Test
    public void shouldHaveOneManaAtFirstTurn() {
        // Given a game
        // When I ask for Findus mana in his first turn
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then it should be 1
        assertThat(mana, is(1));
    }

    @Test
    public void shouldHaveThreeManaInThirdTurn() {
        // Given a game
        // When I ask for Findus mana at the start of his second turn
        TestHelper.advanceGameNRounds(game, 2);
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then it should be 2
        assertThat(mana, is(3));
    }

    @Test
    public void shouldWinGameWhenOpponentHealthIsZero() {
        // Given a game
        // when I ask for the winner when Findus health is 0
        game.endTurn();
        game.getHero(Player.FINDUS).setHealth(0);
        Player winner = game.getWinner();
        // Then the winner should be Peddersen
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerWhenPeddersensHealthIsZero() {
        // Given a game
        // when I ask for the winner when Peddersens health is 0
        TestHelper.advanceGameNRounds(game, 1);
        game.getHero(Player.PEDDERSEN).setHealth(0);
        Player winner = game.getWinner();
        // Then the winner should be Findus
        assertThat(winner, is(Player.FINDUS));
    }

    @Test
    public void shouldHaveNoWinnerAfterFourRounds() {
        // Given a game
        // When both players have positive health and i ask for the winner after 4 rounds
        TestHelper.advanceGameNRounds(game, 4);
        Player winner = game.getWinner();
        // Then winner should be null
        assertThat(winner, is(not(Player.FINDUS)));
        assertThat(winner, is(not(Player.PEDDERSEN)));
    }

    @Test
    public void shouldCapManaAtSeven() {
        // Given a game
        // When I ask for Findus mana in round 10
        TestHelper.advanceGameNRounds(game, 10);
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then it should be 7
        assertThat(mana, is(7));
    }

    @Test
    public void shouldDeductTwoHealthFromPlayerWhenDeckIsDepleted() {
        // Given a game
        // When I ask for Findus health the round after every card has been drawn and neither player has damaged eachother yet
        TestHelper.advanceGameNRounds(game, 5);
        int health = game.getHero(Player.FINDUS).getHealth();
        // Then health should be 19
        assertThat(health, is(19));
    }

}
