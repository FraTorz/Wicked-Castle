package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GamePersistenceService {

    private final SaveDataFactory saveDataFactory;
    private final SaveManager saveManager;
    private final GameLoader gameLoader;

    public GamePersistenceService() {
        this.saveDataFactory = new SaveDataFactory();
        this.saveManager = new SaveManager();
        this.gameLoader = new GameLoader();
    }

    public void saveGame(GameEngine game, String saveName) throws IOException {
        if(saveManager.exists(saveName)) {
            throw new IllegalArgumentException("SAVE_NAME_ALREADY_EXISTS");
        }
        if(saveManager.hasReachedLimit()) {
            throw new IllegalStateException("SAVE_LIMIT_REACHED");
        }
        SaveData data = saveDataFactory.create(game);
        data.setSaveName(saveName);
        data.setSaveTime(LocalDateTime.now().toString());
        saveManager.save(data);
    }

    public GameEngine loadGame(String saveName) throws IOException {
        SaveData data = saveManager.load(saveName);
        return gameLoader.load(data);
    }

    public List<SaveIndex> listSaves() throws IOException {
        return saveManager.listSaves();
    }

    public void deleteSave(String saveName) throws IOException {
        saveManager.delete(saveName);
    }
}