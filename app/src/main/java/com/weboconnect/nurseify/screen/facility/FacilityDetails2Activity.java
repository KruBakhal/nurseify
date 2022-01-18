package com.weboconnect.nurseify.screen.facility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails2Binding;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityDetails2Activity extends AppCompatActivity {
    ActivityFacilityDetails2Binding binding;
    private FacilityProfile model;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails2Activity.this, R.layout.activity_facility_details2);
        context = this;
        model = new SessionManager(context).get_facilityProfile();
        setData();
     //   getProfileData();
        click();
    }
    private void getProfileData() {
        if (!Utils.isNetworkAvailable(FacilityDetails2Activity.this)) {
            Utils.displayToast(FacilityDetails2Activity.this, getResources().getString(R.string.no_internet));
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        String user_id = new SessionManager(getApplicationContext()).get_facilityProfile().getFacilityId();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<FacilityJobModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_facility_profile(user_id1);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {

                if (response.body() == null) {
                    try {
                        binding.progressBar.setVisibility(View.GONE);
                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (response.body().getApiStatus().equals("1")) {
                    binding.progressBar.setVisibility(View.GONE);
                    FacilityJobModel.Facility model = response.body().getData().get(0);

                    String img = "", web = "", you = "", senior, about;

                    img = model.getCnoImage();
//                    web = model.getFacilityWebsite();
                    you = model.getVideoEmbedUrl();
                    senior = model.getCnoMessage();
                    about = model.getAboutFacility();

                    if (TextUtils.isEmpty(you)) {
                        you = "-";
                    }
                    if (TextUtils.isEmpty(web)) {
                        web = "-";
                    }
                    if (TextUtils.isEmpty(senior)) {
                        senior = "-";
                    }
                    if (TextUtils.isEmpty(about)) {
                        about = "-";
                    }
                    if (TextUtils.isEmpty(img)) {
                        img = "";
                    } else if (!TextUtils.isEmpty(img))
                        Glide.with(context).load(img).placeholder(R.drawable.place_holder_img)
                                .error(R.drawable.place_holder_img).into(binding.imgHeadCNo);
                    else
                        binding.layHeadCno.setVisibility(View.GONE);

                    binding.edSeniorLead.setText(Html.fromHtml(senior));
                    binding.tvAbout.setText(Html.fromHtml(about));
                    binding.edWebsite.setText(web);
                    binding.edYoutube.setText(you);


                } else {
                    Utils.displayToast(FacilityDetails2Activity.this, "Data has not been updated");

                    binding.progressBar.setVisibility(View.GONE);
                }


                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.displayToast(FacilityDetails2Activity.this, "Failed to get updated data !");
                Log.e("TAG" + "getNurseProfile", t.toString());
            }
        });
    }
    private void setData() {
        String img = "", web = "", you = "", senior, about;

        img = model.getCnoImage();
        web = model.getFacilityWebsite();
        you = model.getVideoEmbedUrl();
        senior = model.getCnoMessage();
        about = model.getAboutFacility();

        if (TextUtils.isEmpty(you)) {
            you = "-";
        }
        if (TextUtils.isEmpty(web)) {
            web = "-";
        }
        if (TextUtils.isEmpty(senior)) {
            senior = "-";
        }
        if (TextUtils.isEmpty(about)) {
            about = "-";
        }
        if (TextUtils.isEmpty(img)) {
            img = "";
        } else if (!TextUtils.isEmpty(img))
            Glide.with(context).load(img).placeholder(R.drawable.place_holder_img)
                    .error(R.drawable.place_holder_img).into(binding.imgHeadCNo);
        else
            binding.layHeadCno.setVisibility(View.GONE);

        binding.edSeniorLead.setText(Html.fromHtml(senior));
        binding.tvAbout.setText(Html.fromHtml(about));
        binding.edWebsite.setText(web);
        binding.edYoutube.setText(you);
    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.onClickEvent(v);
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RegistrationFActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, 2);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model));
                startActivityForResult(i, Constant.REQUEST_EDIT);

                Utils.onClickEvent(v);
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacilityDetails2Activity.this, FacilityDetails3Activity.class));
                Utils.onClickEvent(v);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {

                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                Type type = new TypeToken<FacilityProfile>() {
                }.getType();
                model = new Gson().fromJson(data1, type);
                if (model != null) {
                    setData();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(getApplicationContext(), "Empty Data on Result");
            } else {


            }
        }
    }
}