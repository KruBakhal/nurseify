package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.JobDetailsActivity;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    Activity activity;

    public JobAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobs,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {


            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, JobDetailsActivity.class));
                }
            });

            holder.layoutApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   tearmsDialog();
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
        LinearLayout mainLayout,layoutApply;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutApply = itemView.findViewById(R.id.layoutApply);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }

    private void tearmsDialog(){
        final View loc = activity.getLayoutInflater().from(activity).inflate(R.layout.dialog_tearms, null);
        final Dialog dialog = new Dialog(activity,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView textApply = dialog.findViewById(R.id.text_apply);
        textApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
                dialog.dismiss();
            }
        });
    }

    private void selectDate(){
        final View loc = activity.getLayoutInflater().from(activity).inflate(R.layout.dialog_select_dates, null);
        final Dialog dialog = new Dialog(activity,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        CalendarView calenderView = dialog.findViewById(R.id.calenderView);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView textApply = dialog.findViewById(R.id.text_apply);
        textApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
