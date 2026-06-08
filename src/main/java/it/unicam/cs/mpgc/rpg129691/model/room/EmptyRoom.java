package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class EmptyRoom implements Room{
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.NOTHING,
                null,
                "La stanza è vuota."
        );
    }

    @Override
    public boolean isConsumable(){
        return false;
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.empty();
    }
}
