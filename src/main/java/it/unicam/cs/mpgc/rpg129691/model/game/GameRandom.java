package it.unicam.cs.mpgc.rpg129691.model.game;

import java.util.Random;

public class GameRandom {
    private final Random random;
    private final long seed;

    public GameRandom(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }

    public long getSeed() {
        return seed;
    }

}
