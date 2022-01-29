package com.nurseify.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nurseify.app.R;
import com.nurseify.app.screen.nurse.model.HourlyRate_Common_OptionDatum;
import com.nurseify.app.screen.nurse.model.WorkLocationDatum;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;

    List<HourlyRate_Common_OptionDatum> list_shift_durations;
    List<WorkLocationDatum> workLocationDatumList;

    public CustomAdapter(Context context, List<HourlyRate_Common_OptionDatum> list_shift_durations) {
        this.context = context;
        this.list_shift_durations = list_shift_durations;
    }

    public CustomAdapter(Context context, List<WorkLocationDatum> list_geography, int i) {
        this.context = context;
        this.workLocationDatumList = list_geography;

    }

    @Override
    public int getCount() {
        if (workLocationDatumList != null) {
            if (workLocationDatumList.size() > 0) {
                return workLocationDatumList.size();
            } else return 0;
        } else
            return list_shift_durations.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_popup_dropdown, null);
        TextView title = (TextView) view.findViewById(R.id.tvText);
        View lay_item = view.findViewById(R.id.lay_item);
        title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        if (workLocationDatumList != null && workLocationDatumList.size() > 0) {
            title.setText(workLocationDatumList.get(i).getName());
        } else {
            title.setText(list_shift_durations.get(i).getName());
        }
        return view;
    }
}