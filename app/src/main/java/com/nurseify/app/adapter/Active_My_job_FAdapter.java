package com.nurseify.app.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ItemActiveFBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.Facility_JobDatum;
import com.nurseify.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Active_My_job_FAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private final ItemCallback postedListener;
    private List<Facility_JobDatum> listPostedJob;
    private List<Facility_JobDatum> copy_contactList = new ArrayList<>();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public boolean isLoaderVisible = false;
    Activity activity;
    private long mLastClickTime = 0;


    public Active_My_job_FAdapter(Activity activity, List<Facility_JobDatum> listPostedJob, ItemCallback postedListener) {
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
                return new ViewHolder(ItemActiveFBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemActiveFBinding itemView;

        public ViewHolder(@NonNull ItemActiveFBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        @Override
        public void onBind(int position) {
            Facility_JobDatum model = listPostedJob.get(position);

            try {
                /*Glide.with(itemView.imgProfile.getContext()).load(model.getFacilityImage())
                        .placeholder(R.drawable.person)
                        .error(R.drawable.person).into(itemView.imgProfile);*/
                if (!TextUtils.isEmpty(model.getFacilityImage())) {
                    byte[] decodeString = Utils.get_base_images(model.getFacilityImage_base());
                    RequestOptions myOptions = new RequestOptions()
                            .override(100, 100);
                    Glide.with(activity)
                            .load(decodeString).apply(myOptions).placeholder(R.drawable.person)
                            .error(R.drawable.person)
                            .into(itemView.imgProfile);
                }
            } catch (Exception e) {
                Log.d("TAG", "applied nurse onBindViewHolder: " + e.getMessage());
            }
            itemView.tvName.setText(model.getFacilityFirstName() + " " + model.getFacilityLastName());
            itemView.layRating.setVisibility(View.GONE);
            itemView.tvTime.setVisibility(View.GONE);
            String rate = model.getPreferredHourlyPayRate();
            if (TextUtils.isEmpty(rate))
                rate = "0";
            itemView.tvRate.setText("$ " + rate + "/Hr");
            itemView.tvWeeksDaysCount.setText(model.getPreferredAssignmentDurationDefinition());
            itemView.tvShift.setText(model.getPreferredShiftDefinition());

            itemView.tvTitle.setText(model.getPreferredSpecialtyDefinition());
            itemView.tvStartDate.setText(model.getStartDate());
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
