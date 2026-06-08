package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.FixedSprite;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class ExitRoom implements Room{
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.PLAYER_ESCAPED,
                null,
                "Proprio quando credevi di non farcela..."
        );
    }

    @Override
    public boolean isConsumable(){
        return false;
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(FixedSprite.EXIT);
    }

}
