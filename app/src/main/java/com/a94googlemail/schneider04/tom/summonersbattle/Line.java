package com.a94googlemail.schneider04.tom.summonersbattle;

public class Line{
    private float p1x,p1y,p2x,p2y;
    float m,b;
    //Todo  Line  does not work right


    public Line(float p1x, float p1y, float p2x, float p2y) {
        this.p1x = p1x;
        this.p1y = p1y;
        this.p2x = p2x;
        this.p2y = p2y;
        m = (p2y-p1y)/(p2x-p1x);
        b = 0 - (p1x * m);
    }




    public float calcX(float y){
        return (y - b) / m;
    }

    public double calcY(float x){
        return m * x + b;
    }
}
