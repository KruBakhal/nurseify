package com.weboconnect.nurseify.screen.facility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityRegistrationFBinding;
import com.weboconnect.nurseify.screen.facility.model.DropdownModel;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.webService.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegistrationFActivity extends AppCompatActivity {

    ActivityRegistrationFBinding binding;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationFActivity.this,R.layout.activity_registration_f);
        profileSetup1();

        count=0;

        Observer<DropdownModel> observer  = new Observer<DropdownModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull DropdownModel dropdownModel) {

                Log.e("count",count+"");
                count++;

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.merge(RetrofitClient.getInstance().getFacilityApi().call_dropdown_get_medical_records(), RetrofitClient.getInstance().getFacilityApi().call_dropdown_get_bcheck_provider() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    private void profileSetup1(){
        final View loc = getLayoutInflater().from(RegistrationFActivity.this).inflate(R.layout.dialog_profile_setup1, null);
        final Dialog dialog = new Dialog(RegistrationFActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSetup2();
                dialog.dismiss();
            }
        });
    }
    private void profileSetup2(){
        final View loc = getLayoutInflater().from(RegistrationFActivity.this).inflate(R.layout.dialog_profile_setup2, null);
        final Dialog dialog = new Dialog(RegistrationFActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSetup3();
                dialog.dismiss();
            }
        });
    }
    private void profileSetup3(){
        final View loc = getLayoutInflater().from(RegistrationFActivity.this).inflate(R.layout.dialog_profile_setup3, null);
        final Dialog dialog = new Dialog(RegistrationFActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSetup4();
                dialog.dismiss();
            }
        });
    }
    private void profileSetup4(){
        final View loc = getLayoutInflater().from(RegistrationFActivity.this).inflate(R.layout.dialog_profile_setup4, null);
        final Dialog dialog = new Dialog(RegistrationFActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSetup5();
                dialog.dismiss();
            }
        });
    }
    private void profileSetup5(){
        final View loc = getLayoutInflater().from(RegistrationFActivity.this).inflate(R.layout.dialog_profile_setup5, null);
        final Dialog dialog = new Dialog(RegistrationFActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(RegistrationFActivity.this, HomeFActivity.class));
            }
        });
    }
}