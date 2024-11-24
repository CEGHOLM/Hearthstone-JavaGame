package hotstone.broker.server;

import com.google.gson.Gson;
import frds.broker.Invoker;
import hotstone.broker.service.NameService;
import hotstone.framework.Game;

public class HotStoneCardInvoker implements Invoker {
    private final Game servant;
    private final Gson gson;
    private final NameService nameService;

    public HotStoneCardInvoker(Game servant, Gson gson, NameService nameService) {
        this.servant = servant;
        this.gson = gson;
        this.nameService = nameService;
    }
    @Override
    public String handleRequest(String s) {
        return "";
    }
}
