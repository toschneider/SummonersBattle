package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RangedCard extends Card {
    public RangedCard(int fileName, String name, int x, int y, int damage, int health, Level level, boolean enemy, GameSurface gameSurface) {
        super(fileName, name, x, y, damage, health, level, enemy, gameSurface);
        readImage();
    }

    public void readImage(){
        super.readImage();
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.ranged);
        atk = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.55f),(int)(tmp.getHeight()*0.55f),false);
    }
}
