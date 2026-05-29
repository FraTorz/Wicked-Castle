package it.unicam.cs.mpgc.rpg129691.model.enemy;

public class Enemy {
    private final String name;
    private int health;
    private final int minDamage;
    private final int maxDamage;

    public Enemy(String name, int health, int minDamage, int maxDamage) {
        this.name = name;
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }
}
