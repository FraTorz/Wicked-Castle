package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonGenerator;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

public class GameLoader {

    public GameEngine load(SaveData data) {
        GameRandom random = new GameRandom(data.getSeed());
        DungeonGenerator generator = new DungeonGenerator(random, data.getDifficulty());
        DungeonMap map = generator.generate();
        map.restoreVisitedPositions(data.getVisitedPositions());
        Player player = new Player(data.getPlayerHealth(), data.getPlayerPosition());
        Weapon weapon = WeaponFactory.createWeapon(data.getEquippedWeapon());
        player.restoreWeapon(weapon);
        player.restoreHints(data.getHints());
        return new GameEngine(map, player, random);
    }
}