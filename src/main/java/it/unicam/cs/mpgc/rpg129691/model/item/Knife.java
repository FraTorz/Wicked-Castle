package it.unicam.cs.mpgc.rpg129691.model.item;

public class Knife implements Weapon{
    @Override
    public String getName() {
        return "Knife";
    }

    @Override
    public int getMinDamage() {
        return 3;
    }

    @Override
    public int getMaxDamage() {
        return 7;
    }
}
