package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Card;
import hotstone.framework.Effect;
import hotstone.framework.Player;

public class CardClientProxy implements Card, ClientProxy {
    private String id;
    private final Requestor requestor;

    public CardClientProxy(Requestor requestor) {
        this.requestor = requestor;
        id = "pending";
    }

    @Override
    public String getName() {
        String name =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_NAME, String.class);
        return name;
    }

    @Override
    public int getManaCost() {
        int manaCost =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_MANA_COST, Integer.class);
        return manaCost;
    }

    @Override
    public int getAttack() {
        int attack =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_ATTACK, Integer.class);
        return attack;
    }

    @Override
    public int getHealth() {
        int health =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_HEALTH, Integer.class);
        return health;
    }

    @Override
    public boolean isActive() {
        boolean isActive =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_IS_ACTIVE, Boolean.class);
        return isActive;
    }

    @Override
    public Player getOwner() {
        Player owner =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public String getEffectDescription() {
        String effectDescription =
                requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_EFFECT_DESCRIPTION, String.class);
        return effectDescription;
    }

    @Override
    public Effect getEffect() {
        return null;
    }
}
