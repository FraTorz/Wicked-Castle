package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class TreasureRoom extends Room {
    private final Weapon weapon;

    public TreasureRoom(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public RoomResult enter(Player player) {
        player.equipWeapon(weapon);
        super.setVisited(true);
        return new RoomResult(RoomResultType.NOTHING, null);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}