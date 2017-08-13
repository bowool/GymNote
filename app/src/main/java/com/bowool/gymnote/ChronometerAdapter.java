package com.bowool.gymnote;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bowool on 2017/8/13.
 * for chronometer
 */

class ChronometerAdapter extends ArrayAdapter <Integer>{
    Integer mTime ;
    Context mContext;
    private int resourceId;
    TextView chronometerView;
    Button switchOfChronometer;
    EditText timeToSet;
    Chronometer mChronometer;
    boolean isRunning = false;
    final String TAG="GymNote.Chronometer";
    ChronometerAdapter(Context context, int resource, Integer[] obj) {
        super(context, resource, obj);
        mContext = context;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mTime = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId , parent ,false);
        switchOfChronometer = view.findViewById(R.id.switch_of_chronometer);
        chronometerView = view.findViewById(R.id.view_of_chronometer);
        timeToSet = view.findViewById(R.id.time_of_chronometer);

        timeToSet.setText(mTime.toString());//设置当前计时器默认值
        chronometerView.setText(new SimpleDateFormat("mm:ss.SSS").format(new Date(0)));//设置当前计时器默认时间
        switchOfChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTime =Integer.valueOf(timeToSet.getText().toString());//每次读取计时器当前默认值

                onSwitch();
            }
        });
        return view;
    }
    private void onSwitch(){
        if(isRunning){
            mChronometer.cancel();
            switchOfChronometer.setText(R.string.start_chronometer);
            mChronometer = null;
        }else{
            mChronometer =new Chronometer(mTime * 1000,100);
            switchOfChronometer.setText(R.string.stop_chronometer);
            mChronometer.start();
        }
        isRunning = !isRunning;

    }

    class Chronometer extends CountDownTimer {
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



        public Chronometer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            chronometerView.setText(R.string.chronometer_done);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, millisUntilFinished + "");
            chronometerView.setText(new SimpleDateFormat("mm:ss.SSS").format(new Date(millisUntilFinished)));
        }
    }
}

