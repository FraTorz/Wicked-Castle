package it.unicam.cs.mpgc.rpg129691.model.map;

import java.util.Objects;

/**
 * Rappresenta una posizione bidimensionale all'interno della mappa del gioco.
 *
 * La posizione è immutabile e definita da:
 * <ul>
 *     <li>riga (row)</li>
 *     <li>colonna (column)</li>
 * </ul>
 *
 * Viene utilizzata per identificare celle della DungeonMap
 * e per il movimento del giocatore.
 */
public class Position {

    private final int row;
    private final int column;

    /**
     * Crea una nuova posizione con coordinate specificate.
     *
     * @param row riga della mappa
     * @param column colonna della mappa
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Restituisce la riga della posizione.
     *
     * @return indice della riga
     */
    public int getRow() {
        return row;
    }

    /**
     * Restituisce la colonna della posizione.
     *
     * @return indice della colonna
     */
    public int getColumn() {
        return column;
    }

    /**
     * Calcola la distanza di Manhattan tra questa posizione e un'altra.
     *
     * Utilizzata per logiche di gioco come:
     * - generazione indizi
     * - distanza dall'uscita
     *
     * @param other altra posizione
     * @return distanza di Manhattan tra le due posizioni
     */
    public int distanceTo(Position other) {
        return Math.abs(row - other.row) + Math.abs(column - other.column);
    }

    /**
     * Confronta due posizioni per uguaglianza logica.
     *
     * Due posizioni sono uguali se hanno stessa riga e stessa colonna.
     *
     * @param o oggetto da confrontare
     * @return true se le posizioni coincidono
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return this.row == position.row && this.column == position.column;
    }

    /**
     * Genera hash code coerente con equals.
     *
     * @return hash della posizione
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
    }
}