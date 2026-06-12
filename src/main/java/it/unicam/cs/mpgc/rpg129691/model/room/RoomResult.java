package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;

/**
 * Rappresenta il risultato generato dall'interazione tra il giocatore
 * e una stanza della mappa.
 *
 * Questa classe funge da oggetto di trasferimento dati (DTO)
 * tra il livello di logica di gioco (Room / GameEngine)
 * e il sistema di gestione eventi.
 *
 * Contiene:
 * <ul>
 *     <li>Il tipo di risultato dell'interazione</li>
 *     <li>Un eventuale nemico coinvolto (se presente)</li>
 *     <li>Un messaggio descrittivo dell'evento</li>
 * </ul>
 */
public class RoomResult {

    private final RoomResultType type;
    private final Enemy enemy;
    private final String message;

    /**
     * Costruisce un nuovo risultato di interazione con una stanza.
     *
     * @param type tipo di risultato dell'azione (combattimento, danno, ecc.)
     * @param enemy nemico coinvolto, se presente (può essere null)
     * @param message messaggio descrittivo dell'evento
     */
    public RoomResult(RoomResultType type, Enemy enemy, String message) {
        this.type = type;
        this.enemy = enemy;
        this.message = message;
    }

    /**
     * Restituisce il tipo di risultato generato dalla stanza.
     *
     * @return tipo di risultato dell'interazione
     */
    public RoomResultType getType() {
        return type;
    }

    /**
     * Restituisce il nemico coinvolto nell'evento, se presente.
     *
     * @return nemico oppure null se non applicabile
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Restituisce il messaggio descrittivo dell'evento.
     *
     * @return messaggio associato al risultato
     */
    public String getMessage() {
        return message;
    }
}