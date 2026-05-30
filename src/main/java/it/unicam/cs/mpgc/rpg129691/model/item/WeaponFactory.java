package it.unicam.cs.mpgc.rpg129691.model.item;

import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;

import java.util.Random;

public class WeaponFactory {
    private final GameRandom random;

    public WeaponFactory(GameRandom random) {
        this.random = random;
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