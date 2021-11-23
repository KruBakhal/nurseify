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

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.screen.nurse.JobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.model.OfferedJobModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferedJobAdapter extends RecyclerView.Adapter<OfferedJobAdapter.ViewHolder> {

    Activity activity;
    List <OfferedJobModel.OfferedJob> list;
    OfferedJobCallback callback;

    public OfferedJobAdapter(Activity activity, List <OfferedJobModel.OfferedJob> list,OfferedJobCallback callback) {
        this.activity = activity;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offered,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            int pp = position;

            holder.tv_name.setText(list.get(pp).getFacilityName());
            holder.tv_title.setText(list.get(pp).getJobTitle());
            holder.tv_duration.setText(list.get(pp).getAssignmentDurationDefinition());
            holder.tv_shift.setText(list.get(pp).getShiftDefinition());
            holder.tv_amount.setText("$ "+list.get(pp).getHourlyPayRate()+"/Hr");

            for (int i=0; i<list.get(pp).getWorkingDaysDefinition().size(); i++){
                if (i==0){
                    holder.tv_workingDay.setText(list.get(pp).getWorkingDaysDefinition().get(i));
                }
                else {
                    holder.tv_workingDay.append(", "+list.get(pp).getWorkingDaysDefinition().get(i));
                }
            }

            holder.layoutReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onReject(list.get(pp).getOfferId());
                }
            });

            holder.layoutAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   callback.onAccept(list.get(pp).getOfferId());
                }
            });

            Glide.with(holder.imageView.getContext()).load(list.get(pp).getFacilityLogo()).into(holder.imageView);

        }catch (Exception e){
            Log.e("OfferedJobAdapter ",e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutAccept,layoutReject;

        CircleImageView imageView;
        TextView tv_name,tv_title,tv_duration,tv_shift,tv_workingDay,tv_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutAccept = itemView.findViewById(R.id.layoutAccept);
            layoutReject = itemView.findViewById(R.id.layoutReject);
            imageView = itemView.findViewById(R.id.imageView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            tv_shift = itemView.findViewById(R.id.tv_shift);
            tv_workingDay = itemView.findViewById(R.id.tv_working_day);
            tv_amount = itemView.findViewById(R.id.tv_amount);

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
