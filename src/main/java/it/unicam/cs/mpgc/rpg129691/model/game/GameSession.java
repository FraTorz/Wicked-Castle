package it.unicam.cs.mpgc.rpg129691.model.game;

/**
 * Singleton che mantiene il riferimento alla partita
 * attualmente in esecuzione.
 *
 * Viene utilizzato principalmente dai controller JavaFX
 * per condividere lo stesso GameEngine durante la navigazione
 * tra le varie schermate dell'applicazione.
 */
public class GameSession {

    private static GameSession instance;
    private GameEngine game;

    /**
     * Costruttore privato per impedire l'istanziazione esterna.
     */
    private GameSession() {}

    /**
     * Restituisce l'unica istanza della sessione di gioco.
     *
     * @return istanza singleton di GameSession
     */
    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    /**
     * Restituisce la partita attualmente caricata.
     *
     * @return GameEngine corrente
     */
    public GameEngine getGame() {
        return game;
    }

    /**
     * Imposta la partita corrente.
     *
     * Viene utilizzato quando una nuova partita viene creata
     * oppure quando viene caricata da un salvataggio.
     *
     * @param game partita da memorizzare nella sessione
     */
    public void setGame(GameEngine game) {
        this.game = game;
    }

    /**
     * Rimuove la partita attualmente associata alla sessione.
     */
    public void clear() {
        game = null;
    }

}