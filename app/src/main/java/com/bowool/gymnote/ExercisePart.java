package com.bowool.gymnote;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bowoo on 2017/8/26.
 */

public class ExercisePart extends DataSupport {
    private ArrayList <Action> actions;
    private String partName;
    private Date lastTrainDay;
    private int id;

    public ExercisePart(String partName) {
        this.partName = partName;
    }

    public ExercisePart(ArrayList<Action> actions, String partName) {
        if(this.actions == null)
            actions = new ArrayList<>();
        this.actions.addAll(actions);
        this.partName = partName;
    }
    public ExercisePart(Action action, String partName) {
        if(this.actions == null)
            actions = new ArrayList<>();
        this.actions .add(action);
        this.partName = partName;
    }

    public ArrayList<Action> getActions() {
        return actions == null ? new ArrayList<Action>() : actions;
    }

    public String getPartName() {
        return partName;
    }

    public int getId() {
        return id;
    }

    public void setActions(ArrayList<Action> actions) {
        if(this.actions == null)
            actions = new ArrayList<>();
        this.actions.addAll(actions);
    }
    public void setActions(Action action){
        if(this.actions == null)
            actions = new ArrayList<>();
        this.actions.add(action);
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Date getLastTrainDay() {
        return lastTrainDay;
    }

    public void setLastTrainDay(Date lastTrainDay) {
        this.lastTrainDay = lastTrainDay;
    }

    @Override
    public String toString() {
        return "ExercisePart{" +
                "actions=" + getActions().size() +
                ", partName='" + partName + '\'' +
                ", lastTrainDay=" + lastTrainDay +
                ", id=" + id +
                '}';
    }

    @Override
    public synchronized boolean save() {
        Exception e = new Exception("gymnote");
        e.printStackTrace();
        Log.d("ExercisePart", "save: " + this.toString());
        return super.save();
    }
}
