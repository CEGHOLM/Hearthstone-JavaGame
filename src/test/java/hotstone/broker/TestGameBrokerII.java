package hotstone.broker;
import hotstone.framework.*;

import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
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
}
