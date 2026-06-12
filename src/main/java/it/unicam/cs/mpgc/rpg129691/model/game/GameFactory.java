package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonGenerator;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

/**
 * Factory responsabile della creazione delle partite.
 *
 * Si occupa di inizializzare tutte le componenti necessarie:
 * <ul>
 *     <li>Generatore casuale</li>
 *     <li>Mappa del dungeon</li>
 *     <li>Giocatore</li>
 *     <li>Sistema di combattimento</li>
 *     <li>GameEngine</li>
 * </ul>
 */
public class GameFactory {

    /**
     * Salute iniziale assegnata al giocatore.
     */
    private static final int INITIAL_PLAYER_HEALTH = 100;

    /**
     * Crea una partita utilizzando difficoltà e seed specificati.
     *
     * Questo metodo è utilizzato sia per la creazione di nuove
     * partite sia per il caricamento di salvataggi esistenti.
     *
     * @param difficulty difficoltà della partita
     * @param seed seed utilizzato per la generazione del dungeon
     * @return nuova istanza completamente inizializzata di GameEngine
     */
    public GameEngine createSpecificGame(Difficulty difficulty, long seed) {
        GameRandom random = new GameRandom(seed);
        DungeonGenerator generator = new DungeonGenerator(random, difficulty);
        DungeonMap map = generator.generate();
        Player player = new Player(INITIAL_PLAYER_HEALTH, map.getStartPosition());
        CombatSystem combatSystem = new CombatSystem(random);
        return new GameEngine(map, player, combatSystem);
    }

    /**
     * Crea una nuova partita utilizzando un seed generato
     * dal timestamp corrente.
     *
     * @param difficulty difficoltà della partita
     * @return nuova partita
     */
    public GameEngine createNewGame(Difficulty difficulty) {
        return createSpecificGame(difficulty, System.currentTimeMillis());
    }
}