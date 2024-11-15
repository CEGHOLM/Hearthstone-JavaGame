/*
 * Copyright (C) 2022 - 2024. Henrik Bærbak Christensen, Aarhus University.
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

package hotstone.broker.doubles;

import frds.broker.Servant;
import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;
import hotstone.observer.GameObserver;

import java.util.List;

/** A Test Stub for game, to make easily recognizable output
 * to assert on in the Broker test cases. Some methods have
 * already been defined.
 */

/* TODO: Fill more stub behaviour for testing your GameClientProxy
 * and GameInvoker code.
 */
public class StubGameForBroker implements Game, Servant {
  private int turnNumber = 312;
  @Override
  public int getTurnNumber() {
    return turnNumber;
  }
  @Override
  public Player getPlayerInTurn() {
    return Player.FINDUS;
  }
  @Override
  public Player getWinner() {
    return Player.PEDDERSEN;
  }

  @Override
  public MutableHero getHero(Player who) {
    return null;
  }

  @Override
  public int getDeckSize(Player who) {
    return 37;
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
    return 17;
  }

  @Override
  public MutableCard getCardInField(Player who, int indexInField) {
    return null;
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    return null;
  }

  @Override
  public int getFieldSize(Player who) {
    return 11;
  }

  @Override
  public List<? extends Card> getDeck(Player who) {
    return List.of();
  }

  @Override
  public void endTurn() {
    turnNumber++;
  }

  @Override
  public Status playCard(Player who, MutableCard card, int atIndex) {
    return null;
  }

  @Override
  public Status attackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
    return null;
  }

  @Override
  public Status attackHero(Player playerAttacking, MutableCard attackingCard) {
    return null;
  }

  @Override
  public Status usePower(Player who) {
    return null;
  }

  @Override
  public void addObserver(GameObserver observer) {
    // NOT RELEVANT FOR BROKER EXERCISES
  }
}
