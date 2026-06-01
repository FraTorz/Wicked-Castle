package it.unicam.cs.mpgc.rpg129691.model.map;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.room.*;

import java.util.HashSet;
import java.util.Set;

public class DungeonMap {
    private final Room[][] rooms;
    private final int size;
    private Position exitPosition;
    private final long seed;
    private final Difficulty difficulty;
    private final Set<Position> visitedPositions;

    public DungeonMap(long seed, Difficulty difficulty) {
        this.size = difficulty.getMapSize();
        this.rooms = new Room[size][size];
        this.seed = seed;
        this.difficulty = difficulty;
        this.visitedPositions = new HashSet<>();
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

    public void visit(Position position) {
        visitedPositions.add(position);
    }

    public boolean isVisited(Position position) {
        return visitedPositions.contains(position);
    }

    public long getSeed() {
        return seed;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Set<Position> getVisitedPositions() {
        return Set.copyOf(visitedPositions);
    }

    public void restoreVisitedPositions(Set<Position> visitedPositions) {
        this.visitedPositions.clear();
        for(Position position : visitedPositions) {
            this.visitedPositions.add(position);
            if(!position.equals(this.exitPosition)) {
                setRoom(position, new EmptyRoom());
            }
        }
    }
}
