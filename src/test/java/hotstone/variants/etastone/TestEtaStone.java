package hotstone.variants.etastone;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.strategies.RandomStrategy;
import hotstone.standard.SpyMutableGame;
import hotstone.variants.epsilonstone.StubRandomStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

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

}
