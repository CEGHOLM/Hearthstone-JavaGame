package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.*;
import org.junit.jupiter.api.*;

import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonStone {

    private Game game;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new AlphaStoneManaStrategy(), new AlphaStoneWinnerStrategy(),
                new EpsilonStoneHeroStrategy(), new AlphaStoneDeckBuilderStrategy());
    }

    @Test
    public void shouldHaveFindusHeroAsFrenchChef() {
        // Given a game
        // When I ask for Findus Hero type
        String heroType = game.getHero(Player.FINDUS).getType();
        // Then it should be "Bocuse"
        assertThat(heroType, is(GameConstants.FRENCH_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldHavePeddersenHeroAsItalianChef() {
        // Given a game
        // When I ask for Peddersens Hero type
        String heroType = game.getHero(Player.PEDDERSEN).getType();
        // Then it should be "Bocuse"
        assertThat(heroType, is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }

    @Test
    public void testRedwinePowerWithStub() {
        // Given a game
        // When Findus uses his hero power on one of Peddersens minions

        // Create a stub to control the random number generator
        RandomStub randomStub = new RandomStub(0); // Force it to pick the first minion

        // Inject stub into the game
        StandardHero frenchChef = (StandardHero) game.getHero(Player.FINDUS);
        frenchChef.setRandomGenerator(randomStub);

        // Peddersens turn and plays a card
        game.endTurn();
        Card card = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, card, 0);

        // Findus turn
        game.endTurn();
        frenchChef.usePower(game);

        // Then Peddersens card should have 1 health left
        Card cardInField = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(cardInField.getHealth(), is(1)); // Assuming the minion started with 3 health
    }

    @Test
    public void shouldHaveCorrectEffectDescriptionForFrenchChef() {
        // Given a game
        // When I ask for Findus heroes effect description
        String findusHero = game.getHero(Player.FINDUS).getEffectDescription();
        // Then it should be "Deal 2 damage to opponent minion"
        assertThat(findusHero, is("Deal 2 damage to opponent minion"));
    }

    @Test
    public void testPastaPowerWithStub() {
        // Given a game where Peddersen is the player with minions
        // When Peddersen uses his power
        StandardHero italianChef = (StandardHero) game.getHero(Player.PEDDERSEN);

        // Peddersen's turn and plays 1 card (minions)
        game.endTurn();
        Card card = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, card, 0);

        // Set up a RandomStub to control randomness (we want the first minion to be selected)
        RandomStub randomStub = new RandomStub(0);
        italianChef.setRandomGenerator(randomStub);

        // End Peddersen's turn and go back to Peddersen's turn to use power
        TestHelper.advanceGameNRounds(game, 1);

        // Use PastaPower to increase the attack of the first minion
        italianChef.usePower(game);

        // Then the minions attack should be 5
        Card minion = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(minion.getAttack(), is(5));
    }

    @Test
    public void shouldHaveCorrectEffectDescriptionForItalianChef() {
        // Given a game
        // When I ask for Peddersens heroes effect description
        String findusHero = game.getHero(Player.PEDDERSEN).getEffectDescription();
        // Then it should be "Add 2 attack to minion"
        assertThat(findusHero, is("Add 2 attack to minion"));
    }

    @Test
    public void testRedwinePowerNoMinionsOnField() {
        // Given Findus hero (FrenchChef) with RedwinePower
        // When Findus uses his power with no minions on the field
        StandardHero frenchChef = (StandardHero) game.getHero(Player.FINDUS);

        // There are no minions on Peddersen's field (Player.PEDDERSEN)

        // Use Redwine power
        frenchChef.usePower(game);

        // Then nothing happens
        assertThat(frenchChef.getMana(), is(3)); // Mana should not have been deducted
        assertThat(frenchChef.canUsePower(), is(true)); // Power status should remain available
    }

    @Test
    public void testPastaPowerNoMinionsOnField() {
        // Given Peddersen hero (ItalianChef) with PastaPower
        // When Peddersen uses his power with no minions in the field
        StandardHero italianChef = (StandardHero) game.getHero(Player.PEDDERSEN);

        // There are no minions on Peddersen's field (Player.PEDDERSEN)

        // Use Pasta power
        italianChef.usePower(game);

        // Then nothing happens
        assertThat(italianChef.getMana(), is(3)); // Mana should not have been deducted
        assertThat(italianChef.canUsePower(), is(true)); // Power status should remain available
    }

    // Inner class for controlling randomness in this test class only
    private class RandomStub extends Random {
        private int nextValue;

        public RandomStub(int nextValue) {
            this.nextValue = nextValue;
        }

        @Override
        public int nextInt(int bound) {
            return nextValue;
        }
    }
}
