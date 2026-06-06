package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;

public class RoomResult {

    private final RoomResultType type;
    private final Enemy enemy;
    private final String message;

    public RoomResult(RoomResultType type, Enemy enemy, String message) {
        this.type = type;
        this.enemy = enemy;
        this.message = message;
    }

    public RoomResultType getType() {
        return type;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public String getMessage() {
        return message;
    }
}