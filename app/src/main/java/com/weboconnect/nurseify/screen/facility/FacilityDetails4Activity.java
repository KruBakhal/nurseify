package com.weboconnect.nurseify.screen.facility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails1Binding;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails4Binding;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;

import java.lang.reflect.Type;

public class FacilityDetails4Activity extends AppCompatActivity {

    ActivityFacilityDetails4Binding binding;
    private Context context;
    private FacilityProfile model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails4Activity.this, R.layout.activity_facility_details4);
        context = this;
        model = new SessionManager(context).get_facility();
        setData();
        click();
    }

    private void setData() {
        if (model == null || model.getFacilitySocial() == null)
            return;
        if (TextUtils.isEmpty(model.getFacilitySocial().getFacebook())) {
            binding.edFb.setText("-");
        } else {
            binding.edFb.setText("" + model.getFacilitySocial().getFacebook());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getTwitter())) {
            binding.edTweet.setText("-");
        } else {
            binding.edTweet.setText("" + model.getFacilitySocial().getTwitter());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getYoutube())) {
            binding.edYoutube.setText("-");
        } else {
            binding.edYoutube.setText("" + model.getFacilitySocial().getYoutube());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getTiktok())) {
            binding.edTiktok.setText("-");
        } else {
            binding.edTiktok.setText("" + model.getFacilitySocial().getTiktok());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getSanpchat())) {
            binding.edSnap.setText("-");
        } else {
            binding.edSnap.setText("" + model.getFacilitySocial().getSanpchat());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getLinkedin())) {
            binding.edLinkin.setText("-");
        } else {
            binding.edLinkin.setText("" + model.getFacilitySocial().getLinkedin());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getPinterest())) {
            binding.edPinterest.setText("-");
        } else {
            binding.edPinterest.setText("" + model.getFacilitySocial().getPinterest());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getInstagram())) {
            binding.edInsta.setText("-");
        } else {
            binding.edInsta.setText("" + model.getFacilitySocial().getInstagram());
        }

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
                i.putExtra(Constant.SECTION, 4);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model));
                startActivityForResult(i, Constant.REQUEST_EDIT);

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