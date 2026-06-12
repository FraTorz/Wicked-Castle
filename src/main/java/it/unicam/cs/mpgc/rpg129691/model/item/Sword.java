package it.unicam.cs.mpgc.rpg129691.model.item;

/**
 * Rappresenta un'arma di tipo Sword.
 *
 * La Sword è un'arma bilanciata che offre un danno
 * superiore rispetto al Knife, risultando adatta
 * nelle fasi intermedie del gioco.
 */
public class Sword extends Weapon {

    /**
     * Crea una nuova istanza di Sword con valori di danno predefiniti.
     *
     * La Sword infligge un danno compreso tra 7 e 15.
     */
    public Sword() {
        super("Sword", 7, 20);
    }

    /**
     * Restituisce il percorso dello sprite associato alla Sword.
     *
     * Utilizzato dal sistema di rendering per rappresentare
     * visivamente l'arma nell'interfaccia grafica.
     *
     * @return path dell'immagine della Sword
     */
    @Override
    public String getSpritePath() {
        return "/img/sword.png";
    }
}