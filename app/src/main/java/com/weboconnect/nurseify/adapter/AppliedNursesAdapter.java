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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ItemAppliedNursesBinding;
import com.weboconnect.nurseify.screen.facility.MessageFacilityActivity;
import com.weboconnect.nurseify.screen.facility.NurseDetailsActivity;
import com.weboconnect.nurseify.screen.facility.model.AppliedNurseModel;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.nurse.ActiveJobDetailsActivity;
import com.weboconnect.nurseify.utils.Constant;

import java.util.List;

public class AppliedNursesAdapter extends RecyclerView.Adapter<AppliedNursesAdapter.ViewHolder> {

    private final List<AppliedNurseModel.AppliedNurseDatum> list;
    Activity activity;

    public AppliedNursesAdapter(Activity activity, List<AppliedNurseModel.AppliedNurseDatum> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAppliedNursesBinding view = ItemAppliedNursesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            AppliedNurseModel.AppliedNurseDatum nurseDatum = list.get(position);
            try {
                Glide.with(activity).load(nurseDatum.getProfile())
                        .placeholder(R.drawable.person).error(R.drawable.person).into(holder.mainLayout.circleImageView);
            } catch (Exception e) {

            }
            holder.mainLayout.tvName.setText(nurseDatum.getName());
            holder.mainLayout.tvRating.setText(nurseDatum.getRating());

            holder.mainLayout.circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /*   activity.startActivity(new Intent(activity, NurseDetailsActivity.class)
                            .putExtra(Constant.ID, nurseDatum.getUserId()));*/
                }
            });
            holder.mainLayout.layMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* activity.startActivity(new Intent(activity, MessageFacilityActivity.class)
                            .putExtra("sender_id", nurseDatum.getUserId())
                            .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseDatum)));*/
                }
            });
            holder.mainLayout.layoutApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    activity.startActivity(new Intent(activity, MessageFacilityActivity.class));

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
        ItemAppliedNursesBinding mainLayout;

        public ViewHolder(@NonNull ItemAppliedNursesBinding itemView) {
            super(itemView.getRoot());
            mainLayout = itemView;

        }
    }
}
