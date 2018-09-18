package com.a94googlemail.schneider04.tom.summonersbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {


    protected List<Card> cardList = new ArrayList<>();
    protected Card[] cardHandPlayer = new Card[5];
    protected Card[] cardHandEnemy = new Card[5];
    protected Character player1, player2;
    protected Card[][] playerCards = new Card[4][3];
    protected Card[][] enemyCards = new Card[4][3];

    private SurfaceHolder holder;
    protected Card selected ;
    private Card test;
    private boolean attacking = false;
    private boolean playerTurn = true;
    private boolean selectedCard = false;
    private boolean selection = true;
    private Position origin ,current,dest;
    private ImageButton won;
    private int round = 0;
    private boolean finished = false;

    public GameSurface(Context context)  {
        super(context);
        won = findViewById(R.id.endimageButton);
        //won.setVisibility(INVISIBLE);
        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);
        // Set callback.
        this.getHolder().addCallback(this);
    }

    /**
     * Initializes both playerCharacters
     */
    private  void initCharacters(){
        player1 = Character.readCharacter(true,"player1",R.drawable.adam,this, getContext());
        player2 = Character.readCharacter(false,"player2",R.drawable.adam,this,getContext());
    }

    /**
     * Initializes Cards in cardList
     */
    private void initCards(){

        for(int i = 0; i<8; i++) {
            Tuple t = Tuple.readTuple("squadcard"+i, this);
            GameObjectStats stats = GameObjectStats.readStats(t.getName(), this);
            Card c;
            if (stats.isMeele()) {
                c = new MeeleCard(t.getFilename(),t.getName(),0,0,0,0,new Level(1,0),false,this);
            } else {
                c = new RangedCard(t.getFilename(),t.getName(),0,0,0,0,new Level(1,0),false,this);
            }
            c.stats = stats;
            cardList.add(c);
        }


        for(int i = 0; i<5; i++) {
            Random rndP = new Random();
            Card tmpP = cardList.get(rndP.nextInt(8));
            if (tmpP.getClass() == MeeleCard.class) {
                cardHandPlayer[i]=(((MeeleCard)tmpP).clone());
            } else if(tmpP.getClass() == RangedCard.class) {
                cardHandPlayer[i]=(((RangedCard)tmpP).clone());
            }
        }
        for(int j = 0; j<5; j++) {
            Random rndE = new Random();
            Card tmpE = cardList.get(rndE.nextInt(8));
            if (tmpE.getClass() == MeeleCard.class) {
                cardHandEnemy[j]=(((MeeleCard)tmpE).clone());
            } else if(tmpE.getClass() == RangedCard.class) {
                cardHandEnemy[j]=(((RangedCard)tmpE).clone());
            }
        }
        setNewPoscardHand(true);
        setNewPoscardHand(false);
        origin = new Position(99,99);
        dest = new Position(99,99);
        current = new Position(99,99);
        selectedCard = false;
    }


    /**
     * Winning
     * @param playerWon which player won
     */
    private void endGame(boolean playerWon) {
        if(playerWon) {
            int ep = (int)(300 * Math.pow(1.05,player1.getLevel().getLevel()));
            player1.earnedEp(ep);
            for(Card c : cardList) {
                c.earnedEp(ep);
            }
        } else {}
        finished = true;
    }

    private void atkMove( Card c1, Card c2){}

    /**
     * runs once per round and lets all charactercards attack the enemy
     */
    public void update()  {
        if(playerTurn) {
            if(!selection) {
                for(int i = 0; i<4;i++) {
                    for(int j = 2;j>=0;j--) {
                        if(playerCards[i][j] != null ) {
                            boolean dealedDMG = false;
                            for(int k = 0; k<3;k++) {
                                if(dealedDMG) {
                                    break;
                                } else {
                                    if(enemyCards[i][k] != null){
                                        atkMove(playerCards[i][j],enemyCards[i][k]);
                                        playerCards[i][j].dealDamage(enemyCards[i][k]);
                                        if(enemyCards[i][k].stats.getHealth() <= 0) {
                                            enemyCards[i][k] = null;
                                        }
                                        dealedDMG = true;
                                    }
                                }
                            }
                            if(!dealedDMG) {
                                playerCards[i][j].attackCharacter(player2);
                                if(player2.stats.getHealth()<=0) {
                                    endGame(true);
                                }
                            }
                            updateView();
                        }
                    }
                }
                selection = true;
                round++;
            }
        } else {
            if(!selection) {
                for(int i = 0; i<4;i++) {
                    for(int j = 2;j>=0;j--) {
                        if(enemyCards[i][j] != null ) {
                            boolean dealedDMG = false;
                            for(int k = 2; k>=0;k--) {
                                if(dealedDMG) {
                                    break;
                                } else {
                                    if(playerCards[i][k] != null){
                                        atkMove(enemyCards[i][j],playerCards[i][k]);
                                        enemyCards[i][j].dealDamage(playerCards[i][k]);
                                        if(playerCards[i][k].stats.getHealth() <= 0) {
                                            playerCards[i][k] = null;
                                        }
                                        dealedDMG = true;
                                    }
                                }
                            }
                            if(!dealedDMG) {
                                enemyCards[i][j].attackCharacter(player1);
                                if(player1.stats.getHealth()<=0) {
                                    endGame(false);
                                }
                            }
                            updateView();
                        }
                    }
                }
                selection = true;
                round++;
            }
        }
        invalidate();
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

    /**
     * Manages the Selection and Placing
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !finished) {
            Position pos = calcPos((int)event.getX(),(int)event.getY());
            if(playerTurn) {
                if(selectedCard && selection  && pos.getY() <=3  && pos.getX()<=2) {
                    playerCards[pos.getY()][pos.getX()] = cardHandPlayer[origin.getX()].clone( cardHandPlayer[origin.getX()].getClass());
                    playerCards[pos.getY()][pos.getX()].setPos(pos.getX(),pos.getY());
                    playerCards[pos.getY()][pos.getX()].setAddedInRound(round);
                    addRndCard(true, origin.getX());
                    setNewPoscardHand(true);


                    origin.setPos(99,99);
                    selection = false;
                    updateView();
                    update();
                    selectedCard = false;
                    playerTurn = false;

                } else if(!selectedCard && selection &&pos.getY() ==4 && pos.getX() <=4) {
                    selectedCard = true;
                    origin.setPos(pos);
                }
            } else  if(!playerTurn){
                if(selectedCard && selection  && pos.getY()<= 3 && pos.getX()>= 3 && pos.getX() <= 5) {
                    pos.setX(pos.getX()-3);
                    enemyCards[pos.getY()][pos.getX()] = cardHandEnemy[origin.getX()].clone(cardHandEnemy[origin.getX()].getClass());
                    enemyCards[pos.getY()][pos.getX()].setPos(pos.getX(),pos.getY());
                    enemyCards[pos.getY()][pos.getX()].setAddedInRound(round);
                    enemyCards[pos.getY()][pos.getX()].flipBitmap();
                    addRndCard(false,origin.getX());
                    setNewPoscardHand(false);

                    origin.setPos(99,99);
                    selection = false;
                    updateView();
                    update();
                    selectedCard = false;
                    playerTurn = true;
                } else if(!selectedCard && selection &&pos.getY() ==4 && pos.getX() >4 && pos.getX()<=9) {
                    selectedCard = true;
                    pos.setX(pos.getX()-5);
                    origin.setPos(pos);
                }
            }


            return true;
        }
        return false;
    }

    /**
     * adds random Card from CardList to CardHand
     * @param player
     * @param pos position in hand for new Card
     */
    private void addRndCard(boolean player, int pos) {
        Random rnd = new Random();
        Card tmp = cardList.get(rnd.nextInt(8));
        if (player) {
            if (tmp.getClass() == MeeleCard.class) {
                cardHandPlayer[pos]=((MeeleCard)tmp).clone();
                cardHandPlayer[pos] = ((MeeleCard)tmp).clone();
            } else if(tmp.getClass() == RangedCard.class) {
                cardHandPlayer[pos]=((RangedCard)tmp).clone();
            }
        } else {
            if (tmp.getClass() == MeeleCard.class) {
                cardHandEnemy[pos]=((MeeleCard)tmp).clone();
            } else if(tmp.getClass() == RangedCard.class) {
                cardHandEnemy[pos]=((RangedCard)tmp).clone();
            }
        }


    }

    /**
     * sets new Position for CardHands
     * @param player
     */
    private void setNewPoscardHand(boolean player) {

        if (player){
            int i = 0;
            for(Card card:cardHandPlayer) {
                card.setPos(i,4);
                i++;
            }
        } else {
            int i = 4;
            for(Card card:cardHandEnemy) {
                card.setPos(i,4);
                card.setEnemy(true);
                i--;
            }
        }

    }


    /**
     * calculates position in gamefield from displayCoordinates
     * @param x
     * @param y
     * @return
     */
    private Position calcPos(int x, int y) {
        int arrx,arry;
        float tx,ty;
        arry = 99;
        arrx = 99;
        tx = (float)x / (float)this.getWidth();
        ty = (float)y / (float)this.getHeight();

        if(ty> 0.3 && ty <0.7546 && tx > 0.195 && tx < 0.805) {
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
        } else if(ty > 0.785) {
            arry = 4;

            if(tx>0.01 && tx < 0.1) {
                arrx = 0;
            } else if(tx>0.11 && tx < 0.2) {
                arrx = 1;
            } else if(tx>0.21 && tx < 0.3) {
                arrx = 2;
            } else if(tx>0.3 && tx < 0.385) {
                arrx = 3;
            } else if(tx>0.397 && tx < 0.48) {
                arrx = 4;
            } else if(tx>0.53 && tx < 0.605) {
                arrx = 5;
            } else if(tx>0.624 && tx < 0.7) {
                arrx = 6;
            } else if(tx>0.72 && tx < 0.79) {
                arrx = 7;
            } else if(tx>0.812 && tx < 0.885) {
                arrx = 8;
            } else if(tx>0.905 && tx < 0.978) {
                arrx = 9;
            }
        }

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
        if (player1 != null){
            player1.draw(canvas);
        }
        if(player2 != null) {
            player2.draw(canvas);
        }


    }


    private void drawCards(Canvas canvas) {

        for(Card cardP: cardHandPlayer) {
            cardP.draw(canvas);
        }
        for(Card cardE: cardHandEnemy) {
            cardE.draw(canvas);
        }
        for(int i = 0; i<4;i++) {
            for (int j = 0;j<3;j++) {
                if(playerCards[i][j]!= null) {
                    playerCards[i][j].drawCharacter(canvas);
                }
                if(enemyCards[i][j]!= null) {
                    enemyCards[i][j].drawCharacter(canvas);
                }
            }
        }
        for(Card[] enCards: enemyCards){
            for(Card enCard: enCards){
                enCard.draw(canvas);
            }
        }
    }

    public void updateView() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            draw(canvas);
        }catch(Exception e)  {
            // Do nothing.
        } finally {
            if(canvas!= null)  {
                // Unlock Canvas.
                holder.unlockCanvasAndPost(canvas);
            }
        }
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
        //setWillNotDraw(false);
        this.holder = holder;
        initCharacters();
        initCards();
        updateView();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


}
