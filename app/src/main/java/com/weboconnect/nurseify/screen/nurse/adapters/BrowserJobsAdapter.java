package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.utils.Utils;

import java.util.List;

public class BrowserJobsAdapter extends RecyclerView.Adapter<BrowserJobsAdapter.ViewHolder> {

    private final List<JobModel.JobDatum> list_jobs;
    Activity activity;

    public BrowserJobsAdapter(Activity activity, List<JobModel.JobDatum> list_jobs, BrowseJobInteface browseJobInteface) {
        this.activity = activity;
        this.list_jobs = list_jobs;
        this.browseJobInteface = browseJobInteface;
    }

    public interface BrowseJobInteface {
        void onClick_Like(JobModel.JobDatum datum, int position);

        void onClick_Apply();

        void onClick_Job(JobModel.JobDatum datum);
    }

    BrowseJobInteface browseJobInteface;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posted_f, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JobModel.JobDatum datum = list_jobs.get(position);
            try {
                Glide.with(holder.itemView.getContext()).load(datum.getFacilityLogo()).into(holder.img);
            } catch (Exception e) {

            }
            holder.tv_name.setText("" + datum.getName());
            holder.tv_specialty.setText("" + datum.getPreferredSpecialtyDefinition());
            holder.tv_created_at_definition.setText("" + datum.getCreatedAtDefinition());
            holder.tv_assignment_duration_definition.setText("" + datum.getPreferredAssignmentDurationDefinition());
            holder.tv_shift_duration.setText("" + datum.getPreferredShiftDurationDefinition());
            holder.tv_applied.setText("" + datum.getTotalApplied());
            String days = null;
            for (int i = 0; i < datum.getPreferredDaysOfTheWeek().size(); i++) {
                String str = datum.getPreferredDaysOfTheWeek().get(i);
                if (TextUtils.isEmpty(days)) {
                    if (str.equals("Thursday")) {
                        days = "Th";
                    } else if (str.equals("Tuesday")) {
                        days = "T";
                    } else {
                        days = (str.substring(0, 1).toUpperCase());
                    }
                } else {
                    if (str.equals("Thursday")) {
                        days = days + ",Th";
                    } else if (str.equals("Tuesday")) {
                        days = days + ",T";
                    } else {
                        days = days + "," + (str.substring(0, 1).toUpperCase());
                    }
                }
            }
            holder.tv_weeks_days.setText("" + days);
            holder.tv_hourly_rate.setText("$ " + datum.getPreferredHourlyPayRate() + "/Hr");

            if (datum.getIsLiked().equals("0")) {
                holder.img_heart.setImageResource(R.drawable.heart);
            } else {
                holder.img_heart.setColorFilter(ContextCompat.getColor(holder.itemView.getContext()
                        , R.color.black), PorterDuff.Mode.SRC_IN);
            }

            if (datum.getIsApplied().equals("0")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.lay_apply.setBackgroundTintList(ContextCompat.getColorStateList(
                            activity, R.color.grad1));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.lay_apply.setBackgroundTintList(ContextCompat.getColorStateList(
                            activity, R.color.secondary_till));
                }
            }

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    browseJobInteface.onClick_Job(datum);
                }
            });
            holder.lay_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datum.getIsApplied().equals("0")) {
                    } else {
                        Utils.displayToast(v.getContext(), "You have already applied for this job !");
                    }
                }
            });
            holder.img_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datum.getIsLiked().equals("0")) {
                        browseJobInteface.onClick_Like(datum,position);
                    } else {
                        Utils.displayToast(v.getContext(), "You have already applied for this job !");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list_jobs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        ImageView img, img_applied, img_heart;
        TextView tv_name, tv_specialty, tv_created_at_definition, tv_assignment_duration_definition, tv_applied, tv_shift_duration, tv_hourly_rate, tv_weeks_days;
        LinearLayout lay_apply;
        TextView tv_applied1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            img = itemView.findViewById(R.id.img);
            img_applied = itemView.findViewById(R.id.img_applied);
            img_heart = itemView.findViewById(R.id.img_heart);
            tv_specialty = itemView.findViewById(R.id.tv_specialty);
            tv_created_at_definition = itemView.findViewById(R.id.tv_created_at_definition);
            tv_assignment_duration_definition = itemView.findViewById(R.id.tv_assignment_duration_definition);
            tv_applied = itemView.findViewById(R.id.tv_applied);
            tv_shift_duration = itemView.findViewById(R.id.tv_shift_duration);
            tv_weeks_days = itemView.findViewById(R.id.tv_weeks_days);
            tv_hourly_rate = itemView.findViewById(R.id.tv_hourly_rate);
            tv_name = itemView.findViewById(R.id.tv_name);
            lay_apply = itemView.findViewById(R.id.lay_apply);
            tv_applied1 = itemView.findViewById(R.id.tv_applied1);

        }
    }
}
