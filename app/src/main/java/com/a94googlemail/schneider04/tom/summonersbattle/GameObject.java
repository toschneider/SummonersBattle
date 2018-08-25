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

public abstract class GameObject implements Serializable {

    protected GameSurface gameSurface;

    protected Bitmap image;
    protected Bitmap heart;
    private int fileName;

    //Todo make health.java
    protected int health;

    protected int x;
    protected int y;

    protected boolean enemy;

    protected Position pos;


    public GameObject(int fileName, int x, int y, int health,boolean enemy, GameSurface gameSurface) {
        pos = new Position(x,y);
        this.enemy = enemy;
        this.health = health;
        this.fileName = fileName;
        this.gameSurface = gameSurface;
    }

    public GameObject(int fileName, int x, int y,boolean enemy, GameSurface gameSurface) {
        pos = new Position(x,y);
        this.enemy = enemy;
        this.gameSurface = gameSurface;
        this.health = 35;
        this.fileName = fileName;
    }


    public void setGameSurface(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    protected void readImage() {

        heart = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.heart);
        Bitmap temp = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.adam);

        if(enemy) {
            temp = flip(temp);
        }
        this.image = Bitmap.createScaledBitmap(temp, gameSurface.getHeight() / 5,gameSurface.getHeight()/4, false);
    }

    /**
     * flips bitmap for enemy
     * @param bitmap
     * @return
     */
    private Bitmap flip(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1,1);
        Bitmap dst = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }



    public Position getPos() {
        return pos;
    }

    public void setPos(int x, int y) { this.pos = new Position(x,y);
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
        if(enemy) {
            image = flip(image);
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(x);
        oos.writeObject(y);
        oos.writeObject(fileName);

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        x = (int)ois.readObject();
        y = (int)ois.readObject();
        fileName = (int)ois.readObject();
    }

}
