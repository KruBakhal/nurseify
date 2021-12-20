package com.weboconnect.nurseify.screen;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginSelectBinding;
import com.weboconnect.nurseify.screen.facility.LoginFacilityActivity;
import com.weboconnect.nurseify.screen.nurse.LoginActivity;
import com.weboconnect.nurseify.utils.Utils;

public class LoginSelectActivity extends AppCompatActivity {

    ActivityLoginSelectBinding binding;
    private Context context = LoginSelectActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginSelectActivity.this, R.layout.activity_login_select);
        binding.nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkReadExternal()) {
                    requestPermission();
                    return;
                }
                startActivity(new Intent(LoginSelectActivity.this, LoginActivity.class));
            }
        });
        binding.facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkReadExternal()) {
                    requestPermission();
                    return;
                }
                startActivity(new Intent(LoginSelectActivity.this, LoginFacilityActivity.class));
            }
        });
    }

    private void permissionSetup() {
        if (!checkReadExternal()) {
            requestPermission();
            return;
        }
    }

    private boolean checkReadExternal() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        // perform action when allow permission success
                        Utils.displayToast(context, "Permission Allowed");
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 229);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 229);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(LoginSelectActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE},
                    123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 229) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Utils.displayToast(context, "Permission Allowed");
                } else {
                    Toast.makeText(this, "Please, Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}