package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityWorkHistoryBinding;
import com.weboconnect.nurseify.intermediate.CertificationCallback;
import com.weboconnect.nurseify.screen.nurse.adapters.CertificationsAdapter;
import com.weboconnect.nurseify.screen.nurse.model.NotificationModel;
import com.weboconnect.nurseify.screen.nurse.model.NurseProfileModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkHistoryActivity extends AppCompatActivity {

    ActivityWorkHistoryBinding binding;

    String user_id;
    String TAG = "WorkHistoryActivity ";


    NurseProfileModel nurseProfileModel;

    CertificationCallback callback = new CertificationCallback() {
        @Override
        public void onRemove(String url, String id) {
            removeImage(url,id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(WorkHistoryActivity.this,R.layout.activity_work_history);


        getNurseProfile();

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkHistoryActivity.this, RegisterActivity.class);
                i.putExtra("state",4);
                startActivity(i);
            }
        });


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getNurseProfile(){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<NurseProfileModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<NurseProfileModel>() {
            @Override
            public void onResponse(Call<NurseProfileModel> call, Response<NurseProfileModel> response) {
                Log.d(TAG+"getNurseProfile ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {
                        nurseProfileModel = response.body();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setData();

                            }
                        });

                    }
                    catch (Exception e){
                        Log.e(TAG+"getNurseProfile",e.toString());
                    }

                }else {
                    Log.e(TAG+"getNurseProfile",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NurseProfileModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"getNurseProfile",t.toString());
            }
        });

    }

    private void setData(){

        binding.tvHighestNurseDegree.setText(nurseProfileModel.getData().getExperience().getHighestNursingDegreeDefinition());
        binding.tvCollageName.setText(nurseProfileModel.getData().getExperience().getCollegeUniName());
        binding.tvFacilityExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAcuteCareFacility());
        binding.tvNursingExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAmbulatoryCareFacility());
        binding.tvCerner.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyCerner());
        binding.tvMeditech.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyMeditech());
        binding.tvEpic.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyEpic());
        binding.tvOther.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyOther());


        binding.recyclerView.setAdapter(new CertificationsAdapter(WorkHistoryActivity.this,nurseProfileModel.getData().getCertitficate(),callback));

        binding.downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(nurseProfileModel.getData().getResume()));
                startActivity(viewIntent);

            }
        });

    }

    private void removeImage(String url, String id){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody img_url = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        RequestBody c_id = RequestBody.create(MediaType.parse("multipart/form-data"), id);

        Call<ResponseModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_remove_image(user_id1,img_url,c_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG+"getNurseProfile ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {

                        getNurseProfile();

                    }
                    catch (Exception e){
                        Log.e(TAG+"getNurseProfile",e.toString());
                    }

                }else {
                    Log.e(TAG+"getNurseProfile",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"getNurseProfile",t.toString());
            }
        });

    }

}