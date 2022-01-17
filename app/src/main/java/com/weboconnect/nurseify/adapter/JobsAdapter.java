package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.facility.NurseDetailsActivity;
import com.weboconnect.nurseify.screen.facility.model.Facility_JobDatum;
import com.weboconnect.nurseify.screen.facility.model.OfferedJobNurse_Datum;

import java.util.List;


public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {

    private NursesAdapter.ViewHolder viewHolder;
    Activity activity;
    private int type;
    List<OfferedJobNurse_Datum> datumList;
    private int selected_city_int;
    JobsAdapterInterface parentInterface;

    public JobsAdapter(Activity context, int type, int selected_city_int,
                       List<OfferedJobNurse_Datum> workLocationData, JobsAdapterInterface parentInterface) {
        this.activity = context;
        this.type = type;
        this.selected_city_int = selected_city_int;
        this.datumList = workLocationData;
        this.parentInterface = parentInterface;
    }

    public JobsAdapter(NursesAdapter.ViewHolder viewHolder, int type, int selected_city,
                       List<OfferedJobNurse_Datum> cityData, JobsAdapterInterface parentInterface) {
        this.activity = null;
        this.viewHolder = viewHolder;
        this.type = type;
        this.selected_city_int = selected_city;
        this.datumList = cityData;
        this.parentInterface = parentInterface;
    }


    public interface JobsAdapterInterface {
        void onCLickItem(int i, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lay_item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvText);
            lay_item = view.findViewById(R.id.lay_item);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_dropdown, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int select = 0;
        boolean isNotSelected = true;
        if (activity != null && activity instanceof NurseDetailsActivity) {
            NurseDetailsActivity activity = (NurseDetailsActivity) this.activity;
            select = activity.selected_job;
        } else {
            select = viewHolder.selected_job;
        }
        OfferedJobNurse_Datum movie = datumList.get(position);
        if (TextUtils.isEmpty(movie.getContent().getSpecialty()))
            holder.title.setText("" + movie.getContent().getName());
        else holder.title.setText("" + movie.getContent().getName()
                + " - " + movie.getContent().getSpecialty());
        holder.title.setGravity(Gravity.LEFT | Gravity.CENTER);

        if (select == position) {
            isNotSelected = false;
        } else {
            isNotSelected = true;
        }
        if (!isNotSelected) {
            holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.lay_item.setBackground(null);
            holder.title.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.gray));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentInterface.onCLickItem(position, type);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (datumList == null || datumList.size() == 0)
            return 0;
        return datumList.size();

    }
}