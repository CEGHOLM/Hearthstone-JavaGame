package hotstone.variants.etastone;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableGame;
import org.junit.jupiter.api.Test;

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
        verify(game).changeHeroHealth(Player.PEDDERSEN, 1);
    }
}
