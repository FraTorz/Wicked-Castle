package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza vuota della dungeon map.
 *
 * L'EmptyRoom non produce alcun effetto sul giocatore
 * ed è utilizzata principalmente come stato finale
 * delle stanze consumate o già esplorate.
 */
public class EmptyRoom implements Room {

    /**
     * Gestisce l'ingresso del giocatore nella stanza vuota.
     *
     * Non viene eseguita alcuna azione significativa e
     * viene restituito un risultato di tipo NOTHING.
     *
     * @param player il giocatore che entra nella stanza
     * @return risultato di tipo NOTHING con messaggio descrittivo
     */
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.NOTHING,
                null,
                "La stanza è vuota."
        );
    }

    /**
     * Indica che la stanza vuota non è consumabile,
     * in quanto rappresenta uno stato terminale della cella.
     *
     * @return false, la stanza non viene mai consumata
     */
    @Override
    public boolean isConsumable() {
        return false;
    }

    /**
     * Le stanze vuote non hanno alcuno sprite associato.
     *
     * @return Optional vuoto
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.empty();
    }
}