package com.bowool.gymnote;

import org.litepal.crud.DataSupport;

/**
 * Created by bowoo on 2017/8/20.
 */

public class TrainRecord extends DataSupport {
    private int weight;
    private int count;
    private int id ;

    public TrainRecord(int weight, int count) {
        this.weight = weight;
        this.count = count;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TrainRecord{" +
                "weight=" + weight +
                ", count=" + count +
                ", id=" + id +
                '}';
    }
}
