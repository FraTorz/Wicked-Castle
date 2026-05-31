package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class EmptyRoom implements Room{
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(RoomResultType.NOTHING, null);
    }

    @Override
    public char getSymbol() {
        return '.';
    }

    @Override
    public boolean isConsumable(){
        return false;
    }
}
