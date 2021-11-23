package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.ActiveJobDetailsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {

    Activity activity;

    public ActiveAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ActiveJobDetailsActivity.class));
                }
            });

        }catch (Exception e){
            Log.e("ActiveAdapter ",e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mainLayout;

        CircleImageView imageView;
        TextView tv_name;
        TextView tv_title;
        TextView tv_duration;
        TextView tv_shift;
        TextView tv_workingDay;
        TextView tv_amount;
        TextView tv_start_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            imageView = itemView.findViewById(R.id.imageView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            tv_shift = itemView.findViewById(R.id.tv_shift);
            tv_workingDay = itemView.findViewById(R.id.tv_working_day);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_start_date = itemView.findViewById(R.id.tv_start_date);

        }
    }
}