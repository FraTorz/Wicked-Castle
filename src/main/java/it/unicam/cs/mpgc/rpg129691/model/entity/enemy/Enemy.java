package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;

public abstract class Enemy extends Entity {

    public Enemy(String name, int health, int minDamage, int maxDamage) {
        super(name, health);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    private final int minDamage;
    private final int maxDamage;

    @Override
    public int getMinDamage() { return minDamage; }

    @Override
    public int getMaxDamage() { return maxDamage; }
}