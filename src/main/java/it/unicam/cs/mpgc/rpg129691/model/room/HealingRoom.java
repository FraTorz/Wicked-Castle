package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza che ripristina punti vita al giocatore.
 *
 * Quando il giocatore entra nella stanza, viene immediatamente
 * applicato un effetto di cura e viene restituito un evento
 * di tipo PLAYER_HEALED.
 *
 * Questa stanza modifica direttamente lo stato del giocatore.
 */
public class HealingRoom implements Room {

    private final int healingPoints;

    /**
     * Crea una stanza di cura con una quantità fissa di punti vita.
     *
     * @param healingPoints quantità di vita ripristinata
     */
    public HealingRoom(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    /**
     * Applica l'effetto di guarigione al giocatore.
     *
     * Il giocatore recupera punti vita e viene restituito
     * un RoomResult che descrive l'evento.
     *
     * @param player il giocatore che entra nella stanza
     * @return risultato di tipo PLAYER_HEALED
     */
    @Override
    public RoomResult enter(Player player) {
        player.heal(healingPoints);

        return new RoomResult(
                RoomResultType.PLAYER_HEALED,
                null,
                "Hai guadagnato " + healingPoints + " punti vita."
        );
    }

    /**
     * Restituisce lo sprite della pozione per la visualizzazione.
     *
     * @return sprite della stanza di cura
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.POTION);
    }
}