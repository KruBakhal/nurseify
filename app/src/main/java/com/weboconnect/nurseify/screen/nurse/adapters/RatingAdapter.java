package com.weboconnect.nurseify.screen.nurse.adapters;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.Completed_F_Adapter;
import com.weboconnect.nurseify.screen.facility.my_jobs.Complete_Jobs_Fragment;
import com.weboconnect.nurseify.screen.nurse.CompletedJobDetailsActivity;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private Complete_Jobs_Fragment adapter;
    private boolean isAdapter = false;
    private int type = 1;
    CompletedJobDetailsActivity activity;
    List<String> list;

    public RatingAdapter(CompletedJobDetailsActivity activity, int type, List<String> list) {
        this.activity = activity;
        this.list = list;
        this.type = type;
    }

    public RatingAdapter(Complete_Jobs_Fragment activity, int type, List<String> list, boolean b) {
        this.adapter = activity;
        this.list = list;
        this.type = type;
        this.isAdapter = b;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            String strs = "";
            if (isAdapter) {
                if (type == 1) {
                    strs = adapter.selected_onBoard;
                } else if (type == 2)
                    strs = adapter.selected_onNurse;
                else if (type == 3)
                    strs = adapter.selected_onLeader;
                else if (type == 4)
                    strs = adapter.selected_onTool;
            } else {
                if (type == 1) {
                    strs = activity.selected_onBoard;
                } else if (type == 2)
                    strs = activity.selected_onNurse;
                else if (type == 3)
                    strs = activity.selected_onLeader;
                else if (type == 4)
                    strs = activity.selected_onTool;
            }
            if (!TextUtils.isEmpty(strs) && Integer.parseInt(strs) == position) {
                holder.com2.setBackground(holder.itemView.getResources().getDrawable(R.drawable.bg_rating_active));
            } else {
                holder.com2.setBackground(holder.itemView.getResources().getDrawable(R.drawable.bg_rating));
            }

            holder.com2.setText("" + list.get(position));

            holder.com2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isAdapter) {
                        if (type == 1) {
                            activity.selected_onBoard = String.valueOf(position);
                        } else if (type == 2)
                            activity.selected_onNurse = String.valueOf(position);
                        else if (type == 3)
                            activity.selected_onLeader = String.valueOf(position);
                        else if (type == 4)
                            activity.selected_onTool = String.valueOf(position);
                    }else{
                        if (type == 1) {
                            adapter.selected_onBoard = String.valueOf(position);
                        } else if (type == 2)
                            adapter.selected_onNurse = String.valueOf(position);
                        else if (type == 3)
                            adapter.selected_onLeader = String.valueOf(position);
                        else if (type == 4)
                            adapter.selected_onTool = String.valueOf(position);
                    }
                    notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView com2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            com2 = itemView.findViewById(R.id.com2);

        }
    }

}
