package hotstone.broker;
import hotstone.framework.*;

import hotstone.framework.mutability.MutableCard;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
import hotstone.utility.TestHelper;
import hotstone.variants.alphastone.AlphaStoneFactory;
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
import hotstone.broker.server.HotStoneGameInvoker;

import java.util.List;
import java.util.stream.StreamSupport;

/** Test class for broker II, using test-driven
 *  development process to develop the Broker implementation
 *  of client proxies and invokers, for all methods
 *  in Game, that take objects as parameters.
 */
public class TestGameBrokerII {
    // The client side's game client proxy
    private Game gameClientProxy;

    @BeforeEach
    public void setup() {
        // === We start at the server side of the Broker pattern:
        // define the servant, next the invoker

        // Given a Servant game, here the simplest of our games "AlphaStone"
        Game servant = new StandardHotStoneGame(new AlphaStoneFactory());
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

    @Test
    public void shouldHaveFirstCardAsTres() {
        // Given a game
        // When I ask for the first card in player Findus hand
        Card cardInHand = gameClientProxy.getCardInHand(Player.FINDUS, 0);

        // Then it should be Tres
        assertThat(cardInHand.getName(), is(GameConstants.TRES_CARD));
        assertThat(cardInHand.getAttack(), is(3));
        assertThat(cardInHand.getManaCost(), is(3));
        assertThat(cardInHand.getHealth(), is(3));
    }

    @Test
    public void shouldHaveBabyAsHero() {
        // Given a game
        // When I ask for the hero of Findus
        Hero hero = gameClientProxy.getHero(Player.FINDUS);

        // Then it should be baby
        assertThat(hero.getType(), is(GameConstants.BABY_HERO_TYPE));
    }

    @Test
    public void shouldReturnHandContainingUnoDosTres() {
        // Given a game
        // When I ask for the hand of Findus
        Iterable<? extends Card> hand = gameClientProxy.getHand(Player.FINDUS);

        // Then it should contain 3 cards, uno, dos and tres
        // Convert to a list for simplicity
        List<? extends Card> handList = StreamSupport.stream(hand.spliterator(), false).toList();

        assertThat(handList.size(), is(3));
        assertThat(handList.get(0).getName(), is(GameConstants.TRES_CARD));
        assertThat(handList.get(1).getName(), is(GameConstants.DOS_CARD));
        assertThat(handList.get(2).getName(), is(GameConstants.UNO_CARD));
    }

    @Test
    public void shouldBeStatusOKWhenPlayingACard() {
        // Given a game
        // When Findus tries to play a card
        Card card = gameClientProxy.getCardInHand(Player.FINDUS, 0);
        Status status = gameClientProxy.playCard(Player.FINDUS, card, 0);

        // Then status should be OK
        assertThat(status, is(Status.OK));
    }

    @Test
    public void shouldHaveCardTresInField() {
        // Given a game
        // When I ask for the card in Findus field
        // After card tres has been played
        Card card = gameClientProxy.getCardInHand(Player.FINDUS, 0);
        gameClientProxy.playCard(Player.FINDUS, card, 0);
        Card cardInField = gameClientProxy.getCardInField(Player.FINDUS, 0);

        // Then it should be card tres
        assertThat(cardInField.getName(), is(GameConstants.TRES_CARD));
    }

    @Test
    public void shouldReturnFieldContainingUnoAndDos() {
        // Given a game
        // When I ask for the field after Findus has played card uno and dos
        Card uno = gameClientProxy.getCardInHand(Player.FINDUS, 2);
        Card dos = gameClientProxy.getCardInHand(Player.FINDUS, 1);

        gameClientProxy.playCard(Player.FINDUS, uno, 0);
        gameClientProxy.playCard(Player.FINDUS, dos, 1);

        Iterable<? extends Card> field = gameClientProxy.getField(Player.FINDUS);

        // Then the field should contain those two card
        // Convert to a list for simplicity
        List<? extends Card> fieldList = StreamSupport.stream(field.spliterator(), false).toList();

        assertThat(fieldList.size(), is(2));
        assertThat(fieldList.get(1).getName(), is(GameConstants.DOS_CARD));
        assertThat(fieldList.get(0).getName(), is(GameConstants.UNO_CARD));
    }

    @Test
    public void shouldBeStatusOKWhenAttackingCardCorrectly() {
        // Given a game
        // When Findus tries to attack Peddersens minion
        // Play Findus card
        Card findusCard = gameClientProxy.getCardInHand(Player.FINDUS, 0);
        gameClientProxy.playCard(Player.FINDUS, findusCard, 0);

        // End turn and play Peddersens card
        gameClientProxy.endTurn();
        Card peddersenCard = gameClientProxy.getCardInHand(Player.PEDDERSEN, 0);
        gameClientProxy.playCard(Player.PEDDERSEN, peddersenCard, 0);

        // End turn and attack Peddersens minion with Findus'
        gameClientProxy.endTurn();
        Status status = gameClientProxy.attackCard(Player.FINDUS, findusCard, peddersenCard);

        // Then status should be OK
        assertThat(status, is(Status.OK));
    }

    @Test
    public void shouldBeStatusOKWhenAttackingHeroCorrectly() {
        // Given a game
        // When Findus tries to attack Peddersens hero
        // Play the card
        Card card = gameClientProxy.getCardInHand(Player.FINDUS, 0);
        gameClientProxy.playCard(Player.FINDUS, card, 0);

        // Advance one round
        gameClientProxy.endTurn();
        gameClientProxy.endTurn();

        // Attack Peddersens hero
        Status status = gameClientProxy.attackHero(Player.FINDUS, card);

        // The status should be OK
        assertThat(status, is(Status.OK));
    }

    @Test
    public void shouldBeStatusOKWhenUsingPowerCorrectly() {
        // Given a game
        // When Findus uses his hero power
        Status status = gameClientProxy.usePower(Player.FINDUS);
        // Then status should be OK
        assertThat(status, is(Status.OK));
    }
}
