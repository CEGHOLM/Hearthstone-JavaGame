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
import hotstone.doubles.StubCard;
import hotstone.doubles.StubHero;
import hotstone.framework.*;
import hotstone.variants.NullEffect;

/** TODO: Template code for solving the Broker exercises */
public class HotStoneGameInvoker implements Invoker {

  private final Game servant;
  private final Gson gson;
  private Card fakeItCard = new StubCard("Card", 17, 15, 77,
          true, Player.FINDUS, new NullEffect());
  private Hero fakeItHero = new StubHero();

  public HotStoneGameInvoker(Game servant) {
    this.servant = servant;
    this.gson = new Gson();
  }

  private Card lookupCard(String objectId) {
    return fakeItCard;
  }

  private Hero lookupHero(String objectId) {
    return fakeItHero;
  }

  @Override
  public String handleRequest(String request) {
    // Do the demarshalling
    RequestObject requestObject =
            gson.fromJson(request, RequestObject.class);
    String objectId = requestObject.getObjectId();
    JsonArray array =
            JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();

    ReplyObject reply;

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

      } else if (operationName.equals(OperationNames.GAME_GET_FIELD_SIZE)) {
        // Get the player from the JSON array
        Player who = gson.fromJson(array.get(0), Player.class);

        // Call the servants getFieldSize() method
        int fieldSize = servant.getFieldSize(who);

        // Create reply
        reply = new ReplyObject(200, gson.toJson(fieldSize));

        // Card methods
      } else if (operationName.equals(OperationNames.CARD_GET_NAME)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getName() method
        String name = servant.getName();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(name));

      } else if (operationName.equals(OperationNames.CARD_GET_MANA_COST)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getManaCost() method
        int manaCost = servant.getManaCost();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(manaCost));

      } else if (operationName.equals(OperationNames.CARD_GET_ATTACK)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getAttack() method
        int attack = servant.getAttack();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(attack));

      } else if (operationName.equals(OperationNames.CARD_GET_HEALTH)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getHealth() method
        int health = servant.getHealth();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(health));

      } else if (operationName.equals(OperationNames.CARD_IS_ACTIVE)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants IsActive() method
        boolean isActive = servant.isActive();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(isActive));

      } else if (operationName.equals(OperationNames.CARD_GET_OWNER)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getOwner() method
        Player owner = servant.getOwner();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(owner));

      } else if (operationName.equals(OperationNames.CARD_GET_EFFECT_DESCRIPTION)) {
        // Lookup the right card to invoke the method on
        Card servant = lookupCard(objectId);

        // Call the servants getEffectDescription() method
        String effectDescription = servant.getEffectDescription();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(effectDescription));

        // Hero methods
      } else if (operationName.equals(OperationNames.HERO_GET_MANA)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants getMana() method
        int mana = servant.getMana();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(mana));

      } else if (operationName.equals(OperationNames.HERO_GET_HEALTH)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants getHealth() method
        int health = servant.getHealth();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(health));

      } else if (operationName.equals(OperationNames.HERO_IS_ACTIVE)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants canUsePower() method
        boolean canUsePower = servant.canUsePower();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(canUsePower));

      } else if (operationName.equals(OperationNames.HERO_GET_TYPE)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants getType() method
        String type = servant.getType();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(type));

      } else if (operationName.equals(OperationNames.HERO_GET_OWNER)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants getOwner() method
        Player owner = servant.getOwner();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(owner));

      } else if (operationName.equals(OperationNames.HERO_GET_EFFECT_DESCRIPTION)) {
        // Lookup the right hero to invoke the method on
        Hero servant = lookupHero(objectId);

        // Call the servants getEffectDescription() method
        String effectDescription = servant.getEffectDescription();

        // Create reply
        reply = new ReplyObject(200, gson.toJson(effectDescription));

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
