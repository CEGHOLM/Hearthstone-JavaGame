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

package hotstone.broker.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.broker.service.NameService;
import hotstone.broker.service.StandardNameService;
import hotstone.framework.*;
import hotstone.framework.mutability.MutableCard;

import java.util.ArrayList;
import java.util.List;

public class HotStoneGameInvoker implements Invoker {

  private final Game servant;
  private final Gson gson;
  private final NameService nameService;

  public HotStoneGameInvoker(Game servant, Gson gson, NameService nameService) {
    this.servant = servant;
    this.gson = gson;
    this.nameService = nameService;
  }

  private Card lookupCard(String objectId) {
    return nameService.getCard(objectId);
  }

  @Override
  public String handleRequest(String request) {
    // Do the demarshalling
    RequestObject requestObject =
            gson.fromJson(request, RequestObject.class);
    String objectId = requestObject.getObjectId();
    JsonArray array =
            JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();

    ReplyObject reply = null;

    try {
      // Dispatching: Check the operation name
      String operationName = requestObject.getOperationName();

      if (operationName.equals(OperationNames.GAME_GET_TURN_NUMBER)) {
        // Call the servants getTurnNumber() method
        int turnNumber = servant.getTurnNumber();

        // Create a reply
        reply = new ReplyObject(200, gson.toJson(turnNumber));

      } else if (operationName.equals(OperationNames.GAME_GET_DECK_SIZE)) {
        // Get the player from the JSON array
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call  the servants getDeckSize() method
        int deckSize = servant.getDeckSize(who);

        // Create a reply
        reply = new ReplyObject(200, gson.toJson(deckSize));

      } else if (operationName.equals(OperationNames.GAME_GET_HAND_SIZE)) {
        // Get the player from the JSON array
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the servants getHandSize() method
        int handSize = servant.getHandSize(who);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(handSize));

      } else if (operationName.equals(OperationNames.GAME_END_OF_TURN)) {
        // Call the servants endTurn() method
        servant.endTurn();

        // Create reply
        reply = new ReplyObject(200, gson.toJson("OK"));

      } else if (operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
        // Call the servants getPlayerInTurn() method
        Player playerInTurn = servant.getPlayerInTurn();

        // Create a reply
        reply = new ReplyObject(200, gson.toJson(playerInTurn));

      } else if (operationName.equals(OperationNames.GAME_GET_WINNER)) {
        // Call the servants getWinnerMethod() method
        Player winner = servant.getWinner();

        // Create a reply
        reply = new ReplyObject(200, gson.toJson(winner));

      } else if (operationName.equals(OperationNames.GAME_GET_HAND)) {
        // Get the player
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the getHand() method to get the cards
        Iterable<? extends Card> hand = servant.getHand(who);

        // Create a list of ID's for the cards
        List<String> idList = new ArrayList<>();
        for (Card card : hand) {
          String cardId = card.getID();
          nameService.addCard(cardId, card);
          idList.add(cardId);
        }

        // Create reply
        reply = new ReplyObject(200, gson.toJson(idList));

      } else if (operationName.equals(OperationNames.GAME_PLAY_CARD)) {
        // Get the player, card and index
        Player who = gson.fromJson(array.get(0), Player.class);
        MutableCard card = (MutableCard) lookupCard(gson.fromJson(array.get(1), String.class));
        int index = gson.fromJson(array.get(2), Integer.class);

        // Call the playCard() method
        Status status = servant.playCard(who, card, index);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(status));

      } else if (operationName.equals(OperationNames.GAME_ATTACK_CARD)) {
        // Get the player and cards
        Player attacktingPlayer = gson.fromJson(array.get(0), Player.class);
        Card attackingCard = lookupCard(gson.fromJson(array.get(1), String.class));
        Card defendingCard = lookupCard(gson.fromJson(array.get(2), String.class));

        // Call the attackCard() method
        Status status = servant.attackCard(attacktingPlayer, attackingCard, defendingCard);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(status));

      } else if (operationName.equals(OperationNames.GAME_ATTACK_HERO)) {
        // Get the attacking player and card
        Player attackingPlayer = gson.fromJson(array.get(0), Player.class);
        Card attackingCard = lookupCard(gson.fromJson(array.get(1), String.class));

        // Call the attackHero() method
        Status status = servant.attackHero(attackingPlayer, attackingCard);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(status));

      } else if (operationName.equals(OperationNames.GAME_USE_POWER)) {
        // Get the player
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the usePower() method
        Status status = servant.usePower(who);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(status));

      } else if (operationName.equals(OperationNames.GAME_GET_CARD_IN_FIELD)) {
        // Get the player and the index
        Player who = gson.fromJson(array.get(0), Player.class);
        int index = gson.fromJson(array.get(1), Integer.class);

        // Call the getCardInField() method
        Card card = servant.getCardInField(who, index);

        // Generate the ID for the card
        String cardId = card.getID();

        // Register the card in the name service
        nameService.addCard(cardId, card);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(cardId));

      } else if (operationName.equals(OperationNames.GAME_GET_FIELD)) {
        // Get the player
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the getField() method to get the cards/minions
        Iterable<? extends Card> field = servant.getField(who);

        // Create a list of ID's for the cards/minions
        List<String> idList = new ArrayList<>();
        for (Card minion : field) {
          String cardId = minion.getID();
          nameService.addCard(cardId, minion);
          idList.add(cardId);
        }

        // Create reply
        reply = new ReplyObject(200, gson.toJson(idList));

      } else if (operationName.equals(OperationNames.GAME_GET_HERO)) {
        // Get the player and index from JSON array
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the getHero() method
        Hero hero = servant.getHero(who);

        // Generate the id for the hero
        String heroId = hero.getID();

        // Register teh hero in the name service
        nameService.addHero(heroId, hero);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(heroId));

      } else if (operationName.equals(OperationNames.GAME_GET_CARD_IN_HAND)) {
        // Get the player and index from JSON array
        Player who = gson.fromJson(array.get(0), Player.class);
        int index = gson.fromJson(array.get(1), Integer.class);

        // Call the getCardInHand() method
        Card card = servant.getCardInHand(who, index);

        // Generate the ID for the card
        String cardId = card.getID();

        // Register the card in the name service
        nameService.addCard(cardId, card);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(cardId));

      } else if (operationName.equals(OperationNames.GAME_GET_FIELD_SIZE)) {
        // Get the player from the JSON array
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the servants getFieldSize() method
        int fieldSize = servant.getFieldSize(who);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(fieldSize));

        // Card methods
      } else {
        // Unknown operation
        reply = new ReplyObject(501, "Unknown operation: " + operationName);
      }

    } catch (Exception e) {
      // Handle errors
      reply = new ReplyObject(500, "Server error: " + e.getMessage());
    }

    // Marshalling: Convert reply to Json and return
    return gson.toJson(reply);
  }
}
