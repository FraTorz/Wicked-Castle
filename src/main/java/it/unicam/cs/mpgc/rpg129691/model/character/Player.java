package it.unicam.cs.mpgc.rpg129691.model.character;

import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class Player {
    private int health;
    private Position position;

    public Player(int health, Position position) {
        this.health = health;
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public Position getPosition() {
        return position;
    }

    public void moveTo(Position newPosition) {
        this.position = newPosition;
    }

    public void heal(int amount) {
        health += amount;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }
}
