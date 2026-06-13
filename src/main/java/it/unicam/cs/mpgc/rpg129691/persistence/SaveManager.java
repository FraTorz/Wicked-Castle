package it.unicam.cs.mpgc.rpg129691.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Gestisce la persistenza delle partite su filesystem.
 *
 * Questa classe fornisce le operazioni fondamentali per:
 * <ul>
 *     <li>salvare una partita su file JSON</li>
 *     <li>caricare una partita salvata</li>
 *     <li>recuperare l'elenco dei salvataggi disponibili</li>
 *     <li>eliminare un salvataggio</li>
 *     <li>verificare l'esistenza di un salvataggio e i limiti di archiviazione</li>
 * </ul>
 *
 * Utilizza Gson per serializzare e deserializzare gli oggetti
 * {@link SaveData} memorizzati nella directory {@code saves}.
 */
public class SaveManager {

    private final Gson gson;
    private static final Path SAVE_DIR = Path.of("saves");
    private static final int MAX_SAVES = 5;

    public SaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Salva una partita su file JSON.
     *
     * Il file viene creato nella directory {@code saves}
     * con nome basato su {@link SaveData#getSaveName()}.
     *
     * @param data dati della partita da salvare
     * @throws IOException se si verifica un errore di scrittura
     */
    public void save(SaveData data) throws IOException {
        if(!Files.exists(SAVE_DIR)) {
            Files.createDirectories(SAVE_DIR);
        }
        Path file = SAVE_DIR.resolve(data.getSaveName() + ".json");
        String json = gson.toJson(data);
        Files.writeString(file, json);
    }

    /**
     * Carica una partita salvata.
     *
     * @param saveName nome del salvataggio
     * @return dati della partita deserializzati
     * @throws IOException se il file non esiste o non è leggibile
     */
    public SaveData load(String saveName) throws IOException {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        return readSaveData(file);
    }

    /**
     * Restituisce la lista dei salvataggi disponibili.
     *
     * Ogni file JSON viene convertito in un {@link SaveIndex}
     * contenente solo i metadati utili per la UI.
     *
     * @return lista dei salvataggi disponibili
     * @throws IOException se si verifica un errore di lettura della directory
     */
    public List<SaveIndex> listSaves() throws IOException {
        if (!Files.exists(SAVE_DIR)) {
            return List.of();
        }

        List<SaveIndex> result = new ArrayList<>();
        for (Path file : getSaveFiles()) {
            result.add(createSaveIndex(file));
        }
        return result;
    }

    /**
     * Recupera tutti i file di salvataggio presenti nella directory.
     *
     * Vengono considerati esclusivamente i file con estensione {@code .json}.
     *
     * @return lista dei percorsi dei file di salvataggio
     * @throws IOException se si verifica un errore durante la lettura della directory
     */
    private List<Path> getSaveFiles() throws IOException {
        try (Stream<Path> stream = Files.list(SAVE_DIR)) {
            return stream
                    .filter(path -> path.toString().endsWith(".json"))
                    .toList();
        }
    }

    /**
     * Costruisce un {@link SaveIndex} a partire da un file di salvataggio.
     *
     * @param file file di salvataggio
     * @return metadati estratti dal salvataggio
     * @throws IOException se si verifica un errore di lettura
     */
    private SaveIndex createSaveIndex(Path file) throws IOException {
        SaveData data = readSaveData(file);
        return createSaveIndex(data);
    }

    /**
     * Legge e deserializza un file di salvataggio.
     *
     * @param file file da leggere
     * @return dati completi della partita salvata
     * @throws IOException se si verifica un errore di lettura
     */
    private SaveData readSaveData(Path file) throws IOException {
        String json = Files.readString(file);
        return gson.fromJson(json, SaveData.class);
    }

    /**
     * Converte un oggetto {@link SaveData} nel corrispondente
     * {@link SaveIndex}, contenente soltanto le informazioni
     * necessarie alla visualizzazione nella UI.
     *
     * @param data dati completi del salvataggio
     * @return metadati del salvataggio
     */
    private SaveIndex createSaveIndex(SaveData data) {
        SaveIndex index = new SaveIndex();
        index.setSaveName(data.getSaveName());
        index.setDifficulty(data.getDifficulty());
        index.setPlayerHealth(data.getPlayerHealth());
        index.setSaveTime(data.getSaveTime());
        return index;
    }

    /**
     * Elimina un salvataggio dal filesystem.
     *
     * @param saveName nome del salvataggio da eliminare
     * @throws IOException se si verifica un errore durante la cancellazione
     */
    public void delete(String saveName) throws IOException {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        Files.deleteIfExists(file);
    }

    /**
     * Verifica se è stato raggiunto il limite massimo di salvataggi.
     *
     * @return true se il numero di salvataggi è >= {@value #MAX_SAVES}
     * @throws IOException se si verifica un errore nella lettura dei file
     */
    public boolean hasReachedLimit() throws IOException {
        return listSaves().size() >= MAX_SAVES;
    }

    /**
     * Verifica se un salvataggio esiste già.
     *
     * @param saveName nome del salvataggio
     * @return true se il file esiste
     */
    public boolean exists(String saveName) {
        Path file = SAVE_DIR.resolve(saveName + ".json");
        return Files.exists(file);
    }
}