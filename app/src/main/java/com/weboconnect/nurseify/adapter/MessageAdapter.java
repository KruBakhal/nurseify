package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.MessageActivity;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Activity activity;
    boolean isNurse;
    public MessageAdapter(Activity activity,boolean isNurse) {
        this.isNurse = isNurse;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isNurse){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        }else {
            view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_f,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, MessageActivity.class));
                }
            });
        }catch (Exception e){
            Log.e("Service_Adapter",e.toString());
        }
    }
    @Override
    public int getItemCount() {
        return 6;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
