package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;

public class RoomResult {
    private final RoomResultType type;
    private final Enemy enemy;

    public RoomResult(RoomResultType type, Enemy enemy) {
        this.type = type;
        this.enemy = enemy;
    }

    public RoomResultType getType() {
        return type;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
