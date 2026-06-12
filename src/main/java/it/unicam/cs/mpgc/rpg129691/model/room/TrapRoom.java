package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza contenente una trappola.
 *
 * Quando il giocatore entra nella stanza, subisce immediatamente
 * una quantità fissa di danno.
 *
 * Questa stanza modifica direttamente lo stato del giocatore.
 */
public class TrapRoom implements Room {

    private final int damagePoints;

    /**
     * Crea una trappola con un determinato valore di danno.
     *
     * @param damagePoints danno inflitto al giocatore
     */
    public TrapRoom(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    /**
     * Applica il danno al giocatore e restituisce il risultato dell'evento.
     *
     * @param player il giocatore che entra nella stanza
     * @return risultato di tipo PLAYER_DAMAGED
     */
    @Override
    public RoomResult enter(Player player) {
        player.takeDamage(damagePoints);

        return new RoomResult(
                RoomResultType.PLAYER_DAMAGED,
                null,
                "Hai attivato una trappola e subito " + damagePoints + " danni."
        );
    }

    /**
     * Restituisce lo sprite della trappola per la visualizzazione.
     *
     * @return sprite della trappola
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.TRAP);
    }
}