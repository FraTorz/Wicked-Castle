package it.unicam.cs.mpgc.rpg129691.model.game;

public class GameSession {

    private static GameSession instance;
    private GameEngine currentGame;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    public GameEngine getGame() {
        return currentGame;
    }

    public void setGame(GameEngine game) {
        this.currentGame = game;
    }

}