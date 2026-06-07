package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

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

}
