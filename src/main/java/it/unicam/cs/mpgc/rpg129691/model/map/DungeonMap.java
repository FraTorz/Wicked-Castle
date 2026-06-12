package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.room.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Rappresenta la mappa del dungeon di gioco.
 *
 * La mappa è una griglia quadrata di Room e gestisce:
 * <ul>
 *     <li>posizionamento delle stanze</li>
 *     <li>posizione di uscita</li>
 *     <li>stato di esplorazione (celle visitate)</li>
 *     <li>parametri di generazione (seed e difficoltà)</li>
 * </ul>
 *
 * Questa classe rappresenta il modello centrale della navigazione nel gioco.
 */
public class DungeonMap {

    private final Room[][] rooms;
    private final int size;
    private Position exitPosition;
    private final long seed;
    private final Difficulty difficulty;
    private final Set<Position> visitedPositions;

    /**
     * Crea una nuova mappa del dungeon.
     *
     * @param seed seme per la generazione deterministica della mappa
     * @param difficulty difficoltà selezionata (influenza dimensione e probabilità)
     */
    public DungeonMap(long seed, Difficulty difficulty) {
        this.size = difficulty.getMapSize();
        this.rooms = new Room[size][size];
        this.seed = seed;
        this.difficulty = difficulty;
        this.visitedPositions = new HashSet<>();
    }

    /**
     * Restituisce la dimensione della mappa (lato della griglia quadrata).
     *
     * @return dimensione della mappa
     */
    public int getSize() {
        return size;
    }

    /**
     * Restituisce la stanza presente in una determinata posizione.
     *
     * @param position posizione della cella
     * @return Room presente nella posizione indicata
     */
    public Room getRoom(Position position) {
        return rooms[position.getRow()][position.getColumn()];
    }

    /**
     * Imposta una stanza in una determinata posizione della mappa.
     *
     * @param position posizione della cella
     * @param room stanza da inserire
     */
    public void setRoom(Position position, Room room) {
        rooms[position.getRow()][position.getColumn()] = room;
    }

    /**
     * Verifica se una posizione è all'interno dei limiti della mappa.
     *
     * @param position posizione da verificare
     * @return true se la posizione è valida
     */
    public boolean isInside(Position position) {
        return position.getRow() >= 0 &&
                position.getRow() < size &&
                position.getColumn() >= 0 &&
                position.getColumn() < size;
    }

    /**
     * Restituisce la posizione della stanza di uscita del dungeon.
     *
     * @return posizione dell'uscita
     */
    public Position getExitPosition() {
        return exitPosition;
    }

    /**
     * Imposta la posizione della stanza di uscita.
     *
     * @param exitPosition posizione dell'uscita
     */
    public void setExitPosition(Position exitPosition) {
        this.exitPosition = exitPosition;
    }

    /**
     * Restituisce la posizione iniziale del giocatore.
     *
     * Per design è sempre il centro della mappa.
     *
     * @return posizione di partenza
     */
    public Position getStartPosition() {
        int center = size / 2;
        return new Position(center, center);
    }

    /**
     * Marca una posizione come visitata dal giocatore.
     *
     * @param position posizione visitata
     */
    public void visit(Position position) {
        visitedPositions.add(position);
    }

    /**
     * Verifica se una posizione è già stata esplorata.
     *
     * @param position posizione da controllare
     * @return true se la posizione è stata visitata
     */
    public boolean isVisited(Position position) {
        return visitedPositions.contains(position);
    }

    /**
     * Restituisce il seed utilizzato per la generazione della mappa.
     *
     * @return seed di generazione
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Restituisce la difficoltà della mappa.
     *
     * @return difficoltà attuale
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Restituisce una copia immutabile delle posizioni visitate.
     *
     * @return insieme delle posizioni esplorate
     */
    public Set<Position> getVisitedPositions() {
        return Set.copyOf(visitedPositions);
    }

    /**
     * Ripristina lo stato delle posizioni visitate durante il caricamento di una partita.
     *
     * Inoltre converte in EmptyRoom tutte le stanze già esplorate
     * (eccetto la stanza di uscita).
     *
     * @param visitedPositions insieme delle posizioni da ripristinare
     */
    public void restoreVisitedPositions(Set<Position> visitedPositions) {
        this.visitedPositions.clear();
        for (Position position : visitedPositions) {
            this.visitedPositions.add(position);
            if (!position.equals(this.exitPosition)) {
                setRoom(position, new EmptyRoom());
            }
        }
    }
}