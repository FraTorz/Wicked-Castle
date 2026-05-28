package it.unicam.cs.mpgc.rpg129691.model.character;

import it.unicam.cs.mpgc.rpg129691.model.item.*;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class Player {
    private int health;
    private Position position;
    private Weapon equippedWeapon;

    public Player(int health, Position position) {
        this.health = health;
        this.position = position;
        this.equippedWeapon = new Knife();
    }

    public void equipWeapon(Weapon newWeapon){
        if(newWeapon.getMaxDamage() > equippedWeapon.getMaxDamage()) {
            equippedWeapon = newWeapon;
            System.out.println("You equipped: " + newWeapon.getName());
        } else {
            System.out.println("You found " + newWeapon.getName() +
                    ", but your current weapon is stronger.");
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
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
