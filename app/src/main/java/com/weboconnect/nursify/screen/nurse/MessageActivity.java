package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.adapter.ChatAdapter;
import com.weboconnect.nursify.databinding.ActivityMessageBinding;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MessageActivity.this,R.layout.activity_message);
        binding.recyclerViewJobs.setAdapter(new ChatAdapter(MessageActivity.this));
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}