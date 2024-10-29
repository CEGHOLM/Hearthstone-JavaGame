package hotstone.transcription;

import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.gammastone.GammaStoneFactory;

public class TestTranscriptionManually {

    public static void main(String[] args) {
        // Create a transcribed version of the game
        MutableGame gameWithTranscript = new TranscriptionDecorator(new StandardHotStoneGame(new GammaStoneFactory()));

        // Turn on transcription
        ((TranscriptionDecorator) gameWithTranscript).setTranscribing(true);

        // Run tests
        testEndTurn(gameWithTranscript);
        testPlayCard(gameWithTranscript);
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
}
