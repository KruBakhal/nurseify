package com.weboconnect.nurseify.screen.nurse.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowserJobsAdapter extends RecyclerView.Adapter<BrowserJobsAdapter.ViewHolder>
        implements Filterable {

    private List<JobModel.JobDatum> list_jobs;
    private List<JobModel.JobDatum> copy_contactList = new ArrayList<>();
    Activity activity;
    private long mLastClickTime = 0;

    public BrowserJobsAdapter(Activity activity, List<JobModel.JobDatum> list_jobs, BrowseJobInteface browseJobInteface) {
        this.activity = activity;
        this.list_jobs = list_jobs;
        this.copy_contactList = list_jobs;
        this.browseJobInteface = browseJobInteface;

    }

    public void setList(JobModel.JobDatum datum, int position) {
        list_jobs.set(position, datum);
        notifyItemChanged(position);
    }

    public void add_Item(List<JobModel.JobDatum> data) {
        list_jobs.addAll(data);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (list_jobs != null && list_jobs.size() != 0)
            list_jobs.clear();
        notifyDataSetChanged();
    }

    public interface BrowseJobInteface {
        void onClick_Like(JobModel.JobDatum datum, int position);

        void onClick_Apply(int position, JobModel.JobDatum datum);

        void onClick_Job(JobModel.JobDatum datum, int position);
    }

    public BrowseJobInteface browseJobInteface;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_posted_f, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            JobModel.JobDatum datum = list_jobs.get(position);
            holder.bind(datum, position);
        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }


    @Override
    public int getItemCount() {
        return list_jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mainLayout;
        public ImageView img, img_applied, img_heart, img_heart1;
        public TextView tv_name, tv_specialty, tv_created_at_definition, tv_assignment_duration_definition,
                tv_applied, tv_shift_duration, tv_hourly_rate, tv_weeks_days;
        public LinearLayout lay_apply;
        public TextView tv_applied1;
        public View lay_share, lay_heart, edit;

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
            lay_share = itemView.findViewById(R.id.lay_share);
            img_heart1 = itemView.findViewById(R.id.img_heart1);
            lay_heart = itemView.findViewById(R.id.lay_heart);
            edit = itemView.findViewById(R.id.edit);


        }

        public void bind(JobModel.JobDatum datum, int position) {
            try {
                Glide.with(itemView.getContext()).load(datum.getFacilityLogo())
                        .placeholder(R.drawable.person).error(R.drawable.person).into(img);
            } catch (Exception e) {

            }
            tv_name.setText("" + datum.getName());
            tv_specialty.setText("" + datum.getPreferredSpecialtyDefinition());
            tv_created_at_definition.setText("" + datum.getCreatedAtDefinition());
            tv_assignment_duration_definition.setText("" + datum.getPreferredAssignmentDurationDefinition());
            tv_shift_duration.setText("" + datum.getPreferredShiftDurationDefinition());
            tv_applied.setText("" + datum.getTotalApplied() + "+ Applied");
            String days = "";
            for (int i = 0; i < datum.getPreferredDaysOfTheWeek().size(); i++) {
                String str = datum.getPreferredDaysOfTheWeek().get(i);
                if (TextUtils.isEmpty(days)) {
                    if (str.startsWith("T")) {
                        if (str.equals("Thursday")) {
                            days = "Th";
                        } else if (str.equals("Tuesday")) {
                            days = "T";
                        } else {
                            days = (str.substring(0, 1).toUpperCase());
                        }
                    } else if (str.startsWith("S")) {
                        if (str.equals("Sunday")) {
                            days = "Su";
                        } else if (str.equals("Saturday")) {
                            days = "Sa";
                        } else {
                            days = (str.substring(0, 1).toUpperCase());
                        }
                    } else {
                        days = (str.substring(0, 1).toUpperCase());
                    }
                } else {
                    if (str.startsWith("T")) {
                        if (str.equals("Thursday")) {
                            days = days + ", Th";
                        } else if (str.equals("Tuesday")) {
                            days = days + ", T";
                        } else {
                            days = days + ", " + (str.substring(0, 1).toUpperCase());
                        }
                    } else if (str.startsWith("S")) {
                        if (str.equals("Sunday")) {
                            days = days + ", Su";
                        } else if (str.equals("Saturday")) {
                            days = days + ", Sa";
                        } else {
                            days = days + ", " + (str.substring(0, 1).toUpperCase());
                        }
                    } else {
                        days = days + "," + (str.substring(0, 1).toUpperCase());
                    }
                    /*if (str.equals("Thursday")) {
                        days = days + ",Th";
                    } else if (str.equals("Tuesday")) {
                        days = days + ",T";
                    } else {
                        days = days + "," + (str.substring(0, 1).toUpperCase());
                    }*/
                }
            }
            tv_weeks_days.setText("" + days);
            tv_hourly_rate.setText("$ " + datum.getPreferredHourlyPayRate() + "/Hr");

            if (datum.getIsLiked().equals("0")) {
                img_heart.setVisibility(View.VISIBLE);
                img_heart1.setVisibility(View.GONE);
            } else {
                img_heart1.setVisibility(View.VISIBLE);
                img_heart.setVisibility(View.GONE);
            }
            edit.setVisibility(View.GONE);
            if (datum.getIsApplied().equals("0")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    lay_apply.setBackgroundTintList(ContextCompat.getColorStateList(
                            activity, R.color.grad1));
                }
                tv_applied1.setText("Apply");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    lay_apply.setBackgroundTintList(ContextCompat.getColorStateList(
                            activity, R.color.secondary_till));
                }
                tv_applied1.setText("Applied");
            }

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    browseJobInteface.onClick_Job(datum, position);
                }
            });
            lay_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datum.getIsApplied().equals("0")) {
                        browseJobInteface.onClick_Apply(position, datum);
                    } else {
                        browseJobInteface.onClick_Apply(position, datum);
                    }
                }
            });
         /*   lay_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datum.getIsLiked().equals("0")) {
                        browseJobInteface.onClick_Like(datum, position);
                    } else {
                        browseJobInteface.onClick_Like(datum, position);
                    }
                }
            });*/

            String finalDays = days;
            lay_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 300) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    String shareBody = "Facility Name: " + datum.getName()
                            + "\nTitle: " + datum.getPreferredSpecialtyDefinition()
                            + "\nShift: " + datum.getPreferredShiftDurationDefinition()
                            + "\nAssignment: " + datum.getPreferredAssignmentDurationDefinition()
                            + "\nWork Days: " + finalDays
                            + "\nHourly Rate: " + datum.getPreferredHourlyPayRate()+" /Hr"
                            + "\nhttps://play.google.com/store/apps/details?id=" + activity.getPackageName();
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    activity.startActivity(Intent.createChooser(intent, "Share"));
                }
            });
            lay_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    browseJobInteface.onClick_Like(datum, position);
                    performLike(datum.getJobId(), datum.getIsLiked(), position, datum);
                }
            });

        }

        void performLike(String jobId, String isLiked, int position, JobModel.JobDatum datum) {

            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen

            if (!Utils.isNetworkAvailable(itemView.getContext())) {
                Utils.displayToast(itemView.getContext(), itemView.getResources().getString(R.string.no_internet));
                return;
            }
            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen


            if (isLiked.equals("0")) {
                isLiked = "1";
            } else {
                isLiked = "0";
            }
            String user_id = new SessionManager(itemView.getContext()).get_user_register_Id();
            RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
            RequestBody jobId1 = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);
            RequestBody isLiked1 = RequestBody.create(MediaType.parse("multipart/form-data"), isLiked);


            Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                    .call_like_job(user_id1, jobId1, isLiked1);

            String finalIsLiked = isLiked;
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {

                            return;
                        }
                        if (response.isSuccessful()) {
                            ResponseModel jobModel = response.body();
                            datum.setIsLiked(finalIsLiked);
                            list_jobs.set(position, datum);
                            browseJobInteface.onClick_Like(datum, position);
                            notifyItemChanged(position);
                        } else {

                        }
                    } catch (Exception e) {


                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {


                }
            });


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
            final ArrayList<JobModel.JobDatum> results = new ArrayList<>();
            if (list_jobs != null)

                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (JobModel.JobDatum g : copy_contactList) {

                            if (g.getPreferredSpecialtyDefinition().toLowerCase()
                                    .startsWith(constraint.toString().toLowerCase())) {
                                results.add(g);
                            } else if (g.getName().toLowerCase()
                                    .startsWith(constraint.toString().toLowerCase())) {
                                results.add(g);
                            }

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
            list_jobs = (ArrayList<JobModel.JobDatum>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
