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
    private ArrayList<ExercisePart> exerciseParts= new ArrayList<ExercisePart>();
    private  ArrayList<ExerciseRecord> exerciseRecords = new ArrayList<ExerciseRecord>();
    private Date lastTrainDay;
    private int id;

    public Action(String actionName, ExercisePart exerciseParts) {
        this.actionName = actionName;
        this.exerciseParts.add(exerciseParts);
    }

    public Action(String actionName, ArrayList<ExercisePart> exerciseRs) {
        this.actionName = actionName;
        this.exerciseParts.addAll(exerciseRs);
    }

    public Action() {}

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public ArrayList<ExercisePart> getExerciseParts() {
        return exerciseParts;
    }

    @Override
    public synchronized boolean save() {
        Log.d("gymnote","save action base : "+this.toString());
        return super.save();
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                ", exerciseParts=" + exerciseParts +
                ", exerciseRecords=" + exerciseRecords +
                ", lastTrainDay=" + lastTrainDay +
                ", id=" + id +
                '}';
    }

    public void addExerciseRecord(ExerciseRecord e){
        exerciseRecords.add(e);
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

    public void setExerciseParts(ArrayList<ExercisePart> exerciseParts) {
        this.exerciseParts.addAll(exerciseParts);
    }
}
