package com.weboconnect.nurseify.screen.facility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.NotificationAdapter;
import com.weboconnect.nurseify.databinding.FragmentNotificationBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.model.JobPhoto;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
import com.weboconnect.nurseify.screen.nurse.model.NotificationModel;
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

public class NotificationActivity extends AppCompatActivity {
    private FragmentNotificationBinding binding;
    String id;
    View view;

    String user_id;
    String TAG = "NotificationFragment ";

    NotificationAdapter notificationAdapter;
    List<NotificationModel.Notification> notificationList = new ArrayList<>();
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(NotificationActivity.this, R.layout.fragment_notification);
        context = this;
        notificationAdapter = new NotificationAdapter(this, notificationList
                , new ItemCallback() {
            @Override
            public void onClick(int position) {

                performDelete(notificationList.get(position).getNotificationId(),
                        new SessionManager(context).get_facilityProfile().getUserId(), position);

            }

            private void performDelete(String assetId, String user_id, int position) {
                if (!Utils.isNetworkAvailable(context)) {
                    Utils.displayToast(context, context.getResources().getString(R.string.no_internet));
                    return;
                }
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
                RequestBody assetId1 = RequestBody.create(MediaType.parse("multipart/form-data"), assetId);

                Call<ResponseModel> call = RetrofitClient.getInstance().getFacilityApi()
                        .call_remove_notification(user_id1, assetId1);

                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            Utils.displayToast(context, "" + response.body().getMessage());
                            progressDialog.cancel();
                            return;
                        }
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            Utils.displayToast(context, "Item data deleted !");
                            notificationList.remove(position);
                            notificationAdapter.removeItem(position);
                            notificationAdapter.notifyDataSetChanged();
                        } else {
                            progressDialog.dismiss();
                            Utils.displayToast(context, "Failed to delete item data");
                        }
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.cancel();
                        Log.e("TAG" + "getNurseProfile", t.toString());
                    }
                });

            }
        });
        binding.recyclerViewJobs.setAdapter(notificationAdapter);
        binding.btnBack.setVisibility(View.VISIBLE);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.onClickEvent(v);
            }
        });
        getNotification();

    }

    private void getNotification() {

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(this).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<NotificationModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_notification(user_id1);

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                Log.d(TAG + "getNotification ResCode", response.code() + "");
                if (response.isSuccessful()) {
                    try {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.tvMsg.setVisibility(View.GONE);
                        NotificationModel notificationModel = response.body();
                        notificationList.addAll(notificationModel.getNotification());
                        binding.recyclerViewJobs.setAdapter(notificationAdapter);
                        notificationAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e(TAG + "getNotification", e.toString());
                        binding.progressBar.setVisibility(View.GONE);
                        binding.tvMsg.setVisibility(View.VISIBLE);
                        binding.tvMsg.setText("Yet, No Notification Found !");
                    }

                } else {
                    Log.e(TAG + "getNotification", response.message());
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvMsg.setVisibility(View.VISIBLE);
                    binding.tvMsg.setText("Yet, No Notification Found !");
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.tvMsg.setVisibility(View.VISIBLE);
                binding.tvMsg.setText("Yet, No Notification Found !");
                Log.e(TAG + "getNotification", t.toString());
            }
        });

    }

}
