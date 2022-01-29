package com.nurseify.app.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ItemPastFBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.OfferedNurse_Datum;

import java.util.ArrayList;
import java.util.List;

public class PastAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private final ItemCallback postedListener;
    private List<OfferedNurse_Datum> listPostedJob;
    private List<OfferedNurse_Datum> copy_contactList = new ArrayList<>();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public boolean isLoaderVisible = false;
    Activity activity;
    private long mLastClickTime = 0;


    public PastAdapter(Activity activity, List<OfferedNurse_Datum> listPostedJob, ItemCallback postedListener) {
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

                return new PastAdapter.ViewHolder(ItemPastFBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

    public void addItems(List<OfferedNurse_Datum> postItems) {
        listPostedJob.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        listPostedJob.add(new OfferedNurse_Datum());
        notifyItemInserted(listPostedJob.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = listPostedJob.size() - 1;
        OfferedNurse_Datum item = getItem(position);
        if (item != null) {
            listPostedJob.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void clear() {
        listPostedJob.clear();
        notifyDataSetChanged();
    }

    OfferedNurse_Datum getItem(int position) {
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
        ItemPastFBinding itemView;

        public ViewHolder(@NonNull ItemPastFBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        @Override
        public void onBind(int position) {
            OfferedNurse_Datum model = listPostedJob.get(position);
            Glide.with(itemView.imgProfile.getContext()).load(model.getNurseImage()).placeholder(R.drawable.person)
                    .error(R.drawable.person).into(itemView.imgProfile);
            itemView.tvName.setText(model.getNurseFirstName() + " " + model.getNurseLastName());
            if (model.getRating() != null && !TextUtils.isEmpty(model.getRating()))
                itemView.tvRating.setText(model.getRating());
            String rate = model.getPreferredHourlyPayRate();
            if (TextUtils.isEmpty(rate))
                rate = "0";
            itemView.tvRate.setText("$ " + rate + "/Hr");
            itemView.tvWeeksDaysCount.setText(model.getPreferredAssignmentDurationDefinition());
            itemView.tvWeeksDays.setText(model.getPreferredDaysOfTheWeekString());
            itemView.tvTitle.setText(model.getPreferredSpecialtyDefinition());
            itemView.tvShift.setText(model.getPreferredShift_definition());
            itemView.tvStartDate.setText(model.getStart_date());
            itemView.tvEndDate.setText(model.getEnd_date());
            itemView.tvTime.setText(model.getOfferedAt());
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
            fRecords = new PastAdapter.RecordFilter1();
        }
        return fRecords;
    }

    public class RecordFilter1 extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults oReturn = new FilterResults();
            final ArrayList<OfferedNurse_Datum> results = new ArrayList<>();
            if (listPostedJob != null)
                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (OfferedNurse_Datum g : copy_contactList) {

                            try {
                                if (g.getNurseFirstName().toLowerCase()
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
            listPostedJob = (ArrayList<OfferedNurse_Datum>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
