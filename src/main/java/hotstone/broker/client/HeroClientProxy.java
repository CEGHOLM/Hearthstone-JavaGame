package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.*;
import hotstone.framework.Player;

public class HeroClientProxy implements Hero, ClientProxy {
    private final String id;
    private final Requestor requestor;

    public HeroClientProxy(String id, Requestor requestor) {
        this.requestor = requestor;
        this.id = id;
    }

    @Override
    public int getMana() {
        int mana =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_MANA, Integer.class);
        return mana;
    }

    @Override
    public int getHealth() {
        int health =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_HEALTH, Integer.class);
        return health;
    }

    @Override
    public boolean canUsePower() {
        boolean canUsePower =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_IS_ACTIVE, Boolean.class);
        return canUsePower;
    }

    @Override
    public String getType() {
        String type =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_TYPE, String.class);
        return type;
    }

    @Override
    public Player getOwner() {
        Player owner =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public String getEffectDescription() {
        String effectDescription =
                requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_EFFECT_DESCRIPTION, String.class);
        return effectDescription;
    }

    @Override
    public String getID() {
        return id;
    }
}
