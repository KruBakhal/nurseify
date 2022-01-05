package com.weboconnect.nurseify.adapter;

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
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ItemActiveFBinding;
import com.weboconnect.nurseify.databinding.ItemCompletedBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.screen.facility.model.Facility_JobDatum;
import com.weboconnect.nurseify.screen.nurse.model.CompletedJobModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Completed_F_Adapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {


    private final ItemCallback postedListener;
    private List<Facility_JobDatum> listPostedJob;
    private List<Facility_JobDatum> copy_contactList = new ArrayList<>();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    Activity activity;
    private long mLastClickTime = 0;


    public Completed_F_Adapter(Activity activity, List<Facility_JobDatum> listPostedJob, ItemCallback postedListener) {
        this.activity = activity;
        this.listPostedJob = listPostedJob;
        this.postedListener = postedListener;
        this.copy_contactList = listPostedJob;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:

                return new ViewHolder(ItemCompletedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == listPostedJob.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int pos) {
        holder.onBind(pos);
    }

    public void remove_Job(int position) {
        listPostedJob.remove(position);
        notifyItemRemoved(position);
    }

    public void addItems(List<Facility_JobDatum> postItems) {
        listPostedJob.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        listPostedJob.add(new Facility_JobDatum());
        notifyItemInserted(listPostedJob.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = listPostedJob.size() - 1;
        Facility_JobDatum item = getItem(position);
        if (item != null) {
            listPostedJob.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void clear() {
        listPostedJob.clear();
        notifyDataSetChanged();
    }

    Facility_JobDatum getItem(int position) {
        if (listPostedJob == null || listPostedJob.size() == 0)
            return null;
        return listPostedJob.get(position);
    }

    @Override
    public int getItemCount() {
        if (listPostedJob == null || listPostedJob.size() == 0)
            return 0;

        return listPostedJob.size();
    }

    class ViewHolder extends BaseViewHolder {
        ItemCompletedBinding itemView;

        public ViewHolder(@NonNull ItemCompletedBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        @Override
        public void onBind(int position) {
            Facility_JobDatum datum = listPostedJob.get(position);

            try {
                Glide.with(activity).load(datum.getFacilityImage()).placeholder(R.drawable.test1)
                        .error(R.drawable.test1).into(itemView.imageView);
            } catch (Exception e) {

            }
            itemView.tvName.setText("" + datum.getFacilityFirstName() + " " + datum.getFacilityLastName());
            itemView.tvTitle.setText("" + datum.getPreferredSpecialtyDefinition());
            itemView.tvDuration.setText("" + datum.getPreferredAssignmentDurationDefinition());
//            tv_shift.setText("" + datum.getShiftDefinition());
            itemView.tvWorkingDay.setText("" + datum.getPreferredDaysOfTheWeekString());
            itemView.tvAmount.setText("$ " + datum.getPreferredHourlyPayRate() + "/Hr");
            itemView.tvStartDate.setText("" + datum.getStartDate());
            itemView.tvEndDate.setText("" + datum.getEndDate());
        }

        @Override
        public int getCurrentPosition() {
            return super.getCurrentPosition();
        }

        @Override
        protected void clear() {

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
            final ArrayList<Facility_JobDatum> results = new ArrayList<>();
            if (listPostedJob != null)
                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (Facility_JobDatum g : copy_contactList) {

                            try {
                                if (g.getPreferredSpecialtyDefinition().toLowerCase()
                                        .startsWith(constraint.toString().toLowerCase())) {
                                    results.add(g);
                                }
                            } catch (Exception exception) {
                                Log.d("TAG", "performFiltering: " + exception.getMessage());
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
            listPostedJob = (ArrayList<Facility_JobDatum>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
