/*
 * Copyright (C) 2022-2024. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hotstone.standard;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.framework.strategies.*;
import hotstone.observer.GameObserver;
import hotstone.observer.ObserverHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/** This is the 'temporary test stub' in TDD
 * terms: the initial empty but compilable implementation
 * of the game interface.
 *
 * It already includes a bit of FAKE-IT code for the first
 * test case about hand management and player in turn.
 *
 * Start solving the AlphaStone exercise by
 * following the TDD rhythm: pick a one-step-test
 * from your test list, quickly add a test,
 * run it to see it fail, and then modify this
 * implementing class (and supporting classes)
 * to make your test case run. Refactor and repeat.
 *
 * While this is the implementation of Game for
 * the AlphaStone game, you will constantly
 * refactor it over the course of the exercises
 * to become the 'core implementation' which will
 * enable a lot of game variants. This is also
 * why it is not called 'AlphaGame'.
 */
public class StandardHotStoneGame implements Game, MutableGame {
  private ManaProductionStrategy manaProductionStrategy;
  private WinningStrategy winningStrategy;
  private HeroStrategy heroStrategy;
  private final DeckBuilderStrategy deckBuilderStrategy;
  private int turnNumber;
  private Map<Player, MutableHero> heroes = new HashMap<>();
  private Map<Player, List<MutableCard>> hands = new HashMap<>();
  private Map<Player, List<MutableCard>> decks = new HashMap<>();
  private Map<Player, List<MutableCard>> fields = new HashMap<>();
  private ObserverHandler observerHandler = new ObserverHandler();

  public StandardHotStoneGame(HotstoneFactory factory) {
    // Initialize strategies
    this.manaProductionStrategy = factory.createManaProductionStrategy();
    this.winningStrategy = factory.createWinningStrategy();
    this.heroStrategy = factory.createHeroStrategy();
    this.deckBuilderStrategy = factory.createDeckBuilderStrategy();

    // Initialize round and turn number
    this.turnNumber = 0;

    // Initialize heroes
    heroes.put(Player.FINDUS, heroStrategy.getHero(Player.FINDUS));
    heroes.put(Player.PEDDERSEN, heroStrategy.getHero(Player.PEDDERSEN));

    // Set initial mana for the players
    assignManaToPlayer(getPlayerInTurn());
    assignManaToPlayer(Player.computeOpponent(getPlayerInTurn()));

    // Initialize decks
    decks.put(Player.FINDUS, deckBuilderStrategy.buildDeck(Player.FINDUS));
    decks.put(Player.PEDDERSEN, deckBuilderStrategy.buildDeck(Player.PEDDERSEN));

    // Initialize hands
    for (Player player : Player.values()) {
      List<MutableCard> deck = decks.get(player);
      List<MutableCard> hand = new ArrayList<>();
      for (int i = 0; i < 3 && !deck.isEmpty(); i++) {
        hand.add(deck.remove(0)); // Remove the first card from the deck and add it to the hand
      }
      hands.put(player, hand);
    }

    // Initialize fields
    fields.put(Player.FINDUS, new ArrayList<>());
    fields.put(Player.PEDDERSEN, new ArrayList<>());
  }

  private void assignManaToPlayer(Player player) {
    int mana = manaProductionStrategy.calculateMana(turnNumber);
    heroes.get(player).setMana(mana);
    observerHandler.notifyHeroUpdate(player);
  }

  @Override
  public Player getPlayerInTurn() {
    return turnNumber%2 == 0 ? Player.FINDUS : Player.PEDDERSEN;
  }

  @Override
  public MutableHero getHero(Player who) {
    return heroes.get(who);
  }

  @Override
  public Player getWinner() {
    Player winner = winningStrategy.getWinner(this);
    if (winner != null) {
      observerHandler.notifyGameWon(winner);
    }
    return winner;
  }

  @Override
  public int getTurnNumber() {
    return turnNumber;
  }

  @Override
  public List<? extends Card> getDeck(Player who) {
    return decks.get(who);
  }

  @Override
  public int getDeckSize(Player who) {
    return decks.get(who).size();
  }

  @Override
  public MutableCard getCardInHand(Player who, int indexInHand) {
    return hands.get(who).get(indexInHand);
  }

  @Override
  public Iterable<? extends Card> getHand(Player who) {
    return hands.get(who);
  }

  @Override
  public int getHandSize(Player who) {
    return hands.get(who).size();
  }

  @Override
  public MutableCard getCardInField(Player who, int indexInField) {
    return fields.get(who).get(indexInField);
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
  public void endTurn() {
    Player currentPlayer = getPlayerInTurn();

    // End-of-turn processing for current player
    handleEndOfTurnEffects(currentPlayer);

    // Switch to the next player
    turnNumber++;

    Player nextPlayer = getPlayerInTurn();

    // Start-of-turn processing for next player
    assignManaToPlayer(nextPlayer);
    drawCard(nextPlayer);

    // Notify the observer that the turn has changed
    observerHandler.notifyChangeTurnTo(nextPlayer);
  }

  private void handleEndOfTurnEffects(Player player) {
    // Increment turns on field for each card
    List<MutableCard> playerField = fields.get(player);
    for (Card card : playerField) {
      ((StandardCard) card).incrementTurnsOnField();
    }

    // Reset hero power status
    heroes.get(player).setPowerStatus(true);

    // Damage hero if deck is empty
    if (decks.get(player).isEmpty()) {
      changeHeroHealth(player, -GameConstants.HERO_HEALTH_PENALTY_ON_EMPTY_DECK);
    }
  }

  @Override
  public void changeHeroHealth(Player who, int amount) {
    getHero(who).takeDamage(amount);
    observerHandler.notifyHeroUpdate(who);
  }

  @Override
  public void drawCard(Player who){
    if (turnNumber >= 2) { // Ensure players don't draw on the first turn
      List<MutableCard> deck = decks.get(who);
      List<MutableCard> hand = hands.get(who);
      if (!deck.isEmpty()) {
        hand.add(0, deck.remove(0));
        observerHandler.notifyCardDraw(who, hand.get(0));
      }
    }
  }

  @Override
  public Status playCard(Player who, MutableCard card, int atIndex) {
    // Check that the attack is possible
    Status status = isPlayPossible(who, card);
    if (status != Status.OK) return status;

    // Change the mana of the hero based in mana cost
    int heroMana = getHero(who).getMana();
    int cardManaCost = card.getManaCost();

    changeHeroMana(heroes.get(who), heroMana - cardManaCost);

    card.applyEffect(this);

    // Move card from hand to field
    addCardToField(who, card);

    // Notify the observer that a card has been played
    observerHandler.notifyPlayCard(who, card, atIndex);

    return Status.OK;
  }

  private void changeHeroMana(MutableHero heroes, int amount) {
    heroes.setMana(amount);
    observerHandler.notifyHeroUpdate(heroes.getOwner());
  }

  private Status isPlayPossible(Player who, MutableCard card) {
    // Check it's the players turn
    if (!who.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    // Check it's the players card
    if (!who.equals(card.getOwner())) {
      return Status.NOT_OWNER;
    }
    // Check the player has enough mana
    if (getHero(who).getMana() < card.getManaCost()) {
      return Status.NOT_ENOUGH_MANA;
    }
    return Status.OK;
  }

  @Override
  public Status attackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
    // Check if the attack is allowed
    Status status = isAttackPossible(playerAttacking, attackingCard, defendingCard);
    if (status != Status.OK) return status;

    // Execute attack
    executeAttack(attackingCard, defendingCard);

    // Notify the observer of the attack on a card
    observerHandler.notifyAttackCard(playerAttacking, attackingCard, defendingCard);

    // Return status OK if attack is ok
    return Status.OK;
  }

  private void executeAttack(MutableCard attackingCard, MutableCard defendingCard) {
    // Apply damage
    if (defendingCard.getHealth() > 0) {
      reduceCardHealth(defendingCard, attackingCard.getAttack());
    }

    if (attackingCard.getHealth() > 0) {
      reduceCardHealth(attackingCard, defendingCard.getAttack());
    }

    // Remove defeated cards
    removeIfDefeated(defendingCard);
    removeIfDefeated(attackingCard);

    // Mark the card as having attacked
    deactivateCard(attackingCard);
  }

  @Override
  public void reduceCardHealth(MutableCard card, int attack) {
    if (attack > 0) {
      card.takeDamage(attack);
      observerHandler.notifyCardUpdate(card);
    }
    removeIfDefeated(card);
  }

  private void deactivateCard(MutableCard attackingCard) {
    attackingCard.attack();
    observerHandler.notifyCardUpdate(attackingCard);
  }

  private void removeIfDefeated(MutableCard card) {
    if (card.getHealth() <= 0) {
      removeMinionFromField(card.getOwner(), card);
    }
  }

  @Override
  public void removeMinionFromField(Player who, MutableCard card) {
    fields.get(who).remove(card);
    observerHandler.notifyCardRemove(who, card);
  }

  @Override
  public void changeMinionAttack(MutableCard card, int i) {
    card.changeAttack(i);
    observerHandler.notifyCardUpdate(card);
  }

  private Status isAttackPossible(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
    // Check it's the players turn
    boolean isAttackingPlayersTurn = getPlayerInTurn() == playerAttacking;
    if (!isAttackingPlayersTurn) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    // Check the owner of the attacking card
    boolean isOwningAttackingCard = attackingCard.getOwner() == playerAttacking;
    if (!isOwningAttackingCard) {
      return Status.NOT_OWNER;
    }
    // Check the card is active
    boolean cardCanAttack = attackingCard.canAttack();
    if(!cardCanAttack) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }
    // Check that you're not attacking your own minion
    boolean isAttackingOwnMinion = defendingCard.getOwner() == playerAttacking;
    if (isAttackingOwnMinion) {
      return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;
    }
    return Status.OK;
  }

