package com.bowool.gymnote;

import android.content.Intent;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bowoo on 2017/8/16.
 */

public class TrainRecord_back extends DataSupport{
    ArrayList<Integer> count = null;
    ArrayList<Double> weight = null;
    private String countString = null;
    private String weightString = null;
    private int id;




    public ArrayList<Integer> getCount() {
        if(count == null && countString != null){
            count = new ArrayList<>();
            for(String countTmp : Arrays.asList(countString.split(","))){
                count.add(Integer.valueOf(countTmp));
            }
        }
        return count;
    }

    public ArrayList<Double> getWeight() {
        if(weight == null && weightString != null){
            weight = new ArrayList<>();
            for(String weightTmp : Arrays.asList(weightString.split(","))){
                weight.add(Double.valueOf(weightTmp));
            }
        }
        return weight;
    }

    public void add(int countNum,double weightNum){
        if (count == null && weight == null){
            count = new ArrayList<>();
            weight = new ArrayList<>();
        }
        count.add(countNum);
        weight.add(weightNum);
    }

    public int getId() {
        return id;
    }

    @Override
    public synchronized boolean save() {
        if (count != null && weight != null){
            String countStringTmp = null;
            for(Integer tmp :count){
                if (countStringTmp == null){
                    countStringTmp = tmp.toString();
                }else{
                    countStringTmp = countStringTmp + ","+tmp;
                }
            }

            countString =countStringTmp;

            String weightStringTmp = null;
            for(Double tmp :weight){
                if (weightStringTmp == null){
                    weightStringTmp = tmp.toString();
                }else{
                    weightStringTmp = weightStringTmp + ","+tmp;
                }
            }

            weightString = weightStringTmp;
        }
        return super.save();
    }

}
