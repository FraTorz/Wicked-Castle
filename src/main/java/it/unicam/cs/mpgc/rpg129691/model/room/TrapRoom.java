package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class TrapRoom implements Room{
    private boolean visited;
    private final int damagePoints;

    public TrapRoom(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    @Override
    public void enter(Player player) {
        player.takeDamage(damagePoints);
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
        return 'X';
    }
}
