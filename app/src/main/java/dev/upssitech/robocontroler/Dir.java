package dev.upssitech.robocontroler;

public enum Dir {
    FORWARDS(0),
    BACKWARDS(1),
    LEFT(2),
    RIGHT(3);

    int id;

    Dir(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
