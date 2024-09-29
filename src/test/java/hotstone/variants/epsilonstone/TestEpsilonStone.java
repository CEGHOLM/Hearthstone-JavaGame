package hotstone.variants.epsilonstone;

import hotstone.framework.*;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.*;
import org.junit.jupiter.api.*;

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
}
