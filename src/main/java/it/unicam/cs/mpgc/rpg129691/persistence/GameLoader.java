package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

public class GameLoader {

    private final GameFactory gameFactory = new GameFactory();

    public GameEngine load(SaveData data) {
        GameEngine game = gameFactory.createSpecificGame(data.getDifficulty(), data.getSeed());
        DungeonMap map = game.getMap();
        map.restoreVisitedPositions(data.getVisitedPositions());
        Player player = game.getPlayer();
        restorePlayer(player, data);
        return game;
    }

    private void restorePlayer(Player player, SaveData data){
        player.restoreHealth(data.getPlayerHealth());
        player.moveTo(data.getPlayerPosition());
        Weapon weapon = WeaponFactory.createWeapon(data.getEquippedWeapon());
        player.restoreWeapon(weapon);
        player.restoreHints(data.getHints());

    }

}