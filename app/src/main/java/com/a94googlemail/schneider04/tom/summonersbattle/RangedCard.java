package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class RangedCard extends Card {
    public RangedCard(int fileName, String name, int x, int y, int damage, int health, Level level, boolean enemy, GameSurface gameSurface) {
        super(fileName, name, x, y, damage, health, level, enemy, gameSurface);
        readImage();
        if (enemy){
            flip(image);
        }
    }

    public void readImage(){
        super.readImage();
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.ranged);
        atk = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.55f),(int)(tmp.getHeight()*0.55f),false);
    }

    public RangedCard clone() {
        return new RangedCard(imageName,name,pos.getX(),pos.getY(),stats.getDamage(),stats.getHealth(),stats.getLevel().clone(),enemy,gameSurface);
    }
    public static RangedCard readCard(String filename, int imageName, GameSurface gamesurface, Context context) {
        FileInputStream fis;
        ObjectInputStream ois;
        RangedCard r;
        try {
            fis = context.openFileInput(filename);
            ois  = new ObjectInputStream(fis);
            r = (RangedCard) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            int dmg = 0;
            int hp = 0;
            r = new RangedCard(imageName,filename,(int)(0.025 *gamesurface.getWidth()),(int)(0.35 *gamesurface.getHeight()),dmg,hp,
                    new Level(1,0),false,gamesurface);

        }
        return r;
    }
    public void writeCard(String filename, Context context) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(filename,Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            writeCard(oos);
            oos.close();
            fos.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
