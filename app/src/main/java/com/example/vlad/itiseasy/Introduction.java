package com.example.vlad.itiseasy;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class Introduction extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout activity_introduction;
    TextView taskShow;
    TextView mainTitle;
    EditText answerOfTask;
    Button btnNextTask, btnBack;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnC_ALL;
    ImageButton btnC;
    private int LVL =0;
    SoundPool sounds;
    private int sExplosion1,sExplosion2;
    Vibrator vibrator;
    MyTimer myTimer;
    private  static int tCounterNum=0;
    Task task;
    Intent intent_Keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent_Keyboard = getIntent();
        if(intent_Keyboard.getIntExtra("activity", 1) == 2){ //Определение необходимой для использования клавиатуры
            setContentView(R.layout.activity_introduction1);
        } else {
            setContentView(R.layout.activity_introduction);
        }

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sExplosion1 = sounds.load(getApplicationContext(), R.raw.wrong_ans, 1);
        sExplosion2 = sounds.load(getApplicationContext(), R.raw.right_ans, 1);

        activity_introduction = (RelativeLayout) findViewById(R.id.activity_introduction);

        //Только для кнопок клавиатуры
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnC = (ImageButton) findViewById(R.id.btnC);
        btnC_ALL = (Button) findViewById(R.id.btnC_ALL);

        btnNextTask = (Button) findViewById(R.id.btnNextTask);
        btnBack = (Button) findViewById(R.id.btnBack);

        mainTitle = (TextView) findViewById(R.id.mainTitle);
        taskShow = (TextView) findViewById(R.id.taskShow);

        answerOfTask = (EditText) findViewById(R.id.answerOfTask);

        btnNextTask.setTypeface(MainActivity.typeface);
        mainTitle.setTypeface(MainActivity.typeface);
        btnBack.setTypeface(MainActivity.typeface);
        taskShow.setTypeface(MainActivity.typeface);
        answerOfTask.setTypeface(MainActivity.typeface);
        btn1.setTypeface(MainActivity.typeface);
        btn2.setTypeface(MainActivity.typeface);
        btn3.setTypeface(MainActivity.typeface);
        btn4.setTypeface(MainActivity.typeface);
        btn5.setTypeface(MainActivity.typeface);
        btn6.setTypeface(MainActivity.typeface);
        btn7.setTypeface(MainActivity.typeface);
        btn8.setTypeface(MainActivity.typeface);
        btn9.setTypeface(MainActivity.typeface);
        btn0.setTypeface(MainActivity.typeface);
        btnC_ALL.setTypeface(MainActivity.typeface);

        btnNextTask.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        int iSelectedItem = bundle.getInt("RNB_CODE", 0);

        switch(iSelectedItem){
            case 0: LVL = 50;
                break;
            case 1: LVL = 150;
                break;
            case 2: LVL = 1000;
                break;
        }
        startTheApp();
    }

    public void startTheApp(){
        myTimer = MyTimer.getInstance(this);
        showTheTask();
    }

    //Генератор заданий
    public  void showTheTask(){
        task = new Task(LVL);
        taskShow.setText(task.generateTask());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnNextTask:
                if(answerOfTask.getText().toString().equals(""))
                    return;
                if (task.checkResult(Integer.parseInt(answerOfTask.getText().toString()))) {
                    tCounterNum += 5;
                    if(MainActivity.playTheMusic)
                        sounds.play(sExplosion2, 1.0f, 1.0f, 0, 0, 1.5f);
                        myTimer.correctAnswer();
                        myTimer.updateTheCounter();

                    //подсветка
                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(activity_introduction, "backgroundColor", Color.GREEN, Color.WHITE).setDuration(450);
                    objectAnimator.setEvaluator(new ArgbEvaluator());
                    objectAnimator.start();
                } else {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(activity_introduction, "backgroundColor", Color.RED, Color.WHITE).setDuration(450);
                    objectAnimator.setEvaluator(new ArgbEvaluator());
                    objectAnimator.start();
                    if(MainActivity.playTheMusic){
                        sounds.play(sExplosion1, 1.0f, 1.0f, 0, 0, 1.5f);
                        vibrator.vibrate(500);
                    }
                }
                showTheTask();
                answerOfTask.setText("");
                break;

            case R.id.btnBack:
                setResult(RESULT_CANCELED);
                tCounterNum = 0;
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel(true);
    }

    //обработчик кнопок
    public void onClickBtnNum(View v){
        switch(v.getId()){
            case R.id.btn1:
                answerOfTask.append("1");
                break;
            case R.id.btn2:
                answerOfTask.append("2");
                break;
            case R.id.btn3:
                answerOfTask.append("3");
                break;
            case R.id.btn4:
                answerOfTask.append("4");
                break;
            case R.id.btn5:
                answerOfTask.append("5");
                break;
            case R.id.btn6:
                answerOfTask.append("6");
                break;
            case R.id.btn7:
                answerOfTask.append("7");
                break;
            case R.id.btn8:
                answerOfTask.append("8");
                break;
            case R.id.btn9:
                answerOfTask.append("9");
                break;
            case R.id.btn0:
                answerOfTask.append("0");
                break;
            case R.id.btnC:
                if(answerOfTask.getText().toString().equals(""))
                    return;
                answerOfTask.setText(answerOfTask.getText().delete(answerOfTask.getText().length()-1, answerOfTask.getText().length()));
                break;
            case R.id.btnC_ALL:
                answerOfTask.setText("");
                break;
        }
    }

    static class MyTimer extends AsyncTask<Void, Void, Void> {

        TextView mTimer;
        TextView  mtCounter;
        Intent intent = new Intent();

        private static MyTimer instance;
        int timeCounter = 60;

        WeakReference<Activity> mWeakActivity;

        private MyTimer(Activity activity){
            mWeakActivity = new WeakReference<>(activity);
        }

        public static MyTimer getInstance(Activity activity){
            if(instance == null){
                instance = new MyTimer(activity);
                instance.execute();
            }
            return instance;
        }

        @Override
        protected void onPreExecute() {
            if (mWeakActivity != null) {
                mTimer = (TextView) mWeakActivity.get().findViewById(R.id.timer);
                mtCounter = (TextView) mWeakActivity.get().findViewById(R.id.tCounter);

                mTimer.setTypeface(MainActivity.typeface);
                mtCounter.setTypeface(MainActivity.typeface);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (timeCounter >0) {
                Log.d(MainActivity.TAG, "run: " + timeCounter);

                if (isCancelled()){
                    return null;
                }
                publishProgress();
                timeCounter--;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timeCounter =0;
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mTimer.setText(String.valueOf(timeCounter));
            mtCounter.setText(String.valueOf(Introduction.tCounterNum));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tCounterNum = 0;
            instance = null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finishTheGame();
        }

        void finishTheGame(){
            intent.putExtra("score", mtCounter.getText().toString());
            mWeakActivity.get().setResult(RESULT_OK,intent);
            tCounterNum = 0;
            mWeakActivity.get().finish();
            instance = null;
        }

        void updateTheCounter() {
            publishProgress();
        }

        void correctAnswer(){
            Animation anim = AnimationUtils.loadAnimation(mWeakActivity.get().getApplicationContext(), R.anim.true_answer);
            mtCounter.startAnimation(anim);
        }
    }
}
