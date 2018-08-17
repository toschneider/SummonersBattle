package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Character extends GameObject {

    private int health;
    private CardLevel level;
    private long lastDrawNanoTime =-1;
    private GameSurface gameSurface;

    public Character(Bitmap image, int rowCount, int colCount, int x, int y,int health, CardLevel level) {
        super(image,rowCount,colCount,x,y);

        this.health = health;
        this.level = level;
    }

    public void update() {
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void earnedEp(int ep) {
        this.level.updateEp(ep);
    }

    public void receiveDamage(int dmg) {
        this.health -= (this.health > dmg)?dmg:this.health;
    }

}
