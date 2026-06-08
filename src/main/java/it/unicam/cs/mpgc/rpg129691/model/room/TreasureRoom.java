package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class TreasureRoom implements Room {
    private final Weapon weapon;

    public TreasureRoom(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public RoomResult enter(Player player) {
        boolean equipped = player.equipWeapon(weapon);
        String message;
        message = "Hai trovato " + weapon.getName() +
                (equipped ? " e l'hai equipaggiata." : " ma la tua arma è migliore.");
        return new RoomResult(
                RoomResultType.TREASURE_FOUND,
                null,
                message
        );
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(weapon);
    }
}