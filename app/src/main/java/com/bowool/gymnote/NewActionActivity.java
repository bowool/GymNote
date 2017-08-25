package com.bowool.gymnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Random;

public class NewActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_action);
    }
    void initExerciseParts(){
        RelativeLayout exercisePartsContainer = (RelativeLayout) findViewById(R.id.exercise_parts_container);
        ArrayList <ExercisePart> exerciseParts = new ArrayList<>(DataSupport.findAll(ExercisePart.class));
        for (ExercisePart exercisePart :exerciseParts){
            Button button = new Button(this);
            button.setText(exercisePart.getPartName());
            button.setBackground(getDrawable(R.drawable.button_shape_normal));
            GradientDrawable myGrad = (GradientDrawable)button.getBackground();
            myGrad.setColor(0xff000000 | new Random().nextInt(0x00ffffff));//随机取圆球颜色
            //TODO: not finished

        }

    }
    public void startTrain(View v){
        Intent intent = new Intent(NewActionActivity.this,NewRecordsActivity.class);
        startActivity(intent);
    }
    public void refreshView(){

    }
    public void newExercisePart(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View diaView = LayoutInflater.from(NewActionActivity.this).inflate(R.layout.add_exercise_part_dialog,null);
        final EditText name = diaView.findViewById(R.id.exercise_part_name);
        dialog.setView(diaView);
        dialog.setPositiveButton(getString(R.string.finished), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里写点击按钮后的逻辑代码
                ExercisePart exp = new ExercisePart(name.getText().toString());
                exp.save();
                Toast.makeText(NewActionActivity.this, getString(R.string.save_exercise_part) + "  " + exp.getPartName() + "  " +
                       getString(R.string.finished) ,Toast.LENGTH_SHORT).show();
                refreshView();
            }
        });
        dialog.show();



    }
    public void newAction(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View diaView = LayoutInflater.from(NewActionActivity.this).inflate(R.layout.add_aciton_dialog,null);
        final EditText name = diaView.findViewById(R.id.action_name);
        ArrayList <ExercisePart> exerciseParts = new ArrayList<>(DataSupport.findAll(ExercisePart.class));

        //TODO: not finished
        dialog.setView(diaView);
        dialog.setPositiveButton(getString(R.string.finished), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里写点击按钮后的逻辑代码
                ExercisePart exp = new ExercisePart(name.getText().toString());
                exp.save();
                refreshView();
            }
        });
        dialog.show();
    }
}
