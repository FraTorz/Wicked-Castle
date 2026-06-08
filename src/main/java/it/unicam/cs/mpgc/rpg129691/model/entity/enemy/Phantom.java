package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

public class Phantom extends Enemy {

    public Phantom() {
        super("Phantom", 30, 4, 8);
    }

    @Override
    public String getSpritePath() {
        return "/img/phantom.png";
    }

}