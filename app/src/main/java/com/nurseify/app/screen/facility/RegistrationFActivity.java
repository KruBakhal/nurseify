package com.nurseify.app.screen.facility;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityRegistrationFBinding;
import com.nurseify.app.screen.facility.profile_setup_dialog.ProfileSetupDialog_1;
import com.nurseify.app.screen.facility.profile_setup_dialog.ProfileSetupDialog_2;
import com.nurseify.app.screen.facility.profile_setup_dialog.ProfileSetupDialog_3;
import com.nurseify.app.screen.facility.profile_setup_dialog.ProfileSetupDialog_4;
import com.nurseify.app.screen.facility.profile_setup_dialog.ProfileSetupDialog_5;
import com.nurseify.app.screen.facility.model.FacilityProfile;
import com.nurseify.app.screen.facility.viewModel.DialogStatus;
import com.nurseify.app.screen.facility.viewModel.DialogStatusMessage;
import com.nurseify.app.screen.facility.viewModel.RegistrationViewModel;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;

import java.lang.reflect.Type;

public class RegistrationFActivity extends AppCompatActivity {

    ActivityRegistrationFBinding binding;

    private Context context;
    public RegistrationViewModel viewModel;
    private FacilityProfile model;
    private ProfileSetupDialog_1 dialog_1;
    private ProfileSetupDialog_2 dialog_2;
    private ProfileSetupDialog_3 dialog_3;
    private ProfileSetupDialog_4 dialog_4;
    private ProfileSetupDialog_5 dialog_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegistrationFActivity.this, R.layout.activity_registration_f);
        context = this;
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        setupData();
        observer_view();

    }

    private void setupData() {
        String data = getIntent().getStringExtra(Constant.STR_RESPONSE_DATA);
        viewModel.option_call = getIntent().getIntExtra(Constant.SECTION, 0);
        viewModel.isEditMode = getIntent().getBooleanExtra(Constant.EDIT_MODE, false);
        Type type = new TypeToken<FacilityProfile>() {
        }.getType();
        model = new Gson().fromJson(data, type);
        if (model == null) {
            model = new SessionManager(context).get_facilityProfile();
        }
        viewModel.main_model = model;
        viewModel.new_facilityModel = model;
        if (viewModel.option_call == 0 || viewModel.option_call == 1) {
            profileSetup1();
        } else if (viewModel.option_call == 2) {
            profileSetup2();
        } else if (viewModel.option_call == 3) {
            profileSetup3();
        } else if (viewModel.option_call == 4) {
            profileSetup5();
        }
    }

    private void observer_view() {
        viewModel.do_DismissDialog().observe(this, new Observer<DialogStatusMessage>() {
            void action_Of_Msg(DialogStatus dialogStatus, Dialog dialog) {
                switch (dialogStatus) {
                    case Close:
                        if (dialog != null) dialog.dismiss();
                        break;
                    case Dismiss:
                        if (dialog != null) dialog.dismiss();
                        break;
                    case Done:
                        if (dialog != null) dialog.dismiss();
                        break;
                    case Cancel:
                        if (dialog != null) dialog.cancel();
                        break;
                }
            }

            @Override
            public void onChanged(DialogStatusMessage dialogStatus) {
                Dialog dialog = null;
                switch (dialogStatus.getDialogType()) {
                    case 1:
                        dialog = dialog_1.getDialog();
                        if (dialogStatus.getDialogStatus() == DialogStatus.Done
                                || dialogStatus.getDialogStatus() == DialogStatus.Dismiss) {
                            action_Of_Msg(dialogStatus.getDialogStatus(), dialog);
                            profileSetup2();
                        } else if (dialogStatus.getDialogStatus() == DialogStatus.Cancel) {
//                            Utils.displayToast(context, "You cant move forward untill profile setup completed !");
                            finish();
                        }
                        break;
                    case 2:
                        dialog = dialog_2.getDialog();
                        action_Of_Msg(dialogStatus.getDialogStatus(), dialog);

                        if (dialogStatus.getDialogStatus() == DialogStatus.Done
                                || dialogStatus.getDialogStatus() == DialogStatus.Dismiss) {
                            if (viewModel.isEditMode) {
                                setResult(RESULT_OK, new Intent().putExtra(Constant.STR_RESPONSE_DATA,
                                        new Gson().toJson(viewModel.main_model)));
                                finish();
                            } else {
                                profileSetup3();
                            }
                        } else if (dialogStatus.getDialogStatus() == DialogStatus.Cancel) {
                            profileSetup1();
                        }

                        break;
                    case 3:
                        dialog = dialog_3.getDialog();
                        action_Of_Msg(dialogStatus.getDialogStatus(), dialog);
                        if (dialogStatus.getDialogStatus() == DialogStatus.Done
                                || dialogStatus.getDialogStatus() == DialogStatus.Dismiss)
                            profileSetup4();
                        else if (dialogStatus.getDialogStatus() == DialogStatus.Cancel) {
                            profileSetup2();
                        }
                        break;
                    case 4:
                        dialog = dialog_4.getDialog();
                        action_Of_Msg(dialogStatus.getDialogStatus(), dialog);
                        if (dialogStatus.getDialogStatus() == DialogStatus.Done
                                || dialogStatus.getDialogStatus() == DialogStatus.Dismiss)
                            if (viewModel.isEditMode) {
                                setResult(RESULT_OK, new Intent().putExtra(Constant.STR_RESPONSE_DATA,
                                        new Gson().toJson(viewModel.main_model)));
                                finish();
                            } else {
                                profileSetup5();
                            }
                        else if (dialogStatus.getDialogStatus() == DialogStatus.Cancel) {
                            profileSetup3();
                        }
                        break;
                    case 5:
                        dialog = dialog_5.getDialog();
                        action_Of_Msg(dialogStatus.getDialogStatus(), dialog);
                        if (dialogStatus.getDialogStatus() == DialogStatus.Done
                                || dialogStatus.getDialogStatus() == DialogStatus.Dismiss) {
                            if (viewModel.isEditMode) {
                                setResult(RESULT_OK, new Intent().putExtra(Constant.STR_RESPONSE_DATA,
                                        new Gson().toJson(viewModel.main_model)));
                            } else {
                                startActivity(new Intent(context, HomeFActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                            finish();
                        } else if (dialogStatus.getDialogStatus() == DialogStatus.Cancel) {
                            profileSetup4();
                        }
                        break;
                }
            }
        });
        viewModel.getToastMesssage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.displayToast(context, s);
            }
        });
    }

    private void profileSetup1() {
        dialog_1 = ProfileSetupDialog_1.newInstance(1, "");
        dialog_1.show(getSupportFragmentManager(), "setup_1");

    }

    private void profileSetup2() {
        dialog_2 = ProfileSetupDialog_2.newInstance(2, "");
        dialog_2.show(getSupportFragmentManager(), "setup_2");
    }

    private void profileSetup3() {
        dialog_3 = ProfileSetupDialog_3.newInstance(3, "");
        dialog_3.show(getSupportFragmentManager(), "setup_3");

    }

    private void profileSetup4() {
        dialog_4 = ProfileSetupDialog_4.newInstance(4, "");
        dialog_4.show(getSupportFragmentManager(), "setup_4");

    }

    private void profileSetup5() {
        dialog_5 = ProfileSetupDialog_5.newInstance(5, "");
        dialog_5.show(getSupportFragmentManager(), "setup_5");

    }
}