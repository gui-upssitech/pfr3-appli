package dev.upssitech.robocontroler.controler;

import java.util.ArrayList;
import java.util.HashMap;

public class GAxisManager {

    public interface GAxisChangeListener {
        void onAxisChange(GAxis axis, int value);
    }

    private final HashMap<GAxis, Integer> axies;
    private final ArrayList<GAxisChangeListener> listeners;

    public GAxisManager() {
        this.axies = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void addListener(GAxisChangeListener listener) {
        this.listeners.add(listener);
    }

    public void setValue(GAxis axis, float value) {
        int rounded = Math.round(value * 100);
        if (!this.axies.containsKey(axis) || this.axies.get(axis) != rounded) {
            for (GAxisChangeListener listener : listeners) listener.onAxisChange(axis, rounded);
        }
        this.axies.put(axis, rounded);
    }

    public int getValue(GAxis axis) {
        return axies.getOrDefault(axis, 0);
    }
}
