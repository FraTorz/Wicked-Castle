package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class HealingRoom extends Room{
    private final int healingPoints;

    public HealingRoom(int healingPoints) {
        this.healingPoints = healingPoints;
    }

    @Override
    public RoomResult enter(Player player) {
        player.heal(healingPoints);
        super.setVisited(true);
        return new RoomResult(RoomResultType.PLAYER_HEALED, null);
    }

    @Override
    public char getSymbol() {
        return '+';
    }
}
