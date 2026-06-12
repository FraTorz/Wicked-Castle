package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Giant Spider.
 *
 * La Giant Spider è un nemico veloce ma fragile,
 * caratterizzato da:
 * <ul>
 *     <li>bassa salute</li>
 *     <li>danno medio-alto variabile</li>
 * </ul>
 *
 * È una specializzazione concreta della classe Enemy.
 */
public class GiantSpider extends Enemy {

    /**
     * Crea una Giant Spider con statistiche predefinite.
     *
     * Valori:
     * <ul>
     *     <li>HP: 20</li>
     *     <li>Danno: 10 - 20</li>
     * </ul>
     */
    public GiantSpider() {
        super("Giant Spider", 20, 10, 20);
    }

    /**
     * Restituisce il percorso dello sprite della Giant Spider.
     *
     * @return path dell'immagine della Giant Spider
     */
    @Override
    public String getSpritePath() {
        return "/img/giantSpider.png";
    }
}