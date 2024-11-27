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
        testRemoveMinionFromField(createNewGameWithTranscript());
        testChangeMinionAttack(createNewGameWithTranscript());
        testNoTranscript(createNewGameWithTranscript());
        testMidwayTranscriptTurnOff(createNewGameWithTranscript());
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
        MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);
    }

    private static void testAttackCard(MutableGame game) {
        System.out.println("Test: Attack card");
        // Get cards
        MutableCard attackingCard = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
        MutableCard defendingCard = (MutableCard) game.getCardInHand(Player.PEDDERSEN, 1);

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
        MutableCard attackingCard = (MutableCard) game.getCardInHand(Player.FINDUS, 0);

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

    private static void testRemoveMinionFromField(MutableGame game) {
        System.out.println("Test: Remove minion from field");

        // Play card
        MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, card, 0);

        // Remove card
        game.removeMinionFromField(Player.FINDUS, card);
    }

    private static void testChangeMinionAttack(MutableGame game) {
        System.out.println("Test: Change minion attack");

        // Play card
        MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);

        // Change minion attack
        game.changeMinionAttack(card, 1);
    }

    private static void testNoTranscript(MutableGame game) {
        System.out.println("Test: No transcription");

        // Turn off transcription
        ((TranscriptionDecorator) game).setTranscribing(false);

        // Call methods that normally would be logged
        game.endTurn();
        MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);
        game.usePower(Player.FINDUS);
    }

    private static void testMidwayTranscriptTurnOff(MutableGame game) {
        System.out.println("Test: Turn off transcription mid game");

        // Call methods that normally would be logged
        game.endTurn();
        MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);

        // Turn off transcription
        ((TranscriptionDecorator) game).setTranscribing(false);
        game.usePower(Player.FINDUS);
    }
}
