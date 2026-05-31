package it.unicam.cs.mpgc.rpg129691.model.map;

import java.util.Objects;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int distanceTo(Position other) {
        return Math.abs(row - other.row)
                + Math.abs(column - other.column);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Position position)) return false;
        return this.row == position.row && this.column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
    }
}
