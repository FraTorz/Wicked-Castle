package it.unicam.cs.mpgc.rpg129691.ui.render;

public interface SpriteProvider {

    String getSpritePath();

    default boolean isPlayer(){
        return false;
    }
}
