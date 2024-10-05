package hotstone.standard;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.HeroPowerStrategy;
import hotstone.framework.Player;

import java.util.Random;

public abstract class StandardHero implements Hero, MutableHero {
    private int mana;
    private int health;
    private String heroType;
    private boolean powerStatus = true;
    private Player owner;
    private HeroPowerStrategy heroPowerStrategy;
    private Random randomGenerator; // Random generator for hero powers

    public StandardHero(int mana, int health, String heroType, Player owner, HeroPowerStrategy heroPowerStrategy) {
        this.mana = mana;
        this.health = health;
        this.heroType = heroType;
        this.owner = owner;
        this.heroPowerStrategy = heroPowerStrategy;
        this.randomGenerator = new Random(); // Default random generator
    }

    // Getter for random generator
    public Random getRandomGenerator() {
        return randomGenerator;
    }

    // Setter for random generator (used for test stubbing)
    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public int getMana() {
        return mana;
    }

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
        return heroPowerStrategy.getEffectDescription();
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public void usePower(Game game) {
        this.heroPowerStrategy.usePower(game, this);
    }
}

