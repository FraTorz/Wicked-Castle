package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta la stanza di uscita del dungeon.
 *
 * Quando il giocatore entra in questa stanza, il gioco
 * viene considerato vinto e viene generato un evento
 * di tipo PLAYER_ESCAPED.
 *
 * Questa stanza rappresenta la condizione di vittoria
 * del gioco.
 */
public class ExitRoom implements Room {

    /**
     * Gestisce l'ingresso del giocatore nella stanza di uscita.
     *
     * L'ingresso in questa stanza determina la vittoria del giocatore.
     *
     * @param player il giocatore che entra nella stanza
     * @return risultato di tipo PLAYER_ESCAPED
     */
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.PLAYER_ESCAPED,
                null,
                "Proprio quando credevi di non farcela..."
        );
    }

    /**
     * La ExitRoom non viene consumata perché rappresenta
     * una condizione finale del gioco.
     *
     * @return false, la stanza rimane invariata
     */
    @Override
    public boolean isConsumable() {
        return false;
    }

    /**
     * Restituisce lo sprite della stanza di uscita.
     *
     * @return sprite fisso della exit
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.EXIT);
    }
}