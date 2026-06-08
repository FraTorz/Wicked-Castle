package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;

public class EnemyFactory {
    private final GameRandom random;

    public EnemyFactory(GameRandom random) {
        this.random = random;
    }

    public Enemy createRandomEnemy() {
        int value = random.nextInt(100);
        if(value < 40) {
            return new Phantom();
        }
        if(value < 75) {
            return new GiantSpider();
        }
        return new DarkWizard();
    }
}
