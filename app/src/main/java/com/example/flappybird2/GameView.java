package com.example.flappybird2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GameView extends View {

    private Bird bird;
    private final Handler handler;
    private final Runnable r;
    private ArrayList<Pipe> arrayPipes;
    private int sumPipe,distance;
    private int score,bestScore=0;
    private boolean start;
    private Context context;
    private int soundJump;
    private float volume;
    private boolean loadedSound;
    private SoundPool soundPool;
    private String dataBaseScore,playingScore;
    public static int scoreCount;
    public  static int difficulty=400;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://flappybird2-e6513-default-rtdb.firebaseio.com/");
    public GameView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        this.context=context;
        SharedPreferences sp=context.getSharedPreferences("gameSetting",Context.MODE_PRIVATE);
        if(sp != null){
            bestScore=sp.getInt("bestScore",0);
        }
        score=0;
        start=false;
        initBird();
        initPipe();
        handler=new Handler();
        r=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        if(Build.VERSION.SDK_INT >= 21){
            AudioAttributes audioAttributes=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool= builder.build();
        }else{
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        }

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                loadedSound=true;

            }
        });
        soundJump = this.soundPool.load(context,R.raw.jump_02,1);

    }

    private void initPipe() {
        sumPipe=6;
        distance=difficulty*Constants.SCREEN_HEIGHT/1920;
        arrayPipes=new ArrayList<>();
        for(int i=0;i<sumPipe;i++){
            if(i < sumPipe/2){

                this.arrayPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1080)/(sumPipe/2)),0,1000*Constants.SCREEN_WIDTH/1080,2*Constants.SCREEN_HEIGHT/16));
                this.arrayPipes.get(this.arrayPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe2));
                this.arrayPipes.get(this.arrayPipes.size()-1).randomY();

            }else{
                this.arrayPipes.add(new Pipe(this.arrayPipes.get(i-sumPipe/2).getX(),this.arrayPipes.get(i-sumPipe/2).getY()+this.arrayPipes.get(i-sumPipe/2).getHeight()+this.distance,1000*Constants.SCREEN_WIDTH/1080,2*Constants.SCREEN_HEIGHT/16));
                this.arrayPipes.get(this.arrayPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe1));
            }

        }

    }

    private void initBird() {
        bird = new Bird();
        bird.setWidth(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setHeight(100 * Constants.SCREEN_HEIGHT / 1920);
        bird.setX(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setY(Constants.SCREEN_HEIGHT/2 - bird.getHeight()/2);
        ArrayList<Bitmap> arrBms=new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        bird.setArrBms(arrBms);
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if(start){
            bird.draw(canvas);
            for(int i=0;i<sumPipe;i++){

                if(bird.getRect().intersect(arrayPipes.get(i).getRect()) || bird.getY()- bird.getHeight()<0 || bird.getY()>Constants.SCREEN_HEIGHT){
                    Pipe.speed=0;
                    MainActivity.txt_score_over.setText(MainActivity.txt_score.getText());
                    dataBaseScore=Login.hScore;
                    playingScore= (String) MainActivity.txt_score.getText();
                    scoreCount=Integer.parseInt(dataBaseScore);
                    if(scoreCount<Integer.parseInt(playingScore)){
                        MainActivity.txt_best_score.setText("Best :"+ playingScore);
                        databaseReference.child("users").child(Login.phoneNumber).child("score").setValue(playingScore);
                    }else{
                        MainActivity.txt_best_score.setText("Best :"+ bestScore);
                    }

                    MainActivity.txt_score.setVisibility(INVISIBLE);
                    MainActivity.r1_game_over.setVisibility(VISIBLE);

                }


                if(this.bird.getX()+this.bird.getWidth() > arrayPipes.get(i).getX()+arrayPipes.get(i).getWidth()/2 && this.bird.getX()+this.bird.getWidth() <= arrayPipes.get(i).getX()+arrayPipes.get(i).getWidth()/2+Pipe.speed && i<sumPipe/2){

                    score++;
                    if(score>bestScore){
                        bestScore=score;
                        SharedPreferences sp= context.getSharedPreferences("GameSetting",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putInt("BestScore",bestScore);
                        editor.apply();
                    }
                    MainActivity.txt_score.setText(""+score);
                }

                if(this.arrayPipes.get(i).getX() < -arrayPipes.get(i).getWidth()){
                    this.arrayPipes.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i < sumPipe/2){
                        arrayPipes.get(i).randomY();
                    }else{
                        arrayPipes.get(i).setY(this.arrayPipes.get(i-sumPipe/2).getY()+this.arrayPipes.get(i-sumPipe/2).getHeight()+this.distance);
                    }
                }
                this.arrayPipes.get(i).draw(canvas);
            }
        }else{
            if(bird.getY()>Constants.SCREEN_HEIGHT/2){
                bird.setDrop(-15*Constants.SCREEN_HEIGHT/1920);
            }
            bird.draw(canvas);
        }
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            bird.setDrop(-15);
            if(loadedSound){
                int streamId = this.soundPool.play(this.soundJump,(float) 0.5,(float) 0.5,1,0,1f);

            }
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        MainActivity.txt_score.setText(0);
        score=0;
        initPipe();
        initBird();

    }
}
