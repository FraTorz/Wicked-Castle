package it.unicam.cs.mpgc.rpg129691.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SaveManager {

    private final Gson gson;
    private static final Path SAVE_DIR = Path.of("saves");
    private static final int MAX_SAVES = 5;

    public SaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(SaveData data) throws IOException {
        if(!Files.exists(SAVE_DIR)) {
            Files.createDirectories(SAVE_DIR);
        }
        Path file = SAVE_DIR.resolve(data.getSaveName() + ".json");
        String json = gson.toJson(data);
        Files.writeString(file, json);
    }

    public SaveData load(String saveName) throws IOException {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        String json = Files.readString(file);
        return gson.fromJson(json, SaveData.class);
    }

    public List<SaveIndex> listSaves() throws IOException {
        if(!Files.exists(SAVE_DIR)) {
            return List.of();
        }
        List<SaveIndex> result = new ArrayList<>();
        List<Path> files;
        try(Stream<Path> stream = Files.list(SAVE_DIR)) {
            files = stream
                    .filter(path -> path.toString().endsWith(".json"))
                    .toList();
        }
        for(Path file : files) {
            String json = Files.readString(file);
            SaveData data = gson.fromJson(json, SaveData.class);
            SaveIndex index = new SaveIndex();
            index.setSaveName(data.getSaveName());
            index.setDifficulty(data.getDifficulty());
            index.setPlayerHealth(data.getPlayerHealth());
            index.setSaveTime(data.getSaveTime());
            result.add(index);
        }
        return result;
    }

    public void delete(String saveName) throws IOException {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        Files.deleteIfExists(file);
    }

    public boolean hasReachedLimit() throws IOException {
        return listSaves().size() >= MAX_SAVES;
    }

    public boolean exists(String saveName) {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        return Files.exists(file);
    }
}