package it.unicam.cs.mpgc.rpg129691.model.hint;

import it.unicam.cs.mpgc.rpg129691.model.map.Position;

/**
 * Rappresenta un indizio ottenuto dal giocatore durante il gioco.
 *
 * Un Hint è associato a una posizione specifica della mappa
 * e contiene un messaggio informativo utile al giocatore.
 *
 * L'identità di un Hint è basata esclusivamente sulla sua posizione
 * di origine.
 */
public class Hint {

    private final String message;
    private final Position sourcePosition;

    /**
     * Crea un nuovo indizio.
     *
     * @param message testo descrittivo dell'indizio
     * @param sourcePosition posizione della mappa da cui l'indizio proviene
     */
    public Hint(String message, Position sourcePosition) {
        this.message = message;
        this.sourcePosition = sourcePosition;
    }

    /**
     * Restituisce il messaggio associato all'indizio.
     *
     * @return messaggio dell'indizio
     */
    public String getMessage() {
        return message;
    }

    /**
     * Restituisce la posizione della mappa associata all'indizio.
     *
     * @return posizione di origine dell'indizio
     */
    public Position getSourcePosition() {
        return sourcePosition;
    }

    /**
     * Confronta due Hint basandosi esclusivamente sulla posizione di origine.
     *
     * Due Hint sono considerati uguali se provengono dalla stessa posizione,
     * indipendentemente dal messaggio.
     *
     * @param o oggetto da confrontare
     * @return true se i due Hint hanno la stessa posizione
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hint hint)) return false;
        return sourcePosition.equals(hint.sourcePosition);
    }

    /**
     * Genera hash code coerente con equals.
     *
     * @return hash basato sulla posizione di origine
     */
    @Override
    public int hashCode() {
        return sourcePosition.hashCode();
    }
}