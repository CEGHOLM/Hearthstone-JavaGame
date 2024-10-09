package hotstone.variants.etastone;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.DeckBuilderStrategy;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.SpyMutableGame;
import hotstone.variants.StubRandomStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class TestEtaStone {

    @Test
    public void shouldDealOneDamageToOpponentHero() {
        // Create a Test Spy for MutableGame
        MutableGame game = mock(MutableGame.class);
        Player attackingPlayer = Player.FINDUS;
        BrownRiceEffect brownRiceEffect = new BrownRiceEffect();

        // Apply the effect
        brownRiceEffect.applyEffect(game, attackingPlayer);

        // Verify that the correct method in MutableGame was called
        verify(game).changeHeroHealth(Player.PEDDERSEN, -1);
    }

    @Test
    public void shouldAddTwoHealthToHero() {
        // Create a Test Spy for MutableGame
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;
        PokeBowlEffect pokeBowlEffect = new PokeBowlEffect();

        // Apply effect
        pokeBowlEffect.applyEffect(game, player);

        // Verify that the correct method in MutableGame was called
        verify(game).changeHeroHealth(Player.FINDUS, 2);
    }

    @Test
    public void shouldMakePlayerDrawACard() {
        // Create a Test Spy for MutableGame
        MutableGame game = mock(MutableGame.class);
        Player player = Player.FINDUS;
        NoodleSoupEffect noodleSoupEffect = new NoodleSoupEffect();

        // Ensure that the deck has cards
        when(game.getDeckSize(player)).thenReturn(1);

        // Apply effect
        noodleSoupEffect.applyEffect(game, player);

        // Verify that the correct method in MutableGame was called
        verify(game).drawCard(player);
    }

    @Test
    public void shouldAddOneAttackToRandomFriendlyMinion() {
        // Create a Spy for MutableGame
        SpyMutableGame game = new SpyMutableGame();
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always choose the first minion
        TomatoSaladEffect tomatoSaladEffect = new TomatoSaladEffect(randomStub);

        // Create a mock minion and add it to the field
        MutableCard minion = mock(MutableCard.class);
        game.addMinionToField(minion);  // Add the mock minion to the game's field

        // Apply the TomatoSaladEffect
        tomatoSaladEffect.applyEffect(game, player);

        // Verify that the first minion had its attack increased by 1
        verify(minion).increaseAttack(1);
    }

    @Test
    public void shouldDestroyRandomMinionOnOpponentField() {
        // Create a Spy for MutableGame
        SpyMutableGame game = new SpyMutableGame();
        Player player = Player.FINDUS;
        Player opponent = Player.computeOpponent(player);

        // Use a stub for RandomStrategy to always pick the first minion
        RandomStrategy randomStub = new StubRandomStrategy(0);
        SpringRollsEffect springRollsEffect = new SpringRollsEffect(randomStub);

        // Create two mock minions and add them to the opponent's field
        MutableCard minion1 = mock(MutableCard.class);
        MutableCard minion2 = mock(MutableCard.class);
        game.addMinionToField(minion1);  // Add the first mock minion to the opponent's field
        game.addMinionToField(minion2);  // Add the second mock minion

        // Apply the SpringRollsEffect
        springRollsEffect.applyEffect(game, player);

        // Verify that the first minion was removed from the opponent's field
        assertThat(game.getFieldSize(opponent), is(1));  // One minion should be removed
    }

    @Test
    public void shouldAddTwoAttackToRandomEnemyMinion() {
        // Create a Spy for MutableGame
        SpyMutableGame game = new SpyMutableGame();
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always choose the first minion
        BakedSalmonEffect bakedSalmonEffect = new BakedSalmonEffect(randomStub);

        // Create a mock minion and add it to the field
        MutableCard minion = mock(MutableCard.class);
        game.addMinionToField(minion);  // Add the mock minion to the game's field

        // Apply the TomatoSaladEffect
        bakedSalmonEffect.applyEffect(game, player);

        // Verify that the first minion had its attack increased by 1
        verify(minion).increaseAttack(2);
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

    // Helper method to verify the card specifications
    public void verifyCardSpecs(List<? extends Card> deck, String cardName, int cost, int attack, int health, String effectDescription) {
        // Given the name of the card, find the first such
        Card theCard = deck.stream().filter(card -> card.getName().equals(cardName)).findFirst().orElse(null);
        // Then the card exists
        assertThat(theCard, is(notNullValue()));
        // Then the card has the correct cost, attack, health and effect description
        assertThat(theCard.getManaCost(), is(cost));
        assertThat(theCard.getAttack(), is(attack));
        assertThat(theCard.getHealth(), is(health));
        assertThat(theCard.getEffectDescription(), is(effectDescription));
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
        SpyMutableGame game = mock(SpyMutableGame.class);
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
