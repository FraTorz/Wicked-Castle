package it.unicam.cs.mpgc.rpg129691.persistence;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;
import it.unicam.cs.mpgc.rpg129691.model.item.WeaponFactory;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;

/**
 * Responsabile del ripristino di una partita salvata.
 *
 * Converte un oggetto {@link SaveData} in una nuova istanza
 * di {@link GameEngine}, ricostruendo lo stato completo del gioco.
 *
 * Questa classe rappresenta il processo inverso rispetto a {@link SaveDataFactory}.
 */
public class GameLoader {

    private final GameFactory gameFactory = new GameFactory();

    /**
     * Ricostruisce una partita a partire dai dati salvati.
     *
     * Il processo include:
     * <ul>
     *     <li>ricreazione della struttura base del gioco tramite {@link GameFactory}</li>
     *     <li>ripristino dello stato della mappa</li>
     *     <li>ripristino dello stato del giocatore</li>
     * </ul>
     *
     * @param data dati serializzati della partita
     * @return istanza completa di {@link GameEngine} ripristinata
     */
    public GameEngine load(SaveData data) {
        GameEngine game = gameFactory.createSpecificGame(data.getDifficulty(), data.getSeed());
        DungeonMap map = game.getMap();
        map.restoreVisitedPositions(data.getVisitedPositions());
        Player player = game.getPlayer();
        restorePlayer(player, data);
        return game;
    }

    /**
     * Ripristina lo stato del giocatore a partire dai dati salvati.
     *
     * Include:
     * <ul>
     *     <li>punti vita</li>
     *     <li>posizione nella mappa</li>
     *     <li>arma equipaggiata</li>
     *     <li>indizi raccolti</li>
     * </ul>
     *
     * @param player giocatore da ripristinare
     * @param data dati di salvataggio
     */
    private void restorePlayer(Player player, SaveData data){
        player.restoreHealth(data.getPlayerHealth());
        player.moveTo(data.getPlayerPosition());
        Weapon weapon = WeaponFactory.createWeapon(data.getEquippedWeapon());
        player.restoreWeapon(weapon);
        player.restoreHints(data.getHints());
    }

}