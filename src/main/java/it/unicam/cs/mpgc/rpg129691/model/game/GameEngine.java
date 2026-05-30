package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.Room;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomResult;

public class GameEngine {
    private final DungeonMap map;
    private final Player player;
    private final CombatSystem combatSystem;
    private GameState gameState;

    public GameEngine(DungeonMap map, Player player, GameRandom random) {
        this.map = map;
        this.player = player;
        this.combatSystem = new CombatSystem(random);
        this.gameState = GameState.RUNNING;
    }

    public void movePlayer(Direction direction) {
        Position next = nextPosition(direction);
        if(map.isInside(next)) {
            player.moveTo(next);
            Room room = map.getRoom(next);
            RoomResult result = room.enter(player);
            handleRoomResult(result);
        }
    }

    private Position nextPosition(Direction direction){
        Position current = player.getPosition();
        return switch(direction) {
            case NORTH -> new Position(current.getRow() - 1, current.getColumn());
            case SOUTH -> new Position(current.getRow() + 1, current.getColumn());
            case EAST -> new Position(current.getRow(), current.getColumn() + 1);
            case WEST -> new Position(current.getRow(), current.getColumn() - 1);
        };
    }

    private void handleRoomResult(RoomResult result) {
        switch(result.getType()) {
            case COMBAT -> handleCombat(result);
            case PLAYER_ESCAPED -> gameState = GameState.PLAYER_WON;
            default -> {}
        }
        checkPlayerStatus();
    }

    private void handleCombat(RoomResult roomResult){
        CombatResult combatResult = combatSystem.fight(player, roomResult.getEnemy());
        for(String message : combatResult.getCombatLog().getMessages()) {
            System.out.println(message);
        }
        if(!combatResult.hasPlayerWon()) {
            gameState = GameState.PLAYER_LOST;
        }
    }

    private void checkPlayerStatus() {
        if (player.getHealth() <= 0) {
            gameState = GameState.PLAYER_LOST;
        }
    }


    public GameState getGameState() {
        return gameState;
    }
}
