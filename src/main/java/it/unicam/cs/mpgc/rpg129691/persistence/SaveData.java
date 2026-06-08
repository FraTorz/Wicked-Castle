package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

import java.util.Set;

public class SaveData {

    private long seed;
    private Difficulty difficulty;
    private int playerHealth;
    private Position playerPosition;
    private String equippedWeapon;
    private Set<Position> visitedPositions;
    private Set<Hint> hints;
    private String saveName;
    private String saveTime;

    public long getSeed() {
        return seed;
    }
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }
    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getEquippedWeapon() {
        return equippedWeapon;
    }
    public void setEquippedWeapon(String equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Set<Position> getVisitedPositions() {
        return visitedPositions;
    }
    public void setVisitedPositions(Set<Position> visitedPositions) {
        this.visitedPositions = visitedPositions;
    }

    public Set<Hint> getHints() {
        return hints;
    }
    public void setHints(Set<Hint> hints) {
        this.hints = hints;
    }

    public String getSaveName() {
        return saveName;
    }
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveTime() {
        return saveTime;
    }
    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

}