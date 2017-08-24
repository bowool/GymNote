package com.bowool.gymnote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class NewRecordsActivity extends AppCompatActivity {


    ActionAdapter actionAdapter;
    ArrayList<Action> actionToday;
    ListView actionList;
    ViewSwitcher historySwitcher;
    Context mContext;
    final String TAG = "gymnote.NewRecordsAct";
    private android.view.ViewGroup mRoot;
    ArrayList <ArrayList> trainRecordsToday;
    private ViewSwitcher recordsSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_records);
        mContext=getBaseContext();
        mRoot =(ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);

        /*chronometer list creator begin*/
        Integer [] defaultChronometer = { 30 , 60 , 90};
        ChronometerAdapter chronometerAdapter = new ChronometerAdapter(this,R.layout.chronometer_item,defaultChronometer);
        ListView chronometerList =(ListView) findViewById(R.id.chronometer_list);
        chronometerList.setAdapter(chronometerAdapter);
        /*chronometer list creator end*/

        /*action list creator begin*/
        TextView actionListTitle = (TextView)findViewById(R.id.action_view_title);
        String exercisePart = "胸";//// TODO: 2017/8/20 上一个界面写好之后以Intent传入的替换，如果可以，将最近锻炼时间放到上一个界面加载
        List<Action>acs =  DataSupport.findAll(Action.class);
        Date lastTrainDay =new Date();
        for(Action ac :acs){
            if(ac.getExerciseParts().contains(exercisePart)){
                if(ac.getExerciseRecords().size() > 1 && ac.getExerciseRecords().get(ac.getExerciseRecords().size() - 1 ) != null){
                    Date day = ac.getExerciseRecords().get(ac.getExerciseRecords().size() - 1 ).getTrainDay();
                    lastTrainDay = day.before(lastTrainDay) ?  day : lastTrainDay;}
            }
        }


        long dayToNow = DateManager.dayToNow(lastTrainDay);
        SpannableStringBuilder recordText= new SpannableStringBuilder(exercisePart);
        recordText.setSpan(new AbsoluteSizeSpan(100), 0, recordText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder dayToNowString= new SpannableStringBuilder("    " + dayToNow + getString(R.string.das_ago));
        dayToNowString.setSpan(new AbsoluteSizeSpan(50), 0, dayToNowString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionListTitle.setText(recordText.append(dayToNowString));


        getdatabase();//// TODO: 2017/8/19 for debug
        actionList =(ListView) findViewById(R.id.action_container);
        actionAdapter =new ActionAdapter(this ,R.layout.action_item,actionToday);
        actionList.setAdapter(actionAdapter);
        setActionListClick();
        /*action list creator end*/

        /*history list creator begin*/
        historySwitcher = (ViewSwitcher) findViewById(R.id.history_container);
        historySwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            // 实际上就是返回一个view
            @Override
            public View makeView()
            {
                return LayoutInflater.from(mContext).inflate(R.layout.history_view,mRoot,
                        false);
            }
        });
        // 页面加载时先显示空
        showHistory(null);
        /*history list creator end*/

        /*Operator station begin*/
        trainRecordsToday = new ArrayList<>();
        for (int i = 0 ; i  < actionToday.size() ; i++)
            trainRecordsToday.add(new ArrayList<TrainRecord>());


        recordsSwitcher = (ViewSwitcher) findViewById(R.id.record_train);
        recordsSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            // 实际上就是返回一个view
            @Override
            public View makeView()
            {
                return LayoutInflater.from(mContext).inflate(R.layout.records_view,mRoot,
                        false);
            }
        });
        showRecords(null);


        /*Operator station end*/



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showHistory(Action action){
        Log.d(TAG,"showHistory : "+action);
        View viewToShow =historySwitcher.getNextView();
        if (action == null){
            SpannableStringBuilder text= new SpannableStringBuilder(getString(R.string.history_empty));
            text.setSpan(new AbsoluteSizeSpan(60), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            viewToShow.findViewById(R.id.history_list).setVisibility(View.GONE);
            TextView tv = viewToShow.findViewById(R.id.history_action_name);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            historySwitcher.showNext();
        }else{
            List<ExerciseRecord>exRd =  DataSupport.where("action_id = ?", String.valueOf(action.getId())).find(ExerciseRecord.class,true);
            ExerciseRecord ex = exRd.get(0);///// TODO: exRd.size() == 0 ?
            Log.d(TAG, "showHistory: exRd : "+ exRd + "ex :"+ex);
            ArrayList<String>  showList =new ArrayList<>();
            for ( TrainRecord tr :ex.getTrainRecords()){
                showList . add(tr.getWeight() + "KG * " + tr.getCount() + "组"); //// TODO: 2017/8/20 格式转换
            }

            ListView historyList = viewToShow.findViewById(R.id.history_list);
            historyList.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_list_item_1, (String[]) showList.toArray(new String[showList.size()]));
            historyList.setAdapter(adapter);

            TextView tv = viewToShow.findViewById(R.id.history_action_name);

            long dayToNow = DateManager.dayToNow(ex.getTrainDay());
            SpannableStringBuilder recordText= new SpannableStringBuilder(getString(R.string.history_title));
            recordText.setSpan(new AbsoluteSizeSpan(60), 0, recordText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableStringBuilder dayToNowString= new SpannableStringBuilder("    " + dayToNow + getString(R.string.das_ago));
            dayToNowString.setSpan(new AbsoluteSizeSpan(30), 0, dayToNowString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv.setText(recordText.append(dayToNowString));
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            Log.d(TAG, "showHistory: "+action.getActionName());
            historySwitcher.showNext();

        }

    }

    public void showRecords(Action action){
        Log.d(TAG,"showRecords : "+action);
        View viewToShow =recordsSwitcher.getNextView();
        if (action == null){
            SpannableStringBuilder text= new SpannableStringBuilder(getString(R.string.history_empty));
            text.setSpan(new AbsoluteSizeSpan(60), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            viewToShow.findViewById(R.id.records_list).setVisibility(View.GONE);
            TextView tv = viewToShow.findViewById(R.id.records_finished);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            recordsSwitcher.showNext();
        }else{
            trainRecordsToday.get(actionToday.indexOf(action));

            ArrayList<String>  showList =new ArrayList<>();
            for ( TrainRecord tr : (ArrayList<TrainRecord>) trainRecordsToday.get(flagActionSelect)){
                showList . add(tr.getWeight() + "KG * " + tr.getCount() + "组"); //// TODO: 2017/8/20 格式转换
                Log.d(TAG, "showRecords: tr = "+tr);
            }
            ListView historyList = viewToShow.findViewById(R.id.records_list);
            historyList.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_list_item_1, (String[]) showList.toArray(new String[showList.size()]));
            historyList.setAdapter(adapter);
            Log.d(TAG, "showRecords: "+action.getActionName());
            recordsSwitcher.showNext();

        }

    }
    //左上角 动作列表 监听器
    int flagActionSelect = -1;
    void setActionListClick(){
        actionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flagActionSelect != -1){
                    if (hasFinished(i)){
                        actionAdapter.setItemFinished(flagActionSelect);
                    }else{
                        actionAdapter.setItemNoFinished(flagActionSelect);
                    }
                }
                flagActionSelect =i;
                actionAdapter.setItemSelected(i);
                showHistory(actionToday.get(i));
            }
        });

    }
    boolean hasFinished(int i){//// TODO: 2017/8/19 judge the action has finished or not
        return false;
    }

    public void save_action(View view) {
        Snackbar.make(view, R.string.dialog_save_action_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        showDialogOfItem();
    }

    double weightOfNewItem;
    int timesOfNewItem;
    void showDialogOfItem(){

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.add_item_dialog,null);

        PickerView minute_pv =v.findViewById(R.id.picker_one);
        PickerView second_pv =v.findViewById(R.id.picker_two);

        List<String> dataWeight = new ArrayList<String>();
        List<String> dataTimes = new ArrayList<String>();
        for (double i = 0,j = 2.5; j < 100; i++)
        {
            if(j < 30)
                j = j + 2.5;
            else
                j = j + 5;

            dataWeight.add(j + "");
        }
        for (int i = 0; i < 30; i++)
        {
            dataTimes.add(i + "");
        }
        minute_pv.setData(dataWeight);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(NewRecordsActivity.this, "选择了 " + text + " 重",
                        Toast.LENGTH_SHORT).show();
                weightOfNewItem = Double.valueOf(text);
            }
        });
        second_pv.setData(dataTimes);
        second_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(NewRecordsActivity.this, "选择了 " + text + " 组",
                        Toast.LENGTH_SHORT).show();
                timesOfNewItem = Integer.valueOf(text);
            }
        });

        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NewRecordsActivity.this,"继续浏览精彩内容",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNeutralButton("手动输入", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NewRecordsActivity.this,"起来活动活动吧" ,Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NewRecordsActivity.this,"欢迎下次使用", Toast.LENGTH_SHORT).show();
                saveTrainRecord();
            }
        });

        dialog.setView(v);
        Dialog ab = dialog.create();
        ab.show();

        WindowManager manager = getWindowManager();
        Display d = manager.getDefaultDisplay();
        Window window = ab.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = (int) (d.getHeight() * 0.5);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        ab.getWindow().setAttributes(params);


    }

    private void saveTrainRecord() {
        trainRecordsToday.get(flagActionSelect).add(new TrainRecord(weightOfNewItem,timesOfNewItem));
        showRecords(actionToday.get(flagActionSelect));
    }


    public void getdatabase( ) {//TODO:debug app ,need to delete
        List<Action> ext = DataSupport.findAll(Action.class);
        actionToday = new ArrayList<Action> (ext);
        for (Action action : ext){
            Log.d(TAG, "getdatabase: action is "+action+" ;action id is "+action.getId());
            Log.d(TAG, "getdatabase: action part is "+action.getExerciseParts());
            if (action.getExerciseParts() instanceof  ArrayList){
                Log.d(TAG, "getdatabase: is ArrayList ");
            }
            if (action.getExerciseParts().contains("腿")){
                Log.d(TAG, "getdatabase: read database :"+action);
            }
        }

    }



}
