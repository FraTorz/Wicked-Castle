package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.hint.HintGenerator;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.*;

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

    public GameEvent movePlayer(Direction direction) {
        consumeCurrentRoom();
        Position next = nextPosition(direction);
        if (!map.isInside(next)) {
            return new GameEvent(
                    RoomResultType.INVALID_MOVE,
                    "Non puoi andare in quella direzione.",
                    null,
                    null
            );
        }
        player.moveTo(next);
        map.visit(next);
        Room room = map.getRoom(next);
        RoomResult result = room.enter(player);
        GameEvent event = handleRoomResult(result);
        updateGameState(result.getType());
        return event;
    }

    private void consumeCurrentRoom(){
        Position current = player.getPosition();
        if(map.getRoom(current).isConsumable())
            map.setRoom(current, new EmptyRoom());
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

    private GameEvent handleRoomResult(RoomResult result) {
        if(result.getType() == RoomResultType.COMBAT) {
            return handleCombat(result);
        }
         return new GameEvent(result.getType(), result.getMessage(), null, null);
    }

    private GameEvent handleCombat(RoomResult result){
        CombatResult combatResult = combatSystem.fight(player, result.getEnemy());
        Hint hint = null;
        if(player.isAlive()){
            hint = hintGenerator.generate(player.getPosition(), map.getExitPosition());
            player.addHint(hint);
        }
        return new GameEvent(result.getType(), result.getMessage(), combatResult, hint);
    }

    private void updateGameState(RoomResultType result) {
        if(!player.isAlive()) {
            gameState = GameState.PLAYER_LOST;
            return;
        }
        if(result == RoomResultType.PLAYER_ESCAPED) {
            gameState = GameState.PLAYER_WON;
        }
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
