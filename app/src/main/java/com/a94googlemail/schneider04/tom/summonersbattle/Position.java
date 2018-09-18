package com.a94googlemail.schneider04.tom.summonersbattle;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setPos(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setPos(Position pos) {
        setPos(pos.getX(),pos.getY());
    }

    public Position clone() {
        return new Position(x,y);
    }
}
