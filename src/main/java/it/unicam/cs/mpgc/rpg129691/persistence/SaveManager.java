package it.unicam.cs.mpgc.rpg129691.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveManager {

    private final Gson gson;

    public SaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(SaveData data, Path path) throws IOException {
        String json = gson.toJson(data);
        Files.writeString(path, json);
    }

    public SaveData load(Path path) throws IOException {
        String json = Files.readString(path);
        return gson.fromJson(json, SaveData.class);
    }
}