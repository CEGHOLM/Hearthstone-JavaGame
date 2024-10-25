package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;

import java.util.ArrayList;
import java.util.List;

public class SpyMutableGame implements MutableGame {
    private List<MutableCard> fieldMinions;
    private String lastCall; // Record the last method that was called

    public SpyMutableGame() {
        fieldMinions = new ArrayList<>();
        lastCall = null;
    }

    public String getLastCall() {
        return lastCall;
    }

    public void recordMethodCall(String methodCall) {
        this.lastCall = methodCall;
    }

    public void addMinionToField(MutableCard minion) {
        fieldMinions.add(minion);
    }


    @Override
    public void endTurn() {
        recordMethodCall("endTurn");
    }

    @Override
    public Status playCard(Player who, MutableCard card, int atIndex) {
        recordMethodCall("playCard");
        card.applyEffect(this);
        return Status.OK;
    }

    @Override
    public Status attackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
        recordMethodCall("attackCard");
        return null;
    }

    @Override
    public Status attackHero(Player playerAttacking, MutableCard attackingCard) {
        recordMethodCall("attackHero");
        return null;
    }

    @Override
    public Status usePower(Player who) {
        recordMethodCall("usePower");
        return null;
    }

    @Override
    public void changeHeroHealth(Player player, int i) {
        recordMethodCall("changeHeroHealth for: " + player + " with amount: " + i);
    }

    @Override
    public void drawCard(Player player) {
        recordMethodCall("drawCard");
    }

    @Override
    public void removeMinionFromField(Player player, MutableCard card) {
        recordMethodCall("removeMinionFromField");
        fieldMinions.remove(card);
    }

    @Override
    public Player getPlayerInTurn() {
        return null;
    }

    @Override
    public MutableHero getHero(Player who) {
        return null;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public int getTurnNumber() {
        return 0;
    }

    @Override
    public int getDeckSize(Player who) {
        return 1;
    }

    @Override
    public MutableCard getCardInHand(Player who, int indexInHand) {
        return null;
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return null;
    }

    @Override
    public int getHandSize(Player who) {
        return 0;
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return null;
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return fieldMinions;
    }

    @Override
    public int getFieldSize(Player who) {
        return fieldMinions.size();
    }

    @Override
    public List<? extends Card> getDeck(Player who) {
        return List.of();
    }
}
