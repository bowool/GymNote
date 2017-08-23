package com.bowool.gymnote;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by bowool on 2017/8/16.
 */

public class Action extends DataSupport {
    private String actionName;
    private String exerciseParts;
    private  ArrayList<ExerciseRecord> exerciseRecords = new ArrayList<ExerciseRecord>();
    private Date lastTrainDay;
    private int id;

    public Action(String actionName, String exerciseParts) {
        this.actionName = actionName;
        this.exerciseParts = exerciseParts;
    }
    public Action(String actionName, String[] exerciseParts) {
        this.actionName = actionName;
        String exercisePartsTmp = null;
        for(String tmp :exerciseParts){
            if (exercisePartsTmp == null){
                exercisePartsTmp = tmp;
            }else{
                exercisePartsTmp = exercisePartsTmp + ","+tmp;
            }
        }
        this.exerciseParts = exercisePartsTmp;
    }

    public Action(String actionName, ArrayList<String> exerciseParts) {
        this.actionName = actionName;
        String exercisePartsTmp = null;
        for(String tmp :exerciseParts){
            if (exercisePartsTmp == null){
                exercisePartsTmp = tmp;
            }else{
                exercisePartsTmp = exercisePartsTmp + ","+tmp;
            }
        }
        this.exerciseParts = exercisePartsTmp;
    }

    public Action() {}

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public ArrayList<String> getExerciseParts() {
        return new ArrayList<>(Arrays.asList(exerciseParts.split(",")));
    }

    public void setExerciseParts(String exerciseParts) {
        this.exerciseParts = exerciseParts;
    }

    @Override
    public synchronized boolean save() {
        Log.d("gymnote","save action base : "+this.toString());
        return super.save();
    }

    @Override
    public String toString() {
        return id +" "+ actionName + " " + exerciseParts;

    }

    public int getId() {
        return id;
    }

    public ArrayList<ExerciseRecord> getExerciseRecords() {
        return new ArrayList (DataSupport.where("action_id = ?", String.valueOf(id)).find(ExerciseRecord.class));
    }

    public void setExerciseRecords(ArrayList<ExerciseRecord> exerciseRecords) {
        this.exerciseRecords = exerciseRecords;
    }

    public Date getLastTrainDay() {
        return lastTrainDay;
    }

    public void setLastTrainDay(Date lastTrainDay) {
        this.lastTrainDay = lastTrainDay;
    }
}
