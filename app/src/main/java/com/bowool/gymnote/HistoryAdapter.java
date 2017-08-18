package com.bowool.gymnote;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bowoo on 2017/8/19.
 */

public class HistoryAdapter extends ArrayAdapter<ArrayList>{


    public HistoryAdapter(Context context, int resource, List<ArrayList> objects) {
        super(context, resource, objects);
    }
}
