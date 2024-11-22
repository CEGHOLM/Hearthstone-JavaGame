package hotstone.standard;

import hotstone.framework.*;
import hotstone.framework.mutability.MutableGame;
import hotstone.framework.mutability.MutableHero;

import java.util.UUID;

public class StandardHero implements Hero, MutableHero {
    private int mana;
    private int health;
    private String heroType;
    private boolean powerStatus = true;
    private Player owner;
    private Effect heroPower;
    private final String id;

    public StandardHero(int mana, int health, String heroType, Player owner, Effect heroPower) {
        this.mana = mana;
        this.health = health;
        this.heroType = heroType;
        this.owner = owner;
        this.heroPower = heroPower;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int newValue) {
        this.mana = newValue;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean canUsePower() {
        return powerStatus;
    }

    @Override
    public void setPowerStatus(boolean newStatus) {
        this.powerStatus = newStatus;
    }

    @Override
    public String getType() {
        return heroType;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return heroPower.getEffectDescription();
    }

    @Override
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    @Override
    public void takeDamage(int damage) {
        this.health += damage;
    }

    @Override
    public void usePower(MutableGame game) {
        heroPower.applyEffect(game, owner);  // Use the common Effect interface to apply power
    }

    @Override
    public String getID() {
        return id;
    }
}

