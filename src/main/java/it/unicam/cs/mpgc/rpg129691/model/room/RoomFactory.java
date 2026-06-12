package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.EnemyFactory;
import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;

/**
 * Factory responsabile della creazione delle stanze del dungeon.
 *
 * La tipologia di stanza viene determinata in modo probabilistico
 * in base alla difficoltà del gioco.
 *
 * Dipende da:
 * <ul>
 *     <li>GameRandom per la randomizzazione</li>
 *     <li>Difficulty per le probabilità e parametri bilanciamento</li>
 *     <li>WeaponFactory per generare loot</li>
 *     <li>EnemyFactory per generare nemici</li>
 * </ul>
 */
public class RoomFactory {

    private final GameRandom random;
    private final Difficulty difficulty;
    private final WeaponFactory weaponFactory;
    private final EnemyFactory enemyFactory;

    /**
     * Crea una RoomFactory con i parametri di gioco.
     *
     * @param random generatore casuale
     * @param difficulty difficoltà del gioco
     */
    public RoomFactory(GameRandom random, Difficulty difficulty) {
        this.random = random;
        this.difficulty = difficulty;
        this.weaponFactory = new WeaponFactory(random);
        this.enemyFactory = new EnemyFactory(random);
    }

    /**
     * Genera una stanza casuale in base alle probabilità della difficoltà.
     *
     * L'ordine di generazione è:
     * <ol>
     *     <li>EmptyRoom</li>
     *     <li>HealingRoom</li>
     *     <li>TrapRoom</li>
     *     <li>TreasureRoom</li>
     *     <li>EnemyRoom (fallback)</li>
     * </ol>
     *
     * @return stanza generata casualmente
     */
    public Room createRandomRoom() {
        int value = random.nextInt(100);
        int threshold = difficulty.getEmptyProbability();
        if(value < threshold) {
            return new EmptyRoom();
        }
        threshold += difficulty.getHealingProbability();
        if(value < threshold) {
            return new HealingRoom(difficulty.getHealingAmount());
        }
        threshold += difficulty.getTrapProbability();
        if(value < threshold) {
            return new TrapRoom(difficulty.getTrapDamage());
        }
        threshold += difficulty.getTreasureProbability();
        if(value < threshold) {
            return new TreasureRoom(weaponFactory.createRandomWeapon());
        }
        return new EnemyRoom(enemyFactory.createRandomEnemy());
    }
}
