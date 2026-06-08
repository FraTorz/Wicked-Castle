package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

public class DarkWizard extends Enemy{
    public DarkWizard() {
        super("Dark Wizard", 50, 8, 14);
    }

    @Override
    public String getSpritePath() {
        return "/img/darkWizard.png";
    }
}
