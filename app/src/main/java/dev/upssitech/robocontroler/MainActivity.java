package dev.upssitech.robocontroler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

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

    private TextView dirView, speedView;

    private MutableLiveData<Integer> speed;
    private MutableLiveData<Dir> dir;
    private Gamepad gamepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init other variables

        dir = new MutableLiveData<>(Dir.FORWARDS);
        speed = new MutableLiveData<>(0);

        dir.observe(this, newDir -> repaint());
        speed.observe(this, newSpeed -> repaint());

        gamepad = new Gamepad();

        // Get view components
        dirView = findViewById(R.id.dir);
        speedView = findViewById(R.id.speed);

        repaint();

        // Event management

        gamepad.onJoystickMoved((side, x, y) -> {
            if(side == GDirection.RIGHT) return;

            if(y > 80)  dir.setValue(Dir.BACKWARDS);
            if(y < -80) dir.setValue(Dir.FORWARDS);
            if(x < -80) dir.setValue(Dir.LEFT);
            if(x > 80)  dir.setValue(Dir.RIGHT);
        });

        gamepad.onTriggerMoved((side, value) -> {
            if(side == GDirection.RIGHT) speed.setValue(value);
        });
    }

    private void repaint() {
        dirView.setText(dir.getValue().toString());
        speedView.setText("" + speed.getValue());
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