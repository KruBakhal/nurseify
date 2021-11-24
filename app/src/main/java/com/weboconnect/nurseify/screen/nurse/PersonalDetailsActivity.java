package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityPersonalDetailsBinding;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.SessionManager;

public class PersonalDetailsActivity extends AppCompatActivity {

    ActivityPersonalDetailsBinding binding;

    UserProfileData userProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(PersonalDetailsActivity.this,R.layout.activity_personal_details);

        userProfileData = new SessionManager(getApplicationContext()).get_User();

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

        for (int i=0; i<userProfileData.getSpecialty().size(); i++){
            if (i==0)
                binding.tvSpecialty.setText(userProfileData.getSpecialty().get(i));
            else
                binding.tvSpecialty.append(", "+userProfileData.getSpecialty().get(i));
        }


        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonalDetailsActivity.this, RegisterActivity.class);
                i.putExtra("state",1);
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
}