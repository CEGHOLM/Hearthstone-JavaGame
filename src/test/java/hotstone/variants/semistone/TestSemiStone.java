package hotstone.variants.semistone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.HeroStrategy;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.StubRandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

public class TestSemiStone {
    private MutableGame game;

    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new SemiStoneFactory());
    }

    @Test
    public void shouldProduceCorrectManaBasedOnBetaStone() {
        // Given a SemiStone game
        // When I ask for the mana at the start of turn 3
        TestHelper.advanceGameNRounds(game, 2);
        int mana = game.getHero(Player.FINDUS).getMana();

        // Then it should be 3 according to BetaStone rules
        assertThat(mana, is(3));
    }

    @Test
    public void shouldDetermineWinnerCorrectlyBasedOnBetaStone() {
        // Given a game
        // When Peddersen's health reaches 0
        game.getHero(Player.PEDDERSEN).setHealth(0);
        Player winner = game.getWinner();

        // Then the winner should be Findus as per BetaStone rules
        assertThat(winner, is(Player.FINDUS));
    }

    @Test
    public void shouldHaveNoWinnerIfBothHeroesAreAliveAfterSixRounds() {
        // Given a game
        // When six rounds have been played and both heroes are still alive
        TestHelper.advanceGameNRounds(game, 6);
        Player winner = game.getWinner();

        // Then there should be no winner yet
        assertThat(winner, is(not(Player.FINDUS)));
        assertThat(winner, is(not(Player.PEDDERSEN)));
    }

    @Test
    public void shouldResetHeroManaAtStartOfNewRound() {
        // Given a game where Findus uses his hero power
        game.usePower(Player.FINDUS);
        TestHelper.advanceGameNRounds(game, 1);
        int mana = game.getHero(Player.FINDUS).getMana();

        // Then mana should reset to the appropriate value at the start of the new round
        assertThat(mana, is(2));
    }

    @Test
    public void shouldHandleDeckSizeCorrectlyAfterCardDraws() {
        // Given a game
        // When Findus draws cards at the start of each turn
        TestHelper.advanceGameNRounds(game, 3);
        int deckSize = game.getDeckSize(Player.FINDUS);
        // Then the deck size should be reduced appropriately
        assertThat(deckSize, is(12));
    }

}

