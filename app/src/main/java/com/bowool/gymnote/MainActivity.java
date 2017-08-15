package com.bowool.gymnote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer [] defaultChronometer = { 30 , 60 , 90};
        ChronometerAdapter chronometerAdapter = new ChronometerAdapter(this,R.layout.chronometer_item,defaultChronometer);
        ListView chronometerList =(ListView) findViewById(R.id.chronometer_list);
        chronometerList.setAdapter(chronometerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void save_action(View view) {
        Snackbar.make(view, R.string.dialog_save_action_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
