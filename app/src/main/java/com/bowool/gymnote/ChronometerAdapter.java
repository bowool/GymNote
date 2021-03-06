package com.bowool.gymnote;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bowool on 2017/8/13.
 * for chronometer
 */

class ChronometerAdapter extends ArrayAdapter <Integer>{
    ArrayList <Integer> mTimes = new ArrayList<>() ;
    Context mContext;
    private int resourceId;

    ArrayList <View> views = new ArrayList<>();
    ArrayList <Chronometer> chronometers = new ArrayList<>();
    final String TAG="GymNote.Chronometer";
    ChronometerAdapter(Context context, int resource, Integer[] obj) {
        super(context, resource, obj);
        mContext = context;
        resourceId = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position >= views.size()){
            views.add(position , LayoutInflater.from(getContext()).inflate(resourceId , parent ,false));

            TextView switchOfChronometer = views.get(position).findViewById(R.id.switch_of_chronometer);
            TextView chronometerView = views.get(position).findViewById(R.id.view_of_chronometer);
            EditText timeToSet = views.get(position).findViewById(R.id.time_of_chronometer);



            mTimes.add(position,getItem(position));
            Log.d(TAG,"mTimer :getItem"+mTimes.get(position));
            chronometerView.setText(new SimpleDateFormat("mm:ss.SSS").format(new Date(mTimes.get(position) *1000)));//设置当前计时器默认时间
            timeToSet.setText(mTimes.get(position).toString());//设置当前计时器默认值

            chronometers.add(position,new Chronometer(chronometerView,switchOfChronometer,mTimes.get(position)));

            switchOfChronometer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTimes.set(position,Integer.valueOf(
                            ((EditText)views.get(position).findViewById(R.id.time_of_chronometer))
                                    .getText().toString()));//每次读取计时器当前默认值
                    Log.d(TAG,"mTimer :switch"+mTimes.get(position) + "position "+position);
                    chronometers.get(position).setTimer(mTimes.get(position));
                    chronometers.get(position).onSwitch();
                }
            });


        }



        return views.get(position);
    }


}

