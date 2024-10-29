package hotstone.transcription;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.gammastone.GammaStoneFactory;

public class TestTranscriptionManually {

    public static void main(String[] args) {
        // Run tests with a fresh game instance for each test
        testEndTurn(createNewGameWithTranscript());
        testPlayCard(createNewGameWithTranscript());
        testAttackCard(createNewGameWithTranscript());
        testAttackHero(createNewGameWithTranscript());
        testUsePower(createNewGameWithTranscript());
        testChangeHeroHealth(createNewGameWithTranscript());
        testDrawCard(createNewGameWithTranscript());
    }

    private static MutableGame createNewGameWithTranscript() {
        TranscriptionDecorator gameWithTranscript = new TranscriptionDecorator(new StandardHotStoneGame(new GammaStoneFactory()));
        gameWithTranscript.setTranscribing(true);  // Turn on transcribing
        return gameWithTranscript;
    }

    private static void testEndTurn(MutableGame game) {
        System.out.println("Test: End turn");
        game.endTurn();
    }

    private static void testPlayCard(MutableGame game) {
        System.out.println("Test: Play card");
        MutableCard card = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);
    }

    private static void testAttackCard(MutableGame game) {
        System.out.println("Test: Attack card");
        // Get cards
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 0);
        MutableCard defendingCard = game.getCardInHand(Player.PEDDERSEN, 1);

        // Play the cards
        game.playCard(Player.FINDUS, attackingCard, 0);
        game.endTurn();
        game.playCard(Player.PEDDERSEN, defendingCard, 0);
        game.endTurn();

        // Attack the card
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);
    }

    private static void testAttackHero(MutableGame game) {
        System.out.println("Test: Attack hero");
        // Get card
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 0);

        // Play the card
        game.playCard(Player.FINDUS, attackingCard, 0);
        TestHelper.advanceGameNRounds(game, 1);

        // Attack the hero
        game.attackHero(Player.FINDUS, attackingCard);
    }

    private static void testUsePower(MutableGame game) {
        System.out.println("Test: Use power");
        game.usePower(Player.FINDUS);
    }

    private static void testChangeHeroHealth(MutableGame game) {
        System.out.println("Test: Change hero health");
        game.changeHeroHealth(Player.FINDUS, 2);
    }

    private static void testDrawCard(MutableGame game) {
        System.out.println("Test: Draw card");
        game.drawCard(Player.FINDUS);
    }

}
