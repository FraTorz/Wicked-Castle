package it.unicam.cs.mpgc.rpg129691.model.item;

public class Sword implements Weapon{
    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getMinDamage() {
        return 7;
    }

    @Override
    public int getMaxDamage() {
        return 15;
    }
}
