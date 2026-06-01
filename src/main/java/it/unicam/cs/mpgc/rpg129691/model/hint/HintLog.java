package it.unicam.cs.mpgc.rpg129691.model.hint;

import java.util.HashSet;
import java.util.Set;

public class HintLog {

    private final Set<Hint> hints;

    public HintLog() {
        this.hints = new HashSet<>();
    }

    public void add(Hint hint) {
        hints.add(hint);
    }

    public Set<Hint> getHints() {
        return Set.copyOf(hints);
    }

    public void restore(Set<Hint> hints) {
        this.hints.clear();
        this.hints.addAll(hints);
    }
}