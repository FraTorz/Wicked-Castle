package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class HealingRoom implements Room{
    private final int healingPoints;

    public HealingRoom(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    @Override
    public RoomResult enter(Player player) {
        player.heal(healingPoints);
        return new RoomResult(
                RoomResultType.PLAYER_HEALED,
                null,
                "Hai guadagnato " + healingPoints + " punti vita."
        );
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.POTION);
    }

}
