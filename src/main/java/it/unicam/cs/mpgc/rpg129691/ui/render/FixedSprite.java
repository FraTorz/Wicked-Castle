package it.unicam.cs.mpgc.rpg129691.ui.render;

public enum FixedSprite implements SpriteProvider {

    HERO("/img/hero.png"),
    POTION("/img/potion.png"),
    TRAP("/img/trap.png"),
    EXIT("/img/door.png");

    private final String path;

    FixedSprite(String path) {
        this.path = path;
    }

    @Override
    public String getSpritePath() {
        return path;
    }
}