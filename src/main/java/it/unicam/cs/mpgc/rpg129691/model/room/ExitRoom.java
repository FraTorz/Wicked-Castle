package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;

public class ExitRoom extends Room{
    @Override
    public void enter(Player player) {
        super.setVisited(true);
    }

    @Override
    public char getSymbol() {
        return 'E';
    }
}
