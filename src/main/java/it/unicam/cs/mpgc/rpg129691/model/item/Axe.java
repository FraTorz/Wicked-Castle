package it.unicam.cs.mpgc.rpg129691.model.item;

/**
 * Rappresenta un'arma di tipo Axe.
 *
 * L'Axe è un'arma pesante caratterizzata da un alto
 * potenziale di danno, ma meno bilanciata rispetto
 * alle armi più leggere.
 */
public class Axe extends Weapon {

    /**
     * Crea una nuova istanza di Axe con valori di danno predefiniti.
     *
     * L'Axe infligge un danno compreso tra 15 e 30.
     */
    public Axe() {
        super("Axe", 15, 30);
    }

    /**
     * Restituisce il percorso dello sprite associato all'Axe.
     *
     * Utilizzato dal sistema di rendering per visualizzare
     * l'arma nell'interfaccia grafica.
     *
     * @return path dell'immagine dell'Axe
     */
    @Override
    public String getSpritePath() {
        return "/img/axe.png";
    }
}