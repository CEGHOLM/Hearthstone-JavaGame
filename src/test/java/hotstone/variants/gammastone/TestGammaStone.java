package hotstone.variants.gammastone;

import hotstone.framework.*;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class TestGammaStone {
    private Game game;

    // Fixture for BetaStone testing
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new GammaStoneFactory());
    }

    @Test
    public void shouldHavePeddersenAsWinnerIfBothFieldsAreEmptyAfterRoundThree() {
        // Given a game
        // When no one has played a card and 3 rounds have gone by
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();

        // Then winner should be Peddersen
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveFindusAsWinnerIfPeddersensFieldIsEmptyAfterThreeRounds() {
        // Given a game
        // When I ask for the winner after 3 rounds where only Findus has played a card
        Card cardToPlay = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, cardToPlay, 0);
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();
        // Then the winner should be Findus
        assertThat(winner, is(Player.FINDUS));
    }

    @Test
    public void shouldHavePeddersenAsWinnerIfFindusFieldIsEmptyAfterThreeRounds() {
        // Given a game
        // When I ask for the winner after 3 rounds where only Peddersen has played a card
        game.endTurn();
        Card cardToPlay = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, cardToPlay, 0);
        TestHelper.advanceGameNRounds(game, 3);
        Player winner = game.getWinner();
        // Then the winner should be Peddersem
        assertThat(winner, is(Player.PEDDERSEN));
    }

    @Test
    public void shouldHaveThaiChefAsHeroForFindus() {
        // Given a game
        // When I ask for Findus hero
        String heroType = game.getHero(Player.FINDUS).getType();
        // Then type should be ThaiChef
        assertThat(heroType, is(GameConstants.THAI_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldHaveDanishChefAsHeroForPeddersen() {
        // Given a game
        // When I ask for Peddersens hero
        String heroType = game.getHero(Player.PEDDERSEN).getType();
        // Then type should be ThaiChef
        assertThat(heroType, is(GameConstants.DANISH_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldDamagePeddersensHeroTwoHealthWhenUsingHeroPower() {
        // Given a game
        // When Findus uses his hero power
        game.usePower(Player.FINDUS);
        int health = game.getHero(Player.PEDDERSEN).getHealth();
        // Then health should be 19
        assertThat(health, is(19));
    }

    @Test
    public void shouldPlaySovsWhenUsingHeroPower() {
        // Given a game
        // When Peddersen uses his hero power
        game.endTurn();
        game.usePower(Player.PEDDERSEN);
        int fieldSize = game.getFieldSize(Player.PEDDERSEN);
        // Then field size should be 1
        assertThat(fieldSize, is(1));
    }

    @Test
    public void shouldHaveCorrectHeroPowerDescriptions() {
        // Given a game
        // When I ask for the two players hero power description
        String findusHero = game.getHero(Player.FINDUS).getEffectDescription();
        String peddersenHero = game.getHero(Player.PEDDERSEN).getEffectDescription();
        // Then the descriptions should be correct
        assertThat(findusHero, is("Deal 2 damage to opponent hero"));
        assertThat(peddersenHero, is("Summon Sovs card"));
    }

}
