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

/**
 * Skeleton class for AlphaStone test cases
 *
 *    This source code is from the book
 *      "Flexible, Reliable Software:
 *        Using Patterns and Agile Development"
 *      2nd Edition
 *    Author:
 *      Henrik Bærbak Christensen
 *      Department of Computer Science
 *      Aarhus University
 */

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static hotstone.utility.TestHelper.verifyCardSpecs;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestAlphaStone {
  private MutableGame game;

  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new AlphaStoneFactory());
  }

  // Example of an early, simple test case:
  // Turn handling

  @Test
  public void shouldHaveFindusAsFirstPlayer() {
    //Given a game
    //When I ask for the player in turn
    Player player = game.getPlayerInTurn();
    // Should be Findus
    assertThat(player, is(Player.FINDUS));
  }

  @Test
  public void shouldNotHaveFindusAsWinnerInFirstRound() {
    //Given a game
    // When I ask for the winner after one round
    TestHelper.advanceGameNRounds(game, 1);
    Player gameWinner = game.getWinner();
    // Then it should not be Findus
    assertThat(gameWinner, is(not(Player.FINDUS)));
  }

  @Test
  public void shouldHaveFirstTurnAsTurnZero() {
    // Given a game
    // When I ask for the turn number
    int turnNumber = game.getTurnNumber();
    // Then it should be turn 1
    assertThat(turnNumber, is(0));
  }

  @Test
  public void shouldHaveSecondTurnAsTurnOne() {
    // Given a game
    // When I ask for the turn number after Findus has ended his turn
    game.endTurn();
    int turnNumber = game.getTurnNumber();
    // Then it should be turn 1
    assertThat(turnNumber, is(1));
  }

  @Test
  public void shuouldHaveFindusAsWinnerAfterFourRounds() {
      // Given a game
      // When I ask for the winner after 4 rounds, then it should be Findus
      TestHelper.advanceGameNRounds(game, 4);
      Player gameWinner = game.getWinner();
      // Then it should be Findus
      assertThat(gameWinner, is(Player.FINDUS));
  }

  @Test
  public void findusShouldHaveDeckSizeAsFourInFirstRound() {
    // Given a Game
    // When I ask for the deck size of Findus in round 1
    int deckSize = game.getDeckSize(Player.FINDUS);
    // Then it should be 4
    assertThat(deckSize, is(4));
  }

  @Test
  public void shouldHaveThreeCardsInDeckAfterFirstCardIsDrawn() {
    // Given a game
    // When i ask for Findus deck size after the first two rounds
    game.endTurn();
    game.endTurn();
    int deckSize = game.getDeckSize(Player.FINDUS);
    // Then it should be 3
    assertThat(deckSize, is(3));
  }

  @Test
  public void peddersenShouldHaveDeckSizeAsFourInTurnTwo() {
    // Given a Game
    // When I ask for the deck size of Peddersen in turn 2
    TestHelper.advanceGameNRounds(game, 1);
    int deckSize = game.getDeckSize(Player.PEDDERSEN);
    // Then it should be 4
    assertThat(deckSize, is(4));
  }

  @Test
  public void shouldHaveDeckSizeTwoAfterTwoRounds() {
    //Given a Game
    // When I ask for the deck size of Findus after two rounds
    TestHelper.advanceGameNRounds(game, 2);
    int deckSize = game.getDeckSize(Player.FINDUS);
    // Then it should be two
    assertThat(deckSize, is(2));
  }

  @Test
  public void fieldShouldBeEmptyWhenGameStarts() {
    // Given a game
    // At the start of the game, when I ask for the field size on both sides
    int fieldSize = game.getFieldSize(Player.FINDUS) + game.getFieldSize(Player.PEDDERSEN);
    // Then it should be 0
    assertThat(fieldSize, is(0));
  }

  @Test
  public void shouldHaveFourCardsInHandIfNoCardsBeenPlayedInFirstRound() {
    // Given a game
    // If Findus haven't played a card in his first turn, when i ask for his hand size in his second turn
    TestHelper.advanceGameNRounds(game, 1);
    int handSize = game.getHandSize(Player.FINDUS);
    // Then it should be 4
    assertThat(handSize, is(4));
  }

  @Test
  public void shouldHaveThreeCardsInHandAfterFirstRound() {
    // Given a game
    // When I ask for Peddersens hand size after round one
    TestHelper.advanceGameNRounds(game, 1);
    int handSize = game.getHandSize(Player.PEDDERSEN);
    // Then it should be 3
    assertThat(handSize, is(3));
  }

  @Test
  public void manaShouldBeThree() {
    // Given a game
    // When i ask for Baby's mana at the start of a round
    int mana = game.getHero(Player.FINDUS).getMana();
    // It should be 3
    assertThat(mana, is(3));
  }

  @Test
  public void shouldHaveTwentyOneHealth() {
    // Given a game
    // When i ask for Baby's health at the start of a round
    int health = game.getHero(Player.FINDUS).getHealth();
    // It should be 3
    assertThat(health, is(21));
  }

  @Test
  public void shouldHaveBabyAsType() {
    // Given a game
    // When I ask for the hero type
    String heroType = game.getHero(Player.FINDUS).getType();
    // Should be "Baby"
    assertThat(heroType, is("Baby"));
  }

  @Test
  public void shouldRemoveTwoManaWhenUsingPower() {
    // Given a game
    // When I try to use power, and ask for hero mana
    game.usePower(Player.FINDUS);
    int mana = game.getHero(Player.FINDUS).getMana();
    // Should be 1
    assertThat(mana, is(1));
  }

 @Test
 public void shouldNotBeAbleToUsePowerIfAlreadyUsed() {
   // Given a Game
   // When I try to use the Hero's power twice in the same round
   game.usePower(Player.FINDUS);
   Status ableToUsePower = game.usePower(Player.FINDUS);
   // Should not be able to, and return error message "Can't use power twice pr. round"
   assertThat(ableToUsePower, is(Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND));
 }

 @Test
 public void shouldNotBeAbleToUseHeroPowerIfLessThanTwoMana() {
   // Given a game
   // When I try to use my hero power with less than two mana
   game.getHero(Player.FINDUS).setMana(1);
   Status ableToUsePower = game.usePower(Player.FINDUS);
   // Should not be able to, and return error message "Not enough mana"
   assertThat(ableToUsePower, is(Status.NOT_ENOUGH_MANA));
 }

 @Test
 public void shouldHaveFindusAsOwner() {
   // Given a game
   // When I ask for the owner of Findus' hero
   Player ownerOfHero = game.getHero(Player.FINDUS).getOwner();
   // Should be Findus
   assertThat(ownerOfHero, is(Player.FINDUS));
 }

 @Test
 public void shouldHavePeddersenAsOwner() {
   // Given a game
   // When I ask for the owner of Peddersens hero
   Player ownerOfHero = game.getHero(Player.PEDDERSEN).getOwner();
   // Should be Peddersen
   assertThat(ownerOfHero, is(Player.PEDDERSEN));
 }

  @Test
  public void shouldRemoveTwoManaWhenUsingPowerWithPeddersenHero() {
    // Given a game
    // When I try to use hero power with Peddersen, and ask for hero mana
    game.endTurn();
    game.usePower(Player.PEDDERSEN);
    int mana = game.getHero(Player.PEDDERSEN).getMana();
    // Should be 1
    assertThat(mana, is(1));
  }

  @Test
  public void shouldHaveCuteAsEffectDescription() {
    // Given a game
    // When I ask for either players hero' effect description
    String findusDescription = game.getHero(Player.FINDUS).getEffectDescription();
    String peddersenDescription = game.getHero(Player.PEDDERSEN).getEffectDescription();
    // Should both be "Cute"
    assertThat(findusDescription, is("Just Cute"));
    assertThat(peddersenDescription, is("Just Cute"));
  }

  @Test
  public void shouldResetHeroManaToThreeAtStartOfTurnFindus() {
    // Given a game
    // When I ask for Findus' Hero's amount of mana at the start of a new round, after having used hero power the round before
    game.usePower(Player.FINDUS);
    TestHelper.advanceGameNRounds(game, 1);
    int mana = game.getHero(Player.FINDUS).getMana();
    // Should be three
    assertThat(mana, is(3));
  }

  @Test
  public void shouldResetHeroManaToThreeAtStartOfTurnPeddersen() {
    // Given a game
    // When I ask for Findus' Hero's amount of mana at the start of a new round, after having used hero power the round before
    game.usePower(Player.PEDDERSEN);
    TestHelper.advanceGameNRounds(game, 1);
    int mana = game.getHero(Player.PEDDERSEN).getMana();
    // Should be three
    assertThat(mana, is(3));
  }

  @Test
  public void shouldHaveCardTresAtIndexZero() {
    // Given a game, Findus has 3 cards in hand
    int count = game.getHandSize(Player.FINDUS);
    assertThat(count, is(3));
    // When I pick card 0
    Card card = game.getCardInHand(Player.FINDUS, 0);
    // Then is it Tres
    assertThat(card.getName(), is(GameConstants.TRES_CARD));
  }

  @Test
  public void shouldHaveUnoDosTresCardsInitiallyInFindusHand() {
    // Given a game, Findus has 3 cards in hand
    int count = game.getHandSize(Player.FINDUS);
    assertThat(count, is(3));
    // And these are ordered Tres, Dos, Uno in slot 0,1,2
    // When I pick card 0
    Card tres = game.getCardInHand(Player.FINDUS, 0);
    // Then is it Tres
    assertThat(tres.getName(), is(GameConstants.TRES_CARD));
    // When I pick card 1
    Card dos = game.getCardInHand(Player.FINDUS, 1);
    // Then it is Dos
    assertThat(dos.getName(), is(GameConstants.DOS_CARD));
    // When I pick card 2
    Card uno = game.getCardInHand(Player.FINDUS, 2);
    // Then it is Uno
    assertThat(uno.getName(), is(GameConstants.UNO_CARD));
  }

  @Test
  public void shouldHaveUnoDosTresCardsInitiallyInPeddersenHand() {
    // Given a game, Findus has 3 cards in hand
    int count = game.getHandSize(Player.PEDDERSEN);
    assertThat(count, is(3));
    // And these are ordered Tres, Dos, Uno in slot 0,1,2
    // When I pick card 0
    Card tres = game.getCardInHand(Player.PEDDERSEN, 0);
    // Then is it Tres
    assertThat(tres.getName(), is(GameConstants.TRES_CARD));
    // When I pick card 1
    Card dos = game.getCardInHand(Player.PEDDERSEN, 1);
    // Then it is Dos
    assertThat(dos.getName(), is(GameConstants.DOS_CARD));
    // When I pick card 2
    Card uno = game.getCardInHand(Player.PEDDERSEN, 2);
    // Then it is Uno
    assertThat(uno.getName(), is(GameConstants.UNO_CARD));
  }

  @Test
  public void shouldRemoveCardFromHandWhenPlayCard() {
    // Given a game where Findus wants to play card "Tres"
    MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
    // When Findus plays the card
    game.playCard(Player.FINDUS, cardToPlay, 0);
    int handSize = game.getHandSize(Player.FINDUS);
    // Then the card should be removed from the hand
    assertThat(handSize, is(2));
  }

  @Test
  public void shouldRemoveCardFromPeddersensHandWhenPlayCard() {
    // Given a game where Peddersen wants to plays card "Tres"
    game.endTurn();
    MutableCard cardToPlay = game.getCardInHand(Player.PEDDERSEN, 0);
    // When Pedderen plays the card
    game.playCard(Player.PEDDERSEN, cardToPlay, 0);
    int handSize = game.getHandSize(Player.PEDDERSEN);
    // Then card should be removed from hand
    assertThat(handSize, is(2));
  }

  @Test
  public void shouldHaveFourCardsInPeddersensHandIfNoCardsPlayedWhenHisTurnAgain() {
    // Given a game
    // When I ask for Peddersens hand size when it is his turn agian and he haven't played a card
    TestHelper.advanceGameNRounds(game, 1);
    game.endTurn();
    int handSize = game.getHandSize(Player.PEDDERSEN);
    // Then handsize should be 4
    assertThat(handSize, is(4));
  }

  @Test
  public void shouldAddCardToFieldWhenPLayed() {
    // Given a game
    // When Findus plays a card
    MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
    game.playCard(Player.FINDUS, cardToPlay, 0);
    int fieldSize = game.getFieldSize(Player.FINDUS);
    // Then fieldsize should be 1
    assertThat(fieldSize, is(1));
  }

  @Test
  public void shouldNotAddCardToPeddersensFieldWhenFindusPlaysCard() {
    // Given a game
    // When I ask for Peddersens fieldsize in the first turn after Findus has played a card
    MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
    game.playCard(Player.FINDUS, cardToPlay, 0);
    int fieldSize = game.getFieldSize(Player.PEDDERSEN);
    // Then it should be 0
    assertThat(fieldSize, is(0));
  }

  @Test
  public void shouldAddCardToPeddersensFiledWhenHePlaysCard() {
    // Given a game
    // When i ask for Peddersens fieldsize after he has played a card
    game.endTurn();
    MutableCard cardToPlay = game.getCardInHand(Player.PEDDERSEN, 0);
    game.playCard(Player.PEDDERSEN, cardToPlay, 0);
    int fieldSize = game.getFieldSize(Player.PEDDERSEN);
    // Then it should be 1
    assertThat(fieldSize, is(1));
  }

  @Test
  public void shouldNotBeAbleToPlayCardIfNotEnoughMana () {
    // Given a game
    // When I try to play a card but I don't have enough mana i should not be able to play the card
    game.getHero(Player.FINDUS).setMana(1);
    MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
    Status playCardStatus = game.playCard(Player.FINDUS, cardToPlay, 0);
    // Then the game should tell me "Not enough mana"
    assertThat(playCardStatus, is(Status.NOT_ENOUGH_MANA));
  }

  @Test
  public void shouldNotBeAbleToPlayCardWhenNotYourTurn() {
    // Given a game
    // When I try to play a card as Peddersen but it is Findus turn
    MutableCard cardToPlay = game.getCardInHand(Player.PEDDERSEN, 0);
    Status playCardStatus = game.playCard(Player.PEDDERSEN, cardToPlay, 0);
    // Then the game should tell me it's not my turn
    assertThat(playCardStatus, is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void shouldBeAbleToPlayCardWithEnoughManaAndItIsPlayersTurn() {
    // Given a game
    // When it's Findus turn and he tries to play card "Tres"
    MutableCard tres = game.getCardInHand(Player.FINDUS, 0);
    Status playCardStatusFindus = game.playCard(Player.FINDUS, tres, 0);
    // Then the game should tell him "OK"
    assertThat(playCardStatusFindus, is(Status.OK));

    // When findus has ended his turn and Peddersen tries to play a card
    game.endTurn();
    MutableCard dos = game.getCardInHand(Player.PEDDERSEN, 1);
    Status playCardStatusPeddersen = game.playCard(Player.PEDDERSEN, dos, 1);
    // Then the game should tell him "OK"
    assertThat(playCardStatusPeddersen, is(Status.OK));
  }

  @Test
  public void shouldHaveDosAtIndexZeroWhenDresIsPlayed() {
    // Given a game
    // When Findus has played card Tres and I ask for the card at index 0
    MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
    game.playCard(Player.FINDUS, cardToPlay, 0);
    Card cardAtIndexZero = game.getCardInHand(Player.FINDUS, 0);
    // Then it should be card Dos
    assertThat(cardAtIndexZero.getName(), is(GameConstants.DOS_CARD));
  }

  @Test
  public void shouldNotBeAbleToUsePowerIfNotPlayerTurn() {
      //Given a game where it's Findus turn
      //When Peddersen tries to use hero power
      Status usePowerStatus = game.usePower(Player.PEDDERSEN);
      //Then the game should tell it's not his turn
      assertThat(usePowerStatus, is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void shouldHaveCuatroAsFirstCardAfterFirstRound() {
    // Given a game
    // When I ask for Findus first card in hand after round 1
    TestHelper.advanceGameNRounds(game, 1);
    game.getHandSize(Player.FINDUS);
    Card firstCard = game.getCardInHand(Player.FINDUS, 0);
    // Should be quatro
    assertThat(firstCard.getName(), is(GameConstants.CUATRO_CARD));
  }

    @Test
    public void shouldHaveCuatroAsFirstCardAfterTurnThree() {
        // Given a game
        // When I ask for Peddersen first card in hand after turn 3
        TestHelper.advanceGameNRounds(game, 1);
        game.endTurn();
        game.getHandSize(Player.PEDDERSEN);
        Card firstCard = game.getCardInHand(Player.PEDDERSEN, 0);
        // Should be quatro
        assertThat(firstCard.getName(), is(GameConstants.CUATRO_CARD));
    }

    @Test
    public void shouldRemoveCardFromDeckWhenDrawingCards() {
      // Given a game
      // When Findus draws a card from the deck
      TestHelper.advanceGameNRounds(game, 1);
      int deckSize = game.getDeckSize(Player.FINDUS);
      // Then decksize should be 3
      assertThat(deckSize, is(3));
    }

    @Test
    public void shouldRemoveCardFromPeddersensDeckWhenDrawingCards() {
        // Given a game
        // When Findus draws a card from the deck, it should not remove cards from Peddersens deck
        TestHelper.advanceGameNRounds(game, 1);
        game.endTurn();
        int deckSize = game.getDeckSize(Player.PEDDERSEN);
        // Then decksize should be 4
        assertThat(deckSize, is(3));
    }

    @Test
    public void shouldHaveFiveCardsOnHandAndCardCincoAtIndexOneAfterFiveTurns() {
      // Given a game
      // When I ask for both players hand size and the card at index 0 after 5 turns
      TestHelper.advanceGameNRounds(game, 2);
      game.endTurn();
      int findusHandSize = game.getHandSize(Player.FINDUS);
      Card findusFirstCard = game.getCardInHand(Player.FINDUS, 0);
      // Then Findus should have 5 cards in hand and the first card should be "Cinco"
      assertThat(findusHandSize, is(5));
      assertThat(findusFirstCard.getName(), is(GameConstants.CINCO_CARD));

      int peddersenHandSize = game.getHandSize(Player.PEDDERSEN);
      Card peddersenFirstCard = game.getCardInHand(Player.PEDDERSEN, 0);
      // Then Peddersen should have 5 cards in hand in deck and the first card should be "Cinco"
      assertThat(peddersenHandSize, is(5));
      assertThat(peddersenFirstCard.getName(), is(GameConstants.CINCO_CARD));
    }

    @Test
    public void shouldHavePlayedCardsInField() {
      // Given a game
      // When Findus have played card tres
      MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 0);
      game.playCard(Player.FINDUS, cardToPlay, 0);
      Card cardInField = game.getCardInField(Player.FINDUS, 0);
      // Then it should appear in the field
      assertThat(cardInField.getName(), is(GameConstants.TRES_CARD));
    }

    @Test
    public void shouldHavePlayedCardsInPeddersensField() {
      // Given a game
      // When Peddersen have played card Dos
      game.endTurn();
      MutableCard cardToPlay = game.getCardInHand(Player.PEDDERSEN, 1);
      game.playCard(Player.PEDDERSEN, cardToPlay, 0);
      Card cardInField = game.getCardInField(Player.PEDDERSEN, 0);
      // Then it should appear in the field
      assertThat(cardInField.getName(), is(GameConstants.DOS_CARD));
    }

    @Test
    public void shouldLoseCorrectAmountOfHealth() {
      // Given a game
      // When a card attack another card
      // Findus plays a card
      MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 2);
      game.playCard(Player.FINDUS, attackingCard, 0);
      game.endTurn();

      // Peddersen plays a card
      MutableCard defendingCard = game.getCardInHand(Player.PEDDERSEN, 0);
      game.playCard(Player.PEDDERSEN, defendingCard, 0);
      game.endTurn();

      // Findus attacks Peddersens card
      game.attackCard(Player.FINDUS, attackingCard, defendingCard);

      // Then attacking card should lose all health and defending card should lose 1 health
      assertThat(attackingCard.getHealth(), is(-2));
      assertThat(defendingCard.getHealth(), is(2));
    }

    @Test
    public void shouldOnlyBeAbleToAttackWithActiveCards() {
        // Given a game
        // When a card attacks another card
        // Findus plays a card
        MutableCard defendingCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, defendingCard, 0);
        game.endTurn();

        // Peddersen plays a card
        MutableCard attackingCard = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, attackingCard, 0);
        // Peddersen attacks Findus card
        Status canAttack = game.attackCard(Player.PEDDERSEN, attackingCard, defendingCard);
        // Then he should not be able to attack
        assertThat(canAttack, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldBeInactiveAfterAttacking() {
        // Given a game
        // When Findus tries to attack with the same card twice
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, attackingCard, 0);
        game.endTurn();

        // Peddersen plays two cards
        MutableCard defendingCardOne = game.getCardInHand(Player.PEDDERSEN, 1);
        MutableCard defendingCardTwo = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, defendingCardOne, 0);
        game.playCard(Player.PEDDERSEN, defendingCardTwo, 1);
        game.endTurn();

        // Findus attacks Peddersens cards with the same card twice
        Status canAttack = game.attackCard(Player.FINDUS, attackingCard, defendingCardOne);
        Status cantAttack = game.attackCard(Player.FINDUS, attackingCard, defendingCardTwo);
        // Then the first time should be allowed, but second shouldn't
        assertThat(canAttack, is(Status.OK));
        assertThat(cantAttack, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldNotBeAbleToAttackOwnCard() {
        // Given a game
        // When Findus tries to attack his own card
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 1);
        MutableCard defendingCard = game.getCardInHand(Player.FINDUS, 2);
        game.playCard(Player.FINDUS, attackingCard, 0);
        game.playCard(Player.FINDUS, defendingCard, 1);
        TestHelper.advanceGameNRounds(game, 1);

        Status cantAttack = game.attackCard(Player.FINDUS, attackingCard, defendingCard);
        // Then the game should tell he can't attack his own card
        assertThat(cantAttack, is(Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION));
    }

    @Test
    public void shouldRemoveCardIfHealthIsLessThanZero() {
        // Given a game
        // When a card attack another card
        // Findus plays a card
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, attackingCard, 0);
        game.endTurn();

        // Peddersen plays a card
        MutableCard defendingCard = game.getCardInHand(Player.PEDDERSEN, 2);
        game.playCard(Player.PEDDERSEN, defendingCard, 0);
        game.endTurn();

        // Findus attacks Peddersens card
        game.attackCard(Player.FINDUS, attackingCard, defendingCard);

        int peddersenFieldSize = game.getFieldSize(Player.PEDDERSEN);

        // Then Peddersens card should be removed from his field
        assertThat(peddersenFieldSize, is(0));
    }

    @Test
    public void shouldLoseTwoManaWhenPlayingCardDos() {
      // Given a game
      // When Findus plays card dos
      MutableCard cardToPlay = game.getCardInHand(Player.FINDUS, 1);
      game.playCard(Player.FINDUS, cardToPlay, 0);
      int mana = game.getHero(Player.FINDUS).getMana();
      // Then mana should be 1
      assertThat(mana, is(1));
    }

    @Test
    public void shouldBeAbleToUseHeroPowerInNewRound() {
      // Given a game
      // When Findus uses his hero power
      game.usePower(Player.FINDUS);
      // And we advance 1 round
      TestHelper.advanceGameNRounds(game, 1);
      // And he uses it again
      Status canUsePower = game.usePower(Player.FINDUS);
      // Then he should be allowed
      assertThat(canUsePower, is(Status.OK));
    }

    @Test
    public void shouldNotBeAbleToPlayOtherPlayersCards() {
      // Given a game
      // When Findus try to play one of Peddersens cards
      MutableCard peddersenCard = game.getCardInHand(Player.PEDDERSEN, 0);
      Status canPlayCard = game.playCard(Player.FINDUS, peddersenCard, 0);
      // Then the game should tell Findus, that he is not allowed
      assertThat(canPlayCard, is(Status.NOT_OWNER));
    }

    @Test
    public void shouldHaveHeroHealthEighteenAfterAttackedByCardTres() {
      // Given a game
      // When Findus attacks Peddersens hero with card "Tres"
      MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 0);
      game.playCard(Player.FINDUS, attackingCard, 0);
      TestHelper.advanceGameNRounds(game, 1);

      game.attackHero(Player.FINDUS, attackingCard);
      // Then Peddersen's hero should have 18 health
      int health = game.getHero(Player.PEDDERSEN).getHealth();
      assertThat(health, is(18));
    }

    @Test
    public void shouldHaveHeroHealthNineteenAfterAttackedByCardDos() {
        // Given a game
        // When Findus attacks Peddersens hero with card "Dos"
        MutableCard attackingCard = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, attackingCard, 0);
        TestHelper.advanceGameNRounds(game, 1);

        game.attackHero(Player.FINDUS, attackingCard);
        // Then Peddersen's hero should have 19 health
        int health = game.getHero(Player.PEDDERSEN).getHealth();
        assertThat(health, is(19));
    }

    @Test
    public void shouldLoseCorrectAmountOfHealthWhenAttackingEachothersHeroes() {
        // Given a game
        // When Findus and Peddersen attack eacothers heroes
        // Findus plays hos card
        MutableCard attackingCardFindus = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, attackingCardFindus, 0);
        game.endTurn();

        // Peddersen plays his card
        MutableCard attackingCardPeddersen = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, attackingCardPeddersen, 0);
        game.endTurn();

        // They attack eachothers heroes
        game.attackHero(Player.FINDUS, attackingCardFindus);
        game.endTurn();
        game.attackHero(Player.PEDDERSEN, attackingCardPeddersen);
        // Then Peddersen's hero should have 19 health and Findus' hero should have 18 health
        int peddersenHealth = game.getHero(Player.PEDDERSEN).getHealth();
        int findusHealth = game.getHero(Player.FINDUS).getHealth();
        assertThat(peddersenHealth, is(19));
        assertThat(findusHealth, is(18));
    }

    @Test
    public void shouldNotBeAbleToAttackWithCardThatIsNotYours() {
      // Given a game
      // When both players have active cards in the field and they try to attack with eachothers cards
      // Findus plays his cards
      MutableCard findusCardOne = game.getCardInHand(Player.FINDUS, 2);
      MutableCard findusCardTwo = game.getCardInHand(Player.FINDUS, 1);
      game.playCard(Player.FINDUS, findusCardOne, 0);
      game.playCard(Player.FINDUS, findusCardTwo, 1);
      game.endTurn();

      // Peddersen plays his cards
      MutableCard peddersensCardOne = game.getCardInHand(Player.PEDDERSEN, 2);
      MutableCard peddersensCardTwo = game.getCardInHand(Player.PEDDERSEN, 1);
      game.playCard(Player.PEDDERSEN, peddersensCardOne, 0);
      game.playCard(Player.PEDDERSEN, peddersensCardTwo, 1);
      game.endTurn();

      // Findus tries to attack with Peddersens card and visa versa
      Status findusCantAttackWithPeddersensCards = game.attackCard(Player.FINDUS, peddersensCardOne, peddersensCardTwo);
      game.endTurn();
      Status peddersenCantAttackWithFindusCards = game.attackCard(Player.PEDDERSEN, findusCardOne, findusCardTwo);

      // Then the game should not allow it
      assertThat(findusCantAttackWithPeddersensCards, is(Status.NOT_OWNER));
      assertThat(peddersenCantAttackWithFindusCards, is(Status.NOT_OWNER));
    }

    @Test
    public void shouldNotBeAbleToAttackWithCardWhenNotPlayersTurn() {
      // Given a game
      // When Findus tries to attack Peddersen when it's not his turn
      MutableCard findusCard = game.getCardInHand(Player.FINDUS, 0);
      game.playCard(Player.FINDUS, findusCard, 0);
      game.endTurn();

      MutableCard peddersensCard = game.getCardInHand(Player.PEDDERSEN, 0);
      game.playCard(Player.PEDDERSEN, peddersensCard, 0);
      // Findus tries to attack Peddersen
      Status cantAttack = game.attackCard(Player.FINDUS, findusCard, peddersensCard);
      // Then he should not be allowed
      assertThat(cantAttack, is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldOnlyBeAbleToAttackHeroWhenActive() {
      // Given a game
      // When Findus tries to attack Peddersens hero, with an inactive card
      MutableCard card = game.getCardInHand(Player.FINDUS, 0);
      Status cantAttack = game.attackHero(Player.FINDUS, card);
      // Then it should not be allowed
      assertThat(cantAttack, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldOnlyBeAbleToAttackHeroIfOwnerOfAttackingCard() {
      // Given a Game
      // When Findus tries to attack Peddersens hero with Peddersens card
      // Sets it to Peddersens turn
      game.endTurn();
      MutableCard card = game.getCardInHand(Player.PEDDERSEN, 0);
      game.playCard(Player.PEDDERSEN, card, 0);
      // Sets it to Findus turn
      game.endTurn();
      Status cantAttack = game.attackHero(Player.FINDUS, card);
      // Then he should not be allowed to attack with Peddersens card
      assertThat(cantAttack, is(Status.NOT_OWNER));
    }

    @Test
    public void shouldOnlyBeAbleToAttackHeroWhenItIsPlayersTurn() {
      // Given a game
      // When Findus tries to attack Peddersens hero when it's not his turn
      MutableCard card = game.getCardInHand(Player.FINDUS, 0);
      game.playCard(Player.FINDUS, card, 0);
      game.endTurn();
      Status cantAttack = game.attackHero(Player.FINDUS, card);
      // Then he should not be allowed
      assertThat(cantAttack, is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldProduceProperAlphaDeck() {
        // Given a AlphaStone deck
        // When I ask for the deck size and correct card specs
        List<MutableCard> deck = new AlphaStoneDeckBuilderStrategy().buildDeck(Player.FINDUS);

        // Then it should have size 18 and the correct specs
        assertThat(deck.size(), is(GameConstants.ALPHA_DECK_SIZE));
        verifyCardSpecs(deck, GameConstants.UNO_CARD, 1, 1, 1, "");
        verifyCardSpecs(deck, GameConstants.DOS_CARD, 2, 2, 2, "");
        verifyCardSpecs(deck, GameConstants.TRES_CARD, 3, 3, 3, "");
        verifyCardSpecs(deck, GameConstants.CUATRO_CARD, 2, 3, 1, "");
        verifyCardSpecs(deck, GameConstants.CINCO_CARD, 3, 5, 1, "");
        verifyCardSpecs(deck, GameConstants.SEIS_CARD, 2, 1, 3, "");
        verifyCardSpecs(deck, GameConstants.SIETE_CARD, 3, 2, 4, "");
    }

}
