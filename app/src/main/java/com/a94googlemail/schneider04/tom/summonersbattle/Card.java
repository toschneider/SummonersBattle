package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Card extends GameObject implements Serializable {

    private int damage;
    private Level level;
    private String name;
    protected Bitmap atk;

    //protected Bitmap image;


    private long lastDrawNanoTime =-1;


    public Card(int fileName, String name, int x, int y, int damage, int health,
                Level level,boolean enemy , GameSurface gameSurface) {
        super(fileName,x, y, health,enemy, gameSurface);
        this.name = name;
        this.damage = damage;
        //this.cardImage = cardImage;
        this.level = level;
        readImage();


    }

    protected void readImage() {
        Bitmap temp = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.adam);
        this.image = Bitmap.createScaledBitmap(temp, (int)(0.12 * gameSurface.getHeight()),
                (int)(0.16 * gameSurface.getHeight()), false);
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.heart);
        heart = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.4),(int)(tmp.getHeight()*0.4),false);
    }

    //Todo
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

        drawCharacter(canvas);
        /*if(this.y == 4) {
            drawCard(canvas);
        } else {
            drawCharacter(canvas);
        }*/
    }



    public void drawCharacter(Canvas canvas) {
        canvas.drawBitmap(image, getPos().getX(), getPos().getY() - image.getHeight(),null);
        //canvas.drawBitmap(image, x, y,null);
        canvas.drawBitmap(heart, getPos().getX()+image.getWidth()-heart.getWidth()*0.75f,getPos().getY()- image.getHeight()-(heart.getHeight()*0.8f),null);
        this.lastDrawNanoTime= System.nanoTime();
        canvas.drawBitmap(atk,getPos().getX(), getPos().getY()- image.getHeight()-(atk.getHeight()*0.8f),null);
    }
    //Todo
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


    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(name);
        oos.writeObject(damage);
        oos.writeObject(health);
        oos.writeObject(level);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = (String)ois.readObject();
        damage = (int)ois.readObject();
        health = (int)ois.readObject();
        level = (Level)ois.readObject();
        readImage();
    }



}
