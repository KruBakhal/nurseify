package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.LanguageDatum;
import com.weboconnect.nurseify.screen.nurse.model.LeaderRolesData;

import java.util.List;


public class RoleInteresetWindowAdapter extends RecyclerView.Adapter<RoleInteresetWindowAdapter.RoleInteresetMyViewHolder> {

    private List<LeaderRolesData> leaderRolesDataList;
    private int type;
    private List<LanguageDatum> list_lang;
    RegisterActivity activity;

    RoleInterface parentInterface;

    public RoleInteresetWindowAdapter(RegisterActivity context, List<LanguageDatum>
            list_nurse_degrees, int type, RoleInterface questionInterface) {
        this.activity = (RegisterActivity) context;
        this.parentInterface = questionInterface;
        this.list_lang = list_nurse_degrees;
        this.type = type;
    }

    public RoleInteresetWindowAdapter(RegisterActivity context, List<LeaderRolesData> leaderRolesDataList,
                                      int type, int i, RoleInterface roleInterface) {
        this.activity = (RegisterActivity) context;
        this.parentInterface = roleInterface;
        this.leaderRolesDataList = leaderRolesDataList;
        this.type = type;
    }


    public interface RoleInterface {
        void onCLickItem(int i, int position);
    }

    public class RoleInteresetMyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lay_item;

        public RoleInteresetMyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvText);
            lay_item = view.findViewById(R.id.lay_item);
        }
    }


    @Override
    public RoleInteresetMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_dropdown, parent, false);

        return new RoleInteresetMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoleInteresetMyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        boolean status = false;
        if (type == 1) {
            LanguageDatum movie = list_lang.get(position);
            holder.title.setText(movie.getLanguage());
            if (activity.select_Language != null && activity.select_Language.size() != 0) {
                if (activity.select_Language.contains(position)) {
                    status = true;
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } else {
            LeaderRolesData movie = leaderRolesDataList.get(position);
            holder.title.setText(movie.getName());
            if (activity.selected_leadership == position) {
                status = true;
            } else {
                status = false;
            }
        }

        if (status) {
            holder.lay_item.setBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(R.color.grad1));
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
            if (list_lang == null || list_lang.size() == 0)
                return 0;
            return list_lang.size();
        } else {
            if (leaderRolesDataList == null || leaderRolesDataList.size() == 0)
                return 0;
            return leaderRolesDataList.size();
        }
    }
}