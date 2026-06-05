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
    private String lastEventMessage;
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
        lastEventMessage = null;
        lastCombatLog = null;
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
        switch(result.getType()) {
            case COMBAT -> {
                lastEventMessage = "Hai trovato un nemico.";
                log = handleCombat(result.getEnemy());
            }
            case PLAYER_ESCAPED -> {
                lastEventMessage = "Hai trovato l'uscita!";
                gameState = GameState.PLAYER_WON;
            }
            case PLAYER_DAMAGED -> {
                lastEventMessage = "Hai attivato una trappola.";
                if(!player.isAlive()) gameState = GameState.PLAYER_LOST;
            }
            case PLAYER_HEALED -> lastEventMessage = "Hai recuperato salute.";
            case NOTHING -> lastEventMessage = "La stanza è vuota.";
        }
        lastEvent = new GameEvent(lastEventMessage, log);
    }

    private CombatLog handleCombat(Enemy enemy){
        lastCombatLog = combatSystem.fight(player, enemy);
        if(player.isAlive()){
            Hint hint = hintGenerator.generate(player.getPosition(), map.getExitPosition());
            player.addHint(hint);
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

    public String getLastEventMessage() {
        return lastEventMessage;
    }

    public GameEvent getLastEvent() {
        return lastEvent;
    }
}
