package com.a94googlemail.schneider04.tom.summonersbattle;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected GameSurface gameSurface;
    
    protected GameObjectStats stats;

    protected Bitmap image;
    protected Bitmap heart;
    protected int imageName;
    protected Paint paint;
    protected String fileName;

    protected int x;
    protected int y;

    protected boolean enemy = false;

    protected Position pos;


    public GameObject(String fileName,int imageName, int x, int y, int health,boolean enemy, GameSurface gameSurface) {
        stats = GameObjectStats.readStats(fileName,gameSurface);
        pos = new Position(x,y);
        this.enemy = enemy;
        this.stats.setHealth(health);
        this.imageName = imageName;
        this.gameSurface = gameSurface;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        this.fileName = fileName;
    }

    public GameObject(String fileName,int imageName, int x, int y,boolean enemy, GameSurface gameSurface) {
        stats = GameObjectStats.readStats(fileName, gameSurface);
        pos = new Position(x,y);
        this.enemy = enemy;
        this.gameSurface = gameSurface;
        this.stats.setHealth(35);
        this.imageName = imageName;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        this.fileName = fileName;
    }

    public void setGameSurface(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    /**
     * reads all images
     */
    protected void readImage() {

        heart = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.heart);
        Bitmap temp = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.adam);

        if(enemy) {
            temp = flip(temp);
        }
        this.image = Bitmap.createScaledBitmap(temp, gameSurface.getHeight() / 5,gameSurface.getHeight()/4, false);
    }

    public void setCoord(int x, int y) {
        setXCoord(x);
        setYCoord(y);
    }

    public void setXCoord(int x) {
        this.x = x;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    /**
     * flips bitmap for enemy
     * @param bitmap
     * @return
     */
    protected Bitmap flip(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1,1);
        Bitmap dst = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    protected void flipBitmap() {
        this.image = flip(image);
    }



    public Position getPos() {
        return pos;
    }

    public void setPos(int x, int y) {
        this.pos = new Position(x,y);
        if (pos.getY()<4) {
            this.x = ((int)((0.245 + 0.100*pos.getX())*gameSurface.getWidth() -
                    image.getWidth()/2));
            this.y = (int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()-image.getHeight());
        }
    }

    public void setEnemy(boolean enemy) {
        if (!this.enemy ) {
            this.enemy = enemy;
            if(enemy) {
                image = flip(image);
            }
        }

    }

    /**
     * receives damage from enemy.dealdamag(Card)
     * @param dmg
     */
    public void receiveDamage(int dmg) {
        if(stats.getHealth()>0) {
            dmg -= stats.getBlock();
            stats.setHealth(stats.getHealth()-((stats.getPoisoned()>stats.getBlock())?stats.getPoisoned()-stats.getBlock():0));
            this.stats.setHealth(stats.getHealth()-((this.stats.getHealth() > dmg)?dmg:this.stats.getHealth()))  ;
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(x);
        oos.writeObject(y);
        oos.writeObject(imageName);
        oos.writeObject(pos);
        oos.writeObject(stats);

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        x = (int)ois.readObject();
        y = (int)ois.readObject();
        imageName = (int)ois.readObject();
        pos = (Position)ois.readObject();
        stats = (GameObjectStats)ois.readObject();
    }

}
