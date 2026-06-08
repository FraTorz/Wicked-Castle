package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

public class EnemyRoom implements Room{
    private final Enemy enemy;

    public EnemyRoom(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.COMBAT,
                enemy,
                "Hai incontrato un " + enemy.getDisplayName()+ "."
        );
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(enemy);
    }
}
