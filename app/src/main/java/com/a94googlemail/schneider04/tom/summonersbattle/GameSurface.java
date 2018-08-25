package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

private GameThread gameThread;

    protected List<Card> cardList = new ArrayList<>();
    protected List<Card> cardHand = new ArrayList<>();
    protected Character player1, player2;
    protected Card[][] playerCards = new Card[4][3];
    protected Card[][] enemyCards = new Card[4][3];

    protected Card selected;
    private Card test;


    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);
        // Set callback.
        this.getHolder().addCallback(this);
    }

    /**
     * Initializes both playerCharacters
     */
    private  void initCharacters(){
        player1 = new Character(R.drawable.adam,(int)(0.025 *this.getWidth()),(int)(0.35 *this.getHeight()),
                new Level(1,0),false,this);
        player2 = new Character(R.drawable.adam,(int)(0.875 *this.getWidth()),(int)(0.35 *this.getHeight()),
                new Level(1,0),true,this);
    }

    /**
     * Initializes Cards in cardList
     */
    private void initCards(){
        test = new RangedCard(R.drawable.gearicon, "Prophet",
                (int)(0.025 *this.getWidth()),(int)(0.35 *this.getHeight()),4,8,
                new Level(1,0),false,this);
        selected = test;
    }

    private void initTest() {
    }

    public void update()  {
        //updateCards();
    }

    private void updateCards(){
        for(Card card: cardList) {
            card.update();
        }
        for(Card[] playerCards: playerCards) {
            for(Card playerCard: playerCards){
                playerCard.update();
            }
        }
        for(Card[] enemyCards: enemyCards){
            for(Card enemyCard: enemyCards){
                enemyCard.update();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Position tmp = calcPos((int)event.getX(),(int)event.getY());
            //test.setPos((int)event.getX(),(int)event.getY());
            addToLists(tmp);


            return true;
        }
        return false;
    }

    private void addToLists(Position pos) {

        if(pos.getX()<=5 && pos.getX()>=0 && pos.getY()<=4&& pos.getY()>=0) {
            if(pos.getY()<=3) {
                if(pos.getX()<=2) {
                    selected.setEnemy(false);
                    selected.setPos((int)((0.245 + 0.100*pos.getX())*this.getWidth()) -
                            selected.image.getWidth()/2,
                            (int)((0.41 + 0.115*pos.getY())*this.getHeight()));
                    //playerCards[pos.getY()][pos.getX()] = selected;
                } else {
                    selected.setEnemy(true);
                    selected.setPos((int)((0.555 + 0.100*(pos.getX()-3))*this.getWidth()) -
                                    selected.image.getWidth()/2,
                            (int)((0.41 + 0.115*pos.getY())*this.getHeight()));

                    //enemyCards[pos.getY()][pos.getX()-3] = selected;
                }
            } else {
                //selected = cardHand.remove(pos.getX());
                //cardHand = null;
                //cardHand.add(getRandomCardFromList());
            }
        }
    }

    private Card getRandomCardFromList() {
        Random rnd = new Random();
        return cardList.get(rnd.nextInt(8));
    }

    private Position calcPos(int x, int y) {
        int arrx,arry;
        float tx,ty;
        arry = 99;
        arrx = 99;
        tx = (float)x / (float)this.getWidth();
        ty = (float)y / (float)this.getHeight();
        Log.d("CREATION", "\n"+"x: " + tx +"\n"+ "y: " + ty);

        double p1x,p1y,p2x,p2y;

        if(ty > 0.3 && ty < 0.415){
            arry = 0;
        } else if (ty > 0.415 && ty < 0.5277) {
            arry = 1;
        } else if (ty > 0.5277 && ty < 0.6407) {
            arry = 2;
        } else if (ty > 0.6407 && ty < 0.7546) {
            arry = 3;
        }

        if (tx > 0.195 && tx < 0.295) {
            arrx = 0;
        } else if (tx > 0.295 && tx < 0.395) {
            arrx = 1;
        } else if (tx > 0.395 && tx < 0.495) {
            arrx = 2;
        } else if (tx > 0.505 && tx < 0.605) {
            arrx = 3;
        } else if (tx > 0.605 && tx < 0.705) {
            arrx = 4;
        } else if (tx > 0.705 && tx < 0.805) {
            arrx = 5;
        }

        Log.d("CREATION", ""+"arrx: " + arrx +"\n"+ "arry: " + arry);
        return new Position(arrx,arry);
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        drawBackground(canvas);
        drawCharacters(canvas);
        drawCards(canvas);
    }

    private void drawCharacters(Canvas canvas) {
        player1.draw(canvas);
        player2.draw(canvas);
    }


    private void drawCards(Canvas canvas) {

        //super.draw(canvas);
        selected.draw(canvas);
        //test.draw(canvas);
        //cardList.get(1).draw(canvas);
        /*for(Card card: cardsHand) {
            //card.draw(canvas);
        }
        for(Card[] playerCards: playerCards) {
            for(Card playerCard: playerCards){
                //playerCard.draw(canvas);
            }
        }
        for(Card[] enemyCards: enemyCards){
            for(Card enemyCard: enemyCards){
                //enemyCard.draw(canvas);
            }
        }*/
    }

    /**
     * Draws the Background into the given Canvas
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        Bitmap background = BitmapFactory.decodeResource(this.getResources(), R.drawable.battlebackground2x3new);
        Bitmap backgroundscaled = Bitmap.createScaledBitmap(background, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(backgroundscaled,0,0, null);

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        initCharacters();
        initCards();
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }


}
