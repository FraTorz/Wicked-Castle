package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import java.util.Random;

public class EnemyFactory {
    private final Random random;

    public EnemyFactory() {
        this.random = new Random();
    }

    public Enemy createRandomEnemy() {
        int value = random.nextInt(100);
        if(value < 40) {
            return new Skeleton();
        }
        if(value < 75) {
            return new Goblin();
        }
        return new CursedKnight();
    }
}
