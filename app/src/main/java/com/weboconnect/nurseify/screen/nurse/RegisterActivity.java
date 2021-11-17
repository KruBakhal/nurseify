package com.weboconnect.nurseify.screen.nurse;

import static com.weboconnect.nurseify.utils.FileUtils.getPath;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.HourlyRateWindowAdapter;
import com.weboconnect.nurseify.adapter.JobAdapter;
import com.weboconnect.nurseify.adapter.PhotoFilesAdapter;
import com.weboconnect.nurseify.adapter.QuestionAdapter;
import com.weboconnect.nurseify.adapter.RoleInteresetWindowAdapter;
import com.weboconnect.nurseify.adapter.SpecialtyAdapter;
import com.weboconnect.nurseify.adapter.PersonalDetailWindowAdapter;
import com.weboconnect.nurseify.adapter.WorkHistoryWindowAdapter;
import com.weboconnect.nurseify.databinding.ActivityRegisterBinding;
import com.weboconnect.nurseify.databinding.DialogPersonalDetails2Binding;
import com.weboconnect.nurseify.databinding.DialogPersonalDetailsBinding;
import com.weboconnect.nurseify.databinding.DialogRoleInterest2Binding;
import com.weboconnect.nurseify.databinding.DialogRoleInterestBinding;
import com.weboconnect.nurseify.databinding.DialogWorkHistory2Binding;
import com.weboconnect.nurseify.databinding.DialogWorkHistoryBinding;
import com.weboconnect.nurseify.intermediate.API_ResponseCallback;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.nurse.model.AddCredentialModel;
import com.weboconnect.nurseify.screen.nurse.model.CernersDatum;
import com.weboconnect.nurseify.screen.nurse.model.CernersModel;
import com.weboconnect.nurseify.screen.nurse.model.Combine_HourlyRate_DataModel;
import com.weboconnect.nurseify.screen.nurse.model.Combine_PersonalDetail_2_DataModel;
import com.weboconnect.nurseify.screen.nurse.model.Combine_RoleIneterest_DataModel;
import com.weboconnect.nurseify.screen.nurse.model.Combine_WorkHistory_DataModel;
import com.weboconnect.nurseify.screen.nurse.model.CredentialDatum;
import com.weboconnect.nurseify.screen.nurse.model.CredentialModel;
import com.weboconnect.nurseify.screen.nurse.model.DegreeModel;
import com.weboconnect.nurseify.screen.nurse.model.Degree_Datum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel.HourlyRate_DayOfWeek_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.LanguageDatum;
import com.weboconnect.nurseify.screen.nurse.model.LanguageModel;
import com.weboconnect.nurseify.screen.nurse.model.LeaderRolesData;
import com.weboconnect.nurseify.screen.nurse.model.LeaderRolesModel;
import com.weboconnect.nurseify.screen.nurse.model.QuestionModel;
import com.weboconnect.nurseify.screen.nurse.model.RoleModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel.SpecialtyDatum;
import com.weboconnect.nurseify.screen.nurse.model.StateModel;
import com.weboconnect.nurseify.screen.nurse.model.State_Datum;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.screen.nurse.model.WorkHistorysModel;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationModel;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationDatum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitApi;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private int mRegistrationStep = 0;
    private boolean personalDetails = false;
    private boolean hourlyRate = false;
    private boolean workHistory = false;
    private boolean roleInterest = false;
    private List<String> arrayList = new ArrayList();
    private int state = 0;
    private UserProfileData model;
    Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /*----------------------------*/
    private String user_profile;
    public List<State_Datum> list_State = new ArrayList<com.weboconnect.nurseify.screen.nurse.model.State_Datum>();
    public String selected_state = null;
    public List<SpecialtyDatum> list_Specialty = new ArrayList<>();
    public List<Integer> select_specialty = new ArrayList<>();
    private SpecialtyAdapter specialtyAdapter;
    /*----------------------------*/
    public List<HourlyRate_Common_OptionDatum> list_shift_durations = new ArrayList<>();
    public List<HourlyRate_Common_OptionDatum> list_assignment_durations = new ArrayList<>();
    public List<HourlyRate_Common_OptionDatum> list_preferred_shift = new ArrayList<>();
    public List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week = new ArrayList<>();
    public List<WorkLocationDatum> list_geography = new ArrayList<>();
    public String selected_shift_duration = null;
    public String selected_assignment_duration = null;
    public String selected_preferred_shift = null;
    public String selected_geography = null;
    public List<Integer> select_daysOfWeek = new ArrayList<>();
    private SpecialtyAdapter daysOfWeekAdapter;
    private int dayOfMonth1 = 0, monthOfYear1 = 0, year1 = 0;
    /*----------------------------*/
    public List<Degree_Datum> list_nurse_degrees = new ArrayList<>();
    public int selected_nurse_degree = 0;
    public List<CernersDatum> list_experience = new ArrayList<>();
    public int selected_nurse_cerner = 0;
    public int selected_nurse_meditech = 0;
    public int selected_nurse_epic = 0;
    /*------------------------------------------*/
    public List<CredentialDatum> list_Credential = new ArrayList<>();
    public int selected_Credential = 0;

    /*--------------------------*/
    public List<LanguageDatum> list_Language = new ArrayList<LanguageDatum>();
    public List<Integer> select_Language = new ArrayList<>();
    /*------------------------*/
    String uploadCertificate, resumeFile;
    private List<QuestionModel> list_Ques = new ArrayList<>();

    /*-------------------------*/
    private List<String> list_photos = new ArrayList<>();
    private List<String> list_Files = new ArrayList<>();
    private SpecialtyAdapter languageAdapter;
    private List<LeaderRolesData> list_LeaderShipRole = new ArrayList<>();
    public int selected_leadership = 0;
    private PhotoFilesAdapter photoFilesAdapter;
    private PhotoFilesAdapter filesAdapter;
    private DialogWorkHistory2Binding history2Binding;
    private DialogPersonalDetailsBinding personalDetailsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        context = this;

        binding.recyclerViewJobs.setAdapter(new JobAdapter(RegisterActivity.this));
        String data = getIntent().getStringExtra(Constant.STR_RESPONSE_DATA);
        Type type = new TypeToken<UserProfileData>() {
        }.getType();
        model = new Gson().fromJson(data, type);

        if (model != null) {
            setData();
            if (model.getProfileDetailFlag().equals("0")) {
//                state = 1;
                personalDetails = false;
            } else {
                personalDetails = true;
            }
            if (model.getHourlyRateAndAvailFlag().equals("0")) {
                hourlyRate = false;
            } else {
                hourlyRate = true;
            }
        }
        chooseOption();
    }

    private void setData() {

    }

    private void chooseOption() {
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_choose_option, null);
        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        TextView text_personal = dialog.findViewById(R.id.text_personal);
        TextView text_hourly = dialog.findViewById(R.id.text_hourly);
        TextView text_work = dialog.findViewById(R.id.text_work);
        TextView text_role = dialog.findViewById(R.id.text_role);
        if (!TextUtils.isEmpty(model.getProfileDetailFlag()) && model.getProfileDetailFlag().equals("1")) {
            personalDetails = true;
        } else {
            personalDetails = false;
        }
        if (!TextUtils.isEmpty(model.getHourlyRateAndAvailFlag()) && model.getHourlyRateAndAvailFlag().equals("1")) {
            hourlyRate = true;
        } else {
            hourlyRate = false;
        }
        if (personalDetails) {
            text_personal.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.btn_fill_secondary));
            text_personal.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if (hourlyRate) {
            text_hourly.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.btn_fill_secondary));
            text_hourly.setTextColor(Color.parseColor("#FFFFFF"));
        }

        if (workHistory) {
            text_work.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.btn_fill_secondary));
            text_work.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if (roleInterest) {
            text_role.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.btn_fill_secondary));
            text_role.setTextColor(Color.parseColor("#FFFFFF"));
        }

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getProfileDetailFlag().equals("1") && model.getHourlyRateAndAvailFlag().equals("1")) {
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    dialog.dismiss();
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill required form.", Toast.LENGTH_SHORT).show();
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
                if (Utils.isNetworkAvailable(context)) {
                    hourlyRate();
                    dialog.dismiss();
                } else {
                    Utils.displayToast(context, getResources().getString(R.string.no_internet));
                }
            }
        });

        text_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(context)) {
                    dialog.dismiss();
                    workDialog();
                } else {
                    Utils.displayToast(context, getResources().getString(R.string.no_internet));
                }
            }
        });

        text_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(context)) {
                    dialog.dismiss();
                    roleDialog();
                } else {
                    Utils.displayToast(context, getResources().getString(R.string.no_internet));
                }
            }
        });

    }

    private void personalDialog() {
        personalDetailsBinding = DialogPersonalDetailsBinding.inflate(getLayoutInflater());// DataBindingUtil.inflate(getLayoutInflater(),R.layout.dialog_personal_details,null,false) ;getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_personal_details, null);
        Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(personalDetailsBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        TextView next = dialog.findViewById(R.id.next);

        if (model != null) {

            personalDetailsBinding.edFirstName.setText(model.getFirstName());
            personalDetailsBinding.edLastName.setText(model.getLastName());
            personalDetailsBinding.edEmail.setText(model.getEmail());
            personalDetailsBinding.edLicenseState.setText(model.getNursingLicenseState());
            personalDetailsBinding.edLicenseNos.setText(model.getNursingLicenseNumber());
            personalDetailsBinding.edPhone.setText(model.getMobile());

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    String edLicenseState = personalDetailsBinding.edLicenseState.getText().toString();
                    String edLicenseNos = personalDetailsBinding.edLicenseNos.getText().toString();
                    String edFirstName = personalDetailsBinding.edFirstName.getText().toString();
                    String edLastName = personalDetailsBinding.edLastName.getText().toString();
                    String email = personalDetailsBinding.edEmail.getText().toString();
                    String mobile = personalDetailsBinding.edPhone.getText().toString();
//                    model.setFirstName(edFirstName);
//                    model.setLastName(edLastName);
//                    model.setEmail(email);
//                    model.setMobile(mobile);
//                    model.setNursingLicenseState(edLicenseState);
//                    model.setNursingLicenseNumber(edLicenseNos);
                    if (Utils.isNetworkAvailable(context)) {
                        personalDialog2(edLicenseState, edLicenseNos, edFirstName, edLastName, email, mobile);
                        dialog.dismiss();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }

                }

            }

            private boolean checkValidation() {

                String edLicenseState = personalDetailsBinding.edLicenseState.getText().toString();
                String edLicenseNos = personalDetailsBinding.edLicenseNos.getText().toString();
                String edFirstName = personalDetailsBinding.edFirstName.getText().toString();
                String edLastName = personalDetailsBinding.edLastName.getText().toString();
                String email = personalDetailsBinding.edEmail.getText().toString();
                String mobile = personalDetailsBinding.edPhone.getText().toString();

                if (TextUtils.isEmpty(edFirstName)) {
                    Utils.displayToast(context, "Enter First Name !");
                    return false;
                }
                if (TextUtils.isEmpty(edLastName)) {
                    Utils.displayToast(context, "Enter Last Name !");
                    return false;
                }
                if (TextUtils.isEmpty(email)) {
                    Utils.displayToast(context, "Enter Email ID");
                    return false;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    return false;
                }

                if (TextUtils.isEmpty(mobile) ||
                        (mobile.contains("+") && (mobile.length() < 13 || mobile.length() > 13)) ||
                        (!mobile.contains("+") && (mobile.length() < 10 || mobile.length() > 10))) {
                    Utils.displayToast(context, "Enter Valid Mobile Nos");
                    return false;
                }

                if (TextUtils.isEmpty(edLicenseState)) {
                    Utils.displayToast(context, "Select Nurse License State First !");
                    return false;
                }
                if (TextUtils.isEmpty(edLicenseNos)) {
                    Utils.displayToast(context, "Select Nurse License Number First !");
                    return false;
                }


                return true;
            }


        });
        personalDetailsBinding.layProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
                    return;
                }

                if (list_photos != null && list_photos.size() > 3) {
                    Utils.displayToast(context, "Max 4 photos can be upload only !");
                    return;
                }
                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
                resultLauncherProfile.launch(intent);
                Utils.onClickEvent(v);
            }


        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();
    }


    private void personalDialog2(String edLicenseState, String edLicenseNos, String edFirstName, String edLastName, String email, String mobile) {

        DialogPersonalDetails2Binding binding_personalDetail_2 = DialogPersonalDetails2Binding.inflate(getLayoutInflater());//getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_personal_details_2, null);
        Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(binding_personalDetail_2.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        binding_personalDetail_2.edAddress.setText(model.getAddress());
        binding_personalDetail_2.edCity.setText(model.getCity());
        binding_personalDetail_2.edPostalCode.setText(model.getPostcode());
        binding_personalDetail_2.layProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (check_Any_Data_Empty_PersonalDetail_2()) {
            API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                @Override
                public void onShowProgress() {
                    binding_personalDetail_2.layProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucces(Object models) {
                    binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                    if (models instanceof Combine_PersonalDetail_2_DataModel) {
                        Combine_PersonalDetail_2_DataModel combineData = (Combine_PersonalDetail_2_DataModel) models;
                        try {
                            list_Specialty.addAll(combineData.specialtyModel.getData());
                            list_State.addAll(combineData.stateModel.getData());

                            fill_personal_2_field(combineData);
                        } catch (Exception e) {
                            Log.d("TAG", "personalDialog2 apicallback success() onError: " + e.getMessage());
                        }
                    }
                }

                private void fill_personal_2_field(Combine_PersonalDetail_2_DataModel combineData) {
                    setupSelection_Specialty_ByModelData(RegisterActivity.this.model.getSpecialty());

                    if (select_specialty != null && select_specialty.size() != 0) {
                        binding_personalDetail_2.rvSpecialty.setVisibility(View.VISIBLE);
                        binding_personalDetail_2.tvSpecialty.setVisibility(View.GONE);
                    } else {
                        binding_personalDetail_2.rvSpecialty.setVisibility(View.GONE);
                        binding_personalDetail_2.tvSpecialty.setVisibility(View.VISIBLE);
                    }
                    if (specialtyAdapter != null) {
                        specialtyAdapter.notifyDataSetChanged();
                    }
                    if (combineData.stateModel.getData() != null
                            && combineData.stateModel.getData().size() != 0) {
                        list_State.addAll(combineData.stateModel.getData());

                        if (!TextUtils.isEmpty(model.getState())) {
                            if (checkItemInList_State(model.getState(), list_State)) {
                                binding_personalDetail_2.tvState.setText(list_State.get(
                                        getIndexFromList_State(model.getState(), list_State)).getState());
                                selected_state = String.valueOf(getIndexFromList_State(model.getState(), list_State));
                            }
                        } else {
                            selected_state = null;
                        }
                    }
                }


                @Override
                public void onFailed() {
                    binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                }
            };

            fetch_PersonalDetail_2_Field_Data(apiResponseCallback);

        } else {
            setupSelection_Specialty_ByModelData(RegisterActivity.this.model.getSpecialty());
            if (select_specialty != null && select_specialty.size() != 0) {
                binding_personalDetail_2.rvSpecialty.setVisibility(View.VISIBLE);
                binding_personalDetail_2.tvSpecialty.setVisibility(View.GONE);
            } else {
                binding_personalDetail_2.rvSpecialty.setVisibility(View.GONE);
                binding_personalDetail_2.tvSpecialty.setVisibility(View.VISIBLE);
            }
            if (specialtyAdapter != null) {
                specialtyAdapter.notifyDataSetChanged();
            }
            if (list_State != null && list_State.size() != 0) {
                if (!TextUtils.isEmpty(model.getState())) {
                    if (checkItemInList_State(model.getState(), list_State)) {
                        binding_personalDetail_2.tvState.setText(list_State.get(
                                getIndexFromList_State(model.getState(), list_State)).getState());
                        selected_state = String.valueOf(getIndexFromList_State(model.getState(), list_State));
                    }
                } else {
                    selected_state = null;
                }
            }

        }

        specialtyAdapter = new SpecialtyAdapter(context, select_specialty, list_Specialty,
                new SpecialtyAdapter.SpecialtyListener() {
                    @Override
                    public void onClickItem(int position) {
                        if (select_specialty != null && select_specialty.size() != 0 && position < select_specialty.size()) {
                            select_specialty.remove(position);
                            specialtyAdapter.notifyDataSetChanged();
                            if (select_specialty == null || select_specialty.size() == 0) {
                                binding_personalDetail_2.tvSpecialty.setVisibility(View.VISIBLE);
                                binding_personalDetail_2.rvSpecialty.setVisibility(View.GONE);
                            } else {
                                binding_personalDetail_2.tvSpecialty.setVisibility(View.GONE);
                                binding_personalDetail_2.rvSpecialty.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
        binding_personalDetail_2.rvSpecialty.setAdapter(specialtyAdapter);
        binding_personalDetail_2.laySpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(context, v);

                showPopup_Speciality(context, binding_personalDetail_2.view1, 2,
                        binding_personalDetail_2.img1,
                        binding_personalDetail_2.tvSpecialty
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                if (select_specialty == null)
                                    select_specialty = new ArrayList<>();
                                if (select_specialty.contains(position)) {
                                    select_specialty.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    select_specialty.add(position);
                                binding_personalDetail_2.tvSpecialty.setVisibility(View.GONE);
                                binding_personalDetail_2.rvSpecialty.setVisibility(View.VISIBLE);
                                specialtyAdapter.notifyDataSetChanged();
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding_personalDetail_2.layState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);
                showOptionPopup_State(context, binding_personalDetail_2.view2, 6,
                        binding_personalDetail_2.img2, binding_personalDetail_2.tvState
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_state = "" + position;
                                binding_personalDetail_2.tvState
                                        .setText("" + list_State.get(position).getState());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });

        binding_personalDetail_2.next.setOnClickListener(new View.OnClickListener() {
            String address, city, country, postal_code, nos = "", state = "";

            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(context, v);
                if (checkValidation()) {
                    address = binding_personalDetail_2.edAddress.getText().toString();
                    city = binding_personalDetail_2.edCity.getText().toString();
                    country = binding_personalDetail_2.edCountry.getText().toString();
                    postal_code = binding_personalDetail_2.edPostalCode.getText().toString();
                    nos = "";
                    state = list_State.get(Integer.parseInt(selected_state)).getState();
                    for (int i = 0; i < select_specialty.size(); i++) {
                        if (i == 0) {
                            nos = "" + list_Specialty.get(select_specialty.get(i)).getId();
                        } else
                            nos = nos + "," + list_Specialty.get(select_specialty.get(i)).getId();
                    }
                    if (Utils.isNetworkAvailable(context)) {
                        call_sendData_For_PersonalDetail();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }
                }

            }

            private void call_sendData_For_PersonalDetail() {
                RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(context).get_user_register_Id());
                RequestBody request_firstName = RequestBody.create(MediaType.parse("multipart/form-data"),
                        edFirstName);
                RequestBody request_lastName = RequestBody.create(MediaType.parse("multipart/form-data"),
                        edLastName);
                RequestBody request_lastName1 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        email);
                RequestBody request_lastName11 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        mobile);
                RequestBody request_state = RequestBody.create(MediaType.parse("multipart/form-data"),
                        edLicenseState);
                RequestBody request_nos = RequestBody.create(MediaType.parse("multipart/form-data"),
                        edLicenseNos);
                RequestBody request_spec = RequestBody.create(MediaType.parse("multipart/form-data"),
                        nos);
                RequestBody request_spec1 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        address);
                RequestBody request_spec11 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        city);
                RequestBody request_spec111 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        state);
                RequestBody request_spec121 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        postal_code);
                RequestBody request_spec1111 = RequestBody.create(MediaType.parse("multipart/form-data"),
                        country);
                binding_personalDetail_2.layProgress.setVisibility(View.VISIBLE);

                backendApi.call_PersonalDetail(request_id, request_firstName, request_lastName,
                        request_lastName1, request_lastName11, request_state, request_nos,
                        request_spec, request_spec1, request_spec11, request_spec111,
                        request_spec121, request_spec1111).enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            binding_personalDetail_2.layProgress.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            Utils.displayToast(context, "" + response.body().getMessage());
                            return;
                        }

                        if (response.isSuccessful()) {
//                            progressDialog.dismiss();
                            binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                            UserProfile userProfile = response.body();
                            new SessionManager(context).save_user(userProfile.getData());
                            model = userProfile.getData();
                            Utils.displayToast(context, userProfile.getMessage().toString());
                            mRegistrationStep++;
                            personalDetails = true;
                            dialog.dismiss();
                            mRegistrationStep++;
                            personalDetails = true;
                            if (model.getHourlyRateAndAvailFlag().equals("0")) {
                                chooseOption();
                            } else {
                                move_TO_Home_Screen();
                            }
                        } else {

//                            progressDialog.dismiss();
                            binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                        }
                        binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                    /*    if (progressDialog.isShowing())
                            progressDialog.dismiss();*/
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
//                        progressDialog.dismiss();
                        binding_personalDetail_2.layProgress.setVisibility(View.GONE);
                        Log.d("TAG", "call_sendData_For_PersonalDetail() onFailure: " + t.getMessage());
                    }
                });
            }

            private boolean checkValidation() {
                if (select_specialty == null || select_specialty.size() == 0) {
                    Utils.displayToast(context, "Select Specialty First !");
                    return false;
                }
                String address = binding_personalDetail_2.edAddress.getText().toString();
                String city = binding_personalDetail_2.edCity.getText().toString();
                String country = binding_personalDetail_2.edCountry.getText().toString();
                String postal_code = binding_personalDetail_2.edPostalCode.getText().toString();

                if (TextUtils.isEmpty(address)) {
                    Utils.displayToast(context, "Enter Address First");
                    return false;
                }
                if (TextUtils.isEmpty(city)) {
                    Utils.displayToast(context, "Enter City First");
                    return false;
                }
                if (TextUtils.isEmpty(selected_state)) {
                    Utils.displayToast(context, "Select State First");
                    return false;
                }

                if (TextUtils.isEmpty(postal_code)) {
                    Utils.displayToast(context, "Enter Postal Code First");
                    return false;
                }
                if (TextUtils.isEmpty(country)) {
                    Utils.displayToast(context, "Enter Country First");
                    return false;
                }

                return true;
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();

    }

    private void hourlyRate() {
        final View loc = getLayoutInflater().from(RegisterActivity.this).inflate(R.layout.dialog_horly_rate, null);
        Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        LinearLayout layProgress = dialog.findViewById(R.id.layProgress);
        TextView tvProgress = dialog.findViewById(R.id.tvProgress);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        SeekBar distanceSlider = dialog.findViewById(R.id.distanceSlider);
        TextView tv_rate = dialog.findViewById(R.id.tv_rate);
        TextView spinner_shift_duration = dialog.findViewById(R.id.spinner_shift_duration);
        TextView spinner_assignment = dialog.findViewById(R.id.spinner_assignment);
        TextView spinner_preferred_shift = dialog.findViewById(R.id.spinner_preferred_shift);
        TextView tv_weeks_days = dialog.findViewById(R.id.tv_weeks_days);
        TextView spinner_geography = dialog.findViewById(R.id.spinner_geography);
        TextView tv_date = dialog.findViewById(R.id.tv_date);
        View lay_weeks = dialog.findViewById(R.id.lay_weeks);
        View lay_shift = dialog.findViewById(R.id.lay_shift);
        View lay_assignment = dialog.findViewById(R.id.lay_assignment);
        View lay_preferred = dialog.findViewById(R.id.lay_preffered_shift);
        View lay_geo = dialog.findViewById(R.id.lay_geo);
        View View1 = dialog.findViewById(R.id.view1);
        View View2 = dialog.findViewById(R.id.view2);
        View View3 = dialog.findViewById(R.id.view3);
        View View4 = dialog.findViewById(R.id.view4);
        View View5 = dialog.findViewById(R.id.view5);
        ImageView img1 = dialog.findViewById(R.id.img1);
        ImageView img2 = dialog.findViewById(R.id.img2);
        ImageView img3 = dialog.findViewById(R.id.img3);
        ImageView img4 = dialog.findViewById(R.id.img4);
        ImageView img5 = dialog.findViewById(R.id.img5);
        ImageView imgDate = dialog.findViewById(R.id.imgDate);
        RecyclerView rv_weeks_days = dialog.findViewById(R.id.rv_weeks_days);
        TextView next = dialog.findViewById(R.id.next);
        layProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        {
            distanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tv_rate.setText("$ " + progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            distanceSlider.setProgress(1);
            daysOfWeekAdapter = new SpecialtyAdapter(context, select_daysOfWeek, list_days_of_week, 3, 3, new SpecialtyAdapter.SpecialtyListener() {
                @Override
                public void onClickItem(int position) {
                    if (select_daysOfWeek != null && select_daysOfWeek.size() != 0 && position < select_daysOfWeek.size()) {
                        select_daysOfWeek.remove(position);
                        if (daysOfWeekAdapter != null)
                            daysOfWeekAdapter.notifyDataSetChanged();
                        if (tv_weeks_days != null && rv_weeks_days != null)
                            if (select_daysOfWeek == null || select_daysOfWeek.size() == 0) {
                                tv_weeks_days.setVisibility(View.VISIBLE);
                                rv_weeks_days.setVisibility(View.GONE);
                            } else {
                                tv_weeks_days.setVisibility(View.GONE);
                                rv_weeks_days.setVisibility(View.VISIBLE);
                            }
                    }
                }
            });
            rv_weeks_days.setAdapter(daysOfWeekAdapter);
            next.setOnClickListener(new View.OnClickListener() {
                String hourlyRates_str = "", shift_D_str = "", assign_D_str = "", preferred_S_str = "", work_loc = "", days_str = "", date_str = "";
                List<String> listDays = new ArrayList<>();

                @Override
                public void onClick(View v) {
                    if (checkValidation()) {
                        hourlyRates_str = String.valueOf(distanceSlider.getProgress());
                        shift_D_str = String.valueOf(list_shift_durations.get
                                (Integer.parseInt(selected_shift_duration)).getId());
                        assign_D_str = String.valueOf(list_assignment_durations
                                .get(Integer.parseInt(selected_assignment_duration)).getId());
                        preferred_S_str = String.valueOf(list_preferred_shift
                                .get(Integer.parseInt(selected_preferred_shift)).getId());
                        work_loc = list_geography.get(Integer.parseInt(selected_geography)).getId().toString();

                        for (int i = 0; i < select_daysOfWeek.size(); i++) {
                            listDays.add(list_days_of_week.get(select_daysOfWeek.get(i)).getName());
                            if (i == 0) {
                                days_str = list_days_of_week.get(select_daysOfWeek.get(i)).getName();
                            } else
                                days_str = days_str + "," + list_days_of_week.get(select_daysOfWeek.get(i)).getName();
                        }
                        date_str = dayOfMonth1 + "-" + (monthOfYear1) + "-" + year1;

                        if (Utils.isNetworkAvailable(context)) {
                            performHourlyRateSubmitProcess();
                        } else {
                            Utils.displayToast(context, getResources().getString(R.string.no_internet));
                        }

                    }


                }

                private void performHourlyRateSubmitProcess() {

//                    progressDialog = new ProgressDialog(context);
//                    progressDialog.setCancelable(false);
                    layProgress.setVisibility(View.VISIBLE);
                    RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                    RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(context).get_user_register_Id());
                    RequestBody hourly_pay_rate = RequestBody.create(MediaType.parse("multipart/form-data"), hourlyRates_str);
                    RequestBody shift_duration = RequestBody.create(MediaType.parse("multipart/form-data"), shift_D_str);
                    RequestBody assignment_duration = RequestBody.create(MediaType.parse("multipart/form-data"), assign_D_str);
                    RequestBody preferred_shift = RequestBody.create(MediaType.parse("multipart/form-data"), preferred_S_str);
                    RequestBody days_of_the_week = RequestBody.create(MediaType.parse("multipart/form-data"), days_str);
                    RequestBody work_location = RequestBody.create(MediaType.parse("multipart/form-data"), work_loc);
                    RequestBody earliest_start_date = RequestBody.create(MediaType.parse("multipart/form-data"), date_str);


                    backendApi.call_hourly_rates_availability(request_id, hourly_pay_rate, shift_duration, assignment_duration, preferred_shift
                            , days_of_the_week, work_location, earliest_start_date)
                            .enqueue(new Callback<UserProfile>() {
                                @Override
                                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                                    assert response.body() != null;
                                    if (!response.body().getApiStatus().equals("1")) {
                                        Utils.displayToast(context, response.message());
                                        layProgress.setVisibility(View.GONE);
                                        Utils.displayToast(context, "" + response.body().getMessage());
                                        return;
                                    }

                                    if (response.isSuccessful()) {
                                        layProgress.setVisibility(View.GONE);
                                        UserProfile SignupModel = response.body();
                                        Utils.displayToast(context, SignupModel.getMessage());
                                        RegisterActivity.this.model = SignupModel.getData();
                                        new SessionManager(context).save_user(model);
                                        hourlyRate = true;
                                        mRegistrationStep++;
                                        if (state == 0) {
                                            chooseOption();
                                        } else {
                                            finish();
                                        }
                                        dialog.dismiss();
                                    } else {
                                        Utils.displayToast(context, "Data has not been updated");
                                        layProgress.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onFailure(Call<UserProfile> call, Throwable t) {
//                                    progressDialog.dismiss();
                                    Log.d("TAG", "performHourlyRateSubmitProcess() onFailure: " + t.getMessage());
                                    Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                    layProgress.setVisibility(View.GONE);
                                }
                            });

                }

                private boolean checkValidation() {

                    if (distanceSlider.getProgress() == 0) {
                        Utils.displayToast(context, "Please, Select Proper Rate First !");
                        return false;
                    }
                    if (TextUtils.isEmpty(selected_shift_duration)) {
                        Utils.displayToast(context, "Please, Select Shift Duration First !");
                        return false;
                    }
                    if (TextUtils.isEmpty(selected_assignment_duration)) {
                        Utils.displayToast(context, "Please, Select Assignment Duration First !");
                        return false;
                    }
                    if (TextUtils.isEmpty(selected_preferred_shift)) {
                        Utils.displayToast(context, "Please, Select Preferred Shift First !");
                        return false;
                    }
                    if (select_daysOfWeek == null || select_daysOfWeek.size() == 0) {
                        Utils.displayToast(context, "Please, Select Preferred Day Of The Week First !");
                        return false;
                    }
                    if (TextUtils.isEmpty(selected_geography)) {
                        Utils.displayToast(context, "Please, Select Preferred Geography First !");
                        return false;
                    }
                    return true;
                }
            });
            lay_weeks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.hideKeyboardFrom(context, v);
                    if (list_days_of_week == null || list_days_of_week.size() == 0) {
                        Utils.displayToast(context, "data empty");
                        return;
                    }
                    showOptionPopup_Weeks(context, 4, View4, img2, tv_weeks_days,
                            list_days_of_week, select_daysOfWeek
                            , new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    if (select_daysOfWeek == null)
                                        select_daysOfWeek = new ArrayList<>();
                                    if (position < select_daysOfWeek.size() && select_daysOfWeek.contains(position)) {
                                        select_daysOfWeek.remove(Integer.parseInt(String.valueOf(position)));
                                    } else
                                        select_daysOfWeek.add(position);
                                    tv_weeks_days.setVisibility(View.GONE);
                                    rv_weeks_days.setVisibility(View.VISIBLE);
                                    daysOfWeekAdapter.notifyDataSetChanged();
                                }
                            });

                    Utils.onClickEvent(v);
                }

            });
            lay_shift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utils.hideKeyboardFrom(context, v);
                    if (list_shift_durations == null || list_shift_durations.size() == 0) {
                        Utils.displayToast(context, "data empty");
                        return;
                    }

                    showOptionPopup(context, 1, View1, img1, spinner_shift_duration,
                            list_shift_durations,
                            null, selected_shift_duration, new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    selected_shift_duration = String.valueOf(position);
                                    spinner_shift_duration.setText("" + list_shift_durations.get(position).getName());
                                }
                            });

                    Utils.onClickEvent(v);
                }

            });
            lay_assignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utils.hideKeyboardFrom(context, v);
                    if (list_assignment_durations == null || list_assignment_durations.size() == 0) {
                        Utils.displayToast(context, "data empty");
                        return;
                    }
                    showOptionPopup(context, 2, View2, img2, spinner_assignment, list_assignment_durations,
                            null, selected_assignment_duration, new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    selected_assignment_duration = String.valueOf(position);
                                    spinner_assignment.setText("" + list_assignment_durations.get(position).getName());
                                }
                            });

                    Utils.onClickEvent(v);
                }

            });
            lay_preferred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utils.hideKeyboardFrom(context, v);
                    if (list_preferred_shift == null || list_preferred_shift.size() == 0) {
                        Utils.displayToast(context, "data empty");
                        return;
                    }
                    showOptionPopup(context, 3, View3, img3, spinner_preferred_shift, list_preferred_shift,
                            null, selected_preferred_shift, new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    selected_preferred_shift = String.valueOf(position);
                                    spinner_preferred_shift.setText("" + list_preferred_shift.get(position).getName());
                                }
                            });

                    Utils.onClickEvent(v);
                }

            });
            lay_geo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utils.hideKeyboardFrom(context, v);
                    if (list_geography == null || list_geography.size() == 0) {
                        Utils.displayToast(context, "data empty");
                        return;
                    }
                    showOptionPopup(context, 5, View5, img5, spinner_geography, null, list_geography,
                            selected_geography, new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    selected_geography = String.valueOf(position);
                                    spinner_geography.setText("" + list_geography.get(position).getName());
                                }
                            });

                    Utils.onClickEvent(v);
                }

            });
            imgDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utils.hideKeyboardFrom(context, v);

                    final Calendar c = Calendar.getInstance();

                    DatePickerDialog dpd = null;
                    if (dayOfMonth1 == 0 && year1 == 0 && dayOfMonth1 == 0) {
                        dpd = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        dayOfMonth1 = dayOfMonth;
                                        monthOfYear1 = monthOfYear;
                                        year1 = year;
                                    }
                                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

                    } else {
                        dpd = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        dayOfMonth1 = dayOfMonth;
                                        monthOfYear1 = monthOfYear;
                                        year1 = year;
                                    }
                                }, year1, monthOfYear1, dayOfMonth1);
                    }
                    dpd.show();

                    Utils.onClickEvent(v);
                }

            });
        }

        if (check_Any_Data_Empty_HourlyRate()) {
            API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                @Override
                public void onShowProgress() {
                    layProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucces(Object models) {
                    layProgress.setVisibility(View.GONE);
                    if (models instanceof Combine_HourlyRate_DataModel) {
                        Combine_HourlyRate_DataModel combineData = (Combine_HourlyRate_DataModel) models;
                        try {
                            clearAllOptionList();
                            list_preferred_shift.addAll(combineData.getPreferredShift().getData());
                            list_assignment_durations.addAll(combineData.getAssignmentDuration().getData());
                            list_shift_durations.addAll(combineData.getShiftDuration().getData());
                            list_days_of_week.addAll(combineData.getPreferredDayOfWeek().getData());
                            list_geography.addAll(combineData.getWorkLocationModel().getData());
                            setupDialogFieldData();

                        } catch (Exception e) {
                            Log.d("TAG", "getHourlyRate_OptionsData() catch(): " + e.getMessage());
                        }
                    }
                }

                void setupDialogFieldData() {
                    if (!TextUtils.isEmpty(model.getHourlyPayRate())) {
                        distanceSlider.setProgress(Integer.parseInt(model.getHourlyPayRate()));
                    }
                    if (!TextUtils.isEmpty(model.getHourlyPayRate())) {
                        distanceSlider.setProgress(Integer.parseInt(model.getHourlyPayRate()));
                    }
                    if (!TextUtils.isEmpty(model.getShiftDuration())) {
//                selected_shift_duration = String.valueOf(model.getShiftDuration());
                        if (checkItemInList(model.getShiftDuration(), list_shift_durations)) {
                            spinner_shift_duration.setText(list_shift_durations.get(
                                    getIndexFromList(model.getShiftDuration(), list_shift_durations)).getName());
                            selected_shift_duration = String.valueOf(getIndexFromList(model.getShiftDuration(), list_shift_durations));

                        }
                    }
                    if (!TextUtils.isEmpty(model.getAssignmentDuration())) {
//                selected_assignment_duration = String.valueOf(model.getAssignmentDuration());
                        if (checkItemInList(model.getAssignmentDuration(), list_assignment_durations)) {
                            spinner_assignment.setText(list_assignment_durations.get(
                                    getIndexFromList(model.getAssignmentDuration(), list_assignment_durations)).getName());
                            selected_assignment_duration = String.valueOf(getIndexFromList(model.getAssignmentDuration()
                                    , list_assignment_durations));

                        }
                    }
                    if (!TextUtils.isEmpty(model.getPreferredShift())) {
//                selected_preferred_shift = String.valueOf(model.getPreferredShift());
                        if (checkItemInList(model.getPreferredShift(), list_preferred_shift)) {
                            spinner_preferred_shift.setText(list_preferred_shift.get(
                                    getIndexFromList(model.getPreferredShift(), list_preferred_shift)).getName());
                            selected_preferred_shift = String.valueOf(getIndexFromList(model.getPreferredShift()
                                    , list_preferred_shift));
                        }
                    }
                    setupSelection_DaysOfWeeks_ByModelData(model.getDaysOfTheWeek());
                    daysOfWeekAdapter.notifyDataSetChanged();
                    if (select_daysOfWeek != null && select_daysOfWeek.size() != 0) {
                        tv_weeks_days.setVisibility(View.GONE);
                        rv_weeks_days.setVisibility(View.VISIBLE);
                    } else {
                        tv_weeks_days.setVisibility(View.VISIBLE);
                        rv_weeks_days.setVisibility(View.GONE);
                    }
                    if (model.getWorkLocation() != 0) {
//                selected_geography = String.valueOf(model.getWorkLocation());
                        if (checkItemInList(model.getWorkLocation(), list_geography)) {
                            spinner_geography.setText(list_geography.get(
                                    getIndexFromList(model.getWorkLocation(), list_geography)).getName());
                            selected_geography = String.valueOf(getIndexFromList(model.getWorkLocation()
                                    , list_geography));
                        }
                    }
                    if (!TextUtils.isEmpty(model.getEarliestStartDate())) {

                        try {
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Date currentTime = inputFormat.parse(model.getEarliestStartDate());
                            String formattedDate = simpleDateFormat.format(currentTime);
                            DateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
                            year1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                            formatter = new SimpleDateFormat("MM", Locale.getDefault());
                            monthOfYear1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                            formatter = new SimpleDateFormat("dd", Locale.getDefault());
                            dayOfMonth1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                            tv_date.setText("" + formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailed() {
                    layProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    layProgress.setVisibility(View.GONE);
                }
            };
            fetch_HourlyRate_OptionsField_Data(apiResponseCallback);
        } else {
            if (!TextUtils.isEmpty(model.getHourlyPayRate())) {
                distanceSlider.setProgress(Integer.parseInt(model.getHourlyPayRate()));
            }
            if (!TextUtils.isEmpty(model.getHourlyPayRate())) {
                distanceSlider.setProgress(Integer.parseInt(model.getHourlyPayRate()));
            }
            if (!TextUtils.isEmpty(model.getShiftDuration())) {
//                selected_shift_duration = String.valueOf(model.getShiftDuration());
                if (checkItemInList(model.getShiftDuration(), list_shift_durations)) {
                    spinner_shift_duration.setText(list_shift_durations.get(
                            getIndexFromList(model.getShiftDuration(), list_shift_durations)).getName());
                    selected_shift_duration = String.valueOf(getIndexFromList(model.getShiftDuration(), list_shift_durations));

                }
            }
            if (!TextUtils.isEmpty(model.getAssignmentDuration())) {
//                selected_assignment_duration = String.valueOf(model.getAssignmentDuration());
                if (checkItemInList(model.getAssignmentDuration(), list_assignment_durations)) {
                    spinner_assignment.setText(list_assignment_durations.get(
                            getIndexFromList(model.getAssignmentDuration(), list_assignment_durations)).getName());
                    selected_assignment_duration = String.valueOf(getIndexFromList(model.getAssignmentDuration()
                            , list_assignment_durations));

                }
            }
            if (!TextUtils.isEmpty(model.getPreferredShift())) {
//                selected_preferred_shift = String.valueOf(model.getPreferredShift());
                if (checkItemInList(model.getPreferredShift(), list_preferred_shift)) {
                    spinner_preferred_shift.setText(list_preferred_shift.get(
                            getIndexFromList(model.getPreferredShift(), list_preferred_shift)).getName());
                    selected_preferred_shift = String.valueOf(getIndexFromList(model.getPreferredShift()
                            , list_preferred_shift));
                }
            }
            setupSelection_DaysOfWeeks_ByModelData(model.getDaysOfTheWeek());
            daysOfWeekAdapter.notifyDataSetChanged();
            tv_weeks_days.setVisibility(View.GONE);
            rv_weeks_days.setVisibility(View.VISIBLE);
            if (model.getWorkLocation() != 0) {
//                selected_geography = String.valueOf(model.getWorkLocation());
                if (checkItemInList(model.getWorkLocation(), list_geography)) {
                    spinner_geography.setText(list_geography.get(
                            getIndexFromList(model.getWorkLocation(), list_geography)).getName());
                    selected_geography = String.valueOf(getIndexFromList(model.getWorkLocation()
                            , list_geography));
                }
            }
            if (!TextUtils.isEmpty(model.getEarliestStartDate())) {

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date currentTime = inputFormat.parse(model.getEarliestStartDate());
                    String formattedDate = simpleDateFormat.format(currentTime);
                    DateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
                    year1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                    formatter = new SimpleDateFormat("MM", Locale.getDefault());
                    monthOfYear1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                    formatter = new SimpleDateFormat("dd", Locale.getDefault());
                    dayOfMonth1 = Integer.parseInt(formatter.format(currentTime.getTime()));
                    tv_date.setText("" + formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        }
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();
    }

    private void workDialog() {
        DialogWorkHistoryBinding workHistoryBinding = DialogWorkHistoryBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(workHistoryBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        if (check_Any_Data_Empty_WorkHistory()) {
            API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                @Override
                public void onShowProgress() {
                    workHistoryBinding.layProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucces(Object models) {
                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                    if (models instanceof Combine_WorkHistory_DataModel) {
                        Combine_WorkHistory_DataModel combineData = (Combine_WorkHistory_DataModel) models;
                        try {
                            clearAllOptionList();
                            list_nurse_degrees.addAll(combineData.getDegreeModel().getData());
                            list_experience.addAll(combineData.getCernersModel().getData());
                            setupDialogFieldData();

                        } catch (Exception e) {
                            Log.d("TAG", "getHourlyRate_OptionsData() catch(): " + e.getMessage());
                        }
                    }
                }

                void clearAllOptionList() {
                    if (list_nurse_degrees == null) {
                        list_nurse_degrees = new ArrayList<>();
                    } else if (list_nurse_degrees != null || list_nurse_degrees.size() != 0) {
                        list_nurse_degrees.clear();
                    }
                    if (list_experience == null) {
                        list_experience = new ArrayList<>();
                    } else if (list_experience != null || list_experience.size() != 0) {
                        list_experience.clear();
                    }
                    list_nurse_degrees.add(new Degree_Datum(0, "Select Highest Degree"));
                    list_experience.add(new CernersDatum(0, "Select Experience"));
                }

                void setupDialogFieldData() {
//                    if(TextUtils.isEmpty(model.get))

                }

                @Override
                public void onFailed() {
                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                }
            };

            fetch_WorkHistory_Field_Data(apiResponseCallback);

        } else {

        }
        workHistoryBinding.layNurseDegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                showOptionPopup_Degrees(context, 1, workHistoryBinding.view1, workHistoryBinding.img1,
                        Collections.singletonList(list_nurse_degrees),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_nurse_degree = position;
                                if (position == 0) {
                                    workHistoryBinding.tvSearchCredential.setText("");
                                } else
                                    workHistoryBinding.tvSearchCredential.setText("" + list_nurse_degrees.get(position).getName());
                            }
                        });
            }
        });
        workHistoryBinding.layCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                showOptionPopup_Degrees(context, 2, workHistoryBinding.view2, workHistoryBinding.img2,
                        Collections.singletonList(list_experience),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_nurse_cerner = position;
                                if (position == 0) {
                                    workHistoryBinding.tvCenter.setText("");
                                } else
                                    workHistoryBinding.tvCenter.setText("" + list_experience.get(position).getName());
                            }
                        });
            }
        });
        workHistoryBinding.layMeditech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                showOptionPopup_Degrees(context, 3, workHistoryBinding.view3, workHistoryBinding.img3,
                        Collections.singletonList(list_experience),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_nurse_meditech = position;
                                if (position == 0) {
                                    workHistoryBinding.tvMeditech.setText("");
                                } else
                                    workHistoryBinding.tvMeditech.setText("" + list_experience.get(position).getName());
                            }
                        });
            }
        });
        workHistoryBinding.layEpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup_Degrees(context, 4, workHistoryBinding.view4, workHistoryBinding.img4,
                        Collections.singletonList(list_experience),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_nurse_epic = position;
                                if (position == 0) {
                                    workHistoryBinding.tvEpic.setText("");
                                } else
                                    workHistoryBinding.tvEpic.setText("" + list_experience.get(position).getName());
                            }
                        });
            }
        });
        workHistoryBinding.layProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        workHistoryBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (checkValiation()) {
//                    workDialog2();
//                    dialog.dismiss();

                    if (Utils.isNetworkAvailable(context)) {
                        send_WorkHistoryDate_1();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }
                }
            }

            private void send_WorkHistoryDate_1() {
                workHistoryBinding.layProgress.setVisibility(View.VISIBLE);
                RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                RequestBody request_id
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        new SessionManager(context).get_user_register_Id());
                RequestBody highest_nursing_degree
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        list_nurse_degrees.get(selected_nurse_degree).getId().toString());
                RequestBody college_uni_name
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        workHistoryBinding.edColleageName.getText().toString());
                RequestBody college_uni_city
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        workHistoryBinding.edCity.getText().toString());
                RequestBody college_uni_state
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        "AK");
                RequestBody college_uni_country
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        workHistoryBinding.edCountry.getText().toString());
                RequestBody experience_as_acute_care_facility
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        workHistoryBinding.edFacilityExp.getText().toString());
                RequestBody experience_as_ambulatory_care_facility =
                        RequestBody.create(MediaType.parse("multipart/form-data"),
                                workHistoryBinding.edNursingExp.getText().toString());

                RequestBody ehr_proficiency_cerner
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        list_experience.get(selected_nurse_cerner).getId().toString());
                RequestBody ehr_proficiency_meditech
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        list_experience.get(selected_nurse_meditech).getId().toString());
                RequestBody ehr_proficiency_epic
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        list_experience.get(selected_nurse_epic).getId().toString());
                RequestBody ehr_proficiency_other
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        workHistoryBinding.edOther.getText().toString());


                backendApi.call_send_WorkHistory_Experience(
                        highest_nursing_degree, college_uni_name, college_uni_city, college_uni_state, college_uni_country,
                        experience_as_acute_care_facility, experience_as_ambulatory_care_facility,
                        ehr_proficiency_cerner, ehr_proficiency_meditech, ehr_proficiency_epic,
                        ehr_proficiency_other, request_id)
                        .enqueue(new Callback<WorkHistorysModel>() {
                            @Override
                            public void onResponse(Call<WorkHistorysModel> call, Response<WorkHistorysModel> response) {
                                if (response.body() == null) {
                                    try {
                                        workHistoryBinding.layProgress.setVisibility(View.GONE);
                                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (!response.body().getApiStatus().equals("1")) {
                                    Utils.displayToast(context, response.message());
                                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                                    Utils.displayToast(context, "" + response.body().getMessage());
                                    return;
                                }
                                if (response.isSuccessful()) {
                                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                                    workDialog2();
                                    dialog.dismiss();
                                } else {
                                    Utils.displayToast(context, "Data has not been updated");
                                    workHistoryBinding.layProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<WorkHistorysModel> call, Throwable t) {
                                Log.d("TAG", "send_WorkHistoryDate_1() onFailure: " + t.getMessage());
                                Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                workHistoryBinding.layProgress.setVisibility(View.GONE);
                            }
                        });
            }

            private boolean checkValiation() {
                if (selected_nurse_degree == 0) {
                    Utils.displayToast(context, "Select Highest Nursing Degree");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edColleageName.getText())) {
                    Utils.displayToast(context, "Enter College / University Name");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edCity.getText())) {
                    Utils.displayToast(context, "Enter City Name");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edState.getText())) {
                    Utils.displayToast(context, "Enter State Name");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edCountry.getText())) {
                    Utils.displayToast(context, "Enter Country Name");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edFacilityExp.getText())) {
                    Utils.displayToast(context, "Select Highest Nursing Degree");
                    return false;
                }
                if (TextUtils.isEmpty(workHistoryBinding.edNursingExp.getText())) {
                    Utils.displayToast(context, "Select Highest Nursing Degree");
                    return false;
                }
                if (selected_nurse_cerner == 0) {
                    Utils.displayToast(context, "Select Cerner");
                    return false;
                }
                if (selected_nurse_meditech == 0) {
                    Utils.displayToast(context, "Select Meditech");
                    return false;
                }
                if (selected_nurse_epic == 0) {
                    Utils.displayToast(context, "Select Epic");
                    return false;
                }
                return true;
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();
    }


    private void workDialog2() {
        uploadCertificate = null;
        resumeFile = null;
        selected_Credential = 0;
        history2Binding = DialogWorkHistory2Binding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(history2Binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        if (check_Any_Data_Empty_WorkHistory_2()) {
            API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                @Override
                public void onShowProgress() {
                    history2Binding.layProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucces(Object models) {
                    history2Binding.layProgress.setVisibility(View.GONE);
                    if (models instanceof CredentialModel) {
                        CredentialModel combineData = (CredentialModel) models;
                        try {
                            clearAllOptionList();
                            list_Credential.addAll(combineData.getData());
                            setupDialogFieldData();

                        } catch (Exception e) {
                            Log.d("TAG", "getHourlyRate_OptionsData() catch(): " + e.getMessage());
                        }
                    }
                }

                void clearAllOptionList() {
                    if (list_Credential == null) {
                        list_Credential = new ArrayList<>();
                    }
                    list_Credential.add(new CredentialDatum(0, "Select Credential"));
                }

                void setupDialogFieldData() {
//                    if(TextUtils.isEmpty(model.get))

                }

                @Override
                public void onFailed() {
                    history2Binding.layProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    history2Binding.layProgress.setVisibility(View.GONE);
                }
            };

            fetch_WorkHistory_2_Field_Data(apiResponseCallback);

        }
        else {

        }
        history2Binding.layCredential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                showOptionPopup_Degrees(context, 5, history2Binding.view1, history2Binding.img1,
                        Collections.singletonList(list_Credential),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_Credential = position;
                                if (position == 0) {
                                    history2Binding.tvSearchCredential.setText("");
                                } else
                                    history2Binding.tvSearchCredential.setText("" + list_Credential
                                            .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        history2Binding.immgDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                DatePickerDialog dpd = null;

                dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                history2Binding.edEffectiveDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                dayOfMonth1 = dayOfMonth;
//                                monthOfYear1 = monthOfYear;
//                                year1 = year;
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)
                        , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dpd.show();
                Utils.onClickEvent(v);

            }
        });
        history2Binding.immgDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                DatePickerDialog dpd = null;

                dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                history2Binding.edExpiredDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                dayOfMonth1 = dayOfMonth;
//                                monthOfYear1 = monthOfYear;
//                                year1 = year;
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)
                        , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dpd.show();
                Utils.onClickEvent(v);

            }
        });
        history2Binding.layUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                    return;
                }
                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
                resultLauncherCertificate.launch(intent);
                Utils.onClickEvent(v);
            }


        });
        history2Binding.layUploadResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    });
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf"});
                resultLauncherResume.launch(intent);
                Utils.onClickEvent(v);
            }


        });
        history2Binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (checkValiation()) {
                    if (Utils.isNetworkAvailable(context)) {
                        send_WorkHistoryDate_2();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }
                }
            }

            private void send_WorkHistoryDate_2() {
                history2Binding.layProgress.setVisibility(View.VISIBLE);
                RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                RequestBody request_id
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        new SessionManager(context).get_user_register_Id());
                RequestBody type_sd
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        list_Credential.get(selected_Credential).getId().toString());
                RequestBody date1
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        history2Binding.edEffectiveDate.getText().toString());
                RequestBody date2
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        history2Binding.edExpiredDate.getText().toString());
                RequestBody certificate_img
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        uploadCertificate);
                RequestBody resume_file
                        = RequestBody.create(MediaType.parse("multipart/form-data"),
                        resumeFile);


                MultipartBody.Part multipartBodyCertifcate = MultipartBody.Part.createFormData("file", new File(uploadCertificate).getName(), certificate_img);
                MultipartBody.Part multipartBodyResume = MultipartBody.Part.createFormData("file", new File(resumeFile).getName(), resume_file);


                backendApi.call_send_WorkHistory_Certificate(request_id, type_sd, date1, date2,
                        multipartBodyCertifcate, multipartBodyResume)
                        .enqueue(new Callback<AddCredentialModel>() {
                            @Override
                            public void onResponse(Call<AddCredentialModel> call, Response<AddCredentialModel> response) {
                                if (response.body() == null) {
                                    try {
                                        history2Binding.layProgress.setVisibility(View.GONE);
                                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (!response.body().getApiStatus().equals("1")) {
                                    Utils.displayToast(context, null);
                                    Utils.displayToast(context, response.message());
                                    history2Binding.layProgress.setVisibility(View.GONE);
                                    Utils.displayToast(context, "" + response.body().getMessage());
                                    history2Binding.layProgress.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    chooseOption();
                                } else {
                                    Utils.displayToast(context, "Data has not been updated");
                                    history2Binding.layProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<AddCredentialModel> call, Throwable t) {
                                Log.d("TAG", "send_WorkHistoryDate_1() onFailure: " + t.getMessage());
                                Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                history2Binding.layProgress.setVisibility(View.GONE);
                            }
                        });

            }

            private boolean checkValiation() {
                if (selected_Credential == 0) {
                    Utils.displayToast(context, "Select Highest Nursing Degree");
                    return false;
                }
                if (TextUtils.isEmpty(history2Binding.edEffectiveDate.getText())) {
                    Utils.displayToast(context, "Select Effective Date");
                    return false;
                }
                if (TextUtils.isEmpty(history2Binding.edExpiredDate.getText())) {
                    Utils.displayToast(context, "Select Expiration Date ");
                    return false;
                }
                if (TextUtils.isEmpty(uploadCertificate)) {
                    Utils.displayToast(context, "Upload Certificate First !");
                    return false;
                }
                if (TextUtils.isEmpty(resumeFile)) {
                    Utils.displayToast(context, "Upload Resume First !");
                    return false;
                }
                return true;
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();
    }

    private void roleDialog() {
        DialogRoleInterestBinding roleInterestBinding = DialogRoleInterestBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(roleInterestBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        list_Ques = new ArrayList<>();
        list_Ques.addAll(Utils.getQuesList());
        QuestionAdapter questionAdapter = new QuestionAdapter(this, list_Ques, list_LeaderShipRole,
                new QuestionAdapter.QuestionInterface() {
                    @Override
                    public void onCLickItem(int position, int yes) {

                    }
                });
        roleInterestBinding.rvQuestions.setAdapter(questionAdapter);

        languageAdapter = new SpecialtyAdapter(context, list_Language, select_Language, 7, 7, 7,
                new SpecialtyAdapter.SpecialtyListener() {
                    @Override
                    public void onClickItem(int position) {
                        if (select_Language != null && select_Language.size() != 0
                                && position < select_Language.size()) {
                            select_Language.remove(position);
                            if (languageAdapter != null)
                                languageAdapter.notifyDataSetChanged();
                            if (roleInterestBinding.tvLang != null && roleInterestBinding.rvLanguage != null)
                                if (select_Language == null || select_Language.size() == 0) {
                                    roleInterestBinding.tvLang.setVisibility(View.VISIBLE);
                                    roleInterestBinding.rvLanguage.setVisibility(View.GONE);
                                } else {
                                    roleInterestBinding.tvLang.setVisibility(View.GONE);
                                    roleInterestBinding.rvLanguage.setVisibility(View.VISIBLE);
                                }
                        }
                    }
                });
        roleInterestBinding.rvLanguage.setAdapter(languageAdapter);


        roleInterestBinding.rvQuestions.setVisibility(View.VISIBLE);
        roleInterestBinding.layProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (check_Any_Data_Empty_Role_Intereset()) {
            API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                @Override
                public void onShowProgress() {
                    roleInterestBinding.layProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSucces(Object models) {
                    roleInterestBinding.layProgress.setVisibility(View.GONE);

                    /*if (models instanceof LanguageModel) {
                        LanguageModel combineData = (LanguageModel) models;
                        try {
                            clearAllOptionList();
                            list_Language.addAll(combineData.getData());
                            setupDialogFieldData();

                        } catch (Exception e) {
                            Log.d("TAG", "getHourlyRate_OptionsData() catch(): " + e.getMessage());
                        }
                    }*/

                    if (models instanceof Combine_RoleIneterest_DataModel) {
                        Combine_RoleIneterest_DataModel combineData = (Combine_RoleIneterest_DataModel) models;
                        try {
                            clearAllOptionList();
                            list_Language.addAll(combineData.getLanguageModel().getData());
                            list_LeaderShipRole.addAll(combineData.getLeaderRolesModel().getData());
                            setupDialogFieldData();

                        } catch (Exception e) {
                            Log.d("TAG", "getHourlyRate_OptionsData() catch(): " + e.getMessage());
                        }
                    }
                }

                void clearAllOptionList() {
                    if (list_Language == null) {
                        list_Language = new ArrayList<>();
                    } else
                        list_Language.clear();
                    if (list_LeaderShipRole == null) {
                        list_LeaderShipRole = new ArrayList<>();
                    } else
                        list_LeaderShipRole.clear();
                    list_LeaderShipRole.add(new LeaderRolesData(0, "Select Leadership Role"));

                }

                void setupDialogFieldData() {

                }

                @Override
                public void onFailed() {
                    roleInterestBinding.layProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    roleInterestBinding.layProgress.setVisibility(View.GONE);
                }
            };

            fetch_Role_Of_Interset_1_Field_Data(apiResponseCallback);

        } else {

        }
        roleInterestBinding.layLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                showOptionPopup_RoleInterested(context, 6, roleInterestBinding.view1, roleInterestBinding.img1,
                        list_Language,
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                if (select_Language == null)
                                    select_Language = new ArrayList<>();
                                if (position < select_Language.size() && select_Language.contains(position)) {
                                    select_Language.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    select_Language.add(position);
                                roleInterestBinding.tvLang.setVisibility(View.GONE);
                                roleInterestBinding.rvLanguage.setVisibility(View.VISIBLE);
                                languageAdapter.notifyDataSetChanged();
                            }

                        });

                Utils.onClickEvent(v);
            }
        });

        roleInterestBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (checkValiation()) {
                    if (Utils.isNetworkAvailable(context)) {
                        send_RoleInterested();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }

                }

                Utils.onClickEvent(v);
            }

            private void send_RoleInterested() {
                roleInterestBinding.layProgress.setVisibility(View.VISIBLE);
                String id = new SessionManager(context).get_user_register_Id();
                String ans1 = list_Ques.get(0).getAnswer_Str();
                String ans2 = list_Ques.get(1).getAnswer_Str();
                String leader = String.valueOf(list_LeaderShipRole.get(selected_leadership).getId());
                String ans3 = list_Ques.get(3).getAnswer_Str();
                String ans4 = list_Ques.get(4).getAnswer_Str();
                String ans5 = list_Ques.get(5).getAnswer_Str();
                String ans6 = list_Ques.get(6).getAnswer_Str();
                String ans7 = list_Ques.get(7).getAnswer_Str();
                String ans8 = list_Ques.get(8).getAnswer_Str();
                String lang = "";
                for (int i = 0; i < select_Language.size(); i++) {
                    if (i == 0) {
                        lang = "" + list_Language.get(select_Language.get(i)).getLanguage();
                    } else
                        lang = lang + "," + list_Language.get(select_Language.get(i)).getLanguage();
                }
                MediaType mediaType = MediaType.parse("multipart/form-data");
                RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                RequestBody request_id = RequestBody.create(mediaType, id);
                RequestBody serving_preceptor = RequestBody.create(mediaType, ans1);
                RequestBody serving_interim_nurse_leader = RequestBody.create(mediaType, ans2);
                RequestBody leadership_roles = RequestBody.create(mediaType, leader);
                RequestBody linical_educator = RequestBody.create(mediaType, ans3);
                RequestBody is_daisy_award_winner = RequestBody.create(mediaType, ans4);
                RequestBody employee_of_the_mth_qtr_yr = RequestBody.create(mediaType, ans5);
                RequestBody other_nursing_awards = RequestBody.create(mediaType, ans6);
                RequestBody is_professional_practice_council = RequestBody.create(mediaType, ans7);
                RequestBody is_research_publications = RequestBody.create(mediaType, ans8);
                RequestBody languages = RequestBody.create(mediaType, lang);


                backendApi.call_role_interest_1(request_id, serving_preceptor,
                        serving_interim_nurse_leader, leadership_roles,
                        linical_educator, is_daisy_award_winner, employee_of_the_mth_qtr_yr, other_nursing_awards,
                        is_professional_practice_council, is_research_publications, languages)
                        .enqueue(new Callback<RoleModel>() {
                            @Override
                            public void onResponse(Call<RoleModel> call, Response<RoleModel> response) {
                                if (response.body() == null) {
                                    try {
                                        roleInterestBinding.layProgress.setVisibility(View.GONE);
                                        Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (!response.body().getApiStatus().equals("1")) {
                                    Utils.displayToast(context, response.message());
                                    roleInterestBinding.layProgress.setVisibility(View.GONE);
                                    Utils.displayToast(context, "" + response.body().getMessage());
                                    roleInterestBinding.layProgress.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    roleDialog2();
                                } else {
                                    Utils.displayToast(context, "Data has not been updated");
                                    roleInterestBinding.layProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<RoleModel> call, Throwable t) {
                                Log.d("TAG", "send_WorkHistoryDate_1() onFailure: " + t.getMessage());
                                Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                roleInterestBinding.layProgress.setVisibility(View.GONE);
                            }
                        });

            }

            private boolean checkValiation() {
                boolean status = true;
                String st = null;
                for (int i = 0; i < list_Ques.size(); i++) {
                    QuestionModel questionModel = list_Ques.get(i);
                    if (i != 2 && !questionModel.isAnyCheck()) {
                        status = false;
                        st = String.valueOf(i);
                        break;

                    }
                }
                if (!status) {
                    Utils.displayToast(context, "Please, Answer  " + list_Ques.get(Integer.parseInt(st)).getQuestion());
                    return false;
                }

                if (select_Language == null || select_Language.size() == 0) {
                    Utils.displayToast(context, "Please, Select Preferred Day Of The Week First !");
                    return false;
                }

                return true;
            }

        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();

    }


    private void roleDialog2() {
        list_photos.clear();
        list_Files.clear();
        DialogRoleInterest2Binding roleInterest2Binding = DialogRoleInterest2Binding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.AlertDialog);
        dialog.setContentView(roleInterest2Binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        photoFilesAdapter = new PhotoFilesAdapter(list_photos, 1);
        roleInterest2Binding.rvPhotos.setAdapter(photoFilesAdapter);

        filesAdapter = new PhotoFilesAdapter(list_Files, 2);
        roleInterest2Binding.rvFiles.setAdapter(filesAdapter);

        roleInterest2Binding.layAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
                    return;
                }

                if (list_photos != null && list_photos.size() > 3) {
                    Utils.displayToast(context, "Max 4 photos can be upload only !");
                    return;
                }

                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
                resultLauncherPhotos.launch(intent);
                Utils.onClickEvent(v);
            }


        });

        roleInterest2Binding.layAddFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    });
                    return;
                }
                if (list_Files != null && list_Files.size() > 3) {
                    Utils.displayToast(context, "Max 4 PDF files can be upload only !");
                    return;
                }
                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                resultLauncherPDF.launch(intent);
                Utils.onClickEvent(v);
            }

        });

        roleInterest2Binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(context, v);

                if (checkValiation()) {
                    if (Utils.isNetworkAvailable(context)) {
                        send_RoleInterested();
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }
                }
            }

            private void send_RoleInterested() {
                roleInterest2Binding.layProgress.setVisibility(View.VISIBLE);
                String id = new SessionManager(context).get_user_register_Id();
                String intro = roleInterest2Binding.edIntro.getText().toString();
                String links = roleInterest2Binding.edLinks.getText().toString();
                MultipartBody.Part[] photoList = new MultipartBody.Part[list_photos.size()];
                if (list_photos != null && list_photos.size() != 0)
                    for (int index = 0; index < list_photos.size(); index++) {
                        File file = new File(list_photos.get(index));
                        RequestBody body;
                        body = RequestBody.create(MediaType.parse("image/*"), file);
                        photoList[index] = MultipartBody.Part.createFormData("additional_pictures[]",
                                file.getName(), body);
                    }

                MultipartBody.Part[] filesList = new MultipartBody.Part[list_Files.size()];
                if (list_Files != null && list_Files.size() != 0)
                    for (int index = 0; index < list_Files.size(); index++) {
                        File file = new File(list_Files.get(index));
                        RequestBody body;
                        body = RequestBody.create(MediaType.parse("*/*"), file);
                        filesList[index] = MultipartBody.Part.createFormData("additional_files[]",
                                file.getName(), body);
                    }
                MediaType mediaType = MediaType.parse("multipart/form-data");
                RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
                RequestBody request_id = RequestBody.create(mediaType, id);
                RequestBody intro1 = RequestBody.create(mediaType, intro);
                RequestBody link1 = RequestBody.create(mediaType, links);


                backendApi.call_role_interest_2(request_id, intro1, link1, photoList, filesList)
                        .enqueue(new Callback<RoleModel>() {
                            @Override
                            public void onResponse(Call<RoleModel> call, Response<RoleModel> response) {
                                if (response.body() == null) {
                                    try {
                                        roleInterest2Binding.layProgress.setVisibility(View.GONE);
                                        Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (!response.body().getApiStatus().equals("1")) {
                                    Utils.displayToast(context, null);
                                    Utils.displayToast(context, response.message());
                                    roleInterest2Binding.layProgress.setVisibility(View.GONE);
                                    Utils.displayToast(context, "" + response.body().getMessage());
                                    roleInterest = true;
                                    dialog.dismiss();
                                    if (state == 0) {
                                        chooseOption();
                                    } else {
                                        finish();
                                    }
                                } else {
                                    Utils.displayToast(context, "Data has not been updated");
                                    roleInterest2Binding.layProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<RoleModel> call, Throwable t) {
                                Log.d("TAG", "send_RoleInterested() onFailure: " + t.getMessage());
                                Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                                roleInterest2Binding.layProgress.setVisibility(View.GONE);
                            }
                        });

            }

            private boolean checkValiation() {

                boolean intro_bool = false;
                boolean links_bool = false;
                boolean photo_bool = false;
                boolean files_bool = false;

                if (TextUtils.isEmpty(roleInterest2Binding.edIntro.getText().toString())) {
                    intro_bool = false;
                    Utils.displayToast(context, "Write Something Here");
                    return false;
                } else
                    intro_bool = true;
                if (TextUtils.isEmpty(roleInterest2Binding.edLinks.getText().toString())) {
                    links_bool = false;
                } else
                    links_bool = true;

                if (list_photos == null || list_photos.size() == 0) {
                    photo_bool = false;
                } else
                    photo_bool = true;

                if (list_Files == null || list_Files.size() == 0) {
                    files_bool = false;
                } else
                    files_bool = true;

                if (!(intro_bool || links_bool || photo_bool || files_bool)) {
                    Utils.displayToast(context, "First enter data in above fields");
                    return false;
                }

                return true;
            }

        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chooseOption();
            }
        });
        dialog.show();

    }

    private boolean checkReadExternal() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)) {
            return false;
        }
        return true;
    }

    private void fetch_WorkHistory_2_Field_Data(API_ResponseCallback apiResponseCallback) {
        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
        backendApi.call_search_for_credentials_list()
                .enqueue(new Callback<CredentialModel>() {
                    @Override
                    public void onResponse(Call<CredentialModel> call, Response<CredentialModel> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            apiResponseCallback.onFailed();
                            Utils.displayToast(context, "" + response.body().getMessage());
                            return;
                        }

                        if (response.isSuccessful()) {
                            CredentialModel credentialModel = response.body();
                            apiResponseCallback.onSucces(credentialModel);
                        } else {
                            Utils.displayToast(context, "Data has not been updated");
                            apiResponseCallback.onFailed();
                        }

                    }

                    @Override
                    public void onFailure(Call<CredentialModel> call, Throwable t) {
//                                    progressDialog.dismiss();
                        Log.d("TAG", "fetch_WorkHistory_2_Field_Data() onFailure: " + t.getMessage());
                        Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                        apiResponseCallback.onError();
                    }
                });
    }

    private boolean check_Any_Data_Empty_WorkHistory_2() {
        if ((list_Credential == null)
                || list_Credential.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean check_Any_Data_Empty_Role_Intereset() {
        if ((list_Credential == null)
                || list_Credential.size() == 0) {
            return true;
        }
        if ((list_LeaderShipRole == null)
                || list_LeaderShipRole.size() == 0) {
            return true;
        }
        return false;
    }

    private void fetch_Role_Of_Interset_1_Field_Data(API_ResponseCallback apiResponseCallback) {

        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
        Observable<Combine_RoleIneterest_DataModel> listObservable
                = Observable.zip(
                backendApi.call_language()
                        .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_leadership_roles().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                , new BiFunction<LanguageModel, LeaderRolesModel, Combine_RoleIneterest_DataModel>() {
                    @NonNull
                    @Override
                    public Combine_RoleIneterest_DataModel apply(@NonNull LanguageModel languageModel, @NonNull LeaderRolesModel languageModel2) throws Exception {
                        return new Combine_RoleIneterest_DataModel(languageModel, languageModel2);
                    }
                });

        listObservable.subscribe(new Observer<Combine_RoleIneterest_DataModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_RoleIneterest_DataModel combineData) {
                Utils.displayToast(context, null);
                apiResponseCallback.onSucces(combineData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                apiResponseCallback.onError();
                Log.d("TAG", "getHourlyRate_OptionsData() onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }


    public String getFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        try {
            Uri contentUri = MediaStore.Files.getContentUri("external");
            Cursor cursor = getContentResolver().query(contentUri, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                cursor.close();
                return imagePath;
            }
        } catch (Exception e) {
            Log.e("getFilePath Exception", e.toString());
        }
        return null;
    }


    private void move_TO_Home_Screen() {
        chooseOption();
    }


    private boolean check_Any_Data_Empty_WorkHistory() {
        if ((list_nurse_degrees == null || list_experience == null)
                || (list_nurse_degrees.size() == 0 || list_experience.size() == 0)) {
            return true;
        }
        return false;
    }

    private void showOptionPopup_RoleInterested(Context context, int type, View view1, ImageView img1,
                                                List<LanguageDatum> list_nurse_degrees,
                                                ItemCallback itemCallback) {
        if (list_nurse_degrees == null || list_nurse_degrees.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        RoleInteresetWindowAdapter adapter_degree;
        adapter_degree = new RoleInteresetWindowAdapter(RegisterActivity.this,
                list_nurse_degrees, 1,
                new RoleInteresetWindowAdapter.RoleInterface() {
                    @Override
                    public void onCLickItem(int i, int position) {
                        itemCallback.onClick(i);
                        popup.dismiss();
                    }
                });


        recyclerView.setAdapter(adapter_degree);
        popup.showAsDropDown(view1, 0, 0);
    }

    private void showOptionPopup_Degrees(Context context, int type, View view1, ImageView img1,
                                         List<Object> list_nurse_degrees,
                                         ItemCallback itemCallback) {
        if (list_nurse_degrees == null || list_nurse_degrees.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        HourlyRateWindowAdapter parentChildAdapter = null;
        WorkHistoryWindowAdapter adapter_degree;
        adapter_degree = new WorkHistoryWindowAdapter(RegisterActivity.this, type,
                list_nurse_degrees,
                new WorkHistoryWindowAdapter.WorkHistoryWindowInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        itemCallback.onClick(position);
                        popup.dismiss();
                    }
                });


        recyclerView.setAdapter(adapter_degree);
        popup.showAsDropDown(view1, 0, 0);
    }

    private void fetch_WorkHistory_Field_Data(API_ResponseCallback apiResponseCallback) {
        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
        Observable<Combine_WorkHistory_DataModel> listObservable
                = Observable.zip(
                backendApi.call_nursing_degrees_options().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_cerner_medtech_epic_options().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                new BiFunction<DegreeModel, CernersModel,
                        Combine_WorkHistory_DataModel>() {

                    @NonNull
                    @Override
                    public Combine_WorkHistory_DataModel apply(@NonNull DegreeModel specialtyModel,
                                                               @NonNull CernersModel stateModel

                    ) throws Exception {
                        return new Combine_WorkHistory_DataModel(specialtyModel, stateModel);
                    }


                });

        listObservable.subscribe(new Observer<Combine_WorkHistory_DataModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_WorkHistory_DataModel combineData) {
                Utils.displayToast(context, null);
                apiResponseCallback.onSucces(combineData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                apiResponseCallback.onError();
                Log.d("TAG", "getHourlyRate_OptionsData() onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void showOptionPopup_State(Context context, View v, int type, ImageView img1, TextView tvState, ItemCallback itemCallback) {
        if (list_State == null || list_State.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(v.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        PersonalDetailWindowAdapter parentChildAdapter = new PersonalDetailWindowAdapter(RegisterActivity.this,
                3, 3,
                list_State, selected_state, new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
            @Override
            public void onCLickItem(int i, int position) {
                itemCallback.onClick(i);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        popup.showAsDropDown(v, 0, 0);
    }

    void showPopup_Speciality(final Context context, View v, int type, ImageView img1, View view,
                              ItemCallback itemCallback) {
        if (list_Specialty == null || list_Specialty.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(v.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        PersonalDetailWindowAdapter parentChildAdapter
                = new PersonalDetailWindowAdapter(RegisterActivity.this, 2,
                null, list_Specialty,
                new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {

                        itemCallback.onClick(position);
                        popup.dismiss();
                    }

                });
        recyclerView.setAdapter(parentChildAdapter);

        popup.showAsDropDown(v, 0, -30);
    }

    private boolean checkItemInList_State(String state, List<State_Datum> list_state) {
        for (State_Datum workLocationModel :
                list_state) {
            if (workLocationModel.getState().toString().equals(state)) {
                return true;
            }

        }
        return false;
    }

    private int getIndexFromList_State(String state, List<State_Datum> list_state) {
        int pos = 0;
        for (int i = 0; i < list_state.size(); i++) {

            State_Datum workLocationModel = list_state.get(i);
            if (workLocationModel.getState().toString().equals(state.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private void fetch_PersonalDetail_2_Field_Data(API_ResponseCallback apiResponseCallback) {

        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
        Observable<Combine_PersonalDetail_2_DataModel> listObservable
                = Observable.zip(
                backendApi.call_specialty().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_state().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                new BiFunction<SpecialtyModel, StateModel,
                        Combine_PersonalDetail_2_DataModel>() {

                    @NonNull
                    @Override
                    public Combine_PersonalDetail_2_DataModel apply(@NonNull SpecialtyModel specialtyModel,
                                                                    @NonNull StateModel stateModel

                    ) throws Exception {
                        return new Combine_PersonalDetail_2_DataModel(specialtyModel, stateModel);
                    }


                });

        listObservable.subscribe(new Observer<Combine_PersonalDetail_2_DataModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_PersonalDetail_2_DataModel combineData) {
                Utils.displayToast(context, null);
                apiResponseCallback.onSucces(combineData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                apiResponseCallback.onError();
                Log.d("TAG", "getHourlyRate_OptionsData() onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void setupSelection_Specialty_ByModelData(List<String> specialty) {
        if (select_specialty != null)
            select_specialty.clear();
        for (int i = 0; i < list_Specialty.size(); i++) {
            SpecialtyDatum data = list_Specialty.get(i);
            for (int j = 0; j < specialty.size(); j++) {
                Log.d("TAG1", "setupSelectionSpecialtyByModelData: " + "" + data.getId() +
                        " " + specialty.get(j));
                if (("" + data.getId()).equals(specialty.get(j))) {
                    select_specialty.add(i);
                }
            }
        }
    }

    private void setupSelection_DaysOfWeeks_ByModelData(List<String> specialty) {
        select_daysOfWeek.clear();
        for (int i = 0; i < list_days_of_week.size(); i++) {
            HourlyRate_DayOfWeek_OptionDatum data = list_days_of_week.get(i);
            for (int j = 0; j < specialty.size(); j++) {
                Log.d("TAG1", "setupSelectionSpecialtyByModelData: " + "" + data.getId() +
                        " " + specialty.get(j));
                if (("" + data.getId()).equals(specialty.get(j))) {
                    select_daysOfWeek.add(i);
                }
            }
        }
    }

    private void fetch_HourlyRate_OptionsField_Data(API_ResponseCallback apiResponseCallback) {
        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();

        Observable<Combine_HourlyRate_DataModel> listObservable
                = Observable.zip(
                backendApi.call_shift_duration().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_assignment_duration().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_preferred_shifts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_get_weekdays().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_work_location().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()),
                new Function5<HourlyRate_Common_OptionModel, HourlyRate_Common_OptionModel,
                        HourlyRate_Common_OptionModel, HourlyRate_DayOfWeek_OptionModel, WorkLocationModel,
                        Combine_HourlyRate_DataModel>() {

                    @NonNull
                    @Override
                    public Combine_HourlyRate_DataModel apply(@NonNull HourlyRate_Common_OptionModel hourlyRate_common_optionModel,
                                                              @NonNull HourlyRate_Common_OptionModel hourlyRate_common_optionModel2,
                                                              @NonNull HourlyRate_Common_OptionModel hourlyRate_common_optionModel3,
                                                              @NonNull HourlyRate_DayOfWeek_OptionModel hourlyRate_common_optionModel4,
                                                              @NonNull WorkLocationModel workLocationModel
                    ) throws Exception {
                        return new Combine_HourlyRate_DataModel(hourlyRate_common_optionModel, hourlyRate_common_optionModel2,
                                hourlyRate_common_optionModel3, hourlyRate_common_optionModel4, workLocationModel);
                    }


                });

        listObservable.subscribe(new Observer<Combine_HourlyRate_DataModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_HourlyRate_DataModel combineData) {
                Utils.displayToast(context, null);
                apiResponseCallback.onSucces(combineData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                apiResponseCallback.onError();
                Log.d("TAG", "getHourlyRate_OptionsData() onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }


    private void showOptionPopup_Weeks(Context context, int type, View view4, ImageView img2,
                                       TextView tv_weeks_days,
                                       List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week,
                                       List<Integer> select_daysOfWeek, ItemCallback itemCallback) {
        if (list_days_of_week == null || list_days_of_week.size() == 0) {
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view4.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img2.setRotation(-180);
        View finalImg = img2;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        HourlyRateWindowAdapter parentChildAdapter = null;
        parentChildAdapter = new HourlyRateWindowAdapter(RegisterActivity.this,
                type, type, type,
                list_days_of_week,
                new HourlyRateWindowAdapter.HourlyRateWindowInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        itemCallback.onClick(position);
                        popup.dismiss();
                    }
                });
        recyclerView.setAdapter(parentChildAdapter);
        popup.showAsDropDown(view4, 0, 0);

    }

    void showOptionPopup(final Context context, int type, View view1, ImageView img1, TextView view,
                         List<HourlyRate_Common_OptionDatum> list_days_of_week,
                         List<WorkLocationDatum> list_geography, String select_pos,
                         ItemCallback itemCallback) {


        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        HourlyRateWindowAdapter parentChildAdapter = null;
        if (type == 5) {
            parentChildAdapter = new HourlyRateWindowAdapter(RegisterActivity.this,
                    type, type,
                    list_geography,
                    new HourlyRateWindowAdapter.HourlyRateWindowInterface() {
                        @Override
                        public void onCLickItem(int position, int type) {
                            itemCallback.onClick(position);
                            popup.dismiss();
                        }
                    });
        } else {
            parentChildAdapter = new HourlyRateWindowAdapter(RegisterActivity.this,
                    type,
                    list_days_of_week,
                    new HourlyRateWindowAdapter.HourlyRateWindowInterface() {
                        @Override
                        public void onCLickItem(int position, int type) {
                            itemCallback.onClick(position);
                            popup.dismiss();
                        }
                    });
        }
        recyclerView.setAdapter(parentChildAdapter);
        popup.showAsDropDown(view1, 0, 0);
    }

    private boolean check_Any_Data_Empty_HourlyRate() {
        if ((list_shift_durations == null || list_preferred_shift == null
                || list_assignment_durations == null || list_days_of_week == null || list_geography == null)
                || (list_shift_durations.size() == 0 || list_preferred_shift.size() == 0
                || list_assignment_durations.size() == 0 || list_days_of_week.size() == 0
                || list_geography.size() == 0)) {

            return true;
        }
        return false;
    }

    private boolean check_Any_Data_Empty_PersonalDetail_2() {
        if ((list_Specialty == null || list_State == null)
                || (list_Specialty.size() == 0 || list_State.size() == 0)) {

            return true;
        }
        return false;
    }

    private int getIndexFromList(String shiftDuration, List<HourlyRate_Common_OptionDatum> list_shift_durations) {
        int pos = 0;
        for (int i = 0; i < list_shift_durations.size(); i++) {

            HourlyRate_Common_OptionDatum workLocationModel = list_shift_durations.get(i);
            if (workLocationModel.getId().toString().equals(shiftDuration)) {
                pos = i;
            }
        }
        return pos;
    }


    private boolean checkItemInList(String shiftDuration, List<HourlyRate_Common_OptionDatum> list_shift_durations) {
        for (HourlyRate_Common_OptionDatum workLocationModel :
                list_shift_durations) {
            if (workLocationModel.getId().toString().equals(shiftDuration)) {
                return true;
            }

        }
        return false;
    }

    private int getIndexFromList(Integer workLocation, List<WorkLocationDatum> list_geography) {
        int pos = 0;
        for (int i = 0; i < list_geography.size(); i++) {

            WorkLocationDatum workLocationModel = list_geography.get(i);
            if (workLocationModel.getId().toString().equals(workLocation.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private boolean checkItemInList(Integer workLocation, List<WorkLocationDatum> list_geography) {
        for (WorkLocationDatum workLocationModel :
                list_geography) {
            if (workLocationModel.getId().toString().equals(workLocation.toString())) {
                return true;
            }

        }
        return false;
    }

    private void clearAllOptionList() {
        if (list_preferred_shift == null) {
            list_preferred_shift = new ArrayList<>();
        } else if (list_preferred_shift != null || list_preferred_shift.size() != 0) {
            list_preferred_shift.clear();
        }
        if (list_shift_durations == null) {
            list_shift_durations = new ArrayList<>();
        } else if (list_shift_durations != null || list_shift_durations.size() != 0) {
            list_shift_durations.clear();
        }
        if (list_assignment_durations == null) {
            list_assignment_durations = new ArrayList<>();
        } else if (list_assignment_durations != null || list_assignment_durations.size() != 0) {
            list_assignment_durations.clear();
        }
        if (list_days_of_week == null) {
            list_days_of_week = new ArrayList<>();
        } else if (list_days_of_week != null || list_days_of_week.size() != 0) {
            list_days_of_week.clear();
        }
        if (list_geography == null) {
            list_geography = new ArrayList<>();
        } else if (list_geography != null || list_geography.size() != 0) {
            list_geography.clear();
        }

    }

    private ActivityResultLauncher<Intent> resultLauncherProfile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    File file = null;
                    try {
                        file = new File(getPath(context, data.getData()));
                        String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                        Log.d("TAG", file.length() + " size : " + str);
                        if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg"))) {
                            Utils.displayToast(context, "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                        }
                        double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                        if ((sis > 5 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(context, "Select File less than equal to 5 MB");
                            return;
                        }
                    } catch (Exception e) {
                        Utils.displayToast(context, "Select file is damage");
                        return;
                    }
                    if (file != null) {
                        user_profile = file.getAbsolutePath();
                        Glide.with(context).load(file.getAbsolutePath()).
                                into(personalDetailsBinding.imgProfile);
                        personalDetailsBinding.imgProfile.setVisibility(View.VISIBLE);
                    }

                }
            });

    ActivityResultLauncher<Intent> resultLauncherResume = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    File file = null;
                    try {
                        file = new File(getPath(context, data.getData()));
                        String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                        Log.d("TAG", file.length() + " size : " + str);
                        if (!(file.getName().endsWith(".pdf") || file.getName().endsWith(".doc")
                                || file.getName().endsWith(".docx"))) {
                            Utils.displayToast(context, "Select Proper File Format. Only pdf,doc,docx are allowed");
                        }
                        double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                        if ((sis > 1 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(context, "Select File less than equal to 1 MB");
                            return;
                        }
                    } catch (Exception e) {

                    }
                    if (file != null) {
                        resumeFile = file.getAbsolutePath();
                        history2Binding.imgResume.setVisibility(View.VISIBLE);
                    }
                }
            });
    ActivityResultLauncher<Intent> resultLauncherCertificate = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    File file = new File(getPath(context, data.getData()));
                    String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                    Log.d("TAG", file.length() + " size : " + str);
                    if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                            || file.getName().endsWith(".jpeg"))) {
                        Utils.displayToast(context, "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                    }
                    double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                    if ((sis > 5 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                        Utils.displayToast(context, "Select File less than equal to 5 MB");
                        return;
                    }
                    if (file != null) {
                        uploadCertificate = file.getAbsolutePath();
                        Glide.with(context).load(file.getAbsolutePath()).into(history2Binding.imgCertificate);
                        history2Binding.imgCertificate.setVisibility(View.VISIBLE);
                    }
                }
            });
    ActivityResultLauncher<Intent> resultLauncherPDF = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data.getData() == null) {
                        Log.d("TAG", ": empty uri");
                        return;
                    }
                    File file = null;
                    try {
                        file = new File(getPath(context, data.getData()));
                        String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                        Log.d("TAG", file.length() + " size : " + str);
                        if (!(file.getName().endsWith(".pdf") || file.getName().endsWith(".doc")
                                || file.getName().endsWith(".docx"))) {
                            Utils.displayToast(context, "Select Proper File Format. Only pdf,doc,docx are allowed");
                        }
                        double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                        if ((sis > 1 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(context, "Select File less than equal to 1 MB");
                            return;
                        }

                    } catch (Exception e) {
                        Utils.displayToast(context, "Select file is damage");
                    }
                    if (file != null)
                        list_Files.add(file.getAbsolutePath());
                    if (filesAdapter != null)
                        filesAdapter.notifyDataSetChanged();
                }
            });
    ActivityResultLauncher<Intent> resultLauncherPhotos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    File file = null;
                    try {
                        file = new File(getPath(context, data.getData()));
                        String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                        Log.d("TAG", file.length() + " size : " + str);
                        if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg"))) {
                            Utils.displayToast(context, "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                        }
                        double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                        if ((sis > 5 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(context, "Select File less than equal to 5 MB");
                            return;
                        }
                    } catch (Exception e) {
                        Utils.displayToast(context, "Select file is damage");
                        return;
                    }
                    if (file != null)
                        list_photos.add(file.getAbsolutePath());
                    if (photoFilesAdapter != null)
                        photoFilesAdapter.notifyDataSetChanged();
                }
            });
    ActivityResultLauncher<String[]> startForResultPermission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                if (!result.containsValue(false)) {
//                    onClick(roleInterest2Binding.layAddPhotos);
//                    Utils.displayToast(context, "Important Permission Allowed !");
                } else {
                    Utils.displayToast(context, "Important Permission Denied !");
                }
            });

}