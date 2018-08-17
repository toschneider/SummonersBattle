package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Card extends GameObject {

    private int damage;
    private int health;
    private Bitmap cardImage;
    private CardLevel level;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Card(Bitmap image, int rowCount, int colCount, int x, int y, int damage, int health, Bitmap cardImage, CardLevel level) {
        super(image,rowCount, colCount, x, y);

        this.damage = damage;
        this.health = health;
        this.cardImage = cardImage;
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

        if(this.y == 3) {
            drawCard(canvas);
        } else {
            drawCharacter(canvas);
        }
    }

    public void drawCharacter(Canvas canvas) {
        canvas.drawBitmap(image, x, y,null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void drawCard(Canvas canvas) {

    }

    public void earnedEp(int ep) {
        this.level.updateEp(ep);
    }

    public void receiveDamage(int dmg) {
        this.health -= (this.health > dmg)?dmg:this.health;
    }
    private boolean isDead() {
        return this.health == 0;
    }

    public void dealDamage( Card enemy) {
        enemy.receiveDamage(this.damage);
    }
}
