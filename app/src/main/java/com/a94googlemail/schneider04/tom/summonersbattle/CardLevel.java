package com.a94googlemail.schneider04.tom.summonersbattle;

public class CardLevel {
    private int level;
    private int currentEp;

    public CardLevel(int level, int currentEp) {
        this.level = level;
        this.currentEp = currentEp;
    }

    public void updateEp(int ep) {
        if(this.currentEp + ep >= calculateMaxEp()) {
            this.level++;
            this.currentEp = ep + this.currentEp - calculateMaxEp();
            calculateMaxEp();
        } else {
            this.currentEp += ep;
        }
    }

    private int calculateMaxEp() {
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
}
