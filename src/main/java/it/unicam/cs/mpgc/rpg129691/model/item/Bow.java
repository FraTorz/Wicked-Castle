package it.unicam.cs.mpgc.rpg129691.model.item;

public class Bow extends Weapon{

    public Bow() {super("Bow", 15, 40);}

    @Override
    public String getSpritePath() {
        return "/img/bow.png";
    }
}
