package hotstone.broker.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.broker.service.NameService;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class HotStoneHeroInvoker implements Invoker {
    private final Game servant;
    private final Gson gson;
    private final NameService nameService;

    public HotStoneHeroInvoker(Gson gson, NameService nameService) {
        this.gson = gson;
        this.nameService = nameService;
    }

    private Hero lookupHero(String objectId) {
        return nameService.getHero(objectId);
    }

    @Override
    public String handleRequest(String request) {
        // Do the demarshalling
        RequestObject requestObject =
                gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();

        ReplyObject reply = null;

        try {
            // Dispatching: Check the operation name
            String operationName = requestObject.getOperationName();
            if (operationName.equals(OperationNames.HERO_GET_MANA)) {
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
