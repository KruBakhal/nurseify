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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ItemActiveFBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.OfferedNurse_Datum;
import com.nurseify.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActiveFAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private final ItemCallback postedListener;
    private List<OfferedNurse_Datum> listPostedJob;
    private List<OfferedNurse_Datum> copy_contactList = new ArrayList<>();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public boolean isLoaderVisible = false;
    Activity activity;
    private long mLastClickTime = 0;


    public ActiveFAdapter(Activity activity, List<OfferedNurse_Datum> listPostedJob, ItemCallback postedListener) {
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

                return new ActiveFAdapter.ViewHolder(ItemActiveFBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemActiveFBinding itemView;

        public ViewHolder(@NonNull ItemActiveFBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        @Override
        public void onBind(int position) {
            System.gc();
            OfferedNurse_Datum model = listPostedJob.get(position);
            try {
/*
                Glide.with(itemView.imgProfile.getContext()).load(model.getNurseImage()).placeholder(R.drawable.person)
                        .error(R.drawable.person).into(itemView.imgProfile);
*/
                if (!TextUtils.isEmpty(model.getNurseImage())) {
                    byte[] decodeString = Utils.get_base_images(model.getNurseImage_base());
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
            itemView.tvName.setText(model.getNurseFirstName() + " " + model.getNurseLastName());
            if (model.getRating() != null && !TextUtils.isEmpty(model.getRating()))
                itemView.tvRating.setText(model.getRating());
            String rate = model.getPreferredHourlyPayRate();
            if (TextUtils.isEmpty(rate))
                rate = "0";
            itemView.tvRate.setText("$ " + rate + "/Hr");
            itemView.tvWeeksDaysCount.setText(model.getPreferredAssignmentDurationDefinition());
            itemView.tvTitle.setText(model.getPreferredSpecialtyDefinition());
            itemView.tvTime.setText(model.getOfferedAt());
            itemView.tvWorkDays.setText(model.getPreferredDaysOfTheWeekString());
            itemView.tvShift.setText(model.getPreferredShift_definition());
            itemView.tvStartDate.setText(model.getStart_date());
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
            fRecords = new ActiveFAdapter.RecordFilter1();
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
