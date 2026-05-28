package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class TreasureRoom implements Room {
    private boolean visited;
    private final Weapon weapon;

    public TreasureRoom(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void enter(Player player) {
        player.equipWeapon(weapon);
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
        return 'T';
    }
}