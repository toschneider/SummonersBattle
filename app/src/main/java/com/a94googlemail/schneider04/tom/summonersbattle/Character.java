package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Character  extends GameObject implements Serializable {
    private long lastDrawNanoTime =-1;

    public Character(String fileName,int imageName, int x, int y, Level level, boolean enemy, GameSurface gameSurface) {
        super(fileName,imageName,x,y,enemy , gameSurface);
        this.stats.setLevel(level);
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
        stats.setHealth(retHealth());
    }
    private int retHealth(){
        return 35 + 5 * stats.getLevel().getLevel();
    }

    /**
     * draws Character
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, getPos().getX(), getPos().getY(), null);
        canvas.drawBitmap(heart,getPos().getX()+image.getWidth()/2-heart.getWidth()/2,getPos().getY()-heart.getHeight()*1.1f,null);
        canvas.drawText(""+this.stats.getHealth(), getPos().getX()+image.getWidth()/2-heart.getWidth()*0.25f, getPos().getY()-heart.getHeight()*0.55f, paint);

    }

    public void earnedEp(int ep) {
        this.stats.getLevel().updateEp(ep);
    }

    /**
     * reads and scales Bitmap
     */
    protected void readImage() {
        super.readImage();
        Bitmap tmp = heart;
        heart = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.6),(int)(tmp.getHeight()*0.6),false);
    }

    public void receiveDamage(int dmg) {
        if(stats.getHealth()>0) {
            this.stats.setHealth(stats.getHealth() - ((this.stats.getHealth() > dmg)?dmg:this.stats.getHealth()));
        }
    }

    public Level getLevel() {
        return stats.getLevel();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(enemy);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        enemy = (boolean) ois.readObject();
    }

    /**
     * reads Character or creates a new one
     * @param player1 player1?
     * @param fileName
     * @param imageName R id
     * @param gamesurface
     * @param context
     * @return
     */
    public static Character readCharacter(boolean player1,String fileName, int imageName, GameSurface gamesurface, Context context){
        FileInputStream fis;
        ObjectInputStream ois;
        Character c;
        try {
            fis = context.openFileInput(fileName);
            ois  = new ObjectInputStream(fis);
            c = (Character) ois.readObject();

            ois.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (player1) {
                c = new Character(fileName,imageName,(int)(0.025*gamesurface.getWidth()) ,(int)(0.35*gamesurface.getHeight()),
                        new Level(1,0),false,gamesurface);
            } else {
                c = new Character(fileName,imageName,(int)(0.875*gamesurface.getWidth() ),(int)(0.35*gamesurface.getHeight() ),
                        new Level(1,0),true,gamesurface);
            }
            c.writeCharacter(fileName,context);
        }
        return c;
    }

    /**
     * writes Character to internal storage
     * @param fileName
     * @param context
     */
    public void writeCharacter(String fileName, Context context) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
