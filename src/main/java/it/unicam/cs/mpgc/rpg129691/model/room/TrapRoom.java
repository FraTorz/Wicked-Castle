package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class TrapRoom implements Room{

    private final int damagePoints;

    public TrapRoom(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    @Override
    public RoomResult enter(Player player) {
        player.takeDamage(damagePoints);
        return new RoomResult(
                RoomResultType.PLAYER_DAMAGED,
                null,
                "Hai attivato una trappola e subito " + damagePoints + " danni."
        );
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.TRAP);
    }

}
