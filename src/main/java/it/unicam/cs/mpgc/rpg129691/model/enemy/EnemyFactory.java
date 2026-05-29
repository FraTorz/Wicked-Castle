package it.unicam.cs.mpgc.rpg129691.model.enemy;

import java.util.Random;

public class EnemyFactory {
    private final Random random;

    public EnemyFactory() {
        this.random = new Random();
    }

    public Enemy createRandomEnemy() {
        int value = random.nextInt(100);
        if(value < 40) {
            return new Enemy("Skeleton", 30, 4, 8);
        }
        if(value < 75) {
            return new Enemy("Goblin", 20, 6, 12);
        }
        return new Enemy("Cursed Knight", 50, 8, 14);
    }
}
