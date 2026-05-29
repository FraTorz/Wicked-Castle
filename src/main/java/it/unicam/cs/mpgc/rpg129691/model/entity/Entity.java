package it.unicam.cs.mpgc.rpg129691.model.entity;

public abstract class Entity {
    private int health;

    protected Entity(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    protected void increaseHealth(int amount) {
        health += amount;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract int getMinDamage();

    public abstract int getMaxDamage();
}
