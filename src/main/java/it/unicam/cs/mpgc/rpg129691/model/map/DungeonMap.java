package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.room.*;

import java.util.Random;

public class DungeonMap {
    private final Room[][] rooms;
    private final int size;
    private Position exitPosition;

    public DungeonMap(int size) {
        this.size = size;
        this.rooms = new Room[size][size];
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

    public void setExitPosition(Position exitPosition) {
        this.exitPosition = exitPosition;
    }

    public Position getStartPosition() {
        int center = size / 2;
        return new Position(center, center);
    }

}
