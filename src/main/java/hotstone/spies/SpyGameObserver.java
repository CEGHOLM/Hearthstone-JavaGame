package hotstone.spies;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class SpyGameObserver implements GameObserver {

    private List<String> callHistory = new ArrayList<>();
    private String lastCall; // Record last method call
    private Player lastPlayer; // Record last player
    private int lastIndex; // Record last index
    private Card lastAttackingCard; // Record last card or last attacking card
    private Card lastDefendingCard; // Record last defending card

    public SpyGameObserver() {
        this.lastCall = null;
        this.lastPlayer = null;
        this.lastAttackingCard = null;
        this.lastDefendingCard = null;
        this.lastIndex = -1;
    }

    public String getLastCall() {
        return lastCall;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public Card getLastAttackingCard() {
        return lastAttackingCard;
    }

    public Card getLastDefendingCard() {
        return lastDefendingCard;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public List<String> getCallHistory() {
        return callHistory;
    }

    // Overbelastede recordMethodCall-metoder for forskellige observer-opkald
    private void recordMethodCall(String methodCall, Player player, Card card, int index) {
        this.lastCall = methodCall;
        this.lastPlayer = player;
        this.lastAttackingCard = card;
        this.lastIndex = index;
        callHistory.add(methodCall);
    }

    private void recordMethodCall(String methodCall, Player player, Card attackingCard, Card defendingCard) {
        this.lastCall = methodCall;
        this.lastPlayer = player;
        this.lastAttackingCard = attackingCard;
        this.lastDefendingCard = defendingCard;
        callHistory.add(methodCall);
    }
    @Override
    public void onPlayCard(Player who, Card card, int atIndex) {
        recordMethodCall("onPlayCard", who, card, atIndex);
    }

    @Override
    public void onChangeTurnTo(Player playerBecomingActive) {
        recordMethodCall("onChangeTurnTo", playerBecomingActive, lastAttackingCard, lastIndex);
    }

    @Override
    public void onAttackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        recordMethodCall("onAttackCard", playerAttacking, attackingCard, defendingCard);
    }

    @Override
    public void onAttackHero(Player playerAttacking, Card attackingCard) {
        recordMethodCall("onAttackHero", playerAttacking, attackingCard, lastIndex);
    }

    @Override
    public void onUsePower(Player who) {
        recordMethodCall("onUsePower", who, lastAttackingCard, lastIndex);
    }

    @Override
    public void onCardDraw(Player who, Card drawnCard) {
        recordMethodCall("onCardDraw", who, drawnCard, lastIndex);
    }

    @Override
    public void onCardUpdate(Card card) {
        recordMethodCall("onCardUpdate", lastPlayer, card, lastIndex);
    }

    @Override
    public void onCardRemove(Player who, Card card) {
        recordMethodCall("onCardRemove", who, card, lastIndex);
    }

    @Override
    public void onHeroUpdate(Player who) {
        recordMethodCall("onHeroUpdate", who, lastAttackingCard, lastIndex);
    }

    @Override
    public void onGameWon(Player playerWinning) {
        recordMethodCall("onGameWon", playerWinning, lastAttackingCard, lastIndex);
    }
}
