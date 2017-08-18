package com.bowool.gymnote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class NewRecordsActivity extends AppCompatActivity {


    ActionAdapter actionAdapter;
    ArrayList<Action> actionToday;
    ListView actionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_records);

        Integer [] defaultChronometer = { 30 , 60 , 90};
        ChronometerAdapter chronometerAdapter = new ChronometerAdapter(this,R.layout.chronometer_item,defaultChronometer);
        ListView chronometerList =(ListView) findViewById(R.id.chronometer_list);
        chronometerList.setAdapter(chronometerAdapter);

        getdatabase();//// TODO: 2017/8/19 for debug

        actionList =(ListView) findViewById(R.id.action_container);
        actionAdapter =new ActionAdapter(this ,R.layout.action_item,actionToday);
        actionList.setAdapter(actionAdapter);



        setActionListClick();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    int flagActionBefore = -1;
    void setActionListClick(){
        actionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flagActionBefore != -1){
                    if (hasFinished(i)){
                        actionAdapter.setItemFinished(flagActionBefore);
                    }else{
                        actionAdapter.setItemNoFinished(flagActionBefore);
                    }
                }
                flagActionBefore =i;
                actionAdapter.setItemSelected(i);
            }
        });

    }
    boolean hasFinished(int i){//// TODO: 2017/8/19 judge the action has finished or not 
        return false;
    }

    public void save_action(View view) {
        Snackbar.make(view, R.string.dialog_save_action_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }




    public void getdatabase( ) {//TODO:debug app ,need to delete
        List<Action> ext = DataSupport.findAll(Action.class);
        actionToday = new ArrayList<Action> (ext);

    }

    public void deletedatabase(View view) {//TODO:debug app ,need to delete
        DataSupport.deleteAll(Action.class);
    }
    public void setActionDatabase(View v){
        Action action1 =new Action();
        action1.setActionName("卧推");
        action1.setExerciseParts(new String[]{"胸"});
        action1.save();

        Action action2 =new Action();
        action2.setActionName("硬拉");
        action2.setExerciseParts(new String[]{"腿"});
        action2.save();

        Action action3 =new Action();
        action3.setActionName("肩举");
        action3.setExerciseParts(new String[]{"肩"});
        action3.save();

    }
}
