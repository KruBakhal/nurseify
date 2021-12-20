package com.weboconnect.nurseify.screen.nurse.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.ActiveJobDetailsActivity;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private int type = 1;
    ActiveJobDetailsActivity activity;
    List<String> list;

    public RatingAdapter(ActiveJobDetailsActivity activity, int type, List<String> list) {
        this.activity = activity;
        this.list = list;
        this.type = type;
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
            if (type == 1) {
                strs = activity.selected_onBoard;
            } else if (type == 2)
                strs = activity.selected_onNurse;
            else if (type == 3)
                strs = activity.selected_onLeader;
            else if (type == 4)
                strs = activity.selected_onTool;

            if (!TextUtils.isEmpty(strs) && Integer.parseInt(strs) == position) {
                holder.com2.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_rating_active));
            } else {
                holder.com2.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_rating));
            }

            holder.com2.setText("" + list.get(position));

            holder.com2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 1) {
                        activity.selected_onBoard = String.valueOf(position);
                    } else if (type == 2)
                        activity.selected_onNurse = String.valueOf(position);
                    else if (type == 3)
                        activity.selected_onLeader = String.valueOf(position);
                    else if (type == 4)
                        activity.selected_onTool = String.valueOf(position);
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
