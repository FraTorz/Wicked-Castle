package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.EnemyRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.ExitRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.Room;

public class GameEngine {
    private final DungeonMap map;
    private final Player player;
    private final CombatSystem combatSystem;
    private GameState gameState;

    public GameEngine(DungeonMap map, Player player) {
        this.map = map;
        this.player = player;
        this.combatSystem = new CombatSystem();
        this.gameState = GameState.RUNNING;
    }

    public void movePlayer(Direction direction) {
        Position next = nextPosition(direction);
        if(map.isInside(next)) {
            player.moveTo(next);
            Room room = map.getRoom(next);
            room.enter(player);
            checkPlayerStatus();
            checkEnemyRoom(room);
            checkExitRoom(room);
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

    private void checkEnemyRoom(Room room){
        if(room instanceof EnemyRoom enemyRoom) {
            CombatResult result = combatSystem.fight(player, enemyRoom.getEnemy());
            for(String message : result.getCombatLog().getMessages()) {
                System.out.println(message);
            }
            if(!result.hasPlayerWon()) {
                gameState = GameState.PLAYER_LOST;
            }
        }
    }

    private void checkPlayerStatus() {
        if (player.getHealth() <= 0) {
            gameState = GameState.PLAYER_LOST;
        }
    }

    private void checkExitRoom(Room room) {
        if(room instanceof ExitRoom) {
            gameState = GameState.PLAYER_WON;
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
