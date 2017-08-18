package com.bowool.gymnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bowoo on 2017/8/19.
 */

public class ActionAdapter extends ArrayAdapter <Action> {
    ArrayList <Action> actionsToday;
    Context mContext;
    private int resourceId;
    ArrayList <LinearLayout> views = new ArrayList<>();

    public ActionAdapter(Context context, int resource, List<Action> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;
        actionsToday= (ArrayList<Action>) objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        views.add(position, (LinearLayout) LayoutInflater.from(getContext()).inflate(resourceId, parent, false));
        ((TextView)views.get(position).findViewById(R.id.action_item_name)).setText(actionsToday.get(position).getActionName());
        return views.get(position);
    }

    public void setItemFinished(int position){
        views.get(position).findViewById(R.id.action_item_name).setBackgroundColor(views.get(position).getResources().getColor(R.color.itemFinished));
    }

    public void setItemFinished(Action action){
        setItemFinished(actionsToday.indexOf(action));
    }

    public void setItemNoFinished (int position){
        views.get(position).findViewById(R.id.action_item_name).setBackgroundColor(views.get(position).getResources().getColor(R.color.itemNoFinished));
    }
    public void setItemNoFinished (Action action){
        setItemNoFinished(actionsToday.indexOf(action));
    }
    public void setItemSelected(int position){
        views.get(position).findViewById(R.id.action_item_name).setBackgroundColor(views.get(position).getResources().getColor(R.color.itemSelected));
    }
    public void setItemSelected(Action action){
        setItemSelected(actionsToday.indexOf(action));
    }
}
