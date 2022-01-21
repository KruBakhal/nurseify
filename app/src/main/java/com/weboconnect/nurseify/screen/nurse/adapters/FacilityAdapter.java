package com.weboconnect.nurseify.screen.nurse.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.FacilityListCallback;
import com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> implements Filterable {

    Activity activity;
    List<FacilityJobModel.Facility> list;
    List<FacilityJobModel.Facility> copy_contactList = new ArrayList<>();
    FacilityListCallback callback;
    private RecordFilter1 fRecords;

    public FacilityAdapter(Activity activity, List<FacilityJobModel.Facility> list, FacilityListCallback callback) {
        this.activity = activity;
        this.list = list;
        this.copy_contactList = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            Glide.with(activity).load(list.get(position).getFacilityLogo()).load(holder.imageView);

            holder.tv_name.setText(list.get(position).getName());
            holder.tv_description.setText(Html.fromHtml(list.get(position).getAboutFacility()));
            holder.tv_job.setText(list.get(position).getTotalJobs() + " Jobs");
            holder.tv_rating.setText(("" + list.get(position).getRating()));
            holder.tv_address.setText(list.get(position).getPostcode() + " " + list.get(position).getAddress());

            String id = list.get(position).getId();


            if (list.get(position).getIsFollow().equals("0")) {
                holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back));
                holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tv_follow.setText("Follow");
            } else {
                holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back_gray));
                holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tv_follow.setText("Followed");
            }

            if (list.get(position).getIsLike().toString().equals("0")) {
                holder.img_heart.setVisibility(View.VISIBLE);
                holder.img_heart1.setVisibility(View.GONE);
            } else {
                holder.img_heart1.setVisibility(View.VISIBLE);
                holder.img_heart.setVisibility(View.GONE);
            }


            holder.lay_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    String shareBody = list.get(position).getName()
                            + "\n" + Html.fromHtml(list.get(position).getAboutFacility())
                            + "\n" + list.get(position).getAddress() + " " + list.get(position).getPostcode();
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    activity.startActivity(Intent.createChooser(intent, "Share"));
                }
            });
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(position, list.get(position));
                }
            });
            holder.bind(position, list.get(position));
        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add_Item(List<FacilityJobModel.Facility> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mainLayout;

        CircleImageView imageView;
        TextView tv_name;
        TextView tv_address;
        TextView tv_job;
        TextView tv_rating;
        TextView tv_description;

        ImageView img_heart, img_heart1;
        ImageView img_share;
        TextView tv_follow;
        View lay_heart, lay_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            imageView = itemView.findViewById(R.id.image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_job = itemView.findViewById(R.id.tv_job);
            tv_description = itemView.findViewById(R.id.tv_description);
            img_heart = itemView.findViewById(R.id.img_heart);
            tv_follow = itemView.findViewById(R.id.tv_follow);
            img_heart1 = itemView.findViewById(R.id.img_heart1);
            lay_heart = itemView.findViewById(R.id.lay_heart);
            lay_share = itemView.findViewById(R.id.lay_share);

        }

        private void likeFacility(int pos, String facilityId, String like, FacilityJobModel.Facility facility) {

            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen

            if (!Utils.isNetworkAvailable(itemView.getContext())) {
                Utils.displayToast(itemView.getContext(), itemView.getResources().getString(R.string.no_internet));
                return;
            }
            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen
//            showProgress();
            String user_id = new SessionManager(itemView.getContext()).get_user_register_Id();
            RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
            RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
            RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), like);


            Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                    .call_like_facility(user_id1, facility_id, type_);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.isSuccessful()) {
//                            dismissProgress();
                            Log.e("follow", "Success");
                            ResponseModel responseModel = response.body();
//                        Utils.displayToast(itemView.getContext(), "" + responseModel.getMessage());
                            facility.setIsLike(like);
                            list.set(pos, facility);
                            notifyItemChanged(pos);
                        } else {
//                            errorProgress(true);
                        }
                    } catch (Exception e) {
//                        errorProgress(true);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
//                    errorProgress(true);

                }
            });


        }

        public void bind(int position, FacilityJobModel.Facility facility) {
            lay_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getIsLike().equals("1")) {
                        likeFacility(position, facility.getId(), "0", facility);
                    } else {
                        likeFacility(position, facility.getId(), "1", facility);
                    }

                }
            });
            tv_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getIsFollow().equals("0")) {
//                        holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back));
//                        holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.white));
                        followFacility(position, list.get(position).getId(), "1", list.get(position));
                    } else {
                        followFacility(position, list.get(position).getId(), "0", list.get(position));
                    }
                }
            });
        }

        private void followFacility(int pos, String facilityId, String type, FacilityJobModel.Facility facility) {

            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen

            if (!Utils.isNetworkAvailable(itemView.getContext())) {
                Utils.displayToast(itemView.getContext(), itemView.getResources().getString(R.string.no_internet));
                return;
            }
            Utils.displayToast(itemView.getContext(), null); // to cancel toast if showing on screen
            String user_id = new SessionManager(itemView.getContext()).get_user_register_Id();
            RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
            RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
            RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), type);


            Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                    .call_follow_facility(user_id1, facility_id, type_);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            ResponseModel responseModel = response.body();
                            facility.setIsFollow(type);
                            list.set(pos, facility);
                            notifyItemChanged(pos);
                        } else {
//                            errorProgress(true);
                        }
                    } catch (Exception e) {
//                        errorProgress(true);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
//                    errorProgress(true);

                }
            });


        }

    }

    @Override
    public Filter getFilter() {

        if (fRecords == null) {
            fRecords = new RecordFilter1();
        }

        return fRecords;
    }

    private class RecordFilter1 extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults oReturn = new FilterResults();
            final ArrayList<FacilityJobModel.Facility> results = new ArrayList<>();
            if (list != null)

                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (FacilityJobModel.Facility g : copy_contactList) {

                            if (g.getName().toLowerCase()
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
            list = (List<FacilityJobModel.Facility>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
