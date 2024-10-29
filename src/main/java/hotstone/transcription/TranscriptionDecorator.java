package hotstone.transcription;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.observer.GameObserver;

import java.util.List;

public class TranscriptionDecorator implements MutableGame {
    private final MutableGame wrappedGame;
    private boolean isTranscriping = true; // Control variable to enable/disable transcription

    public TranscriptionDecorator(MutableGame game) {
        this.wrappedGame = game;
    }

    public void setTranscriping(boolean transcriping) {
        this.isTranscriping = transcriping;
    }

    private void log(String message) {
        if (isTranscriping) {
            System.out.println(message);
        }
    }
    @Override
    public Player getPlayerInTurn() {
        return wrappedGame.getPlayerInTurn();
    }

    @Override
    public MutableHero getHero(Player who) {
        return wrappedGame.getHero(who);
    }

    @Override
    public Player getWinner() {
        return wrappedGame.getWinner();
    }

    @Override
    public int getTurnNumber() {
        return wrappedGame.getTurnNumber();
    }

    @Override
    public int getDeckSize(Player who) {
        return wrappedGame.getDeckSize(who);
    }

    @Override
    public MutableCard getCardInHand(Player who, int indexInHand) {
        return wrappedGame.getCardInHand(who, indexInHand);
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return wrappedGame.getHand(who);
    }

    @Override
    public int getHandSize(Player who) {
        return wrappedGame.getHandSize(who);
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return wrappedGame.getCardInField(who, indexInField);
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return wrappedGame.getField(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return wrappedGame.getFieldSize(who);
    }

    @Override
    public List<? extends Card> getDeck(Player who) {
        return wrappedGame.getDeck(who);
    }

    @Override
    public void endTurn() {
        log(wrappedGame.getPlayerInTurn() + " ended turn");
        wrappedGame.endTurn();
    }

    @Override
    public Status playCard(Player who, MutableCard card, int atIndex) {
        log(who + " played card " + card.getName() + " at index " + atIndex);
        return wrappedGame.playCard(who, card, atIndex);
    }

    @Override
    public Status attackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
        log(playerAttacking + " attacked " + defendingCard.getName() + " with " + attackingCard.getName());
        return wrappedGame.attackCard(playerAttacking, attackingCard, defendingCard);
    }

    @Override
    public Status attackHero(Player playerAttacking, MutableCard attackingCard) {
        log(playerAttacking + " attacked Hero with " + attackingCard.getName());
        return wrappedGame.attackHero(playerAttacking, attackingCard);
    }

    @Override
    public Status usePower(Player who) {
        log(who + " used hero power");
        return wrappedGame.usePower(who);
    }

    @Override
    public void changeHeroHealth(Player player, int i) {
        log(player + " Hero " + i + " health");
        wrappedGame.changeHeroHealth(player, i);
    }

    @Override
    public void drawCard(Player player) {
        log(player + " drawed a card");
        wrappedGame.drawCard(player);
    }

    @Override
    public void removeMinionFromField(Player player, MutableCard card) {
        log(card.getName() + " was eliminated");
        wrappedGame.removeMinionFromField(player, card);
    }

    @Override
    public void changeMinionAttack(MutableCard card, int i) {
        log(card.getName() + " gained " + i + " attack");
        wrappedGame.changeMinionAttack(card, i);
    }

    @Override
    public void addCardToField(Player who, MutableCard card) {
        wrappedGame.addCardToField(who, card);
    }

    @Override
    public void addObserver(GameObserver observer) {
        wrappedGame.addObserver(observer);
    }
}
