package dev.upssitech.robocontroler.controler;

import android.view.MotionEvent;

public enum GAxis {
    LTRIGGER(MotionEvent.AXIS_LTRIGGER),
    RTRIGGER(MotionEvent.AXIS_RTRIGGER),

    LX(MotionEvent.AXIS_X),
    LY(MotionEvent.AXIS_Y),

    RX(MotionEvent.AXIS_Z),
    RY(MotionEvent.AXIS_RZ);

    int id;

    GAxis(int id) {
        this.id = id;
    }

    public static GAxis valueOf(int id) {
        for (GAxis axis : values())
            if (axis.id == id) return axis;
        return null;
    }
}
