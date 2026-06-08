package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

public class GiantSpider extends Enemy {

    public GiantSpider() {
        super("Giant Spider", 20, 6, 12);
    }

    @Override
    public String getSpritePath() {
        return "/img/giantSpider.png";
    }

}