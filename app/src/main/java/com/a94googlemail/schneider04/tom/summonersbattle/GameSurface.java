package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

private GameThread gameThread;

private List<Card> cardList = new ArrayList<Card>();
    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        for(Card card: cardList) {
            card.update();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /*int x=  (int)event.getX();
            int y = (int)event.getY();

            int movingVectorX =x-  this.chibi1.getX() ;
            int movingVectorY =y-  this.chibi1.getY() ;

            this.chibi1.setMovingVector(movingVectorX,movingVectorY);*/
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        drawBackground(canvas);

        for(Card card: cardList)  {
            card.draw(canvas);
        }
    }



    private void drawBackground(Canvas canvas) {
        super.draw(canvas);
        Rect src = new Rect(0,0,1000,666);
        Rect dest = new Rect(0,0,canvas.getWidth(), canvas.getHeight());
        Bitmap background = BitmapFactory.decodeResource(this.getResources(), R.drawable.battlebackground2x3);
        Bitmap backgroundscaled = Bitmap.createScaledBitmap(background, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(backgroundscaled,0,0, null);

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        /*Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        ChibiCharacter chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);

        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi2);
        ChibiCharacter chibi2 = new ChibiCharacter(this,chibiBitmap2,300,150);

        this.chibiList.add(chibi1);
        this.chibiList.add(chibi2);
        */
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
