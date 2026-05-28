package it.unicam.cs.mpgc.rpg129691.model.item;

public class Axe implements Weapon{
    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public int getMinDamage() {
        return 10;
    }

    @Override
    public int getMaxDamage() {
        return 20;
    }
}
