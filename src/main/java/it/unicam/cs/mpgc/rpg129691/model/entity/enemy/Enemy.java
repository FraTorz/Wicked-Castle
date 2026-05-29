package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;

public abstract class Enemy extends Entity {
    private final String name;
    private final int minDamage;
    private final int maxDamage;

    public Enemy(String name, int health, int minDamage, int maxDamage) {
        super(health);
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public int getMinDamage() {
        return minDamage;
    }

    @Override
    public int getMaxDamage() {
        return maxDamage;
    }

    public String getName() {
        return name;
    }

}
