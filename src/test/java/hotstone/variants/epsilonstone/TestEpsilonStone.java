package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonStone {

    private MutableGame game;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new EpsilonStoneFactory());
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
        // Given Findus uses RedwinePower
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always pick the first minion
        Effect redwinePower = new RedwinePower(randomStub);
        StandardHero frenchChef = new StandardHero(3, 21, "Bocuse", Player.FINDUS, redwinePower);

        // When Peddersen plays a minion and Findus uses RedwinePower
        game.endTurn();  // End Findus' turn
        MutableCard card = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, card, 0);
        game.endTurn();  // End Peddersen's turn
        game.usePower(Player.FINDUS);

        // Then Peddersen's card should have 1 health left
        Card cardInField = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(cardInField.getHealth(), is(1));
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
        // Given Peddersen uses PastaPower
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always pick the first minion
        Effect pastaPower = new PastaPower(randomStub);
        StandardHero italianChef = new StandardHero(3, 21, "Bottura", Player.PEDDERSEN, pastaPower);

        // When Peddersen plays a minion and uses PastaPower
        game.endTurn();  // End Findus' turn
        MutableCard card = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, card, 0);
        TestHelper.advanceGameNRounds(game, 1);  // End Peddersen's turn, advance one round
        italianChef.usePower(game);

        // Then the minion's attack should be increased to 5
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
    public void shouldNotUseRedwinePowerWithNoMinionsOnField() {
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
    public void shouldNotUsePastaPowerWithNoMinionsOnField() {
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

    @Test
    public void shouldDamageMinionWithRedwinePowerWithStubAndMultipleMinions() {
        // Given Findus uses RedwinePower with multiple opponent minions
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always pick the first minion
        Effect redwinePower = new RedwinePower(randomStub);
        StandardHero frenchChef = new StandardHero(3, 21, "Bocuse", Player.FINDUS, redwinePower);

        // When Peddersen plays 3 minions, and Findus uses RedwinePower
        game.endTurn();  // End Findus' turn
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        TestHelper.advanceGameNRounds(game, 1);  // Play the remaining cards
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 1);
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 2);
        game.endTurn();  // End Peddersen's turn
        frenchChef.usePower(game);

        // Then the first minion should have taken 2 damage
        Card firstMinion = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(firstMinion.getHealth(), is(1));
    }

    @Test
    public void shouldIncreaseMinionAttackWithPastaPowerWithMultipleMinions() {
        // Given Peddersen uses PastaPower with multiple minions on the field
        RandomStrategy randomStub = new StubRandomStrategy(1);  // Always pick the second minion
        Effect pastaPower = new PastaPower(randomStub);
        StandardHero italianChef = new StandardHero(3, 21, "Bottura", Player.PEDDERSEN, pastaPower);

        // When Peddersen plays 3 minions and uses PastaPower
        game.endTurn();  // End Findus' turn
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        TestHelper.advanceGameNRounds(game, 1);  // Play the remaining cards
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 1);
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 2);
        italianChef.usePower(game);

        // Then the second minion's attack should have increased by 2
        Card secondMinion = game.getCardInField(Player.PEDDERSEN, 1);
        assertThat(secondMinion.getAttack(), is(4));
    }
}
