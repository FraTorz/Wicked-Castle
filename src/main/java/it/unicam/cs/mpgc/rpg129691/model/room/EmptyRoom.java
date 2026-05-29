package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class EmptyRoom extends Room{
    @Override
    public RoomResult enter(Player player) {
        super.setVisited(true);
        return new RoomResult(RoomResultType.NOTHING, null);
    }

    @Override
    public char getSymbol() {
        return '.';
    }
}
