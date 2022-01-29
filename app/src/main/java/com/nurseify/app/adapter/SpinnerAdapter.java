package com.nurseify.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nurseify.app.R;

import java.util.List;
public class SpinnerAdapter extends ArrayAdapter<String> {
     Context mycontext;
    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.mycontext=context;
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(mycontext.getResources().getColor(R.color.black));
        view.setGravity(Gravity.LEFT);
        //  view.setTypeface(font);
        return view;
    }
    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextColor(mycontext.getResources().getColor(R.color.black));
        // view.setTypeface(font);
        return view;
    }
}
