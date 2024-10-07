package hotstone.variants.etastone;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
