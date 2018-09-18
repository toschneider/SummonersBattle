package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MeeleCard extends Card {

    public MeeleCard(int fileName, String name, int x, int y, int damage, int health, Level level, boolean enemy, GameSurface gameSurface) {
        super(fileName, name, x, y, damage, health, level, enemy, gameSurface);
        readImage();
        if (enemy){
            flip(image);
        }
    }

    public void readImage(){
        super.readImage();
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.melee);
        atk = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.35f),(int)(tmp.getHeight()*0.35f),false);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    public MeeleCard clone() {
        MeeleCard m = new MeeleCard(imageName,name,pos.getX(),pos.getY(),stats.getDamage(),stats.getHealth(),stats.getLevel().clone(),enemy,gameSurface);
        m.stats = this.stats;
        return m;
    }
    public static MeeleCard readCard(String filename, int imageName, GameSurface gamesurface) {
        FileInputStream fis;
        ObjectInputStream ois;
        MeeleCard m;
        try {
            fis = gamesurface.getContext().openFileInput(filename);
            ois  = new ObjectInputStream(fis);
            m = (MeeleCard) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            int dmg = 0;
            int hp = 0;
            m = new MeeleCard(imageName,filename,(int)(0.025 *gamesurface.getWidth()),(int)(0.35 *gamesurface.getHeight()),dmg,hp,
                    new Level(1,0),false,gamesurface);

        }
        return m;
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
