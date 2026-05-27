package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class HealingRoom implements Room{
    private boolean visited;
    private final int healingPoints;

    public HealingRoom(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    @Override
    public void enter(Player player) {
        player.heal(healingPoints);
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
        return '+';
    }
}
