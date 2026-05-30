package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.room.ExitRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomFactory;

public class DungeonGenerator {
    private final GameRandom random;
    private final RoomFactory roomFactory;

    public DungeonGenerator(GameRandom random) {
        this.random = random;
        this.roomFactory = new RoomFactory(random);
    }

    public DungeonMap generate(int size) {
        DungeonMap map = new DungeonMap(size);
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
        Position exitPosition;
        do {
            int row = random.nextInt(map.getSize());
            int column = random.nextInt(map.getSize());
            exitPosition = new Position(row, column);
        } while(isStartPosition(map, exitPosition));
        map.setRoom(exitPosition, new ExitRoom());
        map.setExitPosition(exitPosition);
    }

    private boolean isStartPosition(DungeonMap map, Position position) {
        Position start = map.getStartPosition();
        return start.equals(position);
    }
}
