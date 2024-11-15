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
}
