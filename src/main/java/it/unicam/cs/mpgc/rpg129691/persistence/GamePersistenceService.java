package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service che coordina le operazioni di persistenza del gioco.
 *
 * Questa classe rappresenta il punto di accesso principale per:
 * <ul>
 *     <li>salvataggio delle partite</li>
 *     <li>caricamento delle partite</li>
 *     <li>eliminazione dei salvataggi</li>
 *     <li>consultazione dei salvataggi disponibili</li>
 * </ul>
 *
 * Incapsula la collaborazione tra:
 * <ul>
 *     <li>{@link SaveDataFactory}</li>
 *     <li>{@link SaveManager}</li>
 *     <li>{@link GameLoader}</li>
 * </ul>
 */
public class GamePersistenceService {

    private final SaveDataFactory saveDataFactory;
    private final SaveManager saveManager;
    private final GameLoader gameLoader;
    private static final DateTimeFormatter SAVE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public GamePersistenceService() {
        this.saveDataFactory = new SaveDataFactory();
        this.saveManager = new SaveManager();
        this.gameLoader = new GameLoader();
    }

    /**
     * Salva lo stato corrente di una partita.
     *
     * Prima del salvataggio vengono effettuati alcuni controlli:
     * <ul>
     *     <li>il nome non deve essere già utilizzato</li>
     *     <li>non deve essere stato raggiunto il limite massimo di salvataggi</li>
     * </ul>
     *
     * @param game partita da salvare
     * @param saveName nome da assegnare al salvataggio
     * @throws IOException se si verifica un errore durante la scrittura
     * @throws IllegalArgumentException se il nome esiste già
     * @throws IllegalStateException se è stato raggiunto il limite di salvataggi
     */
    public void saveGame(GameEngine game, String saveName) throws IOException {
        if(saveManager.exists(saveName)) {
            throw new IllegalArgumentException("SAVE_NAME_ALREADY_EXISTS");
        }
        if(saveManager.hasReachedLimit()) {
            throw new IllegalStateException("SAVE_LIMIT_REACHED");
        }
        SaveData data = saveDataFactory.create(game);
        data.setSaveName(saveName);
        data.setSaveTime(LocalDateTime.now().format(SAVE_TIME_FORMAT));
        saveManager.save(data);
    }

    /**
     * Carica una partita precedentemente salvata.
     *
     * @param saveName nome del salvataggio da caricare
     * @return partita ricostruita a partire dai dati salvati
     * @throws IOException se il salvataggio non può essere letto
     */
    public GameEngine loadGame(String saveName) throws IOException {
        SaveData data = saveManager.load(saveName);
        return gameLoader.load(data);
    }

    /**
     * Elimina un salvataggio esistente.
     *
     * @param saveName nome del salvataggio da eliminare
     * @throws IOException se si verifica un errore durante l'eliminazione
     */
    public void deleteSave(String saveName) throws IOException {
        saveManager.delete(saveName);
    }

    /**
     * Restituisce i metadati dei salvataggi disponibili.
     *
     * Le informazioni restituite sono destinate principalmente
     * alla visualizzazione nella UI.
     *
     * @return lista dei salvataggi disponibili
     * @throws IOException se si verifica un errore durante la lettura
     */
    public List<SaveIndex> listSaves() throws IOException {
        return saveManager.listSaves();
    }
}