package it.unicam.cs.mpgc.rpg129691.ui.render;

/**
 * Rappresenta i tipi di tile di base utilizzati per il rendering della mappa.
 *
 * Ogni tile definisce lo sfondo grafico della cella del dungeon,
 * indipendentemente dagli elementi sovrapposti (player, nemici, oggetti, ecc.).
 */
public enum BaseTileType {

    /**
     * Tile che rappresenta una stanza esplorata e percorribile.
     */
    FLOOR,

    /**
     * Tile che rappresenta un muro o una cella fuori dai limiti del dungeon.
     */
    WALL,

    /**
     * Tile che rappresenta una zona non ancora esplorata dal giocatore.
     */
    UNKNOWN
}