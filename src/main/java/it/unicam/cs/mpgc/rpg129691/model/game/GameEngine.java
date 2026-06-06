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
    private CombatLog lastCombatLog;
    private Hint lastHint;
    private GameEvent lastEvent;
    private GameState gameState;

    public GameEngine(DungeonMap map, Player player, GameRandom random) {
        this.map = map;
        this.player = player;
        this.combatSystem = new CombatSystem(random);
        this.hintGenerator = new HintGenerator();
        this.gameState = GameState.RUNNING;
    }

    public boolean movePlayer(Direction direction) {
        lastCombatLog = null;
        lastHint = null;
        Position next = nextPosition(direction);
        if(!map.isInside(next)) return false;
        player.moveTo(next);
        map.visit(next);
        Room room = map.getRoom(next);
        RoomResult result = room.enter(player);
        handleRoomResult(result);
        consumeRoom(room, next);
        return true;
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
        CombatLog log = null;
        EventType eventType;
        switch(result.getType()) {
            case COMBAT -> {
                log = handleCombat(result.getEnemy());
                eventType = EventType.COMBAT;
            }
            case TREASURE_FOUND -> eventType = EventType.TREASURE;
            case PLAYER_HEALED -> eventType = EventType.HEAL;
            case PLAYER_DAMAGED -> {
                eventType = EventType.TRAP;
                if(!player.isAlive()) {
                    gameState = GameState.PLAYER_LOST;
                }
            }
            case PLAYER_ESCAPED -> {
                eventType = EventType.EXIT;
                gameState = GameState.PLAYER_WON;
            }
            case NOTHING -> eventType = EventType.EMPTY;
            default -> throw new IllegalStateException();
        }
        lastEvent = new GameEvent(
                eventType,
                result.getMessage(),
                log
        );
    }

    private CombatLog handleCombat(Enemy enemy){
        lastCombatLog = combatSystem.fight(player, enemy);
        if(player.isAlive()){
            Hint hint = hintGenerator.generate(player.getPosition(), map.getExitPosition());
            player.addHint(hint);
            lastHint = hint;
        } else
            gameState = GameState.PLAYER_LOST;
        return lastCombatLog;
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

    public CombatLog getLastCombatLog() {
        return lastCombatLog;
    }

    public Hint getLastHint() {
        return lastHint;
    }

    public GameEvent getLastEvent() {
        return lastEvent;
    }
}
