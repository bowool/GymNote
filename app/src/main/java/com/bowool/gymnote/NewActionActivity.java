package com.bowool.gymnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bowool.gymnote.widget.*;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewActionActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    private TagListView mExercisePartsTagListView;
    private final List<Tag> mExercisePartsTags = new ArrayList<Tag>();
    private TagListView mActionTagListView;
    private final List<Tag> mActionTags = new ArrayList<Tag>();
    private ArrayList<ExercisePart> selectExerciseParts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_action);
        mExercisePartsTagListView = (TagListView) findViewById(R.id.exercise_parts_tag_view);
        mActionTagListView = (TagListView) findViewById(R.id.action_tag_view);
        setUpExercisePartsLayout();
        setUpActionLayout();



    }

    private void setUpExercisePartsLayout() {
        ArrayList <ExercisePart> exerciseParts = new ArrayList<>(DataSupport.findAll(ExercisePart.class));
        for (ExercisePart exercisePart :exerciseParts){
            Tag tag = new Tag();
            tag.setId(exercisePart.getId());
            tag.setChecked(true);
            tag.setTitle(exercisePart.getPartName() + "("
                    + DateManager.dayToNow(exercisePart.getLastTrainDay())+ getString(R.string.das_ago) + ")");
            mExercisePartsTags.add(tag);

        }
        mExercisePartsTagListView.setTags(mExercisePartsTags);
        mExercisePartsTagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {

            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if(tag.getOr()==true){
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    Toast.makeText(getApplicationContext(),"您取消了"+tagView.getText().toString(), Toast.LENGTH_LONG).show();
                    selectExerciseParts.remove(
                            DataSupport.find(ExercisePart.class,tag.getId(),true)
                    );
                }else{
                    tag.setOr(true);
                    Toast.makeText(getApplicationContext(),tagView.getText().toString()+"id"+tag.getId(), Toast.LENGTH_LONG).show();
                    selectExerciseParts.add(
                            DataSupport.find(ExercisePart.class,tag.getId(),true)
                    );
                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    tagView.setChecked(true);
                }
            }
        });
    }

    private void setUpActionLayout() {
        ArrayList <Action> actions = new ArrayList<>();
        for (ExercisePart select :selectExerciseParts){
            actions.addAll(select.getActions());
            Log.d(TAG, "setUpActionLayout: select :"+select);
        }
        for (Action action :actions){
            Tag tag = new Tag();
            tag.setId(action.getId());
            tag.setChecked(true);
            tag.setTitle(action.getActionName() + "("
                    + DateManager.dayToNow(action.getLastTrainDay())+ getString(R.string.das_ago) + ")");
            mActionTags.add(tag);

        }
        mActionTagListView.setTags(mActionTags);
        mActionTagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {

            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if(tag.getOr()==true){
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    Toast.makeText(getApplicationContext(),"您取消了"+tagView.getText().toString(), Toast.LENGTH_LONG).show();
                }else{
                    tag.setOr(true);
                    Toast.makeText(getApplicationContext(),tagView.getText().toString()+"id"+tag.getId(), Toast.LENGTH_LONG).show();
                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    tagView.setChecked(true);
                }
            }
        });
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
        setUpExercisePartsLayout();
        setUpActionLayout();

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
        TagListView mExercisePartsTagListViewInDialog = (TagListView) diaView.findViewById(R.id.exercise_parts_tag_view_in_dialog);
        mExercisePartsTagListViewInDialog.setTags(mExercisePartsTags);
        final ArrayList<ExercisePart> exercisePartsTmp = new ArrayList<>();
        mExercisePartsTagListViewInDialog.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if(tag.getOr()==true){
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    Toast.makeText(getApplicationContext(),"您取消了"+tagView.getText().toString(), Toast.LENGTH_LONG).show();
                    exercisePartsTmp.remove(
                            DataSupport.find(ExercisePart.class,tag.getId(),true)
                    );

                }else{
                    tag.setOr(true);
                    Toast.makeText(getApplicationContext(),tagView.getText().toString()+"id"+tag.getId(), Toast.LENGTH_LONG).show();
                    exercisePartsTmp.add(
                            DataSupport.find(ExercisePart.class,tag.getId(),true)
                    );

                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    tagView.setChecked(true);
                }
            }
        });

        //TODO: not finished
        dialog.setView(diaView);
        dialog.setPositiveButton(getString(R.string.finished), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里写点击按钮后的逻辑代码
                Action actionTmp = new Action(name.getText().toString(),exercisePartsTmp);
                exercisePartsTmp.clear();

                actionTmp.save();
                refreshView();
            }
        });
        dialog.show();
    }
}
