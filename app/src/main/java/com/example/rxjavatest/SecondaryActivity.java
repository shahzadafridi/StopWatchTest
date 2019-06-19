package com.example.rxjavatest;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class SecondaryActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.mili)
    TextView milliTv;
    @BindView(R.id.minute)
    TextView minuteTv;
    @BindView(R.id.seconds)
    TextView secondsTv;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.reset)
    Button reset;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    Unbinder unbinder;
    Observable<String> observable;
    Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        unbinder = ButterKnife.bind(this);
        handler = new Handler();
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        reset.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.start){
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
        }else if (v.getId() == R.id.pause){
            TimeBuff += MillisecondTime;
            handler.removeCallbacks(runnable);
        }else if (v.getId() == R.id.reset){
            MillisecondTime = 0L ;
            StartTime = 0L ;
            TimeBuff = 0L ;
            UpdateTime = 0L ;
            Seconds = 0 ;
            Minutes = 0 ;
            MilliSeconds = 0 ;
            minuteTv.setText(String.format("%02d", 00));
            secondsTv.setText(String.format("%02d", 00) );
            milliTv.setText(String.format("%03d", 00));
        }
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {


            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            minuteTv.setText(String.format("%02d", Minutes));
            secondsTv.setText(String.format("%02d", Seconds) );
            milliTv.setText(String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
