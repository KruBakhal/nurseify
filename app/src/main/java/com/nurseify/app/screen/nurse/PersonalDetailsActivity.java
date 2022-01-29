package com.nurseify.app.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityPersonalDetailsBinding;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;

import java.lang.reflect.Type;

public class PersonalDetailsActivity extends AppCompatActivity {

    ActivityPersonalDetailsBinding binding;

    UserProfileData userProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(PersonalDetailsActivity.this, R.layout.activity_personal_details);

        userProfileData = new SessionManager(getApplicationContext()).get_User();

        setData();
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonalDetailsActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.PERSON_DETAIL);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(userProfileData));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void setData() {
        binding.tvFirstName.setText(userProfileData.getFirstName());
        binding.tvLastName.setText(userProfileData.getLastName());
        binding.tvEmail.setText(userProfileData.getEmail());
        binding.tvPhone.setText(userProfileData.getMobile());
        binding.tvLicenceState.setText(userProfileData.getNursingLicenseState());
        binding.tvLicenceNo.setText(userProfileData.getNursingLicenseNumber());
        binding.tvPreferredGeography.setText("Preferred Geography");
        binding.tvAddress.setText(userProfileData.getAddress());
        binding.tvCity.setText(userProfileData.getCity());
        binding.tvState.setText(userProfileData.getState());
        binding.tvPostCode.setText(userProfileData.getPostcode());
        binding.tvCountry.setText(userProfileData.getCountry());
        for (int i = 0; i < userProfileData.getSpecialty().size(); i++) {
            if (i == 0)
                binding.tvSpecialty.setText(userProfileData.getSpecialty().get(i).getName());
            else
                binding.tvSpecialty.append(", " + userProfileData.getSpecialty().get(i).getName());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {

                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                Type type = new TypeToken<UserProfileData>() {
                }.getType();
                userProfileData = new Gson().fromJson(data1, type);
                if (userProfileData != null) {
                    setData();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(getApplicationContext(), "Empty Data on Result");
            } else {


            }
        }
    }
}