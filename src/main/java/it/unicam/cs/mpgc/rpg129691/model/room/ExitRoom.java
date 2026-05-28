package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class ExitRoom implements Room{
    private boolean visited;

    @Override
    public void enter(Player player) {
        visited = true;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public char getSymbol() {
        return 'E';
    }
}
