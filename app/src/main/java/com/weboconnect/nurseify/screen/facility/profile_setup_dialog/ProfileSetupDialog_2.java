package com.weboconnect.nurseify.screen.facility.profile_setup_dialog;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import static com.weboconnect.nurseify.utils.Utils.patternAddress;
import static com.weboconnect.nurseify.utils.Utils.patternNumbers;

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
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.PersonalDetailWindowAdapter;
import com.weboconnect.nurseify.databinding.DialogProfileSetup2Binding;
import com.weboconnect.nurseify.intermediate.API_ResponseCallback;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
import com.weboconnect.nurseify.screen.facility.viewModel.RegistrationViewModel;
import com.weboconnect.nurseify.screen.nurse.model.CityDatum;
import com.weboconnect.nurseify.screen.nurse.model.CityModel;
import com.weboconnect.nurseify.screen.nurse.model.State_Datum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.FileUtils;
import com.weboconnect.nurseify.utils.Utils;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSetupDialog_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSetupDialog_2 extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private @NonNull
    DialogProfileSetup2Binding setup1Binding;
    private ImageView imgHead;
    private RegistrationViewModel viewModel;
    private FacilityProfile model;
    private String img_head_cno;

    public ProfileSetupDialog_2() {
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
    public static ProfileSetupDialog_2 newInstance(int param1, String param2) {
        ProfileSetupDialog_2 fragment = new ProfileSetupDialog_2();
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
        setup1Binding = DialogProfileSetup2Binding.inflate(getLayoutInflater(), container, false);
        setCancelable(false);

        imgHead = setup1Binding.imgHeadCNo;
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        if (viewModel.isEditMode) {
            model = viewModel.main_model;
        } else {
            model = viewModel.new_facilityModel;
        }
        observer_view();
        setData();
        valid_textChange_view();
        click();

        return setup1Binding.getRoot();
    }

    private void observer_view() {
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
        viewModel.getList_State().observe(requireActivity(), new Observer<List<State_Datum>>() {
            @Override
            public void onChanged(List<State_Datum> list_State) {
                find_state_observer(list_State);
            }
        });
    }

    private void valid_textChange_view() {
        setup1Binding.edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edAddress.setError(null);
                else if (!patternAddress.matcher(s.toString()).find() || s.toString().length() < 2) {
                    setup1Binding.edAddress.setError("Address Can Contain Alphabets, Numbers, Symbol And Min Length Required Is 2 !");
                } else {
                    setup1Binding.edAddress.setError(null);
                }
            }
        });
        setup1Binding.edPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edPostalCode.setError(null);
                else if (!patternNumbers.matcher(s.toString()).find() || s.toString().length() < 5) {
                    setup1Binding.edPostalCode.setError("Post Code Can Contain Numbers Only And Min Digits Required Is 5!");
                } else {
                    setup1Binding.edPostalCode.setError(null);
                }
            }
        });
        setup1Binding.edWebsite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (!(Patterns.WEB_URL.matcher(s).find())) {
                        setup1Binding.edYoutube.setError("Enter Website URL In Proper Format !");
                    }
                } else {
                    setup1Binding.edWebsite.setError(null);
                }
            }
        });
        setup1Binding.edYoutube.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (!(Utils.patternYoutubeURL.matcher(s).find())) {
                        setup1Binding.edYoutube.setError("Enter Valid Youtube/Vinmo URL In Proper Format !");
                    }
                } else {
                    setup1Binding.edYoutube.setError(null);
                }
            }
        });

    }

    private void click() {
        setup1Binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Cancel, mParam1));
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state_click_action();
            }
        });
        setup1Binding.layCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.selected_state == 0) {
                    Utils.displayToast(getContext(), "Select State First !");
                    return;
                }
                if (viewModel.list_City == null || viewModel.list_City.getValue() == null ||
                        viewModel.list_City.getValue().size() == 0) {
                    Utils.displayToast(getContext(), "No City Found For Select State !");
                    return;
                }
                Utils.hideKeyboardFrom(getContext(), v);
                showOptionPopup_City(getContext(), setup1Binding.view1, 7,
                        setup1Binding.img1, setup1Binding.tvCity
                        , viewModel.getList_City().getValue(), viewModel.selected_City, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_City = position;
                                setup1Binding.tvCity
                                        .setText("" + viewModel.getList_City().getValue()
                                                .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layHeadCno.setOnClickListener(new View.OnClickListener() {
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
                resultHEadCNO.launch(intent);
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.next.setOnClickListener(new View.OnClickListener() {
            String address, city, postal_code, state = "", website = "", youtube = "", senior, about;

            @Override
            public void onClick(View v) {
                FacilityProfile facilityProfile = null;
                if (viewModel.isEditMode) {
                    facilityProfile = viewModel.main_model;
                } else {
                    facilityProfile = viewModel.new_facilityModel;
                }
                if (checkValidation()) {
                    if (Utils.isNetworkAvailable(getContext())) {
                        facilityProfile = setupFieldData(facilityProfile);
                        if (viewModel.isEditMode) {
                            viewModel.main_model = facilityProfile;
                            viewModel.perform_profile_submission((RegistrationFActivity) getActivity(), mParam1);
                        } else {
                            viewModel.new_facilityModel = facilityProfile;
                            viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Done, mParam1));
                        }
                    } else {
                        Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
                    }
                }
            }

            private FacilityProfile setupFieldData(FacilityProfile facilityProfile) {
                address = setup1Binding.edAddress.getText().toString();
                postal_code = setup1Binding.edPostalCode.getText().toString();
                state = viewModel.getList_State().getValue().get(viewModel.selected_state).getIso_name();
                city = viewModel.getList_City().getValue().get(viewModel.selected_City).getName();
                senior = setup1Binding.edSeniorLead.getText().toString().trim();
                about = setup1Binding.edFacility.getText().toString().trim();
                website = setup1Binding.edWebsite.getText().toString();
                youtube = setup1Binding.edYoutube.getText().toString();
                if (TextUtils.isEmpty(senior)) senior = "";
                if (TextUtils.isEmpty(about)) about = "";
                if (TextUtils.isEmpty(website)) website = "";
                if (TextUtils.isEmpty(youtube)) youtube = "";
                facilityProfile.setFacilityAddress(address);
                facilityProfile.setFacilityPostcode(postal_code);
                facilityProfile.setFacilityState(state);
                facilityProfile.setFacilityCity(city);
                facilityProfile.setCnoMessage(senior);
                facilityProfile.setAboutFacility(about);
                facilityProfile.setFacilityWebsite(website);
                facilityProfile.setVideoEmbedUrl(youtube);
                if (TextUtils.isEmpty(img_head_cno))
                    facilityProfile.setCnoImage_new(img_head_cno);
                return facilityProfile;
            }

            private boolean checkValidation() {

                address = setup1Binding.edAddress.getText().toString().trim();
                postal_code = setup1Binding.edPostalCode.getText().toString().trim();
                senior = setup1Binding.edSeniorLead.getText().toString().trim();
                about = setup1Binding.edFacility.getText().toString().trim();
                website = setup1Binding.edWebsite.getText().toString().trim();
                youtube = setup1Binding.edWebsite.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    Utils.displayToast(getContext(), "Enter Address First !");
                    return false;
                }
                if (!patternAddress.matcher(address).find() || address.toString().length() < 2) {
                    Utils.displayToast(getContext(), "Address Can Contain Alphabets, Numbers, Symbol And Min Length Required Is 2 !");
                    return false;
                }

                if (viewModel.selected_state == 0) {
                    Utils.displayToast(getContext(), "Select State First !");
                    return false;
                }
                if (viewModel.selected_City == 0) {
                    Utils.displayToast(getContext(), "Select City First !");
                    return false;
                }

                if (!patternNumbers.matcher(postal_code).find()) {
                    Utils.displayToast(getContext(), "Post Code Can Contain Numbers Only And Min Digits Required Is 5!");
                    return false;
                }
                if (TextUtils.isEmpty(postal_code)) {
                    Utils.displayToast(getContext(), "Enter Postal Code First !");
                    return false;
                }
                if (!(viewModel.isEditMode == true && viewModel.option_call == 1)) {
                    if (!TextUtils.isEmpty(website) && !(Patterns.WEB_URL.matcher(website).find())) {
                        setup1Binding.edYoutube.setError("Enter Website URL In Proper Format !");
                        return false;
                    }
                    if (!TextUtils.isEmpty(youtube) && !(Utils.patternYoutubeURL.matcher(website).find())) {
                        setup1Binding.edYoutube.setError("Enter Youtube/Vinmo URL In Proper Format !");
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private void setData() {

        if (viewModel.getList_State() == null
                || viewModel.getList_State().getValue() == null
                || viewModel.getList_State().getValue().size() == 0) {
            data_setup();
            viewModel.fetch_profile_setup_2(Constant.UNITED_STATE_CODE);
        } else {
            data_setup();
        }
    }

    private void data_setup() {
        String address, city, postal_code, state = "", img = "", web = "", you = "", senior, about;

        if (viewModel.isEditMode) {
            if (model == null)
                return;
            if (viewModel.option_call == 1) {
                setup1Binding.layPart2.setVisibility(View.GONE);
                setup1Binding.next.setText("Submit");
            } else if (viewModel.option_call == 2) {
                setup1Binding.layPart1.setVisibility(View.GONE);
                setup1Binding.next.setText("Submit");
            }
            address = model.getFacilityAddress();
            postal_code = model.getFacilityPostcode();
            state = model.getFacilityState();
            city = model.getFacilityCity();
            img = model.getCnoImage();
            web = model.getFacilityWebsite();
            you = model.getVideoEmbedUrl();
            senior = model.getCnoMessage();
            about = model.getAboutFacility();
        } else {
            address = viewModel.new_facilityModel.getFacilityAddress();
            postal_code = viewModel.new_facilityModel.getFacilityPostcode();
            state = viewModel.new_facilityModel.getFacilityState();
            city = viewModel.new_facilityModel.getFacilityCity();
            img = viewModel.new_facilityModel.getCnoImage();
            web = viewModel.new_facilityModel.getFacilityWebsite();
            you = viewModel.new_facilityModel.getVideoEmbedUrl();
            senior = viewModel.new_facilityModel.getCnoMessage();
            about = viewModel.new_facilityModel.getAboutFacility();
        }
        if (TextUtils.isEmpty(address)) {
            address = "";
        }
        if (TextUtils.isEmpty(postal_code)) {
            postal_code = "";
        }
        if (TextUtils.isEmpty(you)) {
            you = "";
        }
        if (TextUtils.isEmpty(web)) {
            web = "";
        }
        if (TextUtils.isEmpty(senior)) {
            senior = "";
        }
        if (TextUtils.isEmpty(about)) {
            about = "";
        }
        if (TextUtils.isEmpty(img)) {
            img = "";
        } else {
            Glide.with(getContext()).load(img).placeholder(R.drawable.place_holder_img)
                    .error(R.drawable.place_holder_img).into(imgHead);
            imgHead.setVisibility(View.VISIBLE);
        }
        setup1Binding.edAddress.setText(address);
        setup1Binding.edSeniorLead.setText(Html.fromHtml(senior));
        setup1Binding.edFacility.setText(Html.fromHtml(about));
        setup1Binding.edWebsite.setText(web);
        setup1Binding.edYoutube.setText(you);
        setup1Binding.edPostalCode.setText(postal_code);
    }

    private void state_click_action() {
        showOptionPopup_State(getContext(), setup1Binding.view2, 3,
                setup1Binding.img2, setup1Binding.tvState
                , new ItemCallback() {
                    @Override
                    public void onClick(int position) {
                        viewModel.selected_state = position;
                        setup1Binding.tvState.setText("" + viewModel.getList_State()
                                .getValue().get(position).getNames());

                        call_city_task(position, false);

                    }
                });
    }

    private void call_city_task(int position, boolean status) {
        viewModel.fetch_state_by_City(viewModel.getList_State().getValue()
                        .get(position).getState_id(),
                new API_ResponseCallback() {
                    @Override
                    public void onShowProgress() {
                        setup1Binding.layProgress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSucces(Object models) {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                        if (models instanceof CityModel) {
                            CityModel combineData = (CityModel) models;
                            try {

                                viewModel.selected_City = 0;
                                setup1Binding.tvCity.setText("" + viewModel.getList_City().getValue()
                                        .get(viewModel.selected_City).getName());
                                if (status) {
                                    set_city_init_data(viewModel.getList_City().getValue());
                                }

                            } catch (Exception e) {
                                Log.d("TAG", "personalDialog2 apicallback success() onError: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailed(String s) {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                        Utils.displayToast(getContext(), s);
                    }

                    @Override
                    public void onError() {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                    }
                });
    }

    private void find_state_observer(List<State_Datum> list_State) {
        if (model != null && list_State != null && list_State.size() != 0) {
            if (!TextUtils.isEmpty(model.getFacilityState())) {
                if (checkItemInList_State(model.getFacilityState(), list_State)) {
                    setup1Binding.tvState.setText(list_State.get(
                            getIndexFromList_State(model.getFacilityState(), list_State)).getNames());
                    viewModel.selected_state = getIndexFromList_State(model.getFacilityState(), list_State);

                    call_city_task(viewModel.selected_state, true);
                }
            } else {
                viewModel.selected_state = 0;
            }
        }
    }

    private void set_city_init_data(List<CityDatum> list_City) {
        if (list_City != null && list_City != null && list_City.size() != 0) {
            if (!TextUtils.isEmpty(model.getFacilityCity())) {
                if (checkItemInList_City(model.getFacilityCity(), list_City)) {
                    setup1Binding.tvCity.setText(list_City.get(
                            getIndexFromList_City(model.getFacilityCity(), list_City)).getName());
                    viewModel.selected_City = getIndexFromList_City(model.getFacilityCity(), list_City);
                }
            } else {
                viewModel.selected_City = 0;
            }
        }
    }

    private int getIndexFromList_City(String collegeUniCity, List<CityDatum> list_city) {
        int pos = 0;
        for (int i = 0; i < list_city.size(); i++) {
            CityDatum workLocationModel = list_city.get(i);
            if (workLocationModel.getName().toString().equals(collegeUniCity.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private boolean checkItemInList_City(String collegeUniCity, List<CityDatum> list_city) {
        for (CityDatum workLocationModel :
                list_city) {
            if (workLocationModel.getName().toString().equals(collegeUniCity)) {
                return true;
            }

        }
        return false;
    }

    private boolean checkItemInList_State(String state, List<State_Datum> list_state) {
        for (State_Datum workLocationModel :
                list_state) {
            if (!TextUtils.isEmpty(workLocationModel.getIso_name()) && workLocationModel.getIso_name().toString().equals(state)) {
                return true;
            }

        }
        return false;
    }

    private int getIndexFromList_State(String state, List<State_Datum> list_state) {
        int pos = 0;
        for (int i = 0; i < list_state.size(); i++) {

            State_Datum workLocationModel = list_state.get(i);
            if (!TextUtils.isEmpty(workLocationModel.getIso_name()) && workLocationModel.getIso_name().toString().equals(state.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private void showOptionPopup_State(Context context, View v, int type, ImageView img1,
                                       TextView tvState, ItemCallback itemCallback) {
        if (viewModel.getList_State() == null || viewModel.getList_State().getValue() == null || viewModel.getList_State().getValue().size() == 0) {
            Utils.displayToast(getContext(), "No Data For State Found");
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
        PersonalDetailWindowAdapter parentChildAdapter =
                new PersonalDetailWindowAdapter(((RegistrationFActivity) getActivity()),
                        viewModel.getList_State().getValue(), viewModel.getSelected_state(),
                        new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
                            @Override
                            public void onCLickItem(int i, int position) {
                                itemCallback.onClick(i);
                                popup.dismiss();
                            }
                        });
        parentChildAdapter.setType(type);
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(viewModel.selected_state);
        popup.showAsDropDown(v, 0, 0);
    }

    private void showOptionPopup_City(Context context, View v, int type, ImageView img1,
                                      TextView tvState, List<CityDatum> cityData, int selected_City, ItemCallback
                                              itemCallback) {
        if (cityData == null || cityData.size() == 0) {
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

        PersonalDetailWindowAdapter parentChildAdapter
                = new PersonalDetailWindowAdapter(((RegistrationFActivity) getActivity())
                , type
                , cityData, selected_City, new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
            @Override
            public void onCLickItem(int position, int i) {
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(selected_City);
        popup.showAsDropDown(v, 0, 0);
    }

    private ActivityResultLauncher<Intent> resultHEadCNO = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
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
                        img_head_cno = file.getAbsolutePath();
                        Glide.with(getContext()).load(file.getAbsolutePath()).
                                into(imgHead);
                        imgHead.setVisibility(View.VISIBLE);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
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