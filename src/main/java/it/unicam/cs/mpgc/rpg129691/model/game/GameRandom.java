package it.unicam.cs.mpgc.rpg129691.model.game;

import java.util.Random;

public class GameRandom {
    private final Random random;

    public GameRandom() {
        this.random = new Random();
    }

    public GameRandom(long seed) {
        this.random = new Random(seed);
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}
