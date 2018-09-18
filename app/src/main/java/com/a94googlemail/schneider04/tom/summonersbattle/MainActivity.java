package com.a94googlemail.schneider04.tom.summonersbattle;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Intent;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView background;
    public ImageButton battle_button, castle_Button, exitSquad_button;
    public LinearLayout squadView;
    public Character character;
    private GameSurface gamesurface;
    public ArrayList<Tuple> cardList = new ArrayList<>();
    public Tuple[] squad = new Tuple[8];
    public int selected = -1;

    /**
     * initializes everything
     */
    public void init(){
        gamesurface = new GameSurface(this);
        for(int i = 0; i<8;i++) {
            squad[i] = Tuple.readTuple("squadcard"+i, gamesurface);
        }

        SquadView(View.INVISIBLE);
        background = findViewById(R.id.background);
        background.setImageResource(R.drawable.b3background);
        battle_button = (ImageButton)findViewById(R.id.battleButton);
        battle_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    Intent t = new Intent(MainActivity.this, ArenaActivity.class);
                    startActivity(t);
                }
        });
        castle_Button = (ImageButton)findViewById(R.id.castleButton);
        castle_Button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                SquadView(View.VISIBLE);
                                             }
        }
        );
        exitSquad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SquadView(View.INVISIBLE);
            }
        });
        int tag = 0;


        LinearLayout layout = findViewById(R.id.scrollviewLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        for(Tuple card : cardList) {
            //Inflater
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.card_mainmenu, null);
            ImageButton but =  view.findViewById(R.id.cardImageButton);
            but.setImageResource(card.getFilename());
            but.setTag(tag);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected == -1){
                        selected = (int)view.getTag();
                    }
                }
            });
            ConstraintLayout cl = view.findViewById(R.id.cardLayout);
            ViewGroup.LayoutParams params = cl.getLayoutParams();
            params.width = (int)(width/8);
            cl.setLayoutParams(params);

            ImageView b = view.findViewById(R.id.cardHealthImageView);
            ViewGroup.LayoutParams param = b.getLayoutParams();
            param.height = (int)(param.width*0.5);
            param.width = (int)(param.width*0.5);
            b.setLayoutParams(param);


            b = view.findViewById(R.id.cardAtkImageView);
            param = b.getLayoutParams();
            param.height = (int)(param.width*0.5);
            param.width = (int)(param.width*0.5);
            b.setLayoutParams(param);

            ImageView ib = view.findViewById(R.id.cardAtkImageView);
            if(isMeele(card)){
                ib.setImageResource(R.drawable.melee);
            } else {
                ib.setImageResource(R.drawable.ranged);
            }

            GameObjectStats tempstats= getStats(card.getName());
            TextView text = view.findViewById(R.id.cardAtkTextView);
            text.setText(""+tempstats.getDamage());

            text = view.findViewById(R.id.cardHealthTextView);
            text.setText(""+tempstats.getHealth());

            text = view.findViewById(R.id.levelProgressTextView);
            Level lvl = tempstats.getLevel();
            int percent = (int)((double)lvl.getCurrentEp()*100/(double)lvl.calculateMaxEp());
            text.setText(""+ percent);
            text = view.findViewById(R.id.levelTextView);
            text.setText("Lvl"+lvl.getLevel());

            ProgressBar pbar = view.findViewById(R.id.levelProgressBar);
            pbar.setMax(lvl.calculateMaxEp());
            pbar.setProgress(lvl.getCurrentEp());

            view.bringToFront();
            layout.addView(view);
            view.bringToFront();
            tag++;
        }
        LinearLayout lay = findViewById(R.id.squadlinearLayout);
        int counter = 0;
        for(Tuple sq : squad){
            LayoutInflater infl = getLayoutInflater();
            View squadview = infl.inflate(R.layout.card_mainmenu, null);
            setCardLayout(squadview,sq,counter,width);
            squadview.bringToFront();
            lay.addView(squadview);
            squadview.bringToFront();
            counter++;
        }


    }

    /**
     * sets squadview
     * @param v Layout
     * @param t CardTuple
     * @param tag Position/Buttontag
     * @param width Screenwidth for size
     */
    private void setCardLayout(View v, Tuple t, int tag, int width) {
        ImageButton but =  v.findViewById(R.id.cardImageButton);
        v.setTag((int)tag);

        but.setImageResource(t.getFilename());
        but.setTag(tag);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean used = false;
                for(Tuple t : squad) {
                    if(t.getFilename()== cardList.get(selected).getFilename()){
                        used = true;
                    }
                }
                if (selected >=0 && !used){
                    squad[(int)view.getTag()] = cardList.get(selected);
                    ((ImageButton)view).setImageResource(squad[(int)view.getTag()].getFilename());
                    LinearLayout lay = findViewById(R.id.squadlinearLayout);
                    View vt = lay.findViewWithTag((int)view.getTag());

                    Tuple tt = squad[(int)view.getTag()];

                    ImageView ib = vt.findViewById(R.id.cardAtkImageView);
                    if(isMeele(tt)){
                        ib.setImageResource(R.drawable.melee);
                    } else {
                        ib.setImageResource(R.drawable.ranged);
                    }

                    GameObjectStats tstats= getStats(tt.getName());

                    TextView text = vt.findViewById(R.id.cardAtkTextView);
                    text.setText(""+tstats.getDamage());

                    text = vt.findViewById(R.id.cardHealthTextView);
                    text.setText(""+tstats.getHealth());

                    ProgressBar pb = vt.findViewById(R.id.levelProgressBar);
                    pb.setProgress(tstats.getLevel().getCurrentEp());
                    pb.setMax(tstats.getLevel().calculateMaxEp());
                    text =vt.findViewById(R.id.levelProgressTextView);
                    text.setText(""+(int)(((double)tstats.getLevel().getCurrentEp()*100)/((double)tstats.getLevel().calculateMaxEp())));

                    squad[(int)view.getTag()].writeTuple("squadcard"+(int)view.getTag(), gamesurface);
                }
                selected = -1;
            }
        });
        ConstraintLayout cl = v.findViewById(R.id.cardLayout);
        ViewGroup.LayoutParams params = cl.getLayoutParams();
        params.width = (int)(width/8);
        cl.setLayoutParams(params);

        ImageView b = v.findViewById(R.id.cardHealthImageView);
        ViewGroup.LayoutParams param = b.getLayoutParams();
        param.height = (int)(param.width*0.5);
        param.width = (int)(param.width*0.5);
        b.setLayoutParams(param);

        b = v.findViewById(R.id.cardAtkImageView);
        param = b.getLayoutParams();
        param.height = (int)(param.width*0.5);
        param.width = (int)(param.width*0.5);
        b.setLayoutParams(param);

        GameObjectStats tempstats= getStats(t.getName());
        TextView text = v.findViewById(R.id.cardAtkTextView);
        text.setText(""+tempstats.getDamage());

        text = v.findViewById(R.id.cardHealthTextView);
        text.setText(""+tempstats.getHealth());

        text = v.findViewById(R.id.levelProgressTextView);
        Level lvl = tempstats.getLevel();
        int percent = (int)((double)lvl.getCurrentEp()*100/(double)lvl.calculateMaxEp());
        text.setText(""+ percent);
        text = v.findViewById(R.id.levelTextView);
        text.setText("Lvl"+lvl.getLevel());

        ProgressBar pbar = v.findViewById(R.id.levelProgressBar);
        pbar.setMax(lvl.calculateMaxEp());
        pbar.setProgress(lvl.getCurrentEp());
    }

    /**
     * manages visibility for squadview
     * @param visibility
     */
    protected void SquadView(int visibility) {
        squadView = findViewById(R.id.squadlinearLayout);
        squadView.setVisibility(visibility);
        ImageView v = findViewById(R.id.background);
        v.setVisibility(4-visibility);

        HorizontalScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setVisibility(visibility);

        LinearLayout squadLayout = findViewById(R.id.squadlinearLayout);
        squadLayout.setVisibility(visibility);
        ImageButton fb = findViewById(R.id.battleButton);

        // Visible = 0 Invisible = 4 -> 4-visibility = 1. 4-4 = 0  2. 4-0 = 4
        fb.setVisibility(4-visibility);
        ImageButton cb = findViewById(R.id.castleButton);
        cb.setVisibility(4-visibility);


        exitSquad_button = findViewById(R.id.exitSquadimageButton);
        exitSquad_button.setVisibility(visibility);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        initCardTuples();
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateViews() {
        character = Character.readCharacter(true,"player1",R.drawable.adam,gamesurface,getBaseContext());
        ProgressBar levelprogressBar = (ProgressBar) findViewById(R.id.levelprogressBar);
        TextView levelprogressbarTextView = (TextView) findViewById(R.id.levelprogressbartextView);
        int curEp = character.getLevel().getCurrentEp();
        int maxEp = character.getLevel().calculateMaxEp();

        levelprogressBar.setMax(maxEp);
        levelprogressBar.setProgress(curEp);

        levelprogressbarTextView.setText(curEp/maxEp + "%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initCardTuples(){
        cardList.add(new Tuple("prophet",R.drawable.prophet));
        cardList.add(new Tuple("adam",R.drawable.adam));
        cardList.add(new Tuple("voidjuggler",R.drawable.voidjuggler));
        cardList.add(new Tuple("countvlad",R.drawable.countvlad));
        cardList.add(new Tuple("bard",R.drawable.bard));
        cardList.add(new Tuple("crow",R.drawable.crow));
        cardList.add(new Tuple("icegolem",R.drawable.icegolem));
        cardList.add(new Tuple("reaper",R.drawable.reaper));
        cardList.add(new Tuple("shaman",R.drawable.shaman));
        cardList.add(new Tuple("spirit",R.drawable.spirit));
        cardList.add(new Tuple("striker",R.drawable.striker));
        cardList.add(new Tuple("trixy",R.drawable.trixy));
        cardList.add(new Tuple("widow",R.drawable.widow));
    }



    private GameObjectStats getStats(String filename) {
        return GameObjectStats.readStats(filename,gamesurface);
    }

    private boolean isMeele(Tuple t) {
        GameObjectStats stats = GameObjectStats.readStats(t.getName(),gamesurface);
        return stats.isMeele();
    }
}
