package com.bowool.gymnote;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;

public class ExerciseRecord extends DataSupport {
    private String actionName;
    private ArrayList<String> exerciseParts;
    private Date trainDay;
    private TrainRecord trainRecord;

    public Date getTrainDay() {
        return trainDay;
    }

    public void setTrainDay(Date trainDay) {
        this.trainDay = trainDay;
    }

    public ArrayList<String> getExerciseParts() {
        return exerciseParts;
    }

    public void setExerciseParts(ArrayList<String> exerciseParts) {
        this.exerciseParts = exerciseParts;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public TrainRecord getTrainRecord() {
        return trainRecord;
    }

    public void setTrainRecord(TrainRecord trainRecord) {
        this.trainRecord = trainRecord;
    }
}
