package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityRegistrationFBinding;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;

public class RegistrationFActivity extends AppCompatActivity {
    ActivityRegistrationFBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationFActivity.this,R.layout.activity_registration_f);
        profileSetup1();
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