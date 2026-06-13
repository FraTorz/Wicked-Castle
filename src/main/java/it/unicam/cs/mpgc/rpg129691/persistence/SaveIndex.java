package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;

/**
 * Rappresenta i metadati di un salvataggio.
 *
 * Questa classe non contiene lo stato completo della partita,
 * ma solo le informazioni necessarie per visualizzare un riepilogo
 * dei salvataggi disponibili (es. nel menu di caricamento).
 */
public class SaveIndex {

    private String saveName;
    private Difficulty difficulty;
    private int playerHealth;
    private String saveTime;

    /**
     * Restituisce una rappresentazione testuale del salvataggio.
     *
     * Utilizzata tipicamente per la visualizzazione in UI.
     *
     * @return stringa formattata con i dati principali del salvataggio
     */
    @Override
    public String toString() {
        return """
           %s
           Difficoltà: %s
           HP: %d
           Salvato il: %s
           """.formatted(saveName, difficulty, playerHealth, saveTime);
    }

    /**
     * @return nome del salvataggio
     */
    public String getSaveName() {
        return saveName;
    }
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    /**
     * @return difficoltà della partita salvata
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return punti vita del giocatore al momento del salvataggio
     */
    public int getPlayerHealth() {
        return playerHealth;
    }
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    /**
     * @return timestamp del salvataggio
     */
    public String getSaveTime() {
        return saveTime;
    }
    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}