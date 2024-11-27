package hotstone.broker.server;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.broker.service.NameService;
import hotstone.framework.Card;
import hotstone.framework.Player;

public class HotStoneCardInvoker implements Invoker {
    private final Gson gson;
    private final NameService nameService;

    public HotStoneCardInvoker(Gson gson, NameService nameService) {
        this.gson = gson;
        this.nameService = nameService;
    }

    private Card lookupCard(String objectId) {
        return nameService.getCard(objectId);
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
        if (operationName.equals(OperationNames.CARD_GET_NAME)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getName() method
            String name = servant.getName();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(name));

        } else if (operationName.equals(OperationNames.CARD_GET_MANA_COST)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getManaCost() method
            int manaCost = servant.getManaCost();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(manaCost));

        } else if (operationName.equals(OperationNames.CARD_GET_ATTACK)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getAttack() method
            int attack = servant.getAttack();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(attack));

        } else if (operationName.equals(OperationNames.CARD_GET_HEALTH)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getHealth() method
            int health = servant.getHealth();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(health));

        } else if (operationName.equals(OperationNames.CARD_IS_ACTIVE)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants IsActive() method
            boolean isActive = servant.isActive();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(isActive));

        } else if (operationName.equals(OperationNames.CARD_GET_OWNER)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getOwner() method
            Player owner = servant.getOwner();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(owner));

        } else if (operationName.equals(OperationNames.CARD_GET_EFFECT_DESCRIPTION)) {
            // Lookup the right card to invoke the method on
            Card servant = lookupCard(objectId);

            // Call the servants getEffectDescription() method
            String effectDescription = servant.getEffectDescription();

            // Create reply
            reply = new ReplyObject(200, gson.toJson(effectDescription));

            // Hero methods
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
