package dev.upssitech.robocontroler.controler;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Gamepad implements GAxisManager.GAxisChangeListener {

    // Interfaces

    public interface ButtonListener {
        void onButtonPressed(GButton button);
    }

    public interface JoystickListener {
        void onJoystickMoved(GDirection dir, int x, int y);
    }

    public interface TriggerListener {
        void onTriggerMoved(GDirection dir, int value);
    }

    // Attributes

    public static final String TAG = "GameController";

    private InputDevice gamepad;
    private ArrayList<Integer> gamepadIds;

    private final ArrayList<ButtonListener> buttonListeners;
    private final ArrayList<JoystickListener> joystickListeners;
    private final ArrayList<TriggerListener> triggerListeners;

    private final GAxisManager axisManager;

    // Constructor

    public Gamepad() {
        buttonListeners = new ArrayList<>();
        joystickListeners = new ArrayList<>();
        triggerListeners = new ArrayList<>();

        axisManager = new GAxisManager();
        axisManager.addListener(this);

        getControllerIds();
        setGamepad(0);
    }

    // Status info

    public boolean isConnected() {
        return gamepad != null;
    }

    public String getName() {
        return isConnected() ? gamepad.getName() : "No gamepad connected";
    }

    // Event Listeners

    public void onButtonPressed(ButtonListener listener) {
        buttonListeners.add(listener);
    }

    public void onJoystickMoved(JoystickListener listener) {
        joystickListeners.add(listener);
    }

    public void onTriggerMoved(TriggerListener listener) {
        triggerListeners.add(listener);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getDevice() != gamepad || event.getRepeatCount() != 0)
            return false;
        
        GButton button = GButton.valueOf(keyCode);
        if(button == null) return false;

        for(ButtonListener listener : buttonListeners)
            listener.onButtonPressed(button);

        return true;
    }

    public boolean onAxisMove(MotionEvent event) {
        if (event.getDevice() != gamepad || event.getAction() != MotionEvent.ACTION_MOVE)
            return false;

        for (GAxis axis : GAxis.values()) axisManager.setValue(axis, event.getAxisValue(axis.id));

        return true;
    }

    @Override
    public void onAxisChange(GAxis axis, int value) {
        if(axis == GAxis.LX || axis == GAxis.LY) {
            for(JoystickListener listener : joystickListeners)
                listener.onJoystickMoved(GDirection.LEFT, axisManager.getValue(GAxis.LX), axisManager.getValue(GAxis.LY));
        }

        else if(axis == GAxis.RX || axis == GAxis.RY) {
            for(JoystickListener listener : joystickListeners)
                listener.onJoystickMoved(GDirection.RIGHT, axisManager.getValue(GAxis.RX), axisManager.getValue(GAxis.RY));
        }

        else {
            for(TriggerListener listener : triggerListeners) {
                GDirection dir = (axis == GAxis.LTRIGGER) ? GDirection.LEFT : GDirection.RIGHT;
                listener.onTriggerMoved(dir, value);
            }
        }
    }

    // Setup

    public void setGamepad(int n) {
        try {
            gamepad = InputDevice.getDevice(gamepadIds.get(n));
        } catch(IndexOutOfBoundsException ignored) {
            gamepad = null;
        }
    }

    public void getControllerIds() {
        gamepadIds = new ArrayList<>();
        int[] deviceIds = InputDevice.getDeviceIds();

        for (int id : deviceIds) {
            int sources = InputDevice.getDevice(id).getSources();

            if (
                ((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) ||
                ((sources & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK)
            ) {
                if (!gamepadIds.contains(id)) gamepadIds.add(id);
            }
        }
    }
}
