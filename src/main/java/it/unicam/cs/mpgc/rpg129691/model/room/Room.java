package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public interface Room {
    void enter(Player player);

    boolean isVisited();

    void setVisited(boolean visited);

    char getSymbol();
}
