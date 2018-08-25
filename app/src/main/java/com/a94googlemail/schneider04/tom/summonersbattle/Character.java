package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Character  extends GameObject implements Serializable {

    private Level level;
    private long lastDrawNanoTime =-1;

    public Character(int filename, int x, int y, Level level, boolean enemy, GameSurface gameSurface) {
        super(filename,x,y,enemy , gameSurface);
        this.level = level;
        calcHealth();
        readImage();
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
    private void calcHealth() {
        health = retHealth();
    }
    private int retHealth(){
        return 35 + 5 * level.getLevel();
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, getPos().getX(), getPos().getY(), null);
        canvas.drawBitmap(heart,getPos().getX()+image.getWidth()/2-heart.getWidth()/2,getPos().getY()-heart.getHeight()*1.1f,null);
    }

    public void earnedEp(int ep) {

        this.level.updateEp(ep);
    }


    protected void readImage() {
        super.readImage();
        Bitmap tmp = heart;
        heart = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.6),(int)(tmp.getHeight()*0.6),false);
    }

    public void receiveDamage(int dmg) {
        this.health -= (this.health > dmg)?dmg:this.health;
    }

    public Level getLevel() {
        return level;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(health);
        oos.writeObject(level);
        oos.writeObject(enemy);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        health = (int) ois.readObject();
        level = (Level) ois.readObject();
        enemy = (boolean) ois.readObject();
    }

}
