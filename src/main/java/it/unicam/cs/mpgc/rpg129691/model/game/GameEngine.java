package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatLog;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.hint.HintGenerator;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.EmptyRoom;
import it.unicam.cs.mpgc.rpg129691.model.room.Room;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomResult;

public class GameEngine {
    private final DungeonMap map;
    private final Player player;
    private final CombatSystem combatSystem;
    private final HintGenerator hintGenerator;
    private GameState gameState;

    public GameEngine(DungeonMap map, Player player, GameRandom random) {
        this.map = map;
        this.player = player;
        this.combatSystem = new CombatSystem(random);
        this.hintGenerator = new HintGenerator();
        this.gameState = GameState.RUNNING;
    }

    public void movePlayer(Direction direction) {
        Position next = nextPosition(direction);
        if(map.isInside(next)) {
            player.moveTo(next);
            map.visit(next);
            Room room = map.getRoom(next);
            RoomResult result = room.enter(player);
            handleRoomResult(result);
            consumeRoom(room, next);
        }
    }

    private void consumeRoom(Room room, Position position) {
        if(gameState != GameState.RUNNING) return;
        if(room.isConsumable()) {
            map.setRoom(position, new EmptyRoom());
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
            case COMBAT -> handleCombat(result.getEnemy());
            case PLAYER_ESCAPED -> gameState = GameState.PLAYER_WON;
            case PLAYER_DAMAGED -> {
                if(!player.isAlive()) gameState = GameState.PLAYER_LOST;
            }
            default -> {}
        }
    }

    private void handleCombat(Enemy enemy){
        CombatLog combatLog = combatSystem.fight(player, enemy);
        for(String message : combatLog.getMessages()) {
            System.out.println(message);
        }
        if(player.isAlive()){
            Hint hint = hintGenerator.generate(player.getPosition(), map.getExitPosition());
            player.addHint(hint);
            System.out.println(hint.getMessage());
        } else
            gameState = GameState.PLAYER_LOST;
    }

    public DungeonMap getMap(){
        return map;
    }

    public Player getPlayer(){
        return player;
    }

    public GameState getGameState(){
        return gameState;
    }

}
