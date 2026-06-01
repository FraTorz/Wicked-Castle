package it.unicam.cs.mpgc.rpg129691.model.entity;

import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.hint.HintLog;
import it.unicam.cs.mpgc.rpg129691.model.item.*;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;

import java.util.Set;

public class Player extends Entity implements Healable{
    private Position position;
    private Weapon equippedWeapon;
    private final HintLog hintLog;

    public Player(int health, Position position) {
        super(health);
        this.position = position;
        this.equippedWeapon = new Knife();
        this.hintLog = new HintLog();
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

    @Override
    public void heal(int amount) {
        increaseHealth(amount);
    }

    public void moveTo(Position newPosition) {
        this.position = newPosition;
    }

    public void addHint(Hint hint){
        hintLog.add(hint);
    }

    public void restoreHints(Set<Hint> hints){
        this.hintLog.restore(hints);
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void restoreWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    public Position getPosition() {
        return position;
    }

    public HintLog getHintLog() {
        return hintLog;
    }
}
