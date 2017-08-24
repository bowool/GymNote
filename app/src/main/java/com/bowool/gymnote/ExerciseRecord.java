package com.bowool.gymnote;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;

public class ExerciseRecord extends DataSupport {
    private Date trainDay;
    private int id ;
    private ArrayList<TrainRecord> trainRecords;

    public ExerciseRecord(Date trainDay) {
        this.trainDay = trainDay;
        trainRecords = new ArrayList<>();
    }

    public void addTrainRecord(TrainRecord t){
        trainRecords.add(t);
    }

    public Date getTrainDay() {
        return trainDay;
    }

    public void setTrainDay(Date trainDay) {
        this.trainDay = trainDay;
    }

    public int getId() {
        return id;
    }




    @Override
    public String toString() {
        return "ExerciseRecord{" +
                "trainDay=" + trainDay +
                ", id=" + id +
                '}';
    }

    public ArrayList<TrainRecord> getTrainRecords() {
        return trainRecords;
    }

    public void setTrainRecords(ArrayList<TrainRecord> trainRecords) {
        this.trainRecords = trainRecords;
    }

    @Override
    public synchronized boolean save() {
        Log.d("gymnote","save exercise base : "+this.toString());
        return super.save();
    }
}
