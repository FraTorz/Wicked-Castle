package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public abstract class Room{
    private boolean visited;

    public abstract RoomResult enter(Player player);

    public abstract char getSymbol();

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        if(this.visited) return;
        this.visited = visited;
    }
}
