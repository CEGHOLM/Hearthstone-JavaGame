package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.broker.service.StandardNameService;
import hotstone.doubles.StubCard;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.NameService;
import hotstone.framework.Player;
import hotstone.framework.mutability.MutableCard;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHotStoneGame;
import hotstone.variants.NullEffect;
import hotstone.variants.alphastone.AlphaStoneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCardBroker {
    // The client side's card client proxy
    private Card cardClientProxy;

    @BeforeEach
    public void setup() {
        // Create and populate the name service with our stub card
        NameService nameService = new StandardNameService();
        String id = "id";
        nameService.addCard(id, new StubCard("Card", 17, 15, 77, true, Player.FINDUS, new NullEffect()));
        // === Server side setup ===
        // Create a stub servant with canned output
        Game servant = new StandardHotStoneGame(new AlphaStoneFactory());
        Invoker invoker = new HotStoneGameInvoker(servant);

        // === Client side setup ===
        // Create a local method client request handler
        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);

        // Create the CardClientProxy to be tested
        Game proxy = new GameClientProxy(requestor);
        cardClientProxy = proxy.getCardInHand(Player.FINDUS, 0);
    }

    @Test
    public void shouldHaveCardAsName() {
        // Given a stub card which is hardcoded to
        // Return the name "name" as name

        // When I ask for the cards name
        String name = cardClientProxy.getName();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's name reply.
        assertThat(name, is(GameConstants.TRES_CARD));
    }

    @Test
    public void shouldCost17Mana() {
        // Given a stub card which is hardcoded to
        // Return 17 as mana cost

        // When I ask for the cards mana cost
        int manaCost = cardClientProxy.getManaCost();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's 17 reply.
        assertThat(manaCost, is(3));
    }

    @Test
    public void shouldHave15Attack() {
        // Given a stub card which is hardcoded to
        // Return 15 as attack

        // When I ask for the cards attack
        int attack = cardClientProxy.getAttack();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's 15 reply.
        assertThat(attack, is(3));
    }

    @Test
    public void shouldHave77Health() {
        // Given a stub card which is hardcoded to
        // Return 77 as health

        // When I ask for the cards health
        int health = cardClientProxy.getHealth();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's 77 reply.
        assertThat(health, is(3));
    }

    @Test
    public void shouldNotBeActive() {
        // Given a stub card which is hardcoded to
        // Return true as active status

        // When I ask for the cards active status
        boolean isActive = cardClientProxy.isActive();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's true reply.
        assertThat(isActive, is(false));
    }

    @Test
    public void shouldHaveFindusAsOwner() {
        // Given a stub card which is hardcoded to
        // Return Findus as owner

        // When I ask for the cards owner
        Player owner = cardClientProxy.getOwner();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's Findus reply.
        assertThat(owner, is(Player.FINDUS));
    }

    @Test
    public void shouldHaveEmptyEffectDescription() {
        // Given a stub card which is hardcoded to
        // Return an empty effect description

        // When I ask for the effect description of the card
        String effectDescription = cardClientProxy.getEffectDescription();

        // Then the broker chain (clientProxy -> requestor ->
        // client request handler -> invoker -> servant) will
        // return the stub's "" reply.
        assertThat(effectDescription, is(""));
    }
}
