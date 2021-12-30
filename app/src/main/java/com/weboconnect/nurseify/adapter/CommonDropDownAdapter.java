package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.screen.facility.Add_Jobs_Activity;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;

import java.util.List;


public class CommonDropDownAdapter extends RecyclerView.Adapter<CommonDropDownAdapter.MyViewHolder> {

    Context context;
    private int type;
    int selected_city_int;
    private List<CommonDatum> datumList;
    CommonDropDownInterface parentInterface;

    public CommonDropDownAdapter(Activity context, int type, int selected_city_int, List<CommonDatum> workLocationData, CommonDropDownInterface parentInterface) {
        this.context = context;
        this.type = type;
        this.selected_city_int = selected_city_int;
        this.datumList = workLocationData;
        this.parentInterface = parentInterface;
    }

    public interface CommonDropDownInterface {
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
        if ((context instanceof RegistrationFActivity)) {
            RegistrationFActivity activity = (RegistrationFActivity) context;
            CommonDatum movie = datumList.get(position);
            holder.title.setText(movie.getName());
            if (type == 1) {
                select = activity.viewModel.selected_emr;
            } else if (type == 2) {
                select = activity.viewModel.selected_bg_check;
            } else if (type == 3) {
                select = activity.viewModel.selected_soft;
            } else if (type == 4) {
                select = activity.viewModel.selected_scheduling;
            } else if (type == 5) {
                select = activity.viewModel.selected_attendance;
            } else if (type == 6) {
                select = activity.viewModel.selected_trauma;
            }
        } else {
            Add_Jobs_Activity activity = (Add_Jobs_Activity) context;
            CommonDatum movie = datumList.get(position);
            holder.title.setText(movie.getName());
            if (type == 1) {
                select = activity.viewModel.selected_assignment_duration;
            } else if (type == 2) {
                select = activity.viewModel.selected_senior_level;
            } else if (type == 3) {
                select = activity.viewModel.selected_job_funcs;
            } else if (type == 4) {
                select = activity.viewModel.selected_speciality;
            } else if (type == 5) {
                select = activity.viewModel.selected_shift_duration;
            } else if (type == 6) {
                select = activity.viewModel.selected_work_loc;
            }
        }

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
            holder.title.setTextColor(context.getResources().getColor(R.color.gray));
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