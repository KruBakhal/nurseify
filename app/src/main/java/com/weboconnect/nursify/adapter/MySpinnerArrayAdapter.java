package com.weboconnect.nursify.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weboconnect.nursify.R;

import java.util.List;

/**
 * Created by Atut on 19-Aug-17.
 */


public class MySpinnerArrayAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
 /*   Typeface font = Typeface.createFromAsset(getContext().getAssets(),
            "font/Roboto-Regular.ttf");*/
    Context mycontext;
    // (In reality I used a manager which caches the Typeface objects)
    // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

    public MySpinnerArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.mycontext=context;
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        if(position==0) {
            view.setTextColor(mycontext.getResources().getColor(R.color.black));
        }
        else {
            view.setTextColor(mycontext.getResources().getColor(R.color.black));
        }
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
