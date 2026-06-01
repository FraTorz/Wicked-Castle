package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;

import java.io.IOException;
import java.nio.file.Path;

public class GamePersistenceService {

    private final SaveDataFactory saveDataFactory;
    private final SaveManager saveManager;
    private final GameLoader gameLoader;

    public GamePersistenceService() {
        this.saveDataFactory = new SaveDataFactory();
        this.saveManager = new SaveManager();
        this.gameLoader = new GameLoader();
    }

    public void saveGame(GameEngine game, Path path) throws IOException {
        SaveData data = saveDataFactory.create(game);
        saveManager.save(data, path);
    }

    public GameEngine loadGame(Path path) throws IOException {
        SaveData data = saveManager.load(path);
        return gameLoader.load(data);
    }
}