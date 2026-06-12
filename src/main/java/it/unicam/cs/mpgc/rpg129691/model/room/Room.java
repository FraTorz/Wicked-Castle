package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza della dungeon map.
 *
 * Una Room definisce il comportamento che viene attivato
 * quando il giocatore entra nella cella corrispondente della mappa.
 *
 * Le implementazioni concrete possono rappresentare:
 * <ul>
 *     <li>stanze vuote</li>
 *     <li>combattimenti</li>
 *     <li>tesori</li>
 *     <li>eventi speciali</li>
 * </ul>
 *
 * Questo approccio permette di estendere facilmente il sistema
 * aggiungendo nuove tipologie di stanza senza modificare il GameEngine.
 */
public interface Room {

    /**
     * Esegue la logica associata all'ingresso del giocatore nella stanza.
     *
     * @param player il giocatore che entra nella stanza
     * @return il risultato dell'interazione con la stanza
     */
    RoomResult enter(Player player);

    /**
     * Indica se la stanza può essere consumata dopo l'utilizzo.
     *
     * Le stanze consumabili vengono sostituite da stanze vuote
     * dopo essere state attivate.
     *
     * @return true se la stanza può essere consumata, false altrimenti
     */
    default boolean isConsumable() {
        return true;
    }

    /**
     * Restituisce eventualmente uno sprite associato alla stanza.
     *
     * Questo sprite viene utilizzato per il rendering della mappa.
     *
     * @return sprite opzionale della stanza
     */
    Optional<SpriteProvider> getOverlaySprite();
}