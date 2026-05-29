package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class HealingRoom extends Room{
    private final int healingPoints;

    public HealingRoom(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    @Override
    public void enter(Player player) {
        player.heal(healingPoints);
        super.setVisited(true);
    }

    @Override
    public char getSymbol() {
        return '+';
    }
}
