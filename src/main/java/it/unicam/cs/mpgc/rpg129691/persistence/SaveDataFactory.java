package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

/**
 * Factory responsabile della conversione dello stato di gioco
 * in un oggetto {@link SaveData} serializzabile.
 *
 * Questa classe isola la logica di estrazione dei dati dal modello
 * di gioco, evitando che il sistema di persistenza dipenda direttamente
 * da {@link GameEngine}.
 */
public class SaveDataFactory {

    /**
     * Crea un oggetto {@link SaveData} a partire dallo stato corrente del gioco.
     *
     * Il processo di conversione è delegato a metodi specializzati
     * per separare la serializzazione della mappa e del giocatore.
     *
     * @param game istanza corrente del gioco
     * @return oggetto SaveData pronto per la serializzazione
     */
    public SaveData create(GameEngine game) {
        SaveData data = new SaveData();
        DungeonMap map = game.getMap();
        Player player = game.getPlayer();
        saveMap(data, map);
        savePlayer(data, player);
        return data;
    }

    /**
     * Estrae e salva nello {@link SaveData} tutte le informazioni relative alla mappa.
     *
     * Include:
     * <ul>
     *     <li>seed di generazione del dungeon</li>
     *     <li>difficoltà della partita</li>
     *     <li>insieme delle posizioni già visitate</li>
     * </ul>
     *
     * @param data oggetto di salvataggio da popolari
     * @param map mappa corrente del gioco
     */
    private void saveMap(SaveData data, DungeonMap map){
        data.setSeed(map.getSeed());
        data.setDifficulty(map.getDifficulty());
        data.setVisitedPositions(map.getVisitedPositions());
    }

    /**
     * Estrae e salva nello {@link SaveData} tutte le informazioni relative al giocatore.
     *
     * Include:
     * <ul>
     *     <li>punti vita attuali</li>
     *     <li>posizione corrente</li>
     *     <li>arma equipaggiata</li>
     *     <li>indizi raccolti durante la partita</li>
     * </ul>
     *
     * @param data oggetto di salvataggio da popolari
     * @param player giocatore corrente
     */
    private void savePlayer(SaveData data, Player player){
        data.setPlayerHealth(player.getHealth());
        data.setPlayerPosition(player.getPosition());
        data.setEquippedWeapon(player.getEquippedWeapon().getName());
        data.setHints(player.getHintLog().getHints());
    }
}