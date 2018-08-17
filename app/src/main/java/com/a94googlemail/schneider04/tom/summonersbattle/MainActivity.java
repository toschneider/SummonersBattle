package com.a94googlemail.schneider04.tom.summonersbattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    ImageView background;
    public ImageButton battle_button;
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
    }
}
