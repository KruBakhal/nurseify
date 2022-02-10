package com.nurseify.app.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.nurseify.app.AppController;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ItemAppliedNursesBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.HomeFActivity;
import com.nurseify.app.screen.facility.MessageFacilityActivity;
import com.nurseify.app.screen.facility.NurseDetailsActivity;
import com.nurseify.app.screen.facility.model.AppliedNurseModel;
import com.nurseify.app.screen.facility.model.NurseDatum;
import com.nurseify.app.screen.nurse.model.UserProfile;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedNursesAdapter extends RecyclerView.Adapter<AppliedNursesAdapter.ViewHolder> {

    private final List<AppliedNurseModel.AppliedNurseDatum> list;
    Activity activity;
    ItemCallback itemCallback;

    public AppliedNursesAdapter(Activity activity, List<AppliedNurseModel.AppliedNurseDatum> list, ItemCallback itemCallback) {
        this.activity = activity;
        this.list = list;
        this.itemCallback = itemCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAppliedNursesBinding view = ItemAppliedNursesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            AppliedNurseModel.AppliedNurseDatum appliedNurseDatum = list.get(position);
            try {

           /*     Glide.with(activity).load(appliedNurseDatum.getProfile())
                        .placeholder(R.drawable.person).error(R.drawable.person).into(holder.mainLayout.circleImageView);
           */

                if (!TextUtils.isEmpty(appliedNurseDatum.getProfile())) {
                    byte[] decodeString = Utils.get_base_images(appliedNurseDatum.getProfile_base());
                    RequestOptions myOptions = new RequestOptions()
                            .override(100, 100);
                    Glide.with(activity)
                            .load(decodeString).apply(myOptions).placeholder(R.drawable.person)
                            .error(R.drawable.person)
                            .into(holder.mainLayout.circleImageView);
                }
            } catch (Exception e) {
                Log.d("TAG", "applied nurse onBindViewHolder: " + e.getMessage());
            }
            holder.mainLayout.tvName.setText(appliedNurseDatum.getName());
            holder.mainLayout.tvRating.setText(appliedNurseDatum.getRating());

            holder.mainLayout.circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, NurseDetailsActivity.class)
                            .putExtra(Constant.ID, appliedNurseDatum.getUserId())
                            .putExtra("rating", appliedNurseDatum.getRating())
                            .putExtra(Constant.EDIT_MODE, true)
                    );
                }
            });
            holder.mainLayout.layMsg.setOnClickListener(new View.OnClickListener() {

                private ProgressDialog progressDialog;

                @Override
                public void onClick(View v) {
                    if (holder.profileData == null) {
                        getNurseProfile(appliedNurseDatum.getUserId());
                    } else {
                        NurseDatum nurse_model = new NurseDatum();
                        nurse_model.setNurseEmail(holder.profileData.getEmail());
                        nurse_model.setUserId(holder.profileData.getId());
                        nurse_model.setFirstName(holder.profileData.getFullName());
                        nurse_model.setNurseLogo(holder.profileData.getImage());
                        nurse_model.setSpecialty("");
//                        AppController.user_profile_base = holder.profileData.getImage_base();
                        activity.startActivity(new Intent(activity, MessageFacilityActivity.class)
                                .putExtra("receiver_id", appliedNurseDatum.getUserId())
                                /* .putExtra("email", holder.nurseDatum.getEmail())
                                 .putExtra("id", holder.nurseDatum.getId())
                                 .putExtra("full_name", holder.nurseDatum.getFirstName() + " " + holder.nurseDatum.getLastName())
                                 .putExtra("profile_path", holder.nurseDatum.getImage())
                                 .putExtra("specialty", " ")*/
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurse_model)))
                        ;
                    }
                }

                private void getNurseProfile(String userId) {
                    if (!Utils.isNetworkAvailable(activity)) {
                        Utils.displayToast(activity, activity.getResources().getString(R.string.no_internet));
                        return;
                    }
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

                    Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                            .call_nurse_profile(user_id1);

                    call.enqueue(new Callback<UserProfile>() {
                        @Override
                        public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                            if (response.body() == null) {
                                try {
                                    progressDialog.dismiss();
                                    Log.d("TAG", "onResponse: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (response.body().getApiStatus().equals("1")) {
                                progressDialog.dismiss();
                                UserProfileData profileData = response.body().getData();
                                NurseDatum nurse_model = new NurseDatum();
                                nurse_model.setNurseEmail(profileData.getEmail());
                                nurse_model.setUserId(profileData.getId());
                                nurse_model.setFirstName(profileData.getFullName());
                                nurse_model.setNurseLogo(profileData.getImage());
                                nurse_model.setSpecialty("");
//                                AppController.user_profile_base = profileData.getImage_base();
                                activity.startActivity(new Intent(activity, MessageFacilityActivity.class)
                                        .putExtra("receiver_id", appliedNurseDatum.getUserId())
                                        /* .putExtra("email", holder.nurseDatum.getEmail())
                                         .putExtra("id", holder.nurseDatum.getId())
                                         .putExtra("full_name", holder.nurseDatum.getFirstName() + " " + holder.nurseDatum.getLastName())
                                         .putExtra("profile_path", holder.nurseDatum.getImage())
                                         .putExtra("specialty", " ")*/
                                        .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurse_model)));
                            } else {
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<UserProfile> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.e("TAG", "AppliedNursesAdapter " + t.toString());
                        }
                    });

                }
            });
            holder.mainLayout.layoutApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCallback.onClick(position);
                }
            });
        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemAppliedNursesBinding mainLayout;
        public UserProfileData profileData;

        public ViewHolder(@NonNull ItemAppliedNursesBinding itemView) {
            super(itemView.getRoot());
            mainLayout = itemView;

        }
    }
}
