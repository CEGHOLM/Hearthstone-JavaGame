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
package hotstone.broker;

import hotstone.framework.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;

import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;

/** Skeleton test case for a 'depth-first' test-driven
 * development process to develop the Broker implementation
 * of client proxies and invokers, for all 'primitive' methods
 * in Game, that is, methods that do NOT take objects as
 * parameters, only primitive types and Strings.
 */
public class TestGameBroker {
  // The client side's game client proxy
  private Game gameClientProxy;

  @BeforeEach
  public void setup() {
    // === We start at the server side of the Broker pattern:
    // define the servant, next the invoker

    // Given a Servant game, here a test stub with canned output
    Game servant = new StubGameForBroker();
    // Which is injected into the dedicated Invoker which you must
    // develop
    Invoker invoker = new HotStoneGameInvoker(servant);

    // === Next define the client side of the pattern:
    // the client request handler, the requestor, and the client proxy

    // Instead of a network-based client- and server request handler
    // we make a fake object CRH that talks directly with the injected
    // invoker
    ClientRequestHandler crh =
            new LocalMethodClientRequestHandler(invoker);

    // Which is injected into the standard JSON requestor of the
    // FRDS.Broker library
    Requestor requestor = new StandardJSONRequestor(crh);

    // Which is finally injected into the GameClientProxy that
    // you must develop...
    gameClientProxy = new GameClientProxy(requestor);
  }

  // to support remote method call of getTurnNumber()
  @Test
  public void shouldHaveTurnNumber312() {
    // Given a stub game which is hard coded to
    // return 312 as turn number, at the start of the game

    // When I ask for that turn number on the client side
    int turnNumber = gameClientProxy.getTurnNumber();

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's 312 reply.
   assertThat(turnNumber, is(312));
  }

  @Test
  public void shouldHaveDeckSizeAs37() {
    // Given a stub game which is hard code to
    // return 37 as deck size no matter the player

    // When I ask for the deck size of player Findus
    int deckSize = gameClientProxy.getDeckSize(Player.FINDUS);

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's 37 reply.
    assertThat(deckSize, is(37));
  }

  @Test
  public void shouldHaveHandSizeAs17() {
    // Given a stub game which is hard code to
    // return 17 as hand size no matter the player

    // When I ask for the hand size of player Findus
    int handSize = gameClientProxy.getHandSize(Player.FINDUS);

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's 17 reply.
    assertThat(handSize, is(17));
  }

  @Test
  public void shouldIncreaseTurnNumberByOneAfterEndingTurn() {
    // Given a stub game which increases the turn number by one
    // Every time a turn is ended

    gameClientProxy.endTurn();

    // When I ask for the turn number
    int turnNumber = gameClientProxy.getTurnNumber();

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's 313 reply.
    assertThat(turnNumber, is(313));
  }

  @Test
  public void shouldHaveFieldSizeAs11() {
    // Given a stub game which is hard code to
    // return 11 as field size no matter the player

    // When I ask for the hand size of player Findus
    int fieldSize = gameClientProxy.getFieldSize(Player.FINDUS);

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's 11 reply.
    assertThat(fieldSize, is(11));
  }

  @Test
  public void shouldReturnFindusAsPlayerInTurn() {
    // Given a stub game which is hard code to
    // return Findus as the player in turn

    // When I ask for the player in turn
    Player player = gameClientProxy.getPlayerInTurn();

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's Findus reply.
    assertThat(player, is(Player.FINDUS));
  }

  @Test
  public void shouldHavePeddersenAsWinner() {
    // Given a stub game which is hard code to
    // return Peddersen as the winner

    // When I ask for the winner
    Player player = gameClientProxy.getWinner();

    // Then the broker chain (clientProxy -> requestor ->
    // client request handler -> invoker -> servant) will
    // return the stub's Findus reply.
    assertThat(player, is(Player.PEDDERSEN));
  }
}
