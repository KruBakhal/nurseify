package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.screen.nurse.model.ActiveModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> implements Filterable {

    private List<ActiveModel.ActiveDatum> list;
    private List<ActiveModel.ActiveDatum> copy_contactList;
    private final OfferedJobCallback callback;
    Activity activity;

    public ActiveAdapter(Activity activity, List<ActiveModel.ActiveDatum> list, OfferedJobCallback callback) {
        this.activity = activity;
        this.list = list;
        this.copy_contactList = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ActiveModel.ActiveDatum datum = list.get(position);
            try {
                Glide.with(holder.itemView.getContext()).load(datum.getFacilityLogo())
                        .placeholder(R.drawable.person)
                        .error(R.drawable.person).into(holder.imageView);
            } catch (Exception e) {

            }
            holder.tv_name.setText("" + datum.getFacilityName());
            holder.tv_title.setText("" + datum.getTitle());
            holder.tv_duration.setText("" + datum.getWorkDurationDefinition());
            holder.tv_shift.setText("" + datum.getShiftDefinition());
            holder.tv_workingDay.setText("" + datum.getWorkDaysString());
            holder.tv_amount.setText("$ " + datum.getHourlyRate() + "/Hr");
            holder.tv_start_date.setText("" + datum.getStartDate());

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(position);
                }
            });

        } catch (Exception e) {
            Log.e("ActiveAdapter ", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
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

    private Filter fRecords;

    @Override
    public Filter getFilter() {

        if (fRecords == null) {
            fRecords = new RecordFilter1();
        }

        return fRecords;
    }

    public class RecordFilter1 extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults oReturn = new FilterResults();
            final ArrayList<ActiveModel.ActiveDatum> results = new ArrayList<>();
            if (list != null)

                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (ActiveModel.ActiveDatum g : copy_contactList) {

                            if (g.getTitle().toLowerCase()
                                    .startsWith(constraint.toString().toLowerCase()))
                                results.add(g);

                        }


                    }
                    oReturn.values = results;
                } else {
                    oReturn.count = copy_contactList.size();
                    oReturn.values = copy_contactList;
                }
            return oReturn;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list = (List<ActiveModel.ActiveDatum>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
