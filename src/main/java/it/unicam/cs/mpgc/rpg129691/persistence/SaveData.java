package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameState;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

import java.util.List;

public class SaveData {

    private long seed;

    private Difficulty difficulty;

    private Position playerPosition;

    private int playerHealth;

    private String equippedWeapon;

    private List<Position> visitedRooms;

    private List<Position> defeatedEnemies;

    private List<Position> collectedTreasures;

    private GameState gameState;

    // getter/setter

}