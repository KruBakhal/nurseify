package com.weboconnect.nurseify.screen.facility.profile_setup_dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.CommonDropDownAdapter;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.databinding.DialogProfileSetup3Binding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
import com.weboconnect.nurseify.screen.facility.viewModel.RegistrationViewModel;
import com.weboconnect.nurseify.utils.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSetupDialog_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSetupDialog_3 extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private DialogProfileSetup3Binding setup1Binding;
    private RoundedImageView imgProfile;
    private RegistrationViewModel viewModel;
    private FacilityProfile model;
    private String facility_profile;

    public ProfileSetupDialog_3() {
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
    public static ProfileSetupDialog_3 newInstance(int param1, String param2) {
        ProfileSetupDialog_3 fragment = new ProfileSetupDialog_3();
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
        setup1Binding = DialogProfileSetup3Binding.inflate(getLayoutInflater(), container, false);
        setCancelable(false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        if (viewModel.isEditMode) {
            model = viewModel.main_model;
        } else {
            model = viewModel.new_facilityModel;
        }
        observer_View();
        setData();
        textChange_valid();
        click();
        return setup1Binding.getRoot();
    }

    private void textChange_valid() {

        setup1Binding.edBackground.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edBackground.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.edBackground.setError("Enter Other Background Check Provider In Proper Format Only Alphabets And Space Allowed !");
                } else {
                    setup1Binding.edBackground.setError(null);
                }
            }
        });
        setup1Binding.edEmr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edEmr.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.edEmr.setError("Enter Other Electronic Medical Records (EMR) In Proper Format Only Alphabets And Space Allowed !");
                } else {
                    setup1Binding.edEmr.setError(null);
                }
            }
        });
        setup1Binding.edOtherSoft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edOtherSoft.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.edOtherSoft.setError("Enter Other Nurse Credentialing Software In Proper Format Only Alphabets And Space Allowed !");
                } else {
                    setup1Binding.edOtherSoft.setError(null);
                }
            }
        });

    }

    private void setData() {
        if (check_any_list_empty()) {
            viewModel.fetch_profile_setup_3();
        } else {
            set_check(viewModel.getList_bg_check().getValue());
            set_emr(viewModel.getList_emr().getValue());
            set_soft(viewModel.getList_software().getValue());

        }
    }

    private boolean check_any_list_empty() {

        if ((viewModel.getList_emr() == null || viewModel.getList_emr().getValue() == null
                || viewModel.getList_emr().getValue().size() == 0) ||
                (viewModel.getList_bg_check() == null || viewModel.getList_bg_check().getValue() == null
                        || viewModel.getList_bg_check().getValue().size() == 0) ||
                (viewModel.getList_software() == null
                        || viewModel.getList_software().getValue() == null || viewModel.getList_software().getValue().size() == 0)) {
            return true;
        }
        return false;
    }

    private void click() {
        setup1Binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.isEditMode && viewModel.option_call==3) {
                    getActivity().finish();
                } else
                    viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Cancel, mParam1));
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layEmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view1, 1, setup1Binding.img1,
                        setup1Binding.tvEmr,
                        viewModel.getList_emr().getValue(), viewModel.selected_emr, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_emr = position;
                                if (viewModel.getList_emr().getValue().get(viewModel.selected_emr).getId().equals(0)) {
                                    setup1Binding.edEmr.setText("");
                                    setup1Binding.viewEmr.setVisibility(View.VISIBLE);
                                } else {
                                    setup1Binding.viewEmr.setVisibility(View.GONE);
                                }
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view2, 2, setup1Binding.img2,
                        setup1Binding.tvBackground,
                        viewModel.getList_bg_check().getValue(), viewModel.selected_bg_check, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_bg_check = position;
                                if (viewModel.getList_bg_check().getValue().get(viewModel.selected_bg_check).getId().equals(0)) {
                                    setup1Binding.edBackground.setText("");
                                    setup1Binding.viewBg.setVisibility(View.VISIBLE);
                                } else {
                                    setup1Binding.viewBg.setVisibility(View.GONE);
                                }
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view3, 3, setup1Binding.img3,
                        setup1Binding.tvSoft,
                        viewModel.getList_software().getValue(), viewModel.selected_soft, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_soft = position;
                                if (viewModel.getList_software().getValue().get(viewModel.selected_soft).getId().equals(0)) {
                                    setup1Binding.edOtherSoft.setText("");
                                    setup1Binding.viewSoft.setVisibility(View.VISIBLE);
                                } else {
                                    setup1Binding.viewSoft.setVisibility(View.GONE);
                                }
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacilityProfile facilityProfile = null;
                if (viewModel.isEditMode) {
                    facilityProfile = viewModel.main_model;
                } else {
                    facilityProfile = viewModel.new_facilityModel;
                }
                if (checkValidation()) {
                    int EMR = viewModel.getList_emr().getValue().get(viewModel.selected_emr).getId();
                    int BG = viewModel.getList_bg_check().getValue().get(viewModel.selected_bg_check).getId();
                    int Soft = viewModel.getList_software().getValue().get(viewModel.selected_soft).getId();
                    String tvEMR = viewModel.getList_emr().getValue().get(viewModel.selected_emr).getName();
                    String tvBG = viewModel.getList_bg_check().getValue().get(viewModel.selected_bg_check).getName();
                    String tvSoft = viewModel.getList_software().getValue().get(viewModel.selected_soft).getName();
                    String edEMR = setup1Binding.edEmr.getText().toString();
                    String edBG = setup1Binding.edBackground.getText().toString();
                    String edSOFT = setup1Binding.edOtherSoft.getText().toString();

                    facilityProfile.setFacilityEmr(String.valueOf(EMR));
                    facilityProfile.setFacilityEmr_Other(edEMR);
                    facilityProfile.setFacilityEmrDefinition(tvEMR);
                    facilityProfile.setFacilityBcheckProvider(String.valueOf(BG));
                    facilityProfile.setFacilityBcheckProvider_Other(edBG);
                    facilityProfile.setFacilityBcheckProviderDefinition(tvBG);
                    facilityProfile.setNurseCredSoft(String.valueOf(Soft));
                    facilityProfile.setNurseCredSoft_other(edSOFT);
                    facilityProfile.setNurseCredSoftDefinition(tvSoft);
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

            private boolean checkValidation() {

                if (viewModel.selected_emr == 0) {
                    Utils.displayToast(getContext(), "Select Electronic Medical Records");
                    return false;
                }
                if (viewModel.list_emr != null && viewModel.list_emr.getValue() != null
                        && viewModel.list_emr.getValue().size() != 0 &&
                        viewModel.list_emr.getValue().get(viewModel.selected_emr).getId().equals(0)) {
                    if (TextUtils.isEmpty(setup1Binding.edEmr.getText().toString())) {
                        Utils.displayToast(getContext(), "Enter Other Electronic Medical Records In Proper Format Only Alphabets And Space Allowed !");
                        return false;
                    }
                }

                if (viewModel.selected_bg_check == 0) {
                    Utils.displayToast(getContext(), "Select Background Check Provider");
                    return false;
                }

                if (viewModel.list_bg_check != null && viewModel.list_bg_check.getValue() != null
                        && viewModel.list_bg_check.getValue().size() != 0 &&
                        viewModel.list_bg_check.getValue().get(viewModel.selected_bg_check).getId().equals(0)) {
                    if (TextUtils.isEmpty(setup1Binding.edBackground.getText().toString())) {
                        Utils.displayToast(getContext(), "Enter Other Background Check Provider In Proper Format Only Alphabets And Space Allowed !");
                        return false;
                    }
                }
                if (viewModel.selected_soft == 0) {
                    Utils.displayToast(getContext(), "Select Nurse Credentialing Software");
                    return false;
                }
                if (viewModel.list_nurse_credentialing != null && viewModel.list_nurse_credentialing.getValue() != null
                        && viewModel.list_nurse_credentialing.getValue().size() != 0 &&
                        viewModel.getList_software().getValue().get(viewModel.selected_soft).getId().equals(0)) {
                    if (TextUtils.isEmpty(setup1Binding.edOtherSoft.getText().toString())) {
                        Utils.displayToast(getContext(), "Enter Other Nurse Credentialing Software In Proper Format Only Alphabets And Space Allowed !");
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private void observer_View() {
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

        viewModel.getList_emr().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_emr(fromList);
            }
        });
        viewModel.getList_bg_check().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_check(fromList);
            }
        });
        viewModel.getList_software().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_soft(fromList);
            }
        });

    }

    private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                 TextView tvEmr, List<CommonDatum> cityData, int selected_City, ItemCallback itemCallback) {
        if (cityData == null || cityData.size() == 0) {
            Utils.displayToast(context, "dropdown list is empty");
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

        CommonDropDownAdapter parentChildAdapter
                = new CommonDropDownAdapter(((RegistrationFActivity) getActivity())
                , type
                , selected_City, cityData, new CommonDropDownAdapter.CommonDropDownInterface() {
            @Override
            public void onCLickItem(int position, int i) {
                tvEmr.setText(cityData.get(position).getName());
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(selected_City);
        popup.showAsDropDown(v, 0, 0);
    }

    private void set_soft(List<CommonDatum> fromList) {
        String value_id = model.getNurseCredSoft();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_soft = getIndexFromList(value_id, fromList);
                setup1Binding.tvSoft.setText("" + model.getNurseCredSoftDefinition());
                if (viewModel.selected_soft == (fromList.size() - 1)) {
                    setup1Binding.edOtherSoft.setText(model.getNurseCredSoft_other());
                    setup1Binding.viewSoft.setVisibility(View.VISIBLE);
                }
            } else {
                viewModel.selected_soft = 0;
            }
        } else {
            viewModel.selected_soft = 0;
        }
    }

    private void set_check(List<CommonDatum> fromList) {
        String value_id = model.getFacilityBcheckProvider();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_bg_check = getIndexFromList(value_id, fromList);
                setup1Binding.tvBackground.setText("" + model.getFacilityBcheckProviderDefinition());
                if (viewModel.selected_bg_check == (fromList.size() - 1)) {
                    setup1Binding.edBackground.setText(model.getFacilityBcheckProvider_Other());
                    setup1Binding.viewBg.setVisibility(View.VISIBLE);
                }
            } else {
                viewModel.selected_bg_check = 0;
            }

        } else {
            viewModel.selected_bg_check = 0;
        }
    }

    private void set_emr(List<CommonDatum> fromList) {
        String value_id = model.getFacilityEmr();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_emr = getIndexFromList(value_id, fromList);
                setup1Binding.tvEmr.setText("" + model.getFacilityEmrDefinition());
                if (viewModel.selected_emr == (fromList.size() - 1)) {
                    setup1Binding.edEmr.setText(model.getFacilityEmr_Other());
                    setup1Binding.viewEmr.setVisibility(View.VISIBLE);
                }
            } else {
                viewModel.selected_emr = 0;
            }

        } else {
            viewModel.selected_emr = 0;
        }
    }

    private int getIndexFromList(String id, List<CommonDatum> list) {
        int pos = 0;
        for (int i = 0; i < list.size(); i++) {
            CommonDatum workLocationModel = list.get(i);
            if (workLocationModel.getId().toString().equals(id.toString())) {
                pos = i;
            }
        }
        return pos;
    }

    private boolean checkItemInList(String id, List<CommonDatum> list) {
        for (CommonDatum workLocationModel :
                list) {
            if (workLocationModel.getId().toString().equals(id)) {
                return true;
            }

        }
        return false;
    }

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
}