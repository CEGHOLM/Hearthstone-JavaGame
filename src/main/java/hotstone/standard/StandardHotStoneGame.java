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
import hotstone.standard.StandardHero;

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
public class StandardHotStoneGame implements Game {
  private ManaProductionStrategy manaProductionStrategy;
  private WinningStrategy winningStrategy;
  private HeroStrategy heroStrategy;
  private final DeckBuilderStrategy deckBuilderStrategy;
  private int turnNumber;
  private Map<Player, Hero> heroes = new HashMap<>();
  private Map<Player, List<Card>> hands = new HashMap<>();
  private Map<Player, List<Card>> decks = new HashMap<>();
  private Map<Player, List<Card>> fields = new HashMap<>();
  private Map<Player, Integer> playerTurnCounts = new HashMap<>();

  public StandardHotStoneGame(ManaProductionStrategy manaProductionStrategy, WinningStrategy winningStrategy,
                              HeroStrategy heroStrategy, DeckBuilderStrategy deckBuilderStrategy) {
    // Initialize strategies
    this.manaProductionStrategy = manaProductionStrategy;
    this.winningStrategy = winningStrategy;
    this.heroStrategy = heroStrategy;
    this.deckBuilderStrategy = deckBuilderStrategy;
    // Initialize heroes
    heroes.put(Player.FINDUS, heroStrategy.getHero(Player.FINDUS));
    heroes.put(Player.PEDDERSEN, heroStrategy.getHero(Player.PEDDERSEN));

    // Initialize hands
    hands.put(Player.FINDUS, new ArrayList<>(List.of(
            new StandardCard("Tres", 3, 3, 3, Player.FINDUS),
            new StandardCard("Dos", 2, 2, 2, Player.FINDUS),
            new StandardCard("Uno", 1, 1, 1, Player.FINDUS)
    )));
    hands.put(Player.PEDDERSEN, new ArrayList<>(List.of(
            new StandardCard("Tres", 3, 3, 3, Player.PEDDERSEN),
            new StandardCard("Dos", 2, 2, 2, Player.PEDDERSEN),
            new StandardCard("Uno", 1, 1, 1, Player.PEDDERSEN)
    )));

    // Initialize decks
    decks.put(Player.FINDUS, deckBuilderStrategy.buildDeck(Player.FINDUS));
    decks.put(Player.PEDDERSEN, deckBuilderStrategy.buildDeck(Player.PEDDERSEN));

    // Initialize fields
    fields.put(Player.FINDUS, new ArrayList<>());
    fields.put(Player.PEDDERSEN, new ArrayList<>());

    // Initialize player turn counts
    playerTurnCounts.put(Player.FINDUS, 1);
    playerTurnCounts.put(Player.PEDDERSEN, 1);
  }
  @Override
  public Player getPlayerInTurn() {
    return turnNumber%2 == 0 ? Player.FINDUS : Player.PEDDERSEN;
  }

  @Override
  public Hero getHero(Player who) {
    return heroes.get(who);
  }

  @Override
  public Player getWinner() {
    return winningStrategy.getWinner(this);
  }

  @Override
  public int getTurnNumber() {
    return turnNumber;
  }

  @Override
  public List<Card> getDeck(Player who) {
    return decks.get(who);
  }

  @Override
  public int getDeckSize(Player who) {
    return decks.get(who).size();
  }

  @Override
  public Card getCardInHand(Player who, int indexInHand) {
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
  public Card getCardInField(Player who, int indexInField) {
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
    // Increment turn number for the current player
    Player currentPlayer = getPlayerInTurn();
    int currentPlayerTurnCount = playerTurnCounts.get(currentPlayer) + 1;
    playerTurnCounts.put(currentPlayer, currentPlayerTurnCount);

    // Calculate and set the correct amount of mana based on turnNumber
    int mana = manaProductionStrategy.calculateMana(currentPlayerTurnCount);
    getHero(currentPlayer).setMana(mana);

    // Increment turnsOnField for each card on the current player's field
    List<Card> currentPlayerField = fields.get(getPlayerInTurn());
    for (Card card : currentPlayerField) {
      ((StandardCard) card).incrementTurnsOnField();
    }

    // Set power status for the current player's hero
    getHero(currentPlayer).setPowerStatus(true);

    // Decrease hero health by 2 if deck is empty
    if (decks.get(currentPlayer).isEmpty()) {
      getHero(currentPlayer).takeDamage(2);
    }

    // Increment the turn number to switch to the next player
    turnNumber++;

    // Draw a card for the next player if the turn number is 2 or greater
    Player nextPlayer = getPlayerInTurn();
    if (turnNumber >= 2) {
      List<Card> nextPlayerDeck = decks.get(nextPlayer);
      List<Card> nextPlayerHand = hands.get(nextPlayer);
      if (!nextPlayerDeck.isEmpty()) {
        nextPlayerHand.add(0, nextPlayerDeck.remove(0)); // Add top card to hand and remove from deck
      }
    }
  }

  @Override
  public Status playCard(Player who, Card card, int atIndex) {
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
    int heroMana = getHero(who).getMana();
    int cardManaCost = card.getManaCost();
    getHero(who).setMana(heroMana-cardManaCost);
    hands.get(who).remove(card);
    fields.get(who).add(card);
    return Status.OK;
  }

  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    // Check it's the players turn
    if (!playerAttacking.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    // Check the owner of the attacking card
    if (!playerAttacking.equals(attackingCard.getOwner())) {
      return Status.NOT_OWNER;
    }
    // Check the card is active
    if(!attackingCard.canAttack()) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }
    // Check that you're not attacking your own minion
    if (playerAttacking.equals(defendingCard.getOwner())) {
      return  Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;
    }

    // Apply damage
    defendingCard.takeDamage(attackingCard.getAttack());
    attackingCard.takeDamage(defendingCard.getAttack());

    // Check if defending card's health is 0 or less, and remove it from the field if so
    Player defendingPlayer = Player.computeOpponent(playerAttacking);
    if (defendingCard.getHealth() <= 0) {
      fields.get(defendingPlayer).remove(defendingCard);
    }

    // Check if attacking card's health is 0 or less, and remove it from the field if so
    if (attackingCard.getHealth() <= 0) {
      fields.get(playerAttacking).remove(attackingCard);
    }

    ((StandardCard) attackingCard).attack(); // Mark the card as having attacked

    return Status.OK;
  }

  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    // Check attacking player is player in turn
    if (!playerAttacking.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    // Check that attacking player is owner of attacking card
    if (!playerAttacking.equals(attackingCard.getOwner())) {
      return Status.NOT_OWNER;
    }
    // Check the card is active
    if(!attackingCard.canAttack()) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }

    Hero attackedHero = getHero(Player.computeOpponent(playerAttacking));
    int attack = attackingCard.getAttack();
    attackedHero.takeDamage(attack);

    ((StandardCard) attackingCard).attack(); // Mark the card as having attacked

    return Status.OK;
  }

  @Override
  public Status usePower(Player who) {
    if (!who.equals(getPlayerInTurn())) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    // To get the correct hero for either Findus of Peddersen
    Hero hero = heroes.get(who);

    if (!hero.canUsePower()) {
      return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND;
    } else if (hero.getMana() < 2) {
      return Status.NOT_ENOUGH_MANA;
    }
    // Deduct mana and mark power as used
    hero.setMana(hero.getMana()-2);
    hero.setPowerStatus(false);

    // Call the heroes power and execute it
    hero.usePower(this);

    return Status.OK;
  }
}
