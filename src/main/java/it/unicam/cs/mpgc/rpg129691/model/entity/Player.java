package it.unicam.cs.mpgc.rpg129691.model.entity;

import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.hint.HintLog;
import it.unicam.cs.mpgc.rpg129691.model.item.*;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.ui.render.PlayerVisualState;

import java.util.Set;

/**
 * Rappresenta il giocatore controllato dall'utente.
 *
 * Il giocatore possiede:
 * <ul>
 *     <li>una posizione all'interno della mappa</li>
 *     <li>un'arma equipaggiata</li>
 *     <li>un registro degli indizi ottenuti</li>
 * </ul>
 *
 * Le capacità offensive dipendono dall'arma attualmente equipaggiata.
 */
public class Player extends Entity {

    private Position position;
    private Weapon equippedWeapon;
    private final HintLog hintLog;
    private PlayerVisualState visualState = PlayerVisualState.NORMAL;

    /**
     * Crea un nuovo giocatore.
     *
     * Il giocatore viene inizializzato con un coltello come
     * arma di partenza e con un registro degli indizi vuoto.
     *
     * @param health salute iniziale
     * @param position posizione iniziale nella mappa
     */
    public Player(int health, Position position) {
        super("EROE", health);
        this.position = position;
        this.equippedWeapon = new Knife();
        this.hintLog = new HintLog();
    }

    /**
     * Equipaggia una nuova arma soltanto se risulta migliore
     * di quella attualmente equipaggiata.
     *
     * @param newWeapon arma trovata
     * @return true se l'arma è stata equipaggiata, false altrimenti
     */
    public boolean equipWeapon(Weapon newWeapon) {
        boolean betterWeapon = newWeapon.getMaxDamage() > equippedWeapon.getMaxDamage();
        if (betterWeapon) {
            equippedWeapon = newWeapon;
        }
        return betterWeapon;
    }

    /**
     * Restituisce il percorso dell'immagine associata al giocatore.
     *
     * Utilizzato dal sistema di rendering per visualizzare lo sprite
     * del personaggio sulla mappa.
     *
     * @return path della risorsa grafica del giocatore
     */
    @Override
    public String getSpritePath() {
        return switch (visualState) {
            case WIN -> "/img/hero_win.png";
            case DEAD -> "/img/hero_dead.png";
            default -> "/img/hero.png";
        };
    }

    /**
     * Indica che questa entità rappresenta il giocatore controllato dall'utente.
     *
     * Utilizzato dal sistema di rendering e dalla logica di gioco
     * per distinguere il player dagli altri tipi di entità.
     *
     * @return true perché questa entità è il giocatore
     */
    @Override
    public boolean isPlayer(){
        return true;
    }

    /**
     * Restituisce il danno minimo che il giocatore può infliggere.
     *
     * Il valore dipende dall'arma attualmente equipaggiata.
     *
     * @return danno minimo dell'arma equipaggiata
     */
    @Override
    public int getMinDamage(){
        return equippedWeapon.getMinDamage();
    }

    /**
     * Restituisce il danno massimo che il giocatore può infliggere.
     *
     * Il valore dipende dall'arma attualmente equipaggiata.
     *
     * @return danno massimo dell'arma equipaggiata
     */
    @Override
    public int getMaxDamage(){
        return equippedWeapon.getMaxDamage();
    }

    /**
     * Aumenta la salute del giocatore.
     *
     * @param amount quantità di salute recuperata
     */
    public void heal(int amount){
        super.increaseHealth(amount);
    }

    /**
     * Aggiorna la posizione del giocatore.
     *
     * @param newPosition nuova posizione
     */
    public void moveTo(Position newPosition){
        this.position = newPosition;
    }

    /**
     * Registra un nuovo indizio ottenuto.
     *
     * @param hint indizio da aggiungere
     */
    public void addHint(Hint hint){
        hintLog.add(hint);
    }

    /**
     * Ripristina il registro degli indizi durante il caricamento
     * di una partita salvata.
     *
     * @param hints insieme degli indizi da ripristinare
     */
    public void restoreHints(Set<Hint> hints){
        this.hintLog.restore(hints);
    }

    /**
     * Ripristina l'arma equipaggiata durante il caricamento
     * di una partita salvata.
     *
     * @param weapon arma da ripristinare
     */
    public void restoreWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
    }

    /**
     * Ripristina la salute del giocatore.
     *
     * Utilizzato durante il caricamento di una partita salvata.
     *
     * @param health salute da assegnare
     */
    public void restoreHealth(int health){
        super.setHealth(health);
    }

    /**
     * @return arma attualmente equipaggiata
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * @return posizione corrente del giocatore
     */
    public Position getPosition(){
        return position;
    }

    /**
     * @return registro degli indizi ottenuti
     */
    public HintLog getHintLog(){
        return hintLog;
    }


    public PlayerVisualState getVisualState() {
        return visualState;
    }

    public void setVisualState(PlayerVisualState visualState) {
        this.visualState = visualState;
    }
}
