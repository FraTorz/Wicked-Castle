package it.unicam.cs.mpgc.rpg129691.model.item;

public abstract class Weapon {
    private final String name;
    private final int minDamage;
    private final int maxDamage;

    protected Weapon(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }
}