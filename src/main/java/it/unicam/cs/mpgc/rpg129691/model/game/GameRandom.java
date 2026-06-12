package it.unicam.cs.mpgc.rpg129691.model.game;

import java.util.Random;

/**
 * Wrapper del generatore casuale utilizzato dal gioco.
 *
 * Incapsula un'istanza di {@link Random} inizializzata tramite seed,
 * garantendo la riproducibilità della generazione della mappa,
 * dei nemici, delle armi e degli eventi casuali.
 */
public class GameRandom {

    private final Random random;
    private final long seed;

    /**
     * Crea un nuovo generatore casuale utilizzando il seed specificato.
     *
     * @param seed seed utilizzato per inizializzare il generatore
     */
    public GameRandom(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    /**
     * Restituisce un numero casuale compreso tra 0 (incluso)
     * e bound (escluso).
     *
     * @param bound limite superiore escluso
     * @return numero casuale generato
     */
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * Restituisce un numero casuale compreso tra origin (incluso)
     * e bound (escluso).
     *
     * @param origin limite inferiore incluso
     * @param bound limite superiore escluso
     * @return numero casuale generato
     */
    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }

    /**
     * Restituisce il seed associato al generatore.
     *
     * @return seed utilizzato
     */
    public long getSeed() {
        return seed;
    }
}