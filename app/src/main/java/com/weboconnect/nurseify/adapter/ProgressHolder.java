package com.weboconnect.nurseify.adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weboconnect.nurseify.R;


public class ProgressHolder extends BaseViewHolder {
    ProgressBar progressBar;
   public TextView item_tv_msg;

    public ProgressHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
        item_tv_msg = itemView.findViewById(R.id.item_tv_msg);
    }

    @Override
    protected void clear() {
    }
}