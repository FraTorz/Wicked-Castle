package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.EnemyFactory;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;

import java.util.Random;

public class RoomFactory {
    private final Random random;
    private final WeaponFactory weaponFactory;
    private final EnemyFactory enemyFactory;

    public RoomFactory() {
        this.random = new Random();
        this.weaponFactory = new WeaponFactory();
        this.enemyFactory = new EnemyFactory();
    }

    public Room createRandomRoom() {
        int value = random.nextInt(100);
        if(value < 35) {
            return new EmptyRoom();
        }
        if(value < 50) {
            return new HealingRoom(20);
        }
        if(value < 65) {
            return new TrapRoom(15);
        }
        if(value < 80) {
            return new TreasureRoom(weaponFactory.createRandomWeapon());
        }
        return new EnemyRoom(enemyFactory.createRandomEnemy());
    }
}
