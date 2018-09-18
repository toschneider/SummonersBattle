package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Card extends GameObject implements Serializable {
    protected String name;
    protected Bitmap atk;
    private int addedInRound = 999999;


    private long lastDrawNanoTime =-1;


    public Card(int imageName, String name, int x, int y, int damage, int health,
                Level level,boolean enemy , GameSurface gameSurface) {
        super(name,imageName,x, y, health,enemy, gameSurface);
        this.name = name;
        this.stats.setDamage(damage);
        this.stats.setLevel(level);
        readImage();
    }

    /**
     * reads and scales Bitmap
     */
    protected void readImage() {
        Bitmap temp = BitmapFactory.decodeResource(gameSurface.getResources(), imageName);
        this.image = Bitmap.createScaledBitmap(temp, (int)(0.12 * gameSurface.getHeight()),
                (int)(0.16 * gameSurface.getHeight()), false);
        Bitmap tmp = BitmapFactory.decodeResource(gameSurface.getResources(),R.drawable.heart);
        heart = Bitmap.createScaledBitmap(tmp,(int)(tmp.getWidth()*0.4),(int)(tmp.getHeight()*0.4),false);


    }

    public void setAddedInRound(int addedInRound) {
        this.addedInRound = addedInRound;
    }

    public int getAddedInRound() {
        return addedInRound;
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

    /**
     * draws either Card or CardCharacter
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if(this.getPos().getY() == 4) {
            drawCard(canvas);
        } else {
            drawCharacter(canvas);
        }
        this.lastDrawNanoTime= System.nanoTime();
    }


    /**
     * draws CardCharacter for gamefield
     * @param canvas
     */
    public void drawCharacter(Canvas canvas) {
        if(!enemy) {
            canvas.drawBitmap(image, x, y,null);
            canvas.drawBitmap(heart, ((int)((0.245 + 0.100*pos.getX())*gameSurface.getWidth() -
                    image.getWidth()/2))+image.getWidth()-heart.getWidth()*0.75f,(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()-image.getHeight())-(heart.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getHealth(),((int)((0.245 + 0.100*pos.getX())*gameSurface.getWidth() -
                    image.getWidth()/2))+image.getWidth()-heart.getWidth()*0.75f+heart.getWidth()*0.25f,(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()-image.getHeight())-(heart.getHeight()*0.15f),paint);
            this.lastDrawNanoTime= System.nanoTime();
            canvas.drawBitmap(atk,((int)((0.245 + 0.100*pos.getX())*gameSurface.getWidth() -
                    image.getWidth()/2)), (int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()-image.getHeight())-(atk.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getDamage(),((int)((0.245 + 0.100*pos.getX())*gameSurface.getWidth() -
                    image.getWidth()/2)+atk.getWidth()*0.25f),(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()-image.getHeight())-(atk.getHeight()*0.15f),paint);
        } else {
            canvas.drawBitmap(image, (int)((0.555 + 0.100*(pos.getX()))*gameSurface.getWidth() -
                    image.getWidth()/2), (int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()) - image.getHeight(),null);
            canvas.drawBitmap(heart, (int)((0.555 + 0.100*(pos.getX()))*gameSurface.getWidth() -
                    image.getWidth()/2)+image.getWidth()-heart.getWidth()*0.75f,(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()) - image.getHeight()-(heart.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getHealth(),(int)((0.555 + 0.100*(pos.getX()))*gameSurface.getWidth() -
                    image.getWidth()/2)+image.getWidth()-heart.getWidth()*0.75f+heart.getWidth()*0.25f,(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()) - image.getHeight()-(heart.getHeight()*0.15f),paint);
            this.lastDrawNanoTime= System.nanoTime();
            canvas.drawBitmap(atk,(int)((0.555 + 0.100*(pos.getX()))*gameSurface.getWidth() -
                    image.getWidth()/2), (int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()) - image.getHeight()-(atk.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getDamage(),(int)((0.555 + 0.100*(pos.getX()))*gameSurface.getWidth() -
                    image.getWidth()/2)+atk.getWidth()*0.25f,(int)((0.41 + 0.115*pos.getY())*gameSurface.getHeight()) - image.getHeight()-(atk.getHeight()*0.15f),paint);
        }
    }

    /**
     * draws Card in Cardhand
     * @param canvas
     */
    public void drawCard(Canvas canvas) {
        if(!enemy){
            canvas.drawBitmap(image,(int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025)),gameSurface.getHeight()-image.getHeight(),null);

            canvas.drawBitmap(heart, (int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))+image.getWidth()-heart.getWidth()*0.75f,
                    gameSurface.getHeight()- image.getHeight()-(heart.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getHealth(),(int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))+image.getWidth()-heart.getWidth()*0.75f+heart.getWidth()*0.25f,
                    gameSurface.getHeight()- image.getHeight()-(heart.getHeight()*0.15f),paint);

            canvas.drawBitmap(atk,(int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025)), gameSurface.getHeight()- image.getHeight()-(atk.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getDamage(),(int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025)+atk.getWidth()*0.25f),
                    gameSurface.getHeight()- image.getHeight()-(atk.getHeight()*0.15f),paint);
        } else {
            canvas.drawBitmap(image,gameSurface.getWidth()-image.getWidth()-((int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))),gameSurface.getHeight()-image.getHeight(),null);

            canvas.drawBitmap(heart, gameSurface.getWidth()-((int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))+image.getWidth()-heart.getWidth()*1.15f),
                    gameSurface.getHeight()- image.getHeight()-(heart.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getHealth(), gameSurface.getWidth()+heart.getWidth()*0.25f-((int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))+image.getWidth()-heart.getWidth()*1.15f),
                    gameSurface.getHeight()- image.getHeight()-(heart.getHeight()*0.15f), paint);

            canvas.drawBitmap(atk,gameSurface.getWidth()-image.getWidth()-((int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025))),
                    gameSurface.getHeight()- image.getHeight()-(atk.getHeight()*0.8f),null);
            canvas.drawText(""+stats.getDamage(),gameSurface.getWidth()-image.getWidth()-((int)(getPos().getX()*(image.getWidth()*1.3)+(int)(gameSurface.getWidth()*0.025)))+atk.getWidth()*0.25f,
                    gameSurface.getHeight()- image.getHeight()-(atk.getHeight()*0.15f),paint);
        }

    }

    public void earnedEp(int ep) {
        this.stats.getLevel().updateEp(ep);
    }


    private boolean isDead() {
        return this.stats.getHealth() == 0;
    }

    /**
     * deals damage to enemy
     * @param enemy
     */
    public void dealDamage( Card enemy) {
            if(enemy.stats.isFrozen()) {
                enemy.stats.setFrozen(false);
                return;
            }
            for(int i = 0; i<stats.getAmountAttacks();i++) {

                stats.setHealth(stats.getHealth()+ stats.getLifeleech()) ;
                enemy.stats.setFrozen(stats.isFreeze());
                if(enemy.stats.getPoisoned()<stats.getPoison()) {
                    enemy.stats.setPoisoned(stats.getPoison());
                }
                stats.setHealth(stats.getHealth()-enemy.stats.getThornes());
                if(stats.getAoe()>0){
                }
                enemy.receiveDamage(this.stats.getDamage()+stats.getLifeleech());
            }


        //}

    }

    public void attackCharacter(GameObject character) {
        character.receiveDamage(stats.getDamage());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(name);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        name = (String)ois.readObject();
        readImage();
        if (enemy){
            flip(image);
        }
    }

    /**
     * clones Card
     * @param classType RangedCard or MeeleCard
     * @return Card clone
     */
    public Card clone(Class classType) {
        if(classType == RangedCard.class) {
            return new RangedCard(imageName,name,pos.getX(),pos.getY(),stats.getDamage(),stats.getHealth(),stats.getLevel().clone(),enemy,gameSurface);
        } else {
            return new MeeleCard(imageName,name,pos.getX(),pos.getY(),stats.getDamage(),stats.getHealth(),stats.getLevel().clone(),enemy,gameSurface);
        }
    }

    public void writeCard(ObjectOutputStream oos){
        try {
            writeObject(oos);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
