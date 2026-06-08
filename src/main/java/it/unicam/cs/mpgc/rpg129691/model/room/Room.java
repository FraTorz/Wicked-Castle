package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public interface Room {

    RoomResult enter(Player player);

    default boolean isConsumable() { return true; }

    Optional<SpriteProvider> getOverlaySprite();
}