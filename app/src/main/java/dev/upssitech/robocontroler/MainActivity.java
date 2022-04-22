package dev.upssitech.robocontroler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dev.upssitech.robocontroler.controler.GButton;
import dev.upssitech.robocontroler.controler.GDirection;
import dev.upssitech.robocontroler.controler.Gamepad;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "GAME";

    private TextView deviceNameView;
    private TextView counterView;
    private ProgressBar progressBarView;
    private LinearLayout box;

    private int counter;
    private Gamepad gamepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init other variables

        counter = 0;
        gamepad = new Gamepad();

        // Get view components

        deviceNameView = findViewById(R.id.device);
        counterView = findViewById(R.id.counter);
        repaint();

        progressBarView = findViewById(R.id.progressBar);
        progressBarView.setMin(0);
        progressBarView.setMax(100);

        box = findViewById(R.id.box);

        // Event management

        gamepad.onButtonPressed(button -> {
            Log.d(TAG, "onCreate: " + button);

            if(button == GButton.RB) counter++;
            if(button == GButton.LB) counter = Math.max(counter - 1, 0);
            if(button == GButton.B) counter = 0;
            repaint();
        });

        gamepad.onTriggerMoved((dir, value) -> {
            if(dir == GDirection.RIGHT) progressBarView.setProgress(value);
        });

        gamepad.onJoystickMoved(((joy, x, y) -> {
            if(joy == GDirection.LEFT) {
                box.setTranslationX(x);
                box.setTranslationY(y);
            }
        }));
    }

    private void repaint() {
        deviceNameView.setText(gamepad.getName());
        counterView.setText(String.valueOf(counter));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = gamepad.onKeyDown(keyCode, event);
        return res || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        boolean res = gamepad.onAxisMove(event);
        return res || super.onGenericMotionEvent(event);
    }
}