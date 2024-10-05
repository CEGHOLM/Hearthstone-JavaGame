package hotstone.variants.deltastone;

import hotstone.framework.*;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaStone {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new DeltaStoneFactory());
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
    public void shouldHaveFiveManaIfFindusHaveUsedSomeMana() {
        // Given a game
        // When I ask for Peddersens mana after Findus has played a card
        Card card = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, card, 0);

        game.endTurn();
        int mana = game.getHero(Player.PEDDERSEN).getMana();
        // The Peddersens mana should still be 5
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
}