  @Override
  public Status attackHero(Player playerAttacking, MutableCard attackingCard) {
    // Check if the attack is allowed
    Status status = isHeroAttackPossible(playerAttacking, attackingCard);
    if (status != Status.OK) {
      return status;
    }

    // Apply damage to the opponent's hero
    changeHeroHealth(Player.computeOpponent(playerAttacking), -attackingCard.getAttack());

    // Mark the card as having attacked
    deactivateCard(attackingCard);

    // Notify the observer of the attack on a hero and the card change
    observerHandler.notifyAttackHero(playerAttacking, attackingCard);

    return Status.OK;
  }

  //Check if the attack can be made
  private Status isHeroAttackPossible(Player playerAttacking, MutableCard attackingCard) {
    if (!playerAttacking.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    if (!playerAttacking.equals(attackingCard.getOwner())) {
      return Status.NOT_OWNER;
    }
    if (!attackingCard.canAttack()) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }
    return Status.OK;
  }

  @Override
  public Status usePower(Player who) {
    // To get the correct hero for either Findus of Peddersen
    MutableHero hero = heroes.get(who);

    // Check if it is possible to use power
    if (!who.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    if (!hero.canUsePower()) {
      return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND;
    }
    if (hero.getMana() < 2) {
      return Status.NOT_ENOUGH_MANA;
    }

    // Call the heroes power and execute it
    hero.usePower(this);

    // Deduct mana and mark power as used
    changeHeroMana(hero, hero.getMana()-GameConstants.HERO_POWER_COST);
    hero.setPowerStatus(false);

    // Notify observer about hero power usage
    observerHandler.notifyUsePower(who);

    return Status.OK;
  }

  @Override
  public void addObserver(GameObserver observer) {
    observerHandler.addObserver(observer);
  }

  /** Method to help make some unit test easier to test
   *
   * @param player The player whose field we want to add a card to
   * @param card The card we add to the field
   */
  @Override
  public void addCardToField(Player player, MutableCard card) {
    hands.get(player).remove(card);
    fields.get(player).add(card);
  }
}
