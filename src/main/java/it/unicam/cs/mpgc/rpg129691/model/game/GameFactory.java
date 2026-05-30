package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonGenerator;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

public class GameFactory {

    public GameEngine createNewGame(Difficulty difficulty, long seed) {
        GameRandom random = new GameRandom(seed);
        DungeonGenerator generator = new DungeonGenerator(random, difficulty);
        DungeonMap map = generator.generate();
        Player player = new Player(GameConfig.INITIAL_PLAYER_HEALTH, map.getStartPosition());
        return new GameEngine(map, player, random);
    }

    public GameEngine createNewGame(Difficulty difficulty) {
        return createNewGame(difficulty, System.currentTimeMillis());
    }
}
