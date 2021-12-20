package com.weboconnect.nurseify.screen.nurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.SpecialtyAdapter;
import com.weboconnect.nurseify.adapter.PersonalDetailWindowAdapter;
import com.weboconnect.nurseify.databinding.ActivitySignupDetailsBinding;
import com.weboconnect.nurseify.screen.nurse.model.Combine_PersonalDetail_DataModel;
import com.weboconnect.nurseify.screen.nurse.model.RegisterModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyDatum;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationModel;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationDatum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitApi;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupDetailsActivity extends AppCompatActivity {

    ActivitySignupDetailsBinding binding;
    private SignupDetailsActivity context;
    private String edFirstName;
    private String edLastName;
    private String email;
    private String mobile;
    public List<Integer> select_specialty = new ArrayList<>();
    public String select_prefer_geo;
    private List<WorkLocationDatum> list_work_location = new ArrayList<>();
    private List<SpecialtyDatum> list_Specialty = new ArrayList<>();
    private ProgressDialog progressDialog;
    private SpecialtyAdapter specialtyAdapter;
    private SessionManager sessionManger;
    Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[\\[\\]\\{\\}\\';:\\.,#?!@$%^&*-]).{6,}$");
    Pattern pattern2 = Pattern.compile("^[a-zA-Z]*$");
    Pattern pattern3 = Pattern.compile("^[0-9]*$");
    Pattern pattern4 = Pattern.compile("^[a-zA-Z0-9]*$");
    Pattern patternLettersSpace = Pattern.compile("^[a-zA-Z ]*$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignupDetailsActivity.this, R.layout.activity_signup_details);
        context = this;
        sessionManger = new SessionManager(context);
        fetchData();
        setData();
        click();
    }

    private void fetchData() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();

        List<Observable> list = new ArrayList<Observable>();

        Observable<Combine_PersonalDetail_DataModel> listObservable
                = Observable.zip(backendApi.call_work_location().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                backendApi.call_specialty().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                new BiFunction<WorkLocationModel, SpecialtyModel, Combine_PersonalDetail_DataModel>() {
                    @NonNull
                    @Override
                    public Combine_PersonalDetail_DataModel apply(@NonNull WorkLocationModel workLocationModel, @NonNull SpecialtyModel specialtyModel) throws Exception {

                        return new Combine_PersonalDetail_DataModel(workLocationModel, specialtyModel);
                    }
                });

        listObservable.subscribe(new Observer<Combine_PersonalDetail_DataModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_PersonalDetail_DataModel combineData) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                list_work_location.clear();
                list_Specialty.clear();
                Utils.displayToast(context, null);
                list_work_location.addAll(combineData.workLocationModel.getData());
                list_Specialty.addAll(combineData.specialtyModel.getData());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onComplete() {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

    private void showPopup(final Context context, View v, int type, ImageView img1, View view) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(binding.tvSpec.getWidth());
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
                = new PersonalDetailWindowAdapter(SignupDetailsActivity.this, type,
                list_work_location, list_Specialty,
                new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        if (type == 1) {
                            select_prefer_geo = "" + position;
                            binding.tvGreography.setText(list_work_location.get(position).getName());
                        } else {
                            if (select_specialty == null)
                                select_specialty = new ArrayList<>();
                            if (select_specialty.contains(position)) {
                                select_specialty.remove(Integer.parseInt(String.valueOf(position)));
                            } else
                                select_specialty.add(position);
                            binding.tvSpecialty.setVisibility(View.GONE);
                            binding.rvSpecialty.setVisibility(View.VISIBLE);
                            specialtyAdapter.notifyDataSetChanged();
                        }
                        popup.dismiss();
                    }
                });
        recyclerView.setAdapter(parentChildAdapter);

        popup.showAsDropDown(view, 0, 0);
    }

    private void click() {
        binding.edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edPassword.setError(null);
                else if (!pattern.matcher(s.toString()).find()) {
                    binding.edPassword.setError("Enter Password As Per Format Mentioned Below!");
                } else {
                    binding.edPassword.setError(null);
                }
            }
        });

        binding.edConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edConfirmPassword.setError(null);
                else if (!pattern.matcher(s.toString()).find()) {
                    binding.edConfirmPassword.setError("Enter Password As Per Format Mentioned Above!");
                }
                if (!binding.edPassword.getText().toString().equals(binding.edConfirmPassword.getText().toString())) {
                    binding.edConfirmPassword.setError("Please Enter Same Password In Both Field !");
                } else {
                    binding.edConfirmPassword.setError(null);
                }
            }
        });

        binding.edState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edState.setError(null);
                else if (!pattern4.matcher(s.toString()).find() || s.toString().length() < 2) {
                    binding.edState.setError("Nurse State in proper format only alphabet,numbers with no space and min length is 2 !");
                } else {
                    binding.edState.setError(null);
                }
            }
        });

        binding.edLicenseNos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edLicenseNos.setError(null);
                else if (!pattern4.matcher(s.toString()).find() || s.toString().length() < 5) {

                    binding.edLicenseNos.setError("Enter Nurse License Number in proper format only alphabet,numbers with no space and min length is 5 !");
                } else {
                    binding.edLicenseNos.setError(null);
                }
            }
        });

        binding.laySpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_Specialty == null || list_Specialty.size() == 0) {
                    fetchData();
                    Utils.displayToast(context, "Please Wait, fetching specialty data");
                } else
                    showPopup(context, v, 2, binding.img1, binding.tvSpecialty);
                Utils.onClickEvent(v);
            }
        });

        binding.layGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(context, v, 1, binding.img1, binding.tvGreography);
                Utils.onClickEvent(v);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupDetailsActivity.this, LoginActivity.class));
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    performSignupProcess();
                }
            }
        });

    }

    private void performSignupProcess() {

        Utils.displayToast(context, null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog.show();

        String pass, state, lic, spec, key;
        pass = binding.edPassword.getText().toString();
        state = binding.edState.getText().toString();
        lic = binding.edLicenseNos.getText().toString();
        spec = "" + list_work_location.get(Integer.parseInt(select_prefer_geo)).getId();
        key = Build.ID;
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), edFirstName);
        RequestBody requestBody32 = RequestBody.create(MediaType.parse("multipart/form-data"), edLastName);
        RequestBody requestBody34 = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody requestBody33 = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        RequestBody requestBody35 = RequestBody.create(MediaType.parse("multipart/form-data"), pass.toString());
        RequestBody requestBody36 = RequestBody.create(MediaType.parse("multipart/form-data"), state.toString());
        RequestBody requestBody37 = RequestBody.create(MediaType.parse("multipart/form-data"), lic.toString());
        String nos = "";
        for (int i = 0; i < select_specialty.size(); i++) {
            if (i == 0) {
                nos = "" + list_Specialty.get(select_specialty.get(i)).getId();
            } else
                nos = nos + "," + list_Specialty.get(select_specialty.get(i)).getId();
        }
        RequestBody requestBody38 = RequestBody.create(MediaType.parse("multipart/form-data"), nos);
        RequestBody requestBody39 = RequestBody.create(MediaType.parse("multipart/form-data"), "" + spec);
        RequestBody requestBody310 = RequestBody.create(MediaType.parse("multipart/form-data"), key);

        Call<UserProfile> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_Signup(requestBody3, requestBody32, requestBody34, requestBody33,
                        requestBody35, requestBody36, requestBody37, requestBody38, requestBody39, requestBody310);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    Utils.displayToast(context, "" + response.body().getMessage());
                    progressDialog.dismiss();
                    return;
                }
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    UserProfile UserProfile = response.body();
                    sessionManger.set_TYPE(Constant.CONST_NURSE_TYPE);
//                    sessionManger.save_user_register_id(UserProfile.getData().getId());
                    sessionManger.setSession_IN(UserProfile.getData().getId(), UserProfile.getData());
                    Utils.displayToast(context, "SignUp Successfully Completed");

                    Intent i = new Intent(SignupDetailsActivity.this, RegisterActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(UserProfile.getData()));
                    startActivity(i);
                    context.finish();

                } else {
                    progressDialog.dismiss();
                    Utils.displayToast(context, "Signup Failed");
                }

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressDialog.dismiss();
                Utils.displayToast(context, "Something when wrong, please retry again ");

            }
        });

    }

    private boolean checkValidation() {
        String pasas = binding.edPassword.getText().toString();
        String confirm_pass = binding.edConfirmPassword.getText().toString();
        String state = binding.edState.getText().toString();
        String edLicenseNos = binding.edLicenseNos.getText().toString();


        if (TextUtils.isEmpty(pasas)) {
            Utils.displayToast(context, "Enter Password");
            return false;
        }
        if (TextUtils.isEmpty(pasas) && pasas.length() <= 6) {
            Utils.displayToast(context, "Enter More 6 Character Long Password !");
            return false;
        }
        Matcher matcher1 = pattern.matcher(pasas);
        if (!matcher1.find()) {
            Utils.displayToast(context, "Enter Password As Per Format Mentioned Above!");
            return false;
        }
        if (TextUtils.isEmpty(confirm_pass)) {
            Utils.displayToast(context, "Enter Confirm Password");
            return false;
        }
        if (pasas.length() != confirm_pass.length()) {
            Utils.displayToast(context, "Please Enter Same Password In Both Field !");
            return false;
        }
        if (!confirm_pass.equals(pasas)) {
            Utils.displayToast(context, "Please Enter Same Password In Both Field !");
            return false;
        }
        if (TextUtils.isEmpty(state)) {
            Utils.displayToast(context, "Enter Nurse License State First !");
            return false;
        }
        if (edLicenseNos.length() < 2) {
            Utils.displayToast(context, "Nurse State required min length is 2 !");
            return false;
        }
        matcher1 = pattern4.matcher(state);
        if (!matcher1.find() || state.length() < 2) {
            Utils.displayToast(context, "Nurse State can contain only alphabet & numbers with no space.!");
            return false;
        }
        if (TextUtils.isEmpty(edLicenseNos)) {
            Utils.displayToast(context, "Select Nurse License Number First !");
            return false;
        }
        matcher1 = pattern4.matcher(edLicenseNos);
        if (edLicenseNos.length() < 5) {
            Utils.displayToast(context, "Nurse License Number required min length is 5 !");
            return false;
        }
        if (!matcher1.find() || edLicenseNos.length() < 5) {
            Utils.displayToast(context, "Nurse License Number can contain only alphabet & numbers with no space !");
            return false;
        }
        if (edLicenseNos.length() > 20) {
            Utils.displayToast(context, "Enter Nurse License Number max length is 20 !");
            return false;
        }
        if (select_specialty == null || select_specialty.size() == 0) {
            Utils.displayToast(context, "Select Specialty First !");
            return false;
        }
        if (TextUtils.isEmpty(select_prefer_geo)) {
            Utils.displayToast(context, "Select Preferred Geography First !");
            return false;
        }
        return true;
    }

    private void setData() {
        edFirstName = getIntent().getStringExtra(Constant.FIRST_NAME);
        edLastName = getIntent().getStringExtra(Constant.LAST_NAME);
        email = getIntent().getStringExtra(Constant.EMAIL_ID);
        mobile = getIntent().getStringExtra(Constant.MOBILE);
        specialtyAdapter = new SpecialtyAdapter(context, select_specialty, list_Specialty
                , new SpecialtyAdapter.SpecialtyListener() {
            @Override
            public void onClickItem(int position) {
                if (select_specialty != null && select_specialty.size() != 0 && position < select_specialty.size()) {
                    select_specialty.remove(position);
                    specialtyAdapter.notifyDataSetChanged();
                    if (select_specialty == null || select_specialty.size() == 0) {
                        binding.tvSpecialty.setVisibility(View.VISIBLE);
                        binding.rvSpecialty.setVisibility(View.GONE);
                    } else {
                        binding.tvSpecialty.setVisibility(View.GONE);
                        binding.rvSpecialty.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        binding.rvSpecialty.setAdapter(specialtyAdapter);
    }
}