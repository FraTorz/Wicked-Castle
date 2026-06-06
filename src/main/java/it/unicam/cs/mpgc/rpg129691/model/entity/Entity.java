package it.unicam.cs.mpgc.rpg129691.model.entity;

public abstract class Entity {

    private final String displayName;
    private int health;

    protected Entity(String displayName, int health) {
        this.displayName = displayName;
        this.health = health;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract int getMinDamage();
    public abstract int getMaxDamage();

    protected void increaseHealth(int amount) {
        this.health += amount;
    }
    protected void setHealth(int health) {
        this.health = health;
    }
}