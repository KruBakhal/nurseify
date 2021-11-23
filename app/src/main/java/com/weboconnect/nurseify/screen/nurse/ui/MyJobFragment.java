package com.weboconnect.nurseify.screen.nurse.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.adapters.ActiveAdapter;
import com.weboconnect.nurseify.adapter.CompletedAdapter;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.adapter.OfferedAdapter;
import com.weboconnect.nurseify.databinding.FragmentMyJobsBinding;
import com.weboconnect.nurseify.screen.nurse.adapters.OfferedJobAdapter;
import com.weboconnect.nurseify.screen.nurse.model.OfferedJobModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobFragment extends Fragment {
    String id;
    FragmentMyJobsBinding binding;
    View view;

    String user_id;
    String TAG = "MyJobFragment ";

    OfferedJobAdapter offeredJobAdapter;
    List<OfferedJobModel.OfferedJob> offeredJobs = new ArrayList<>();

    OfferedJobCallback offeredJobCallback = new OfferedJobCallback() {
        @Override
        public void onAccept(String jobId) {
            offeredJobAccept(jobId);
        }

        @Override
        public void onReject(String jobId) {
            offeredJobReject(jobId);
        }
    };

    public MyJobFragment() {
    }

    public MyJobFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_jobs, null, false);

        offeredJobAdapter = new OfferedJobAdapter(getActivity(),offeredJobs,offeredJobCallback);
        binding.recyclerViewJobs.setAdapter(offeredJobAdapter);



        getOfferedJob();

        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new OfferedAdapter(getActivity()));
            }
        });

        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new ActiveAdapter(getActivity()));
            }
        });

        binding.textCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new CompletedAdapter(getActivity()));
            }
        });

        return view = binding.getRoot();
    }

    private void getOfferedJob(){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<OfferedJobModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_offered_job("1",user_id1);

        call.enqueue(new Callback<OfferedJobModel>() {
            @Override
            public void onResponse(Call<OfferedJobModel> call, Response<OfferedJobModel> response) {
                Log.d(TAG+"getOfferedJob ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {
                        OfferedJobModel offeredJobModel = response.body();
                        offeredJobs.addAll(offeredJobModel.getOfferedJob());
                        offeredJobAdapter.notifyDataSetChanged();

                    }
                    catch (Exception e){
                        Log.e(TAG+"getOfferedJob",e.toString());
                    }

                }else {
                    Log.e(TAG+"getOfferedJob",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<OfferedJobModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"getOfferedJob",t.toString());
            }
        });

    }

    private void offeredJobAccept(String jobId){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<ResponseModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_offered_job_accept(user_id1,job_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG+"accept ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {

                        ResponseModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")){
                            getOfferedJob();
                        }

                    }
                    catch (Exception e){
                        Log.e(TAG+"accept",e.toString());
                    }

                }else {
                    Log.e(TAG+"accept",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"accept",t.toString());
            }
        });

    }

    private void offeredJobReject(String jobId){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<ResponseModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_offered_job_reject(user_id1,job_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG+"reject ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {

                        ResponseModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")){
                            getOfferedJob();
                        }

                    }
                    catch (Exception e){
                        Log.e(TAG+"reject",e.toString());
                    }

                }else {
                    Log.e(TAG+"reject",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"reject",t.toString());
            }
        });

    }

}