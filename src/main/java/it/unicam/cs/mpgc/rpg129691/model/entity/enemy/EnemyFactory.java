package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;

/**
 * Factory responsabile della creazione di nemici nel gioco.
 *
 * Incapsula la logica di generazione casuale degli enemy,
 * evitando che GameEngine o altre classi conoscano i dettagli
 * delle probabilità o dei tipi concreti disponibili.
 *
 * Questo migliora:
 * <ul>
 *     <li>SRP (responsabilità unica)</li>
 *     <li>estendibilità (nuovi nemici senza modificare GameEngine)</li>
 * </ul>
 */
public class EnemyFactory {

    private final GameRandom random;

    /**
     * Crea una nuova EnemyFactory.
     *
     * @param random generatore di numeri casuali astratto
     */
    public EnemyFactory(GameRandom random) {
        this.random = random;
    }

    /**
     * Genera un nemico casuale basato su distribuzione probabilistica.
     *
     * Distribuzione attuale:
     * <ul>
     *     <li>40% Phantom</li>
     *     <li>35% Giant Spider</li>
     *     <li>25% Dark Wizard</li>
     * </ul>
     *
     * @return un'istanza concreta di Enemy
     */
    public Enemy createRandomEnemy() {
        int value = random.nextInt(100);
        if (value < 40) return new Phantom();
        if (value < 75) return new GiantSpider();
        return new DarkWizard();
    }

}