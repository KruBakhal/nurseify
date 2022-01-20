package com.weboconnect.nurseify.screen.nurse.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.NotificationAdapter;
import com.weboconnect.nurseify.databinding.FragmentNotificationBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
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

public class NotificationFragment extends Fragment {
    String id;
    FragmentNotificationBinding binding;
    View view;

    String user_id;
    String TAG = "NotificationFragment ";

    NotificationAdapter notificationAdapter;
    List<NotificationModel.Notification> notificationList = new ArrayList<>();
    private ProgressDialog progressDialog;

    public NotificationFragment() {
    }

    public NotificationFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, null, false);

        notificationAdapter = new NotificationAdapter(getActivity(), notificationList
                , true, new ItemCallback() {
            @Override
            public void onClick(int position) {

                performDelete(notificationList.get(position).getNotificationId()
                        , new SessionManager(getContext()).get_user_register_Id(), position);

            }

            private void performDelete(String assetId, String user_id, int position)    {
                if (!Utils.isNetworkAvailable(getContext())) {
                    Utils.displayToast(getContext(), getContext().getResources().getString(R.string.no_internet));
                    return;
                }
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
                RequestBody assetId1 = RequestBody.create(MediaType.parse("multipart/form-data"), assetId);

                Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                        .call_remove_notification(user_id1, assetId1);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response == null || response.body() == null) {
                            progressDialog.cancel();
                            Utils.displayToast(getContext(), getContext().getResources().getString(R.string.something_when_wrong));
                            return;
                        }
                        if (!response.body().getApiStatus().equals("1")) {
                            Utils.displayToast(getContext(), "" + response.body().getMessage());
                            progressDialog.cancel();
                            return;
                        }
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            Utils.displayToast(getContext(), "Notification deleted !");
                            /*notificationList.remove(position);*/
                            notificationAdapter.removeItem(position);
                            notificationAdapter.notifyDataSetChanged();
                        } else {
                            progressDialog.dismiss();
                            Utils.displayToast(getContext(), "Failed to delete item data");
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


        getNotification();

        return view = binding.getRoot();
    }

    private void getNotification() {

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<NotificationModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
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