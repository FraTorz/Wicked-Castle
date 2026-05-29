package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class EnemyRoom extends Room{
    private final Enemy enemy;

    public EnemyRoom(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void enter(Player player) {
        this.setVisited(true);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
