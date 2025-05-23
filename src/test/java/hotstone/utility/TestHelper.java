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

package hotstone.utility;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/** Useful helper functions during TDD of the
 * HotStone system. Call these methods in your
 * test cases to verify that your assumptions about
 * the state of the game is correct.
 *
 * And remember to remove these calls once your
 * test cases are running :).
 */
public class TestHelper {
  /** Print the full game state on StdOut, useful
   * for debugging and creating new test cases.
   *
   * @param game the game whose state should be printed.
   */
  public static void printGameState(MutableGame game) {
    System.out.println("=== Game State Print ===");
    System.out.println("  Player in turn: " + game.getPlayerInTurn()
      + ", Turn number: " + game.getTurnNumber());
    for (Player player: Player.values()) {
      System.out.println("--- Player: " + player.toString() + " ---");
      Hero hero = game.getHero(player);
      System.out.println("  Hero (" + hero.getType()
              + ") Mana: " + hero.getMana()
              + ", Health: " + hero.getHealth());
      System.out.println("  Deck size: " + game.getDeckSize(player));
      System.out.print( "  Hand[");
      int index = 0;
      for (Card c :
              game.getHand(player)) {
        System.out.print(" " + index++ + ":" + describeCard(c));

      }
      System.out.println("]");
      System.out.print( "  Field[");
      index = 0;
      for (Card c :
              game.getField(player)) {
        System.out.print(" " + index++ + ":" + describeCard(c));

      }
      System.out.println("]");
    }
    System.out.println("--- END ---");
  }

  private static String describeCard(Card card) {
    return "{" + card.getName()
            + ": (" + card.getManaCost() + ", " + card.getAttack() + ", " + card.getHealth()
            + "), Act: " + (card.isActive() ? "T":"F" + "}") ;
  }

  // Helper method to advance the game some rounds
  public static void advanceGameNRounds(MutableGame game, int roundCount) {
    for (int i = 0; i < roundCount; i++) { game.endTurn(); game.endTurn(); }
  }

  // Helper method to set a few cards into the field
  public static void fieldTresForFindusAndDosForPeddersen(MutableGame game) {
    MutableCard card = (MutableCard) game.getCardInHand(Player.FINDUS, 0);
    game.playCard(Player.FINDUS, card, 0);
    game.endTurn();
    // Remember Peddersen gets a card draw, so Dos is now at
    // index 2
    card = (MutableCard) game.getCardInHand(Player.PEDDERSEN, 2);
    game.playCard(Player.PEDDERSEN, card, 0);
    game.endTurn(); // Back to Findus which draws a card
    assertThat(game.getCardInField(Player.PEDDERSEN, 0).getName(),
            is("Dos"));
  }

  // Helper method to verify the card specifications
  public static void verifyCardSpecs(List<? extends Card> deck, String cardName, int cost, int attack, int health, String effectDescription) {
    // Given the name of the card, find the first such
    Card theCard = deck.stream().filter(card -> card.getName().equals(cardName)).findFirst().orElse(null);
    // Then the card exists
    assertThat(theCard, is(notNullValue()));
    // Then the card has the correct cost, attack, health and effect description
    assertThat(theCard.getManaCost(), is(cost));
    assertThat(theCard.getAttack(), is(attack));
    assertThat(theCard.getHealth(), is(health));
    assertThat(theCard.getEffectDescription(), is(effectDescription));
  }
}
