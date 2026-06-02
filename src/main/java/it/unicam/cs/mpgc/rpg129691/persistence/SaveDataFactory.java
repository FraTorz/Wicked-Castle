package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

import java.time.LocalDateTime;

public class SaveDataFactory {

    public SaveData create(GameEngine game) {
        SaveData data = new SaveData();
        DungeonMap map = game.getMap();
        Player player = game.getPlayer();
        data.setSeed(map.getSeed());
        data.setDifficulty(map.getDifficulty());
        data.setVisitedPositions(map.getVisitedPositions());
        data.setPlayerHealth(player.getHealth());
        data.setPlayerPosition(player.getPosition());
        data.setEquippedWeapon(player.getEquippedWeapon().getName());
        data.setHints(player.getHintLog().getHints());
        return data;
    }
}