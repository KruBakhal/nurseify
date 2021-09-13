package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.adapter.JobAdapter;
import com.weboconnect.nursify.adapter.MySpinnerArrayAdapter;
import com.weboconnect.nursify.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private int mRegistrationStep = 0;
    private boolean personalDetails = false;
    private boolean hourlyRate = false;
    private boolean workHistory = false;
    private boolean roleInterest = false;
    private List<String> arrayList = new ArrayList();
    private int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegisterActivity.this,R.layout.activity_register);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(RegisterActivity.this));
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.getInt("state",0)==1){
                state = 1;
                personalDialog();
            }else if(bundle.getInt("state",0)==2){
                state = 2;
                hourlyRate();
            }else if(bundle.getInt("state",0)==3){
                state = 3;
                workDialog();
            }else if(bundle.getInt("state",0)==4){
                state = 4;
                roleDialog();
            }else{
                chooseOption();
            }
        }else {
            chooseOption();
        }
        arrayList.add("No experience");
        arrayList.add("Beginner (<1 year)");
        arrayList.add("Basic (1-2 years)");
        arrayList.add("Proficient (2-4 years)");
        arrayList.add("Advanced (5+ years)");
    }
    private void chooseOption(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_choose_option, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        TextView text_personal = dialog.findViewById(R.id.text_personal);
        TextView text_hourly = dialog.findViewById(R.id.text_hourly);
        TextView text_work = dialog.findViewById(R.id.text_work);
        TextView text_role = dialog.findViewById(R.id.text_role);
        if(personalDetails){
            text_personal.setBackground(ContextCompat.getDrawable(RegisterActivity.this,R.drawable.btn_fill_secondary));
            text_personal.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(hourlyRate){
            text_hourly.setBackground(ContextCompat.getDrawable(RegisterActivity.this,R.drawable.btn_fill_secondary));
            text_hourly.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(workHistory){
            text_work.setBackground(ContextCompat.getDrawable(RegisterActivity.this,R.drawable.btn_fill_secondary));
            text_work.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(roleInterest){
            text_role.setBackground(ContextCompat.getDrawable(RegisterActivity.this,R.drawable.btn_fill_secondary));
            text_role.setTextColor(Color.parseColor("#FFFFFF"));
        }
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRegistrationStep==2){
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    dialog.dismiss();
                }else{
                    Toast.makeText(RegisterActivity.this,"Please fill required form.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        text_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDialog();
                dialog.dismiss();
            }
        });
        text_hourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourlyRate();
                dialog.dismiss();
            }
        });
        text_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog();
                dialog.dismiss();
            }
        });
        text_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roleDialog();
                dialog.dismiss();
            }
        });
    }
    private void personalDialog(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_personal_details, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDialog2();
                dialog.dismiss();
            }
        });
    }
    private void personalDialog2(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_personal_details_2, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegistrationStep++;
                personalDetails = true;
                if(state==0){
                    chooseOption();
                }else {
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
    private void hourlyRate(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_horly_rate, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourlyRate = true;
                mRegistrationStep++;

                if(state==0){
                    chooseOption();
                }else {
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
    private void workDialog(){
        int spinprojectindex = -1;
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_work_history, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        Spinner spinProject = dialog.findViewById(R.id.spinner_center);
        MySpinnerArrayAdapter spinnerAdapter = new MySpinnerArrayAdapter(RegisterActivity.this, R.layout.spinner_item,arrayList);
        spinProject.setAdapter(spinnerAdapter);
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog2();
                dialog.dismiss();
            }
        });
    }
    private void workDialog2(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_work_history_2, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workHistory = true;

                if(state==0){
                    chooseOption();
                }else {
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
    private void roleDialog(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_role_interest, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roleDialog2();
                dialog.dismiss();
            }
        });
    }
    private void roleDialog2(){
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_work_history_2, null);
        final Dialog dialog = new Dialog(RegisterActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView next = dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roleInterest = true;
                if(state==0){
                    chooseOption();
                }else {
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
}