package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class TrapRoom extends Room{
    private final int damagePoints;

    public TrapRoom(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    @Override
    public RoomResult enter(Player player) {
        player.takeDamage(damagePoints);
        super.setVisited(true);
        return new RoomResult(RoomResultType.PLAYER_DAMAGED, null);
    }

    @Override
    public char getSymbol() {
        return 'X';
    }
}
