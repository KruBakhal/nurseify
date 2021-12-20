package com.weboconnect.nurseify;

import static com.weboconnect.nurseify.utils.Constant.CONST_FACULTY_TYPE;
import static com.weboconnect.nurseify.utils.Constant.CONST_NURSE_TYPE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.weboconnect.nurseify.screen.LoginSelectActivity;
import com.weboconnect.nurseify.screen.facility.HomeFActivity;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private FirebaseCrashlytics crashlytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crashlytics = FirebaseCrashlytics.getInstance();

//        crashlytics.setUserId(Constant.DEVICE_INFO);


        sessionManager = new SessionManager(MainActivity.this);
        String type = sessionManager.get_TYPE();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(type) && type.equals(CONST_NURSE_TYPE)) {
                    if (sessionManager.isUserLoginedIn()) {
                        if (sessionManager.get_User() != null && (sessionManager.get_User().getProfileDetailFlag().equals("0")
                                || sessionManager.get_User().getHourlyRateAndAvailFlag().equals("0"))) {
                            startActivity(new Intent(getApplicationContext(), RegisterActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(sessionManager.get_User())));
                        } else
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                     /*   startActivity(new Intent(getApplicationContext(), RegisterActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(sessionManager.get_User())));*/
//
                        return;
                    }
                }
                if (!TextUtils.isEmpty(type) && type.equals(CONST_FACULTY_TYPE)) {
                    if (sessionManager.isUserLoginedIn()) {
                        startActivity(new Intent(getApplicationContext(), HomeFActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        return;
                    }
                }

                startActivity(new Intent(getApplicationContext(), LoginSelectActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                );
            }
        }, 2000);
    }
}