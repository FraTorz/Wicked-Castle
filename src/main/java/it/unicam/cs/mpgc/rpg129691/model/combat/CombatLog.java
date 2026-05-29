package it.unicam.cs.mpgc.rpg129691.model.combat;

import java.util.ArrayList;
import java.util.List;

public class CombatLog {
    private final List<String> messages;

    public CombatLog() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}