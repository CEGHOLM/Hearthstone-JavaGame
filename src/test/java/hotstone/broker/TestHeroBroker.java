package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.doubles.StubCard;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestHeroBroker {
    private Hero heroClientProxy;

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
        heroClientProxy = new HeroClientProxy(requestor);
    }

    @Test
    public void shouldHave88Mana() {
        // Given a stub hero which is hardcoded to
        // Return the 88 mana

        // When I ask for the heroes mana
        int mana = heroClientProxy.getMana();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's mana reply.
        assertThat(mana, is(88));
    }

    @Test
    public void shouldHave111Health() {
        // Given a stub hero which is hardcoded to
        // Return the 111 health

        // When I ask for the heroes health
        int health = heroClientProxy.getHealth();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's health reply.
        assertThat(health, is(111));
    }

    @Test
    public void shouldBeActive() {
        // Given a stub hero which is hardcoded to
        // Return be able to use it's power

        // When I ask for the power status
        boolean canUsePower = heroClientProxy.canUsePower();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's true reply.
        assertThat(canUsePower, is(true));
    }

    @Test
    public void shouldBeTypeBaby() {
        // Given a stub hero which is hardcoded to
        // Return Baby as hero type

        // When I ask for the hero type
        String type = heroClientProxy.getType();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's true reply.
        assertThat(type, is(GameConstants.BABY_HERO_TYPE));
    }
}
