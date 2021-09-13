package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivitySettingBinding;
import com.weboconnect.nursify.screen.AboutActivity;
import com.weboconnect.nursify.screen.PrivacyActivity;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SettingActivity.this,R.layout.activity_setting);
        binding.layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });
        binding.layoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, PrivacyActivity.class));
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View loc = getLayoutInflater().from(SettingActivity.this).inflate(R.layout.dialog_reset_phone, null);
                final Dialog dialog = new Dialog(SettingActivity.this,R.style.AlertDialog);
                dialog.setContentView(loc);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setCancelable(true);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.close_dialog);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                LinearLayout done = dialog.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
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