package com.a94googlemail.schneider04.tom.summonersbattle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ArenaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Character character = new Character("R.drawable.adam",)
        Context context = getApplicationContext();
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //ImageButton but1 = findViewById(R.id.imageButton);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Intent t = getIntent();
        //setContentView(R.layout.activity_arena);
        setContentView(new GameSurface(this));

    }

    private void turn() {

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        Intent t = getIntent();
        setIntent(t);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent t = getIntent();
        setIntent(t);
    }*/
}
