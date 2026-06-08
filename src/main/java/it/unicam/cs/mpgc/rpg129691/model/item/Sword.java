package it.unicam.cs.mpgc.rpg129691.model.item;

public class Sword extends Weapon{

    public Sword() {
        super("Sword", 7, 15);
    }

    @Override
    public String getSpritePath() {
        return "/img/sword.png";
    }

}
