package hotstone.spies;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.observer.GameObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpyMutableGame implements MutableGame {
    private String lastCall; // Record the last method that was called
    private Map<Player, List<MutableCard>> fields = new HashMap<>();


    public SpyMutableGame() {
        lastCall = null;
        // Each player has an empty list as field at the start of the game
        fields.put(Player.FINDUS, new ArrayList<>());
        fields.put(Player.PEDDERSEN, new ArrayList<>());
    }

    public String getLastCall() {
        return lastCall;
    }

    public void recordMethodCall(String methodCall) {
        this.lastCall = methodCall;
    }

    public void addMinionToField(Player who, MutableCard minion) {
        fields.get(who).add(minion);
    }

    @Override
    public void endTurn() {
        recordMethodCall("endTurn");
    }

    @Override
    public Status playCard(Player who, MutableCard card, int atIndex) {
        recordMethodCall("playCard");
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
        recordMethodCall("removeMinionFromField from " + player + " field");
    }

    @Override
    public void changeMinionAttack(MutableCard card, int i) {
        recordMethodCall("changeMinionAttack by " + i);
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
        return fields.get(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return fields.get(who).size();
    }

    @Override
    public List<? extends Card> getDeck(Player who) {
        return List.of();
    }

    @Override
    public void addObserver(GameObserver observer) {

    }
}
