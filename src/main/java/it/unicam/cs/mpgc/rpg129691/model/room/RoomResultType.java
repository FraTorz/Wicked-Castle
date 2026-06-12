package it.unicam.cs.mpgc.rpg129691.model.room;

/**
 * Rappresenta il tipo di risultato ottenuto dopo l'interazione
 * del giocatore con una stanza.
 *
 * Ogni valore descrive l'effetto principale generato dall'azione
 * del player all'interno del sistema di gioco.
 */
public enum RoomResultType {

    /**
     * Il giocatore ha tentato un movimento non valido (fuori mappa).
     */
    INVALID_MOVE,

    /**
     * Nessun evento rilevante è avvenuto nella stanza.
     */
    NOTHING,

    /**
     * Il giocatore ha subito danno (trappola o altro evento negativo).
     */
    PLAYER_DAMAGED,

    /**
     * Il giocatore ha recuperato punti vita.
     */
    PLAYER_HEALED,

    /**
     * È stato attivato un combattimento tra giocatore e nemico.
     */
    COMBAT,

    /**
     * Il giocatore ha trovato un oggetto (arma o loot).
     */
    TREASURE_FOUND,

    /**
     * Il giocatore ha raggiunto l'uscita e ha vinto la partita.
     */
    PLAYER_ESCAPED;
}