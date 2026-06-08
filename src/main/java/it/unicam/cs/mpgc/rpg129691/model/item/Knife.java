package it.unicam.cs.mpgc.rpg129691.model.item;

public class Knife extends Weapon{

    public Knife() {
        super("Knife", 3, 7);
    }

    @Override
    public String getSpritePath() {
        return "/img/knife.png";
    }

}
