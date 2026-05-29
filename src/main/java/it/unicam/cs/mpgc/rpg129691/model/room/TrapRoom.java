package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class TrapRoom extends Room{
    private final int damagePoints;

    public TrapRoom(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    @Override
    public void enter(Player player) {
        player.takeDamage(damagePoints);
        super.setVisited(true);
    }

    @Override
    public char getSymbol() {
        return 'X';
    }
}
