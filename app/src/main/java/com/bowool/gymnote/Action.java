package com.bowool.gymnote;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bowool on 2017/8/16.
 */

public class Action extends DataSupport {
    private String actionName;
    private ArrayList <String> exerciseParts;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ArrayList<String> getExerciseParts() {
        return exerciseParts;
    }

    public void setExerciseParts(ArrayList<String> exerciseParts) {
        this.exerciseParts = exerciseParts;
    }
    public void setExerciseParts(String[] exerciseParts) {
        setExerciseParts(new ArrayList<>(Arrays.asList(exerciseParts)));
    }

    @Override
    public synchronized boolean save() {
        Log.d("bowool","save action base : "+this.toString());
        return super.save();
    }

    @Override
    public String toString() {
        return getActionName()+getExerciseParts().toString();

    }
}
