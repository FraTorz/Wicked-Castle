package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Phantom.
 *
 * Il Phantom è un nemico etereo con:
 * <ul>
 *     <li>salute media</li>
 *     <li>danno basso ma costante</li>
 * </ul>
 *
 * È una specializzazione concreta della classe Enemy.
 */
public class Phantom extends Enemy {

    /**
     * Crea un Phantom con statistiche predefinite.
     *
     * Valori:
     * <ul>
     *     <li>HP: 30</li>
     *     <li>Danno: 5 - 15</li>
     * </ul>
     */
    public Phantom() {
        super("Phantom", 30, 5, 15);
    }

    /**
     * Restituisce il percorso dello sprite del Phantom.
     *
     * @return path dell'immagine del Phantom
     */
    @Override
    public String getSpritePath() {
        return "/img/phantom.png";
    }
}