package com.a94googlemail.schneider04.tom.summonersbattle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {
    private int level;
    private int currentEp;

    public Level(int level, int currentEp) {
        this.level = level;
        this.currentEp = currentEp;
    }

    /**
     * updates all stats
     * @param ep earned ep
     */
    public void updateEp(int ep) {
        if(this.currentEp + ep >= calculateMaxEp()) {
            this.level++;
            this.currentEp = ep + this.currentEp - calculateMaxEp();
            calculateMaxEp();
        } else {
            this.currentEp += ep;
        }
    }

    /**
     * calculates the needed ep
     * @return
     */
    public int calculateMaxEp() {
        int levelFactor = 5;
        int originalMaxEp = 1000;
        return (int)(originalMaxEp * Math.pow((1 + levelFactor /100), this.level));
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentEp() {
        return currentEp;
    }

    public Level clone() {
        return new Level(level,currentEp);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(level);
        oos.writeObject(currentEp);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        level = (int)ois.readObject();
        currentEp = (int)ois.readObject();
    }

}
