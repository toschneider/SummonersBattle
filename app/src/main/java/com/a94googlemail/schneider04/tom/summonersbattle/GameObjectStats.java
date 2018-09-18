package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameObjectStats implements Serializable {

private int health;
private int damage;
private Level level;
private boolean isMeele;

private int poison ;
private int aoe ;
private int amountAttacks ;
private boolean freeze ;
private int lifeleech ;

private int block ;
private int thornes ;

private int poisoned ;
private boolean frozen ;



    public GameObjectStats(int health, int damage, Level level, boolean isMeele,int poison, int aoe, int amountAttacks,
                            boolean freeze, int block, int thornes, int lifeleech) {
        this.health = health;
        this.damage = damage;
        this.level = level;
        this.isMeele = isMeele;
        this.poison = poison;
        this. aoe = aoe;
        this.amountAttacks = amountAttacks;
        this.freeze = freeze;
        this.block = block;
        this.thornes = thornes;
        this.lifeleech = lifeleech;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(level);
        oos.writeObject(health);
        oos.writeObject(damage);
        oos.writeObject(isMeele);
        oos.writeObject(poison);
        oos.writeObject(aoe);
        oos.writeObject(amountAttacks);
        oos.writeObject(freeze);
        oos.writeObject(block);
        oos.writeObject(poisoned);
        oos.writeObject(frozen);
        oos.writeObject(thornes);
        oos.writeObject(lifeleech);
    }


    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        level = (Level) ois.readObject();
        health = (int) ois.readObject();
        damage = (int) ois.readObject();
        isMeele = (boolean) ois.readObject();
        poison = (int) ois.readObject();
        aoe = (int) ois.readObject();
        amountAttacks = (int)  ois.readObject();
        freeze = (boolean)  ois.readObject();
        block = (int)  ois.readObject();
        poisoned = (int)  ois.readObject();
        frozen = (boolean)  ois.readObject();
        thornes = (int)  ois.readObject();
        lifeleech = (int) ois.readObject();

    }

    /**
     * writes stats to internal storage
     * @param fileName
     * @param gameSurface
     */
    public void writeStats(String fileName, GameSurface gameSurface) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = gameSurface.getContext().openFileOutput(fileName+".stats", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads or creates new Stats
     * @param fileName
     * @param gameSurface
     * @return
     */
    public static GameObjectStats readStats(String fileName, GameSurface gameSurface) {
        FileInputStream fis;
        ObjectInputStream ois;
        GameObjectStats c;

        try {
            fis = gameSurface.getContext().openFileInput(fileName+".stats");
            ois  = new ObjectInputStream(fis);
            c = (GameObjectStats) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            int dmg = 0;
            int hp = 0;
            boolean isMeele = true;
            int poison = 0;
            int aoe = 0;
            int amountAttacks = 1;
            boolean freeze = false;
            int lifeleech= 0;

            int block = 0;
            int thornes = 0;
            if(fileName =="countvlad") {
                dmg = 6;
                hp = 6;
                isMeele = true;
                lifeleech = 2;
            } else if(fileName == "prophet") {
                dmg = 5;
                hp = 7;
                isMeele = true;
            } else if(fileName == "voidjuggler") {
                dmg = 8;
                hp = 5;
                isMeele = false;
                poison = 1;
                aoe = 3;
            } else if(fileName == "adam") {
                dmg = 3;
                hp = 10;
                isMeele = true;
            } else if(fileName == "bard") {
                dmg = 4;
                hp = 8;
                isMeele = false;
            } else if(fileName == "crow") {
                dmg = 4;
                hp = 8;
                isMeele = true;
            } else if(fileName == "icegolem") {
                dmg = 2;
                hp = 10;
                isMeele = true;
                freeze = true;
                block = 2;
            } else if(fileName == "reaper") {
                dmg = 4;
                hp = 7;
                isMeele = true;
                aoe = 2;
            } else if(fileName == "shaman") {
                dmg = 2;
                hp = 10;
                isMeele = false;
                thornes = 2;
            } else if(fileName == "spirit") {
                dmg = 12;
                hp = 1;
                isMeele = false;
            } else if(fileName == "striker") {
                dmg = 3;
                hp = 7;
                isMeele = true;
                amountAttacks = 2;
            } else if(fileName == "trixy") {
                dmg = 1;
                hp = 5;
                isMeele = true;
                lifeleech = 1;
                amountAttacks = 3;
            }  else if(fileName == "widow") {
                dmg = 2;
                hp = 4;
                isMeele = false;
                poison = 3;
            }
            c = new GameObjectStats(hp,dmg,new Level(1,0),isMeele, poison,aoe,amountAttacks,
                    freeze,block,thornes, lifeleech);
            c.writeStats(fileName, gameSurface);
        }
        return c;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isMeele() {
        return isMeele;
    }

    public void setMeele(boolean meele) {
        isMeele = meele;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public int getPoisoned() {
        return poisoned;
    }

    public void setPoisoned(int poisoned) {
        this.poisoned = poisoned;
    }

    public int getAoe() {
        return aoe;
    }

    public void setAoe(int aoe) {
        this.aoe = aoe;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isFreeze() {
        if (freeze) {
            freeze = false;
        }
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public int getAmountAttacks() {
        return amountAttacks;
    }

    public void setAmountAttacks(int amountAttacks) {
        this.amountAttacks = amountAttacks;
    }

    public int getThornes() {
        return thornes;
    }

    public void setThornes(int thornes) {
        this.thornes = thornes;
    }

    public int getLifeleech() {
        return lifeleech;
    }

    public void setLifeleech(int lifeleech) {
        this.lifeleech = lifeleech;
    }
}
