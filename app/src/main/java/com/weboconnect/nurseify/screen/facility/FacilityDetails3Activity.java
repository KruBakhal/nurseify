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
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails3Binding;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;

import java.lang.reflect.Type;

public class FacilityDetails3Activity extends AppCompatActivity {

    ActivityFacilityDetails3Binding binding;
    private Context context;
    private FacilityProfile model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_details3);
        binding = DataBindingUtil.setContentView(FacilityDetails3Activity.this, R.layout.activity_facility_details3);
        context = this;
        model = new SessionManager(context).get_facilityProfile();
        setData();
        click();
    }

    private void setData() {

        String tvEMR;
        if (!TextUtils.isEmpty(model.getFacilityEmr()) && model.getFacilityEmr().equals("0")) {
            binding.tvEmr.setText("Other");
            binding.edEmr.setText("" + model.getFacilityEmrDefinition());
        } else {
            binding.layEmr.setVisibility(View.GONE);
            binding.tvEmr.setText("" + model.getFacilityEmrDefinition());
        }
        if (!TextUtils.isEmpty(model.getFacilityBcheckProvider()) && model.getFacilityBcheckProvider().equals("0")) {
            binding.tvBackground.setText("Other");
            binding.edBackground.setText("" + model.getFacilityBcheckProviderDefinition());
        } else {
            binding.layBackground.setVisibility(View.GONE);
            binding.tvBackground.setText("" + model.getFacilityBcheckProviderDefinition());
        }
        if (!TextUtils.isEmpty(model.getNurseCredSoft()) && model.getNurseCredSoft().equals("0")) {
            binding.tvSoft.setText("Other");
            binding.edOtherSoft.setText("" + model.getNurseCredSoftDefinition());
        } else {
            binding.layCredential.setVisibility(View.GONE);
            binding.tvSoft.setText("" + model.getNurseCredSoftDefinition());
        }
        if (!TextUtils.isEmpty(model.getNurseSchedulingSys()) && model.getNurseSchedulingSys().equals("0")) {
            binding.tvScheduling.setText("Other");
            binding.edScheduling.setText("" + model.getNurseSchedulingSysDefinition());
        } else {
            binding.layScheduling.setVisibility(View.GONE);
            binding.tvScheduling.setText("" + model.getNurseSchedulingSysDefinition());
        }
        if (!TextUtils.isEmpty(model.getTimeAttendSys()) && model.getTimeAttendSys().equals("0")) {
            binding.tvAttendance.setText("Other");
            binding.edAttendance.setText("" + model.getTimeAttendSysDefinition());
        } else {
            binding.layAttendance.setVisibility(View.GONE);
            binding.tvAttendance.setText("" + model.getTimeAttendSysDefinition());
        }

        String edLicenseBed = model.getLicensedBeds();
        String trauma = model.getTraumaDesignationDefinition();
        if (TextUtils.isEmpty(edLicenseBed))
            edLicenseBed = "-";
        if (TextUtils.isEmpty(trauma))
            trauma = "-";
        binding.edLicenseBed.setText(edLicenseBed);
        binding.tvTrauma.setText(trauma);


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
                i.putExtra(Constant.SECTION, 3);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model));
                startActivityForResult(i, Constant.REQUEST_EDIT);

                Utils.onClickEvent(v);
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacilityDetails3Activity.this, FacilityDetails4Activity.class));
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