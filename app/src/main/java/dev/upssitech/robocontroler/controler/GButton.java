package dev.upssitech.robocontroler.controler;

import android.view.KeyEvent;

public enum GButton {
    A(KeyEvent.KEYCODE_BUTTON_A),
    B(KeyEvent.KEYCODE_BUTTON_B),
    X(KeyEvent.KEYCODE_BUTTON_X),
    Y(KeyEvent.KEYCODE_BUTTON_Y),

    LB(KeyEvent.KEYCODE_BUTTON_L1),
    RB(KeyEvent.KEYCODE_BUTTON_R1),

    DPAD_UP(KeyEvent.KEYCODE_DPAD_UP),
    DPAD_DOWN(KeyEvent.KEYCODE_DPAD_DOWN),
    DPAD_LEFT(KeyEvent.KEYCODE_DPAD_LEFT),
    DPAP_RIGHT(KeyEvent.KEYCODE_DPAD_RIGHT);

    int id;

    GButton(int id) {
        this.id = id;
    }

    public static GButton valueOf(int id) {
        for (GButton button : values())
            if (button.id == id) return button;
        return null;
    }
}
