package hotstone.variants.etastone;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.GameConstants;
import hotstone.spies.SpyMutableGame;
import hotstone.variants.StubRandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static hotstone.utility.TestHelper.verifyCardSpecs;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.mockito.Mockito.*;

public class TestEtaStone {
    private SpyMutableGame game;

    @BeforeEach
    public void setUp() {
        game = new SpyMutableGame();
    }

    @Test
    public void shouldDealOneDamageToOpponentHero() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        BrownRiceEffect brownRiceEffect = new BrownRiceEffect();

        // Apply the effect
        brownRiceEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("changeHeroHealth for: PEDDERSEN with amount: -1"));
    }

    @Test
    public void shouldAddTwoHealthToHero() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        PokeBowlEffect pokeBowlEffect = new PokeBowlEffect();

        // Apply effect
        pokeBowlEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("changeHeroHealth for: FINDUS with amount: 2"));
    }

    @Test
    public void shouldMakePlayerDrawACard() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        NoodleSoupEffect noodleSoupEffect = new NoodleSoupEffect();

        // Apply effect
        noodleSoupEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("drawCard"));
    }

    @Test
    public void shouldAddOneAttackToRandomFriendlyMinion() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always choose the first minion
        TomatoSaladEffect tomatoSaladEffect = new TomatoSaladEffect(randomStub);

        // Create a mock minion and add it to the field
        MutableCard minion = mock(MutableCard.class);
        game.addMinionToField(player, minion);  // Add the mock minion to the game's field

        // Apply the TomatoSaladEffect
        tomatoSaladEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("changeMinionAttack by 1"));
    }

    @Test
    public void shouldDestroyRandomMinionOnOpponentField() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        Player opponent = Player.computeOpponent(player);

        // Use a stub for RandomStrategy to always pick the first minion
        RandomStrategy randomStub = new StubRandomStrategy(0);
        SpringRollsEffect springRollsEffect = new SpringRollsEffect(randomStub);

        // Create two mock minions and add them to the opponent's field
        MutableCard minion1 = mock(MutableCard.class);
        MutableCard minion2 = mock(MutableCard.class);
        game.addMinionToField(opponent, minion1);  // Add the first mock minion to the opponent's field
        game.addMinionToField(opponent, minion2);  // Add the second mock minion

        // Apply the SpringRollsEffect
        springRollsEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("removeMinionFromField from PEDDERSEN field"));  // One minion should be removed
    }

    @Test
    public void shouldAddTwoAttackToRandomEnemyMinion() {
        // Given a SpyMutableGame
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always choose the first minion
        BakedSalmonEffect bakedSalmonEffect = new BakedSalmonEffect(randomStub);

        // Create a mock minion and add it to the field
        MutableCard minion = mock(MutableCard.class);
        game.addMinionToField(Player.PEDDERSEN, minion);  // Add the mock minion to the game's field

        // Apply the TomatoSaladEffect
        bakedSalmonEffect.applyEffect(game, player);

        // Assert that the correct method in game was called
        assertThat(game.getLastCall(), is("changeMinionAttack by 2"));
    }

    @Test
    public void shouldProduceProperEtaDeck() {
        // Given a EtaStone deck
        // When I ask for the deck size and correct card specs
        List<MutableCard> deck = new EtaStoneDeckBuilderStrategy().buildDeck(Player.FINDUS);

        // Then it should have size 18 and the correct specs
        assertThat(deck.size(), is(GameConstants.DELTA_DECK_SIZE));
        verifyCardSpecs(deck, GameConstants.BROWN_RICE_CARD, 1, 1, 1, GameConstants.BROWN_RICE_EFFECT);
        verifyCardSpecs(deck, GameConstants.FRENCH_FRIES_CARD, 1, 2, 1, null);
        verifyCardSpecs(deck, GameConstants.GREEN_SALAD_CARD, 2, 2, 3, null);
        verifyCardSpecs(deck, GameConstants.TOMATO_SALAD_CARD, 2, 2, 2, GameConstants.TOMATO_SALAD_EFFECT);
        verifyCardSpecs(deck, GameConstants.POKE_BOWL_CARD, 3, 2, 3, GameConstants.POKE_BOWL_EFFECT);
        verifyCardSpecs(deck, GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, null);
        verifyCardSpecs(deck, GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, GameConstants.NOODLE_SOUP_EFFECT);
        verifyCardSpecs(deck, GameConstants.SPRING_ROLLS_CARD, 5, 3, 5, GameConstants.SPRING_ROLLS_EFFECT);
        verifyCardSpecs(deck, GameConstants.BAKED_SALMON_CARD, 5, 7, 6, GameConstants.BAKED_SALMON_EFFECT);
    }

    @Test
    public void shouldNotIncreaseAttackIfNoFriendlyMinionsOnField() {
        // Given a game
        // When Findus plays tomatoSalad on a empty field
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Stub for random choice
        TomatoSaladEffect tomatoSaladEffect = new TomatoSaladEffect(randomStub);

        // Apply TomatoSaladEffect with no minions on the field
        tomatoSaladEffect.applyEffect(game, player);

        // Then the only interaction with the game should be getting the field
        verify(game).getField(player);

        // After that no more interaction with game, as no minions are present
        verifyNoMoreInteractions(game);
    }

    @Test
    public void shouldNotDestroyAnyMinionIfNoOpponentMinionsOnField() {
        // Given a game
        // When Findus plays springRolls
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;
        Player opponent = Player.computeOpponent(player);

        // Stub for RandomStrategy
        RandomStrategy randomStub = new StubRandomStrategy(0);
        SpringRollsEffect springRollsEffect = new SpringRollsEffect(randomStub);

        // Apply SpringRollsEffect with no minions on opponent's field
        springRollsEffect.applyEffect(game, player);

        // Then the only interaction with the game should be getting the field
        verify(game).getField(opponent);

        // After that no more interaction with game, as no minions are present
        verifyNoMoreInteractions(game);
    }

    @Test
    public void shouldNotIncreaseAttackIfNoOpponentMinionsOnField() {
        // Given a game
        // When Findus plays bakedSalmon on a empty field
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;
        Player opponent = Player.computeOpponent(player);

        // Stub for RandomStrategy
        RandomStrategy randomStub = new StubRandomStrategy(0);
        BakedSalmonEffect bakedSalmonEffect = new BakedSalmonEffect(randomStub);

        // Apply BakedSalmonEffect with no minions on opponent's field
        bakedSalmonEffect.applyEffect(game, player);

        // Then the only interaction with the game should be getting the field
        verify(game).getField(opponent);

        // After that no more interaction with game, as no minions are present
        verifyNoMoreInteractions(game);
    }

    @Test
    public void shouldNotDrawCardIfNoCardsLeftInDeck() {
        // Given a game
        // When Findus plays noodleSoup with a empty deck
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;

        // Set up the deck to be empty
        when(game.getDeckSize(player)).thenReturn(0);

        // Apply the noodleSoupEffect
        NoodleSoupEffect noodleSoupEffect = new NoodleSoupEffect();
        noodleSoupEffect.applyEffect(game, player);

        // Then the draw card method should not be called
        verify(game, never()).drawCard(player);
    }
}
