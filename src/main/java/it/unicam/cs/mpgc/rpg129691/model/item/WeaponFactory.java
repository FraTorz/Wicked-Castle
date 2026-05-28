package it.unicam.cs.mpgc.rpg129691.model.item;

import java.util.Random;

public class WeaponFactory {
    private final Random random;
    public WeaponFactory() {
        this.random = new Random();
    }
    public Weapon createRandomWeapon() {
        int value = random.nextInt(100);
        if(value < 50) {
            return new Knife();
        }
        if(value < 80) {
            return new Sword();
        }
        return new Axe();
    }
}