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
    private final MutableGame wrappedGame;
    private String lastCall; // Record the last method that was called
    private Map<Player, List<MutableCard>> fields = new HashMap<>();


    public SpyMutableGame(MutableGame game) {
        this.wrappedGame = game;
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
        wrappedGame.endTurn();
    }

    @Override
    public Status playCard(Player who, MutableCard card, int atIndex) {
        recordMethodCall("playCard");
        return wrappedGame.playCard(who, card, atIndex);
    }

    @Override
    public Status attackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
        recordMethodCall("attackCard");
        return wrappedGame.attackCard(playerAttacking, attackingCard, defendingCard);
    }

    @Override
    public Status attackHero(Player playerAttacking, MutableCard attackingCard) {
        recordMethodCall("attackHero");
        return wrappedGame.attackHero(playerAttacking, attackingCard);
    }

    @Override
    public Status usePower(Player who) {
        recordMethodCall("usePower");
        return wrappedGame.usePower(who);
    }

    @Override
    public void changeHeroHealth(Player player, int i) {
        recordMethodCall("changeHeroHealth for: " + player + " with amount: " + i);
        wrappedGame.changeHeroHealth(player, i);
    }

    @Override
    public void drawCard(Player player) {
        recordMethodCall("drawCard");
        wrappedGame.drawCard(player);
    }

    @Override
    public void reduceCardHealth(MutableCard card, int attack) {
        recordMethodCall("reduceCardHealth");
        wrappedGame.reduceCardHealth(card, attack);
    }

    @Override
    public void removeMinionFromField(Player player, MutableCard card) {
        recordMethodCall("removeMinionFromField from " + player + " field");
        wrappedGame.removeMinionFromField(player, card);
    }

    @Override
    public void changeMinionAttack(MutableCard card, int i) {
        recordMethodCall("changeMinionAttack by " + i);
        wrappedGame.changeMinionAttack(card, i);
    }

    @Override
    public void addCardToField(Player who, MutableCard card) {
        recordMethodCall("addCardToField");
        wrappedGame.addCardToField(who, card);
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
    public MutableCard getCardInField(Player who, int indexInField) {
        return wrappedGame.getCardInField(who, indexInField);
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
