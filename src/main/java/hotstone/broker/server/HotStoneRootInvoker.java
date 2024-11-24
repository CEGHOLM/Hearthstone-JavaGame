package hotstone.broker.server;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.broker.service.NameService;
import hotstone.broker.service.StandardNameService;
import hotstone.framework.Game;

import java.util.HashMap;
import java.util.Map;

public class HotStoneRootInvoker implements Invoker {
    private final Gson gson;
    private final Map<String, Invoker> invokerMap;

    public HotStoneRootInvoker(Game servant) {
        this.gson = new Gson();

        NameService nameService = new StandardNameService();
        invokerMap = new HashMap<>();

        // Create an invoker for each handled type/class
        // and put them in a map, binding them to the
        // operationName prefixes
        Invoker gameInvoker = new HotStoneGameInvoker(servant);
        invokerMap.put(OperationNames.GAME_PREFIX, gameInvoker);
        Invoker cardInvoker = new HotStoneCardInvoker(servant, gson, nameService);
        invokerMap.put(OperationNames.CARD_PREFIX, cardInvoker);
        Invoker heroInvoker = new HotStoneHeroInvoker(servant, gson, nameService);
        invokerMap.put(OperationNames.HERO_PREFIX, heroInvoker);
    }

    @Override
    public String handleRequest(String request) {
        // Do the demarshalling
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String operationName = requestObject.getOperationName();

        // Identify the invoker to use
        String type = operationName.substring(0, operationName.indexOf(OperationNames.SEPARATOR));
        Invoker subInvoker = invokerMap.get(type);

        String reply;
        // And do the upcall on the subInvoker
        try {
            reply = subInvoker.handleRequest(request);

        } catch (Exception e) {
            // Handle errors
            reply = gson.toJson(new ReplyObject(500, "Server error: " + e.getMessage()));
        }
        return reply;
    }
}
