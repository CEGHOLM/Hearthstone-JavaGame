package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.doubles.StubCard;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCardBroker {
    // The client side's card client proxy
    private Card cardClientProxy;
    private StubCard stubCard;

    @BeforeEach
    public void setup() {
        // === Server side setup ===
        // Create a stub servant with canned output
        Game servant = new StubGameForBroker();
        Invoker invoker = new HotStoneGameInvoker(servant);

        // === Client side setup ===
        // Create a local method client request handler
        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);

        // Create the CardClientProxy to be tested
        cardClientProxy = new CardClientProxy(requestor);
    }

    @Test
    public void shouldHaveCardAsName() {
        // Given a stub card which is hardcoded to
        // Return the name "name"

        // When I ask for the cards name
        String name = cardClientProxy.getName();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(name, is("Card"));
    }

    @Test
    public void shouldCost17Mana() {
        // Given a stub card which is hardcoded to
        // Return the 17 as mana cost

        // When I ask for the cards mana cost
        int manaCost = cardClientProxy.getManaCost();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(manaCost, is(17));
    }

    @Test
    public void shouldHave15Attack() {
        // Given a stub card which is hardcoded to
        // Return the 15 as attack

        // When I ask for the cards attack
        int attack = cardClientProxy.getAttack();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(attack, is(15));
    }

    @Test
    public void shouldHave77Health() {
        // Given a stub card which is hardcoded to
        // Return the 77 as health

        // When I ask for the cards health
        int health = cardClientProxy.getHealth();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(health, is(77));
    }

    @Test
    public void shouldBeActive() {
        // Given a stub card which is hardcoded to
        // Return the true as active status

        // When I ask for the cards active status
        boolean isActive = cardClientProxy.isActive();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(isActive, is(true));
    }
}
