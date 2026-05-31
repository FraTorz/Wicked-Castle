package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.room.ExitRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomFactory;

public class DungeonGenerator {
    private final GameRandom random;
    private final Difficulty difficulty;
    private final RoomFactory roomFactory;


    public DungeonGenerator(GameRandom random, Difficulty difficulty) {
        this.random = random;
        this.difficulty = difficulty;
        this.roomFactory = new RoomFactory(random, difficulty);
    }

    public DungeonMap generate() {
        DungeonMap map = new DungeonMap(random.getSeed(), difficulty);
        initializeRooms(map);
        placeExitRoom(map);
        return map;
    }

    private void initializeRooms(DungeonMap map) {
        for(int row = 0; row < map.getSize(); row++) {
            for(int column = 0; column < map.getSize(); column++) {
                map.setRoom(new Position(row, column), roomFactory.createRandomRoom());
            }
        }
    }

    private void placeExitRoom(DungeonMap map) {
        Position start = map.getStartPosition();
        Position exitPosition;
        do {
            int row = random.nextInt(map.getSize());
            int column = random.nextInt(map.getSize());
            exitPosition = new Position(row, column);
        } while(start.distanceTo(exitPosition) < difficulty.getMinExitDistance());
        map.setRoom(exitPosition, new ExitRoom());
        map.setExitPosition(exitPosition);
    }

}
