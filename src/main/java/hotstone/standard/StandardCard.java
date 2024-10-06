package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.mutability.MutableCard;
import hotstone.framework.Player;

public class StandardCard implements Card, MutableCard {
    private String name;
    private int manaCost;
    private int attack;
    private int health;
    private Player owner;
    private int turnsOnField; // Flag to track if the card has attacked in the current turn
    private boolean hasAttacked;

    public StandardCard(String name, int manaCost, int attack, int health, Player owner) {
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.turnsOnField = 0;
        this.hasAttacked = false;
        this.owner = owner;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isActive() {
        return turnsOnField >= 1; // Active if it's been on the field for at least 1 turn;
    }

    public void incrementTurnsOnField() {
        turnsOnField++;
        hasAttacked = false;
    }

    @Override
    public boolean canAttack() {
        return !this.hasAttacked && isActive(); // Can attack if it hasn't attacked yet this turn and is active
    }

    @Override
    public int takeDamage(int damage) {
        return this.health -= damage;
    }

    @Override
    public void attack() {
        this.hasAttacked = true; // Mark the card as having attacked
    }

    @Override
    public void increaseAttack(int i) {
        this.attack += i;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return null;
    }
}
