package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView background;
    public ImageButton battle_button;
    private Character character;
    public void init(){
        battle_button = (ImageButton)findViewById(R.id.battleButton);
        battle_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    Intent t = new Intent(MainActivity.this, ArenaActivity.class);
                    startActivity(t);
                }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //this.setContentView(new GameSurface(this));

        //background = (ImageView) findViewById(R.id.background);
       //background.setImageResource(R.drawable.splash);
        init();

        Bitmap characterbitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.adam);
        character = new Character(characterbitmap,200,160,0, background.getHeight()/2 - 100, 50, new CardLevel(1,0));
        updateViews();
    }

    private void updateViews() {
        ProgressBar levelprogressBar = (ProgressBar) findViewById(R.id.levelprogressBar);
        TextView levelprogressbarTextView = (TextView) findViewById(R.id.levelprogressbartextView);
        int curEp = character.getLevel().getCurrentEp();
        int maxEp = character.getLevel().calculateMaxEp();

        levelprogressBar.setMax(maxEp);
        levelprogressBar.setProgress(curEp);

        levelprogressbarTextView.setText(curEp/maxEp + "%");
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*try {
            FileOutputStream out = new FileOutputStream("app/res/raw/Character.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }*/
    }
}
