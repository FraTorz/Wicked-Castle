package it.unicam.cs.mpgc.rpg129691.model.hint;

import it.unicam.cs.mpgc.rpg129691.model.map.Position;

/**
 * Generatore di indizi (Hint) per il giocatore.
 *
 * Questo componente traduce la posizione del giocatore
 * e quella dell'uscita in un messaggio testuale utile.
 *
 * Il messaggio include:
 * <ul>
 *     <li>direzione dell'uscita rispetto al giocatore</li>
 *     <li>stima qualitativa della distanza</li>
 * </ul>
 *
 * Questa classe incapsula la logica di generazione degli hint
 * mantenendo separata la logica dal modello di gioco.
 */
public class HintGenerator {

    /**
     * Genera un nuovo indizio basato sulla posizione del giocatore
     * e sulla posizione dell'uscita.
     *
     * @param playerPos posizione attuale del giocatore
     * @param exitPos posizione dell'uscita
     * @return nuovo Hint con messaggio informativo
     */
    public Hint generate(Position playerPos, Position exitPos) {
        String direction = calculateDirection(playerPos, exitPos);
        String distance = calculateDistance(playerPos, exitPos);
        return new Hint("L'uscita si trova " + direction + ". " + distance, playerPos);
    }

    /**
     * Calcola la direzione dell'uscita rispetto al giocatore.
     *
     * @param player posizione del giocatore
     * @param exit posizione dell'uscita
     * @return descrizione testuale della direzione
     */
    private String calculateDirection(Position player, Position exit) {
        String vertical = getVerticalDirection(player, exit);
        String horizontal = getHorizontalDirection(player, exit);
        if (vertical.isEmpty()) return horizontal;
        if (horizontal.isEmpty()) return vertical;
        return vertical + "-" + horizontal;
    }

    private String getVerticalDirection(Position player, Position exit) {
        if (exit.getRow() < player.getRow()) return "a nord";
        if (exit.getRow() > player.getRow()) return "a sud";
        return "";
    }

    private String getHorizontalDirection(Position player, Position exit) {
        if (exit.getColumn() < player.getColumn()) return "ovest";
        if (exit.getColumn() > player.getColumn()) return "est";
        return "";
    }

    /**
     * Restituisce una descrizione qualitativa della distanza tra due posizioni.
     *
     * @param player posizione del giocatore
     * @param exit posizione dell'uscita
     * @return descrizione testuale della distanza
     */
    private String calculateDistance(Position player, Position exit) {
        int distance = player.distanceTo(exit);
        if (distance <= 2) return "L'uscita è molto vicina.";
        if (distance <= 5) return "L'uscita non è lontana.";
        if (distance <= 8) return "L'uscita è ancora distante.";
        return "L'uscita è molto lontana.";
    }
}