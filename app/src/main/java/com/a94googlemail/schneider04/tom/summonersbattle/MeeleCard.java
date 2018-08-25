package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MeeleCard extends Card {

    public MeeleCard(int fileName, String name, int x, int y, int damage, int health, Level level, boolean enemy, GameSurface gameSurface) {
        super(fileName, name, x, y, damage, health, level, enemy, gameSurface);
        readImage();
    }

    public void readImage(){
        super.readImage();
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.melee);
        atk = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.35f),(int)(tmp.getHeight()*0.35f),false);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
}
