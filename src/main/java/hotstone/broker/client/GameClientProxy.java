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

package hotstone.broker.client;

import com.google.gson.reflect.TypeToken;
import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.broker.service.StandardNameService;
import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.mutability.MutableHero;
import hotstone.observer.GameObserver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GameClientProxy implements Game, ClientProxy {
  private String singletonID = "one-game";
  private final Requestor requestor;
  private NameService nameService;

  public GameClientProxy(Requestor requestor) {
    this.requestor = requestor;
    nameService = new StandardNameService();
  }

  @Override
  public int getTurnNumber() {
    int turnNumber =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_TURN_NUMBER, Integer.class);
    return turnNumber;
  }

  @Override
  public Player getPlayerInTurn() {
    Player playerInTurn =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_PLAYER_IN_TURN, Player.class);
    return playerInTurn;
  }

  @Override
  public Hero getHero(Player who) {
    String heroID =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_HERO, String.class, who);
    Hero proxy = new HeroClientProxy(heroID, requestor);
      return proxy;
  }

  @Override
  public Player getWinner() {
    Player winner =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_WINNER, Player.class);
    return winner;
  }

  @Override
  public int getDeckSize(Player who) {
    int deckSize =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_DECK_SIZE, Integer.class, who);
    return deckSize;
  }

  @Override
  public Card getCardInHand(Player who, int indexInHand) {
    String cardId =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_CARD_IN_HAND, String.class, who, indexInHand);
    Card proxy =  new CardClientProxy(cardId, requestor);
    return proxy;
  }

  @Override
  public Iterable<? extends Card> getHand(Player who) {
    // Define the type of a list og String
    Type collectionType =
            new TypeToken<List<String>>() {}.getType();
    // Do the remote call to retrieve the list of IDs for
    // all cards in the hand
    List<String> theIDList =
            requestor.sendRequestAndAwaitReply(singletonID,
                    OperationNames.GAME_GET_HAND,
                    collectionType, who);

    // Convert the ID list into lost of CardClientProxies
    List<Card> proxies = new ArrayList<>();
    for (String id : theIDList) {
      proxies.add(new CardClientProxy(id, requestor));
    }

    // Rerun the list of proxies
    return proxies;
  }

  @Override
  public int getHandSize(Player who) {
    int handSize =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_HAND_SIZE, Integer.class, who);
    return handSize;
  }

  @Override
  public Card getCardInField(Player who, int indexInField) {
    return null;
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    return null;
  }

  @Override
  public int getFieldSize(Player who) {
    int fieldSize =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_GET_FIELD_SIZE, Integer.class, who);
    return fieldSize;
  }

  @Override
  public void endTurn() {
    requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_END_OF_TURN, String.class);
  }

  @Override
  public Status playCard(Player who, Card card, int atIndex) {
    Status status =
            requestor.sendRequestAndAwaitReply(singletonID, OperationNames.GAME_PLAY_CARD, Status.class, who, card.getID(), atIndex);
    return status;
  }

  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    return null;
  }

  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    return null;
  }

  @Override
  public Status usePower(Player who) {
    return null;
  }

  @Override
  public void addObserver(GameObserver observer) {

  }
}
