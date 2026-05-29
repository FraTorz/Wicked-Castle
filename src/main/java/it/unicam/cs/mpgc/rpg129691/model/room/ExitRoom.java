package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class ExitRoom extends Room{
    @Override
    public RoomResult enter(Player player) {
        super.setVisited(true);
        return new RoomResult(RoomResultType.PLAYER_ESCAPED, null);
    }

    @Override
    public char getSymbol() {
        return 'E';
    }
}
