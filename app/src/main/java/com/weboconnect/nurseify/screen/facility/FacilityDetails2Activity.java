package com.weboconnect.nurseify.screen.facility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails1Binding;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails2Binding;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;

import java.lang.reflect.Type;

public class FacilityDetails2Activity extends AppCompatActivity {
    ActivityFacilityDetails2Binding binding;
    private FacilityProfile model;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails2Activity.this, R.layout.activity_facility_details2);
        context = this;
        model = new SessionManager(context).get_facility();
        setData();
        click();
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