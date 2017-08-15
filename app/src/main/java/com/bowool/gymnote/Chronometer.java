package com.bowool.gymnote;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bowoo on 2017/8/15.
 */

public class Chronometer {
    final String TAG="GymNote.Chronometer";
    TextView chronometerView;
    TextView switchOfChronometer;
    boolean isRunning = false;
    int mTimer;
    Clock mChronometer;
    Chronometer(TextView showView ,TextView switcher){
        chronometerView = showView;
        switchOfChronometer = switcher;
        }
    Chronometer(TextView showView ,TextView switcher , int t){
        chronometerView = showView;
        switchOfChronometer = switcher;
        mTimer = t ;
    }

    public void onSwitch(){
        if(isRunning){
            mChronometer.cancel();
            switchOfChronometer.setText(R.string.start_chronometer);
            mChronometer = null;
        }else{
            mChronometer =new Clock(mTimer * 1000,256);
            switchOfChronometer.setText(R.string.stop_chronometer);
            mChronometer.start();
        }
        isRunning = !isRunning;

    }
    public void setTimer( int timer){
        mTimer = timer;
    }
    class Clock extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *            表示以毫秒为单位 倒计时的总数
         *
         *            例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *            表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *            例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */



        public Clock(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            chronometerView.setText(R.string.chronometer_done);
            switchOfChronometer.setText(R.string.start_chronometer);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            chronometerView.setText(new SimpleDateFormat("mm:ss.SSS").format(new Date(millisUntilFinished)));
        }
    }
}
