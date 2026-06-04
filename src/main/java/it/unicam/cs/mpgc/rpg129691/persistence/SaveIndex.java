package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;

public class SaveIndex {

    private String saveName;
    private Difficulty difficulty;
    private int playerHealth;
    private String saveTime;

    @Override
    public String toString() {
        return """
           %s
           Difficoltà: %s
           HP: %d
           %s
           """.formatted(saveName, difficulty, playerHealth, saveTime);
    }

    public String getSaveName() {
        return saveName;
    }
    public void setSaveName(String saveName) {
        this.saveName = saveName;
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

    public String getSaveTime() {
        return saveTime;
    }
    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}