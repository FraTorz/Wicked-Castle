package it.unicam.cs.mpgc.rpg129691.model.hint;

import java.util.ArrayList;
import java.util.List;

public class HintLog {

    private final List<Hint> hints;

    public HintLog() {
        this.hints = new ArrayList<>();
    }

    public void add(Hint hint) {
        hints.add(hint);
    }

    public List<Hint> getHints() {
        return List.copyOf(hints);
    }
}