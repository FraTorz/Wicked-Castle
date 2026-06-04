package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public interface Room{

     RoomResult enter(Player player);

    default boolean isConsumable(){
        return true;
    }

}
