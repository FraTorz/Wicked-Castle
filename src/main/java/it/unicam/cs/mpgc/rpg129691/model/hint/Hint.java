package it.unicam.cs.mpgc.rpg129691.model.hint;

import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class Hint {

    private final String message;
    private final Position sourcePosition;

    public Hint(String message, Position sourcePosition) {
        this.message = message;
        this.sourcePosition = sourcePosition;
    }

    public String getMessage() {
        return message;
    }

    public Position getSourcePosition() {
        return sourcePosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hint hint)) return false;
        return sourcePosition.equals(hint.sourcePosition);
    }

    @Override
    public int hashCode() {
        return sourcePosition.hashCode();
    }
}