package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.room.*;

import java.util.Random;

public class DungeonMap {
    private final Room[][] rooms;
    private final int size;
    private final RoomFactory roomFactory;
    private final Random random;
    private Position exitPosition;

    public DungeonMap(int size) {
        this.size = size;
        this.rooms = new Room[size][size];
        this.roomFactory = new RoomFactory();
        this.random = new Random();
        initializeRooms();
        placeExitRoom();
    }

    private void placeExitRoom() {
        Position exitPosition;
        do {
            int row = random.nextInt(size);
            int column = random.nextInt(size);
            exitPosition = new Position(row, column);
        } while(isStartPosition(exitPosition));
        setRoom(exitPosition, new ExitRoom());
        this.exitPosition = exitPosition;
    }

    private boolean isStartPosition(Position position) {
        int center = size / 2;
        return position.getRow() == center &&
                position.getColumn() == center;
    }

    private void initializeRooms() {
        for(int row = 0; row < size; row++) {
            for(int column = 0; column < size; column++) {
                rooms[row][column] = roomFactory.createRandomRoom();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Room getRoom(Position position) {
        return rooms[position.getRow()][position.getColumn()];
    }

    public void setRoom(Position position, Room room) {
        rooms[position.getRow()][position.getColumn()] = room;
    }

    public boolean isInside(Position position) {
        return position.getRow() >= 0 &&
                position.getRow() < size &&
                position.getColumn() >= 0 &&
                position.getColumn() < size;
    }

    public Position getExitPosition() {
        return exitPosition;
    }
}
