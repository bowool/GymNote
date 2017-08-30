package com.bowool.gymnote;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NewActionActivity extends AppCompatActivity {
    static String TAG = "gymnote.NewAction";
    private TagListView mExercisePartsTagListView;
    private final List<Tag> mExercisePartsTags = new ArrayList<Tag>();
    private TagListView mActionTagListView;
    private final List<Tag> mActionTags = new ArrayList<Tag>();
    private HashMap<Integer,ExercisePart> selectExerciseParts = new HashMap<>();
    private HashMap<Integer,Action> selectActions = new HashMap<>();
    
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
        ArrayList <ExercisePart> exerciseParts = new ArrayList<>(DataSupport.findAll(ExercisePart.class,true));
        selectExerciseParts.clear();
        mExercisePartsTags.clear();
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
                    selectExerciseParts.remove(tag.getId());
                    setUpActionLayout();
                }else{
                    tag.setOr(true);
                    selectExerciseParts.put(
                            tag.getId(),DataSupport.find(ExercisePart.class,tag.getId(),true)
                    );
                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    tagView.setChecked(true);
                    setUpActionLayout();
                }
            }
        });

        mExercisePartsTagListView.setmOnTagLongClickListener(new TagListView.OnTagLongClickListener() {
            @Override
            public void OnTagLongClickListener(TagView tagView, final Tag tag) {
                AlertDialog.Builder builder2=new AlertDialog.Builder(NewActionActivity.this);
                builder2.setMessage(getString(R.string.sure_to_delete,tag.getTitle()));
                builder2.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    //正能量按钮 Positive
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //这里写点击按钮后的逻辑代码
                        selectExerciseParts.remove(tag.getId());
                        mExercisePartsTags.remove(tag);
                        DataSupport.delete(ExercisePart.class,tag.getId());
                        mExercisePartsTagListView.removeTag(tag);
                    }
                });
                builder2.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    //负能量按钮 NegativeButton
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder2.create().show();

            }
        });

        TagView addNew = (TagView) View.inflate(this,
                R.layout.tag, null);
        addNew.setText(R.string.add_new);
        addNew.setBackgroundResource(R.drawable.tag_checked_normal);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newExercisePart(view);
            }
        });
        mExercisePartsTagListView.addView(addNew);
    }
    
    private void refreshSelectExerciseParts(){
        for (int i : (List<Integer>)getListByMap(selectExerciseParts,true)){
            selectExerciseParts.put(
                    i,DataSupport.find(ExercisePart.class,i,true)
                );
            }
        }

    private void setUpActionLayout() {
        final HashMap<Integer,Action> actions = new HashMap<>();
        mActionTags.clear();
        refreshSelectExerciseParts();
        for (ExercisePart select :(List<ExercisePart>) getListByMap(selectExerciseParts,false)){
            for (Action act :select.getActions()){
                actions.put(act.getId(),act);
                Log.d(TAG, "setUpActionLayout: select :"+select+"add : "+act);
            }

        }
        for (Action action :(List<Action>)getListByMap(actions,false)){
            Tag tag = new Tag();
            tag.setId(action.getId());
            tag.setChecked(true);
            tag.setTitle(action.getActionName() + "("
                    + DateManager.dayToNow(action.getLastTrainDay())+ getString(R.string.das_ago) + ")");
            for (Action act : (List<Action>)getListByMap(selectActions,false)){
                if (act.getId() == action.getId())
                    tag.setOr(true);
            }
            mActionTags.add(tag);

        }
        mActionTagListView.setTags(mActionTags);
        mActionTagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {

            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if(tag.getOr()==true){
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    selectActions.remove(tag.getId());
                }else{
                    tag.setOr(true);
                    tagView.setBackgroundResource(R.drawable.tag_checked_pressed);
                    selectActions.put(
                            tag.getId(),DataSupport.find(Action.class,tag.getId(),true)
                    );
                    tagView.setChecked(true);
                }
            }
        });

        mActionTagListView.setmOnTagLongClickListener(new TagListView.OnTagLongClickListener() {
            @Override
            public void OnTagLongClickListener(TagView tagView, final Tag tag) {
                AlertDialog.Builder builder2=new AlertDialog.Builder(NewActionActivity.this);
                builder2.setMessage(getString(R.string.sure_to_delete,tag.getTitle()));
                builder2.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    //正能量按钮 Positive
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //这里写点击按钮后的逻辑代码
                        selectActions.remove(tag.getId());
                        mActionTags.remove(tag);
                        DataSupport.delete(Action.class,tag.getId());
                        mActionTagListView.removeTag(tag);
                    }
                });
                builder2.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    //负能量按钮 NegativeButton
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder2.create().show();
            }
        });

        TagView addNew = (TagView) View.inflate(this,
                R.layout.tag, null);
        addNew.setText(R.string.add_new);
        addNew.setBackgroundResource(R.drawable.tag_checked_normal);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAction(view);
            }
        });
        mActionTagListView.addView(addNew);
    }

    void initExerciseParts(){
        /*RelativeLayout exercisePartsContainer = (RelativeLayout) findViewById(R.id.exercise_parts_container);
        ArrayList <ExercisePart> exerciseParts = new ArrayList<>(DataSupport.findAll(ExercisePart.class));
        for (ExercisePart exercisePart :exerciseParts){
            Button button = new Button(this);
            button.setText(exercisePart.getPartName());
            button.setBackground(getDrawable(R.drawable.button_shape_normal));
            GradientDrawable myGrad = (GradientDrawable)button.getBackground();
            myGrad.setColor(0xff000000 | new Random().nextInt(0x00ffffff));//随机取圆球颜色
            //TODO: not finished

        }*/

    }
    public void startTrain(View v){
        ArrayList<Integer> actionsToday = new ArrayList<>();
        ArrayList<Integer>exercisePartsToday = new ArrayList<>();
        for (Action act :(ArrayList <Action>)getListByMap(selectActions,false)){
            actionsToday.add(act.getId());
        }
        for (ExercisePart exp : (ArrayList<ExercisePart>)getListByMap(selectExerciseParts,false)){
            exercisePartsToday.add(exp.getId());
        }

        Intent intent = new Intent(NewActionActivity.this,NewRecordsActivity.class);
        intent.putIntegerArrayListExtra("actions_today",actionsToday);
        intent.putIntegerArrayListExtra("exercise_parts_today",exercisePartsToday);
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
        List<Tag> mExercisePartsDialogTags = null;
        try {
            mExercisePartsDialogTags = deepCopy(mExercisePartsTags);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //mExercisePartsTagListViewInDialog.resetTagSelect();
        mExercisePartsTagListViewInDialog.setTags(mExercisePartsDialogTags);

        final HashMap<Integer,ExercisePart> exercisePartsTmp = new HashMap<>(selectExerciseParts);
        mExercisePartsTagListViewInDialog.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                if(tag.getOr()){
                    tag.setOr(false);
                    tagView.setBackgroundResource(R.drawable.tag_checked_normal);
                    exercisePartsTmp.remove(tag.getId());

                }else{
                    tag.setOr(true);
                    exercisePartsTmp.put(
                            tag.getId(),DataSupport.find(ExercisePart.class,tag.getId(),true)
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
                Action actionTmp = new Action(name.getText().toString(),(ArrayList<ExercisePart>) getListByMap(exercisePartsTmp,false));

                actionTmp.save();
                for (ExercisePart exercisePart :(ArrayList<ExercisePart>) getListByMap(exercisePartsTmp,false)){
                    Log.d(TAG, "newAction: exercisePartsTmp"+exercisePart);
                    exercisePart.setActions(actionTmp);
                    exercisePart.save();
                }
                exercisePartsTmp.clear();


                setUpActionLayout();
            }
        });
        dialog.show();
    }
        /**
     * 根据map返回key和value的list
     *
     * @param map
     * @param isKey
     *            true为key,false为value
     * @return
     */
    public static List getListByMap(HashMap map,
                                            boolean isKey) {
        List  list = new ArrayList<>();

        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            if (isKey) {
                list.add(key);
            } else {
                list.add(map.get(key));
            }
        }

        return list;
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
