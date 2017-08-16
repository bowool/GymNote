package com.bowool.gymnote;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by bowoo on 2017/8/16.
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
}
