package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.EnemyFactory;
import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;

public class RoomFactory {
    private final GameRandom random;
    private final Difficulty difficulty;
    private final WeaponFactory weaponFactory;
    private final EnemyFactory enemyFactory;

    public RoomFactory(GameRandom random, Difficulty difficulty) {
        this.random = random;
        this.difficulty = difficulty;
        this.weaponFactory = new WeaponFactory(random);
        this.enemyFactory = new EnemyFactory(random);
    }

    public Room createRandomRoom() {
        int value = random.nextInt(100);
        int threshold = difficulty.getEmptyProbability();
        if(value < threshold) {
            return new EmptyRoom();
        }
        threshold += difficulty.getHealingProbability();
        if(value < threshold) {
            return new HealingRoom(difficulty.getHealingAmount());
        }
        threshold += difficulty.getTrapProbability();
        if(value < threshold) {
            return new TrapRoom(difficulty.getTrapDamage());
        }
        threshold += difficulty.getTreasureProbability();
        if(value < threshold) {
            return new TreasureRoom(weaponFactory.createRandomWeapon());
        }
        return new EnemyRoom(enemyFactory.createRandomEnemy());
    }
}
