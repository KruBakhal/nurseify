package com.nurseify.app.screen.facility.profile_setup_dialog;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nurseify.app.R;
import com.nurseify.app.adapter.HourlyRateWindowAdapter;
import com.nurseify.app.adapter.WorkHistoryWindowAdapter;
import com.nurseify.app.databinding.DialogProfileSetup1Binding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.FacilityProfile;
import com.nurseify.app.screen.facility.viewModel.DialogStatus;
import com.nurseify.app.screen.facility.viewModel.DialogStatusMessage;
import com.nurseify.app.screen.facility.viewModel.ProgressUIType;
import com.nurseify.app.screen.facility.viewModel.RegistrationViewModel;
import com.nurseify.app.screen.nurse.model.SpecialtyDatum;
import com.nurseify.app.utils.FileUtils;
import com.nurseify.app.utils.Utils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSetupDialog_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSetupDialog_1 extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private DialogProfileSetup1Binding setup1Binding;
    private RoundedImageView imgProfile;
    private RegistrationViewModel viewModel;
    private FacilityProfile model;
    private String facility_profile;

    public ProfileSetupDialog_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSetupDialog_1.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSetupDialog_1 newInstance(int param1, String param2) {
        ProfileSetupDialog_1 fragment = new ProfileSetupDialog_1();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getTheme() {
        return R.style.AlertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setup1Binding = DialogProfileSetup1Binding.inflate(getLayoutInflater(), container, false);
        setCancelable(false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        model = viewModel.main_model;
        imgProfile = setup1Binding.imgProfile;
        if (viewModel.isEditMode) {
            model = viewModel.main_model;
        } else {
            model = viewModel.new_facilityModel;
        }
        setData();
        textChange_View();
        observer_view();
        click();
        return setup1Binding.getRoot();
    }

    private void textChange_View() {
        setup1Binding.facilityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.facilityName.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.facilityName.setError("Enter Name In Proper Format !");
                } else {
                    setup1Binding.facilityName.setError(null);
                }
            }
        });
        setup1Binding.edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edEmail.setError(null);
                else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).find()) {
                    setup1Binding.edEmail.setError("Enter Email Id In Proper Format !");
                } else {
                    setup1Binding.edEmail.setError(null);
                }
            }
        });
        setup1Binding.edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edPhone.setError(null);
                else if (s.toString().length() < 10) {
                    setup1Binding.edPhone.setError("Phone Nos Can Contain Only Numbers And Min 10 Digits Are Required");
                } else {
                    setup1Binding.edPhone.setError(null);
                }
            }
        });
    }

    private void setData() {
        if (model != null) {
            if (!TextUtils.isEmpty(model.getFacilityLogo())) {
                try {
//                    Glide.with(getContext()).load(model.getFacilityLogo()).into(setup1Binding.imgProfile);

                    if (!TextUtils.isEmpty(model.getFacilityLogo())) {
                        byte[] decodeString = Utils.get_base_images(model.getFacilityLogo_base());
                        RequestOptions myOptions = new RequestOptions()
                                .override(100, 100);
                        Glide.with(getContext())
                                .load(decodeString).apply(myOptions).placeholder(R.drawable.person)
                                .error(R.drawable.person)
                                .into(setup1Binding.imgProfile);
                        setup1Binding.imgProfile.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("TAG", " onBindViewHolder: " + e.getMessage());
                }

            }
            setup1Binding.facilityName.setText(model.getFacilityName());
            setup1Binding.edEmail.setText(model.getFacilityEmail());
            setup1Binding.edPhone.setText(model.getFacilityPhone());
            setup1Binding.tvFacilityType.setText(model.getFacilityPhone());

            set_facility_type(viewModel.getFacilityType_List().getValue());
        }
    }

    private void click() {
        setup1Binding.layFacilityType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup_Filter(7, setup1Binding.tvFacilityType,
                        setup1Binding.view1, setup1Binding.layFacilityType,
                        viewModel.getFacilityType_List().getValue(), new ItemCallback() {
                            @Override
                            public void onClick(int position) {

                            }
                        });

            }
        });
        setup1Binding.layProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);
                if (!checkReadExternal()) {
                    requestPermission();
                    return;
                }


                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpg", "image/png", "image/jpeg"});
                resultLauncherProfile.launch(intent);
                Utils.onClickEvent(v);
            }
        });

        setup1Binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Cancel, mParam1));
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacilityProfile facilityProfile = viewModel.new_facilityModel;
                if (viewModel.isEditMode) {
                    facilityProfile = viewModel.main_model;
                }
                if (checkValidation()) {

                    facilityProfile = setupFieldData(facilityProfile);
                    if (viewModel.isEditMode) {
                        viewModel.main_model = facilityProfile;
                    } else {
                        viewModel.new_facilityModel = facilityProfile;
                    }
                    if (Utils.isNetworkAvailable(getContext())) {
                        viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Done, mParam1));
                    } else {
                        Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
                    }
                }
            }

            private FacilityProfile setupFieldData(FacilityProfile facilityProfile) {
                String edName = setup1Binding.facilityName.getText().toString().trim();
                String email = setup1Binding.edEmail.getText().toString().trim();
                String mobile = setup1Binding.edPhone.getText().toString().trim();
                String ijmg = facility_profile;
                String fatype = "" + viewModel.getFacilityType_List(viewModel.selected_facility_type).getId();

                if (TextUtils.isEmpty(ijmg)) {
                    if (!TextUtils.isEmpty(model.getFacilityLogo())) {
                        facilityProfile.setFacilityLogo(model.getFacilityLogo());
                    }
                } else {
                    facilityProfile.setFacilityLogo_new(ijmg);
                }
                facilityProfile.setFacilityName(edName);
                facilityProfile.setFacilityName(edName);
                facilityProfile.setFacilityEmail(email);
                facilityProfile.setFacilityPhone(mobile);
                facilityProfile.setFacilityType(fatype);
                return facilityProfile;
            }

            private boolean checkValidation() {

                String edName = setup1Binding.facilityName.getText().toString().trim();
                String email = setup1Binding.edEmail.getText().toString().trim();
                String mobile = setup1Binding.edPhone.getText().toString().trim();

                Pattern pattern = Utils.patternLettersSpace;
                Matcher matcher1 = pattern.matcher(edName);

                if (TextUtils.isEmpty(model.getFacilityLogo()) && (TextUtils.isEmpty(facility_profile))) {
                    Utils.displayToast(getContext(), "First, select profile photo!");
                    return false;
                }
                if (TextUtils.isEmpty(edName)) {
                    Utils.displayToast(getContext(), "Enter Facility Name !");
                    return false;
                }
                if (!matcher1.find()) {
                    Utils.displayToast(getContext(), "Enter Name Proper Format. First Name Must Contain Only Alphabet and Space !");
                    return false;
                }
                if (viewModel.selected_facility_type == 0) {
                    Utils.displayToast(getContext(), "Select Facility Type First !");
                    return false;
                }
                if (TextUtils.isEmpty(email)) {
                    Utils.displayToast(getContext(), "Enter Email ID");
                    return false;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Utils.displayToast(getContext(), "Enter Email ID In Proper Format !");
                    return false;
                }
                if (TextUtils.isEmpty(mobile) ||
                        (mobile.contains("+") && (mobile.length() < 13 || mobile.length() > 13)) ||
                        (!mobile.contains("+") && (mobile.length() < 10 || mobile.length() > 10))) {
                    Utils.displayToast(getContext(), "Enter Valid Mobile Nos Min 10 Digit Required !");
                    return false;
                }
                return true;
            }
        });

    }

    private void observer_view() {
        setup1Binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    setup1Binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    setup1Binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    setup1Binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    setup1Binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getFacilityType_List().observe(requireActivity(), new Observer<List<SpecialtyDatum>>() {
            @Override
            public void onChanged(List<SpecialtyDatum> fromList) {
                set_facility_type(fromList);
            }
        });
        viewModel.fetch_profile_setup_1();
    }

    private void set_facility_type(List<SpecialtyDatum> fromList) {
        String value_id = model.getFacilityType();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_facility_type = getIndexFromList(value_id, fromList);
                setup1Binding.tvFacilityType.setText("" + model.getFacilityTypeDefinition());
            }
        }
    }

    private void showOptionPopup_Filter(int type, TextView textView, View view1, LinearLayout img1, List<SpecialtyDatum> list_nurse_degrees, ItemCallback itemCallback) {
        if (list_nurse_degrees == null || list_nurse_degrees.size() == 0) {
            Utils.displayToast(getContext(), "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(getContext());
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(getActivity().getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
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
        adapter_degree = new WorkHistoryWindowAdapter(getActivity(), type,
                Collections.singletonList(list_nurse_degrees),
                new WorkHistoryWindowAdapter.WorkHistoryWindowInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        if (type == 7) {
                            viewModel.selected_facility_type = position;
                            SpecialtyDatum obj = viewModel.getFacilityType_List(position);
                            if (obj != null)
                                textView.setText(obj.getName());
                        }
                        itemCallback.onClick(position);
                        popup.dismiss();
                    }
                });


        recyclerView.setAdapter(adapter_degree);
        recyclerView.scrollToPosition(viewModel.selected_facility_type);

        popup.showAsDropDown(view1, 0, 0);
    }

    private int getIndexFromList(String id, List<SpecialtyDatum> list) {
        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            SpecialtyDatum workLocationModel = list.get(i);
            if (workLocationModel.getId().toString().equals(id.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private boolean checkItemInList(String id, List<SpecialtyDatum> list) {
        for (SpecialtyDatum specialtyDatum : list) {
            if (specialtyDatum.getId().toString().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private ActivityResultLauncher<Intent> resultLauncherProfile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            File file = null;
//                    try {
            if (result.getData() == null || result.getData().getScheme() == null) {
                Utils.displayToast(getContext(), "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                return;
            }
            try {
                file = new File(FileUtils.getPath(getContext(), data.getData()));
            } catch (Exception e) {
                Log.d("TAG", ": " + e.getMessage());
            }
            String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
            Log.d("TAG", file.length() + " size : " + str);
            if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                    || file.getName().endsWith(".jpeg"))) {
                Utils.displayToast(getContext(), "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                return;
            }
            try {
                double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                if ((sis > 1 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                    Utils.displayToast(getContext(), "Select File less than equal to 5 MB");
                    return;
                }
            } catch (NumberFormatException exception) {
                Log.d("TAG", ": " + exception.getMessage());
            }
            if (file != null) {
                facility_profile = file.getAbsolutePath();
                Glide.with(getContext()).load(file.getAbsolutePath()).
                        into(imgProfile);
                imgProfile.setVisibility(View.VISIBLE);
            }

        }
    });

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 229) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    Utils.displayToast(getContext(), "Permission Allowed");
                } else {
                    Utils.displayToast(getContext(), "Allow permission for storage access!");
                }
            }
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
                        Utils.displayToast(getContext(), "Permission Allowed");
                    } else {
                        Utils.displayToast(getContext(), "Allow permission for storage access!");
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
                intent.setData(Uri.parse(String.format("package:%s", getContext().getPackageName())));
                startActivityForResult(intent, 229);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 229);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(requireActivity(), new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE},
                    123);
        }
    }

    private boolean checkReadExternal() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
}