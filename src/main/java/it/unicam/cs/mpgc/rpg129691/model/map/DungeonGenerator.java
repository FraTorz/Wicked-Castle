package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.room.EmptyRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.ExitRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomFactory;

/**
 * Responsabile della generazione completa del DungeonMap.
 *
 * Questa classe incapsula tutta la logica di creazione della mappa,
 * separandola dal modello dati (DungeonMap).
 *
 * Si occupa di:
 * <ul>
 *     <li>inizializzazione delle stanze</li>
 *     <li>posizionamento della stanza di uscita</li>
 *     <li>rispetto delle regole di difficoltà</li>
 * </ul>
 *
 * Garantisce una generazione deterministica tramite seed.
 */
public class DungeonGenerator {

    private final GameRandom random;
    private final Difficulty difficulty;
    private final RoomFactory roomFactory;

    /**
     * Crea un generatore di dungeon basato su random e difficoltà.
     *
     * @param random generatore casuale deterministico
     * @param difficulty difficoltà del gioco (influenza la generazione)
     */
    public DungeonGenerator(GameRandom random, Difficulty difficulty) {
        this.random = random;
        this.difficulty = difficulty;
        this.roomFactory = new RoomFactory(random, difficulty);
    }

    /**
     * Genera una nuova mappa completa del dungeon.
     *
     * @return DungeonMap inizializzata e pronta per il gioco
     */
    public DungeonMap generate() {
        DungeonMap map = new DungeonMap(random.getSeed(), difficulty);
        initializeRooms(map);
        placeExitRoom(map);
        return map;
    }

    /**
     * Inizializza tutte le stanze della mappa.
     *
     * - La stanza di partenza viene sempre impostata come EmptyRoom
     * - Tutte le altre stanze vengono generate casualmente
     * - La posizione iniziale viene marcata come visitata
     *
     * @param map mappa da inizializzare
     */
    private void initializeRooms(DungeonMap map) {
        Position startPostion = map.getStartPosition();
        for (int row = 0; row < map.getSize(); row++) {
            for (int column = 0; column < map.getSize(); column++) {
                Position pos = new Position(row, column);
                if (pos.equals(startPostion)) {
                    map.setRoom(pos, new EmptyRoom());
                    map.visit(pos);
                } else {
                    map.setRoom(pos, roomFactory.createRandomRoom());
                }
            }
        }
    }

    /**
     * Posiziona la stanza di uscita nel dungeon.
     *
     * La posizione viene scelta casualmente ma deve rispettare:
     * - distanza minima dalla posizione iniziale
     * - coerenza con la difficoltà
     *
     * @param map mappa su cui posizionare l'uscita
     */
    private void placeExitRoom(DungeonMap map) {
        Position startPosition = map.getStartPosition();
        Position exitPosition;
        do {
            int row = random.nextInt(map.getSize());
            int column = random.nextInt(map.getSize());
            exitPosition = new Position(row, column);
        } while (startPosition.distanceTo(exitPosition) < difficulty.getMinExitDistance());
        map.setRoom(exitPosition, new ExitRoom());
        map.setExitPosition(exitPosition);
    }
}