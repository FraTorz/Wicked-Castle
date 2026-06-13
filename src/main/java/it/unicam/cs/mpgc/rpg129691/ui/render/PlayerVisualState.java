package it.unicam.cs.mpgc.rpg129691.ui.render;

/**
 * Rappresenta lo stato visivo del giocatore all'interno dell'interfaccia grafica.
 *
 * Questo stato viene utilizzato esclusivamente dal sistema di rendering
 * per determinare quale sprite mostrare e non influenza in alcun modo
 * la logica di gioco.
 *
 * I valori disponibili sono:
 * <ul>
 *     <li>{@link #NORMAL} → aspetto standard durante la partita</li>
 *     <li>{@link #WIN} → aspetto mostrato dopo la vittoria</li>
 *     <li>{@link #DEAD} → aspetto mostrato dopo la sconfitta</li>
 * </ul>
 */
public enum PlayerVisualState {

    /**
     * Stato visivo normale del giocatore.
     */
    NORMAL,

    /**
     * Stato visivo utilizzato quando il giocatore ha vinto la partita.
     */
    WIN,

    /**
     * Stato visivo utilizzato quando il giocatore è stato sconfitto.
     */
    DEAD
}