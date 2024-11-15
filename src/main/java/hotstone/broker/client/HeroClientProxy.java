package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.*;
import hotstone.framework.Player;

public class HeroClientProxy implements Hero, ClientProxy {
    private String id;
    private final Requestor requestor;

    public HeroClientProxy(Requestor requestor) {
        this.requestor = requestor;
        id = "pending";
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
        return "";
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public String getEffectDescription() {
        return "";
    }
}
