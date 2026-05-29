package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public abstract class Room{
    private boolean visited;

    public abstract void enter(Player player);

    public abstract char getSymbol();

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        if(this.visited) return;
        this.visited = visited;
    }
}
