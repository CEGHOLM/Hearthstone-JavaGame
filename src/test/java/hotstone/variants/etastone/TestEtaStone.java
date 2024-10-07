package hotstone.variants.etastone;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.SpyMutableGame;
import hotstone.variants.epsilonstone.StubRandomStrategy;
import org.junit.jupiter.api.Test;
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

        // Apply effect
        noodleSoupEffect.applyEffect(game, player);

        // Verify that the correct method in MutableGame was called
        verify(game).drawCard(player);
    }

    @Test
    public void shouldAddOneAttackToRandomMinion() {
        // Arrange: Create a Spy for MutableGame
        SpyMutableGame game = new SpyMutableGame();
        Player player = Player.FINDUS;
        RandomStrategy randomStub = new StubRandomStrategy(0);  // Always choose the first minion
        TomatoSaladEffect tomatoSaladEffect = new TomatoSaladEffect(randomStub);

        // Create a mock minion and add it to the field
        MutableCard minion = mock(MutableCard.class);
        game.addMinionToField(minion);  // Add the mock minion to the game's field

        // Act: Apply the TomatoSaladEffect
        tomatoSaladEffect.applyEffect(game, player);

        // Assert: Verify that the first minion had its attack increased by 1
        verify(minion).increaseAttack(1);
    }

    @Test
    public void shouldDestroyRandomMinionOnOpponentField() {
        // Arrange: Create a Spy for MutableGame
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

        // Act: Apply the SpringRollsEffect
        springRollsEffect.applyEffect(game, player);

        // Assert: Verify that the first minion was removed from the opponent's field
        assertThat(game.getFieldSize(opponent), is(1));  // One minion should be removed
    }


}
