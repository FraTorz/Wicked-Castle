package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;

public class EnemyRoom extends Room{
    private final Enemy enemy;

    public EnemyRoom(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public RoomResult enter(Player player) {
        this.setVisited(true);
        return new RoomResult(RoomResultType.COMBAT, enemy);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
