package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.SignupDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.model.CernersDatum;
import com.weboconnect.nurseify.screen.nurse.model.CredentialDatum;
import com.weboconnect.nurseify.screen.nurse.model.Degree_Datum;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.State_Datum;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationDatum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WorkHistoryWindowAdapter extends RecyclerView.Adapter<WorkHistoryWindowAdapter.WorkHistoryWindowMyViewHolder> {

    private int type;
    public List<Degree_Datum> list_nurse_degrees = new ArrayList<>();
    public List<CernersDatum> list_experience = new ArrayList<>();
    public List<CredentialDatum> list_Credential = new ArrayList<>();
    Activity context;

    public WorkHistoryWindowAdapter(Activity context, int type, List<Object> list_nurse_degrees,
                                    WorkHistoryWindowInterface parentInterface) {
        this.type = type;
        if (list_nurse_degrees instanceof List) {
            if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof Degree_Datum)) {
                this.list_nurse_degrees = ((List<Degree_Datum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CernersDatum)) {
                this.list_experience = ((List<CernersDatum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CredentialDatum)) {
                this.list_Credential = ((List<CredentialDatum>) list_nurse_degrees.get(0));
            }
        }
        this.context = context;
        this.parentInterface = parentInterface;
    }

    public WorkHistoryWindowAdapter(Activity context, int type, int type1, List<CernersDatum> list_nurse_degrees,
                                    WorkHistoryWindowInterface parentInterface) {
        this.type = type;
        this.list_experience = list_nurse_degrees;
        this.context = context;
        this.parentInterface = parentInterface;
    }

    WorkHistoryWindowInterface parentInterface;


    public interface WorkHistoryWindowInterface {
        void onCLickItem(int i, int position);
    }

    public class WorkHistoryWindowMyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lay_item;

        public WorkHistoryWindowMyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvText);
            lay_item = view.findViewById(R.id.lay_item);
        }
    }


    @Override
    public WorkHistoryWindowMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_dropdown, parent, false);

        return new WorkHistoryWindowMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkHistoryWindowMyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RegisterActivity activity = (RegisterActivity) context;
        boolean isNotSelected = true;
        int selectedPos = 0;
        if (type == 1) {
            Degree_Datum movie = list_nurse_degrees.get(position);
            holder.title.setText(movie.getName());
            selectedPos = activity.selected_nurse_degree;
        } else if (type == 2) {
            CernersDatum movie = list_experience.get(position);
            holder.title.setText(movie.getName());
            selectedPos = activity.selected_nurse_cerner;
        } else if (type == 3) {
            CernersDatum movie = list_experience.get(position);
            holder.title.setText(movie.getName());
            selectedPos = activity.selected_nurse_meditech;
        } else if (type == 4) {
            CernersDatum movie = list_experience.get(position);
            holder.title.setText(movie.getName());
            selectedPos = activity.selected_nurse_epic;
        } else if (type == 5) {
            CredentialDatum movie = list_Credential.get(position);
            holder.title.setText(movie.getName());
            selectedPos = activity.selected_Credential;
        }

        if (selectedPos == position) {
            isNotSelected = false;
        } else {
            isNotSelected = true;
        }

        if (!isNotSelected) {
            holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.lay_item.setBackground(null);
            holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
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
        if (type == 1) {
            if (list_nurse_degrees == null || list_nurse_degrees.size() == 0)
                return 0;
            return list_nurse_degrees.size();
        } else if (type == 5) {
            if (list_Credential == null || list_Credential.size() == 0)
                return 0;
            return list_Credential.size();
        } else {
            if (list_experience == null || list_experience.size() == 0)
                return 0;
            return list_experience.size();
        }
    }
}