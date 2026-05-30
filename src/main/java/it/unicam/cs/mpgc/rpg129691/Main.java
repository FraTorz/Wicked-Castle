package it.unicam.cs.mpgc.rpg129691;

import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class Main {
    static void main() {
        DungeonMap map = new DungeonMap(5);

        // inizializzazione stanze

        Player player = new Player(100, new Position(2,2));

        // GameEngine engine = new GameEngine(map, player);

        // test movimento
    }
}
