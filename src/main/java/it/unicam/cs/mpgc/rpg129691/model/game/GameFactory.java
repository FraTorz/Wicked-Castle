package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonGenerator;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

public class GameFactory {

    private static final int INITIAL_PLAYER_HEALTH = 100;

    public GameEngine createSpecificGame(Difficulty difficulty, long seed) {
        GameRandom random = new GameRandom(seed);
        DungeonGenerator generator = new DungeonGenerator(random, difficulty);
        DungeonMap map = generator.generate();
        Player player = new Player(INITIAL_PLAYER_HEALTH, map.getStartPosition());
        CombatSystem combatSystem = new CombatSystem(random);
        return new GameEngine(map, player, combatSystem);
    }

    public GameEngine createNewGame(Difficulty difficulty) {
        return createSpecificGame(difficulty, System.currentTimeMillis());
    }
}
