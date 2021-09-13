package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivityPersonalDetailsBinding;

public class PersonalDetailsActivity extends AppCompatActivity {

    ActivityPersonalDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(PersonalDetailsActivity.this,R.layout.activity_personal_details);
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