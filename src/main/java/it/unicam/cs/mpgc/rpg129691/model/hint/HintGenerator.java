package it.unicam.cs.mpgc.rpg129691.model.hint;

import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class HintGenerator {
    public Hint generate(Position playerPos, Position exitPos) {
        String direction = calculateDirection(playerPos, exitPos);
        String distance = calculateDistance(playerPos,exitPos);
        return new Hint("L'uscita si trova " + direction + ". " + distance);
    }

    private String calculateDirection(Position player, Position exit) {
        StringBuilder direction = new StringBuilder();
        if(exit.getRow() < player.getRow()) {
            direction.append("a nord");
        }
        if(exit.getRow() > player.getRow()) {
            direction.append("a sud");
        }
        if(exit.getColumn() < player.getColumn()) {
            if(!direction.isEmpty()) {
                direction.append("-");
            } else {
                direction.append("a ");
            }
            direction.append("ovest");
        }
        if(exit.getColumn() > player.getColumn()) {
            if(!direction.isEmpty()) {
                direction.append("-");
            } else {
                direction.append("a ");
            }
            direction.append("est");
        }
        return direction.toString();
    }

    private String calculateDistance(Position player, Position exit) {
        int distance = player.distanceTo(exit);
        if(distance <= 2) {
            return "L'uscita è molto vicina.";
        }
        if(distance <= 5) {
            return "L'uscita non è lontana.";
        }
        if(distance <= 8) {
            return "L'uscita è ancora distante.";
        }
        return "L'uscita è molto lontana.";
    }
}