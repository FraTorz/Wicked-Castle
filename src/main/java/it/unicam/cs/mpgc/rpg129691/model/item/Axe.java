package it.unicam.cs.mpgc.rpg129691.model.item;

public class Axe extends Weapon{

    public Axe() {
        super("Axe", 10, 20);
    }

    @Override
    public String getSpritePath() {
        return "/img/axe.png";
    }

}
