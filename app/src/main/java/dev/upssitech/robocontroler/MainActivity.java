package dev.upssitech.robocontroler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import dev.upssitech.robocontroler.controler.GameControler;

public class MainActivity extends AppCompatActivity {

    private TextView deviceNameView;
    private TextView counterView;

    private int counter;
    private GameControler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get view components

        deviceNameView = (TextView) findViewById(R.id.device);
        counterView = (TextView) findViewById(R.id.counter);

        // Init other variables

        counter = 0;
        controller = new GameControler();
    }
}