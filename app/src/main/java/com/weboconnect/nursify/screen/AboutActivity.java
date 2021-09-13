package com.weboconnect.nursify.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AboutActivity.this, R.layout.activity_about);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}