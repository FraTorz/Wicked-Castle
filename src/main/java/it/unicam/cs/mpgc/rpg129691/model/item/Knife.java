package it.unicam.cs.mpgc.rpg129691.model.item;

/**
 * Rappresenta un'arma di base di tipo Knife.
 *
 * Il Knife è l'arma iniziale del giocatore e possiede
 * un intervallo di danno relativamente basso rispetto
 * alle altre armi del gioco.
 */
public class Knife extends Weapon {

    /**
     * Crea una nuova istanza di Knife con valori di danno predefiniti.
     *
     * Il Knife infligge un danno compreso tra 3 e 7.
     */
    public Knife() {
        super("Knife", 3, 7);
    }

    /**
     * Restituisce il percorso dello sprite associato al Knife.
     *
     * Questo sprite viene utilizzato dal sistema di rendering
     * per visualizzare l'arma nell'interfaccia grafica.
     *
     * @return path dell'immagine del Knife
     */
    @Override
    public String getSpritePath() {
        return "/img/knife.png";
    }
}