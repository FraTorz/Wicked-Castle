package it.unicam.cs.mpgc.rpg129691.model.entity;

import it.unicam.cs.mpgc.rpg129691.model.item.*;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

public class Player extends Entity implements Healable{
    private Position position;
    private Weapon equippedWeapon;

    public Player(int health, Position position) {
        super(health);
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

    @Override
    public int getMinDamage() {
        return equippedWeapon.getMinDamage();
    }

    @Override
    public int getMaxDamage() {
        return equippedWeapon.getMaxDamage();
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Position getPosition() {
        return position;
    }

    public void moveTo(Position newPosition) {
        this.position = newPosition;
    }

    @Override
    public void heal(int amount) {
        increaseHealth(amount);
    }

}
