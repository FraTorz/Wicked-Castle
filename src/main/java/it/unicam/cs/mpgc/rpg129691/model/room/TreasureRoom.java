package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza contenente un'arma (tesoro).
 *
 * Quando il giocatore entra nella stanza:
 * <ul>
 *     <li>recupera l'arma contenuta</li>
 *     <li>l'arma può essere equipaggiata se migliore di quella attuale</li>
 * </ul>
 */
public class TreasureRoom implements Room {

    private final Weapon weapon;

    /**
     * Crea una stanza contenente un'arma.
     *
     * @param weapon arma contenuta nel tesoro
     */
    public TreasureRoom(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gestisce l'ingresso del giocatore nella stanza.
     *
     * Il giocatore trova un'arma che può essere equipaggiata
     * se migliore di quella attualmente posseduta.
     *
     * @param player giocatore che entra nella stanza
     * @return risultato dell'interazione con la stanza
     */
    @Override
    public RoomResult enter(Player player) {
        boolean equipped = player.equipWeapon(weapon);
        String message = "Hai trovato " + weapon.getName() +
                (equipped ? " e l'hai equipaggiata." : ", ma la tua arma è migliore.");
        return new RoomResult(
                RoomResultType.TREASURE_FOUND,
                null,
                message
        );
    }

    /**
     * Restituisce lo sprite associato alla stanza.
     *
     * In questo caso lo sprite è quello dell'arma contenuta.
     *
     * @return sprite dell'arma
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(weapon);
    }
}