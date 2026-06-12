package it.unicam.cs.mpgc.rpg129691.model.game;

/**
 * Rappresenta lo stato corrente della partita.
 */
public enum GameState {

    /**
     * La partita è in corso e il giocatore può continuare a giocare.
     */
    RUNNING,

    /**
     * Il giocatore ha raggiunto l'uscita del dungeon.
     */
    PLAYER_WON,

    /**
     * Il giocatore è stato sconfitto.
     */
    PLAYER_LOST
}