package com.weboconnect.nurseify.screen.facility.profile_setup_dialog;

import static com.weboconnect.nurseify.utils.Utils.patternAlphabetNumbers;

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
import com.weboconnect.nurseify.databinding.DialogProfileSetup4Binding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
import com.weboconnect.nurseify.screen.facility.viewModel.RegistrationViewModel;
import com.weboconnect.nurseify.utils.Utils;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSetupDialog_4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSetupDialog_4 extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private DialogProfileSetup4Binding setup1Binding;
    private RoundedImageView imgProfile;
    private RegistrationViewModel viewModel;
    private FacilityProfile model;
    private String facility_profile;

    public ProfileSetupDialog_4() {
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
    public static ProfileSetupDialog_4 newInstance(int param1, String param2) {
        ProfileSetupDialog_4 fragment = new ProfileSetupDialog_4();
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
        setup1Binding = DialogProfileSetup4Binding.inflate(getLayoutInflater(), container, false);
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
        setup1Binding.edAttendance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edAttendance.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.edAttendance.setError("Enter Other Time & Attendance System Proper Format Only Alphabets And Space Allowed !");
                } else {
                    setup1Binding.edAttendance.setError(null);
                }
            }
        });
        setup1Binding.edScheduling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edScheduling.setError(null);
                else if (!Utils.patternLettersSpace.matcher(s.toString()).find()) {
                    setup1Binding.edScheduling.setError("Enter Other Nurse Scheduling System In Proper Format Only Alphabets And Space Allowed !");
                } else {
                    setup1Binding.edScheduling.setError(null);
                }
            }
        });
        setup1Binding.edLicenseBed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    setup1Binding.edLicenseBed.setError(null);
                else if (!Utils.patternNumbers.matcher(s.toString()).find()) {
                    setup1Binding.edLicenseBed.setError("Licensed Beds Can Contain Only" +
                            "Numbers,No Space And Max Length Is 20 !");
                } else {
                    setup1Binding.edLicenseBed.setError(null);
                }
            }
        });

    }

    private void setData() {
        if (viewModel.isEditMode && viewModel.option_call == 3) {
            setup1Binding.next.setText("Submit");
        }
        if (check_any_list_empty()) {
            viewModel.fetch_profile_setup_4();
            if (!TextUtils.isEmpty(model.getLicensedBeds()))
                setup1Binding.edLicenseBed.setText(model.getLicensedBeds());
        } else {
            set_scheduling(viewModel.getList_scheduling().getValue());
            set_attendance(viewModel.getList_attendance().getValue());
            set_trauma(viewModel.getList_software().getValue());
            if (!TextUtils.isEmpty(model.getLicensedBeds()))
                setup1Binding.edLicenseBed.setText(model.getLicensedBeds());
        }
    }

    private boolean check_any_list_empty() {

        if ((viewModel.getList_scheduling() == null || viewModel.getList_scheduling().getValue() == null
                || viewModel.getList_scheduling().getValue().size() == 0) ||
                (viewModel.getList_attendance() == null || viewModel.getList_attendance().getValue() == null
                        || viewModel.getList_attendance().getValue().size() == 0) ||
                (viewModel.getList_trauma() == null
                        || viewModel.getList_trauma().getValue() == null || viewModel.getList_trauma().getValue().size() == 0)) {
            return true;
        }
        return false;
    }

    private void click() {
        setup1Binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Cancel, mParam1));
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layScheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view1, 4, setup1Binding.img1,
                        setup1Binding.tvScheduling,
                        viewModel.getList_scheduling().getValue(), viewModel.selected_scheduling, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_scheduling = position;
                                if (viewModel.getList_scheduling().getValue().get(viewModel.selected_scheduling).getId().equals(0)) {
                                    setup1Binding.edScheduling.setText("");
                                    setup1Binding.viewSchd.setVisibility(View.VISIBLE);
                                } else {
                                    setup1Binding.viewSchd.setVisibility(View.GONE);
                                }
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view2, 5, setup1Binding.img2,
                        setup1Binding.tvAttendance,
                        viewModel.getList_attendance().getValue(), viewModel.selected_attendance, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_attendance = position;
                                if (viewModel.getList_attendance().getValue().get(viewModel.selected_attendance).getId().equals(0)) {
                                    setup1Binding.edAttendance.setText("");
                                    setup1Binding.viewTime.setVisibility(View.VISIBLE);
                                } else {
                                    setup1Binding.viewTime.setVisibility(View.GONE);
                                }
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        setup1Binding.layTrauma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(getContext(), setup1Binding.view3, 6, setup1Binding.img3,
                        setup1Binding.tvTrauma,
                        viewModel.getList_trauma().getValue(), viewModel.selected_trauma, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_trauma = position;
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
                    int Sch = viewModel.getList_scheduling().getValue().get(viewModel.selected_scheduling).getId();
                    int Att = viewModel.getList_attendance().getValue().get(viewModel.selected_attendance).getId();
                    int Trau = viewModel.getList_trauma().getValue().get(viewModel.selected_trauma).getId();
                    String tvSch = viewModel.getList_scheduling().getValue().get(viewModel.selected_trauma).getName();
                    String tvAtt = viewModel.getList_attendance().getValue().get(viewModel.selected_attendance).getName();
                    String tvTra = viewModel.getList_trauma().getValue().get(viewModel.selected_trauma).getName();
                    String edScheduling = setup1Binding.edScheduling.getText().toString();
                    String edAttendance = setup1Binding.edAttendance.getText().toString();
                    String edLicenseBed = setup1Binding.edLicenseBed.getText().toString();

                    facilityProfile.setNurseSchedulingSys(String.valueOf(Sch));
                    facilityProfile.setNurseSchedulingSys_other(edScheduling);
                    facilityProfile.setNurseSchedulingSysDefinition(tvSch);
                    facilityProfile.setTimeAttendSys(String.valueOf(Att));
                    facilityProfile.setTimeAttendSys_other(edAttendance);
                    facilityProfile.setTimeAttendSysDefinition(tvAtt);
                    facilityProfile.setTraumaDesignation(String.valueOf(Trau));
                    facilityProfile.setTraumaDesignationDefinition(tvTra);
                    facilityProfile.setLicensedBeds(edLicenseBed);

                    if (Utils.isNetworkAvailable(getContext())) {
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

            private boolean checkValidation() {
                String edLicenseNos = setup1Binding.edLicenseBed.getText().toString();
                if (viewModel.selected_scheduling == 0) {
                    Utils.displayToast(getContext(), "Select Nurse Scheduling System");
                    return false;
                }
                if (viewModel.list_scheduling != null && viewModel.list_scheduling.getValue() != null && viewModel.list_scheduling.getValue().size() != 0 &&
                        viewModel.selected_scheduling == (viewModel.list_scheduling.getValue().size() - 1)) {
                    if (TextUtils.isEmpty(setup1Binding.edScheduling.getText().toString())) {
                        Utils.displayToast(getContext(), "Enter Other Nurse Scheduling System !");
                        return false;
                    }
                }

                if (viewModel.selected_attendance == 0) {
                    Utils.displayToast(getContext(), "Select Time & Attendance System");
                    return false;
                }

                if (viewModel.list_attendance != null && viewModel.list_attendance.getValue() != null && viewModel.list_attendance.getValue().size() != 0 &&
                        viewModel.selected_attendance == (viewModel.list_attendance.getValue().size() - 1)) {
                    if (TextUtils.isEmpty(setup1Binding.edAttendance.getText().toString())) {
                        Utils.displayToast(getContext(), "Enter Other Time & Attendance System !");
                        return false;
                    }
                }
                if (TextUtils.isEmpty(edLicenseNos)) {
                    Utils.displayToast(getContext(), "Enter Licensed Beds First !");
                    return false;
                }
                Matcher matcher1 = patternAlphabetNumbers.matcher(edLicenseNos);
                if (!matcher1.find()) {
                    Utils.displayToast(getContext(), "Enter Licensed Beds in proper format only alphabet,no space and numbers are allowed !");
                    return false;
                }
                if (viewModel.selected_trauma == 0) {
                    Utils.displayToast(getContext(), "Select Trauma Designation");
                    return false;
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

        viewModel.getList_attendance().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_attendance(fromList);
            }
        });
        viewModel.getList_scheduling().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_scheduling(fromList);
            }
        });
        viewModel.getList_trauma().observe(this, new Observer<List<CommonDatum>>() {
            @Override
            public void onChanged(List<CommonDatum> fromList) {
                set_trauma(fromList);
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

    private void set_trauma(List<CommonDatum> fromList) {
        String value_id = model.getTraumaDesignation();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_trauma = getIndexFromList(value_id, fromList);
                setup1Binding.tvTrauma.setText("" + model.getTraumaDesignationDefinition());

            } else {
                viewModel.selected_trauma = 0;
            }
        } else {
            viewModel.selected_trauma = 0;
        }
    }

    private void set_scheduling(List<CommonDatum> fromList) {
        String value_id = model.getNurseSchedulingSys();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_scheduling = getIndexFromList(value_id, fromList);
                setup1Binding.tvScheduling.setText("" + model.getNurseSchedulingSysDefinition());
                if (viewModel.selected_scheduling == (fromList.size() - 1)) {
                    setup1Binding.edScheduling.setText(model.getNurseSchedulingSys_other());
                    setup1Binding.viewSchd.setVisibility(View.VISIBLE);
                }
            } else {
                viewModel.selected_scheduling = 0;
            }

        } else {
            viewModel.selected_scheduling = 0;
        }
    }

    private void set_attendance(List<CommonDatum> fromList) {
        String value_id = model.getTimeAttendSys();
        if (!TextUtils.isEmpty(value_id) && fromList != null && fromList.size() != 0) {
            if (checkItemInList(value_id, fromList)) {
                viewModel.selected_attendance = getIndexFromList(value_id, fromList);
                setup1Binding.tvAttendance.setText("" + model.getTimeAttendSysDefinition());
                if (viewModel.selected_emr == (fromList.size() - 1)) {
                    setup1Binding.edAttendance.setText(model.getTimeAttendSys_other());
                    setup1Binding.viewTime.setVisibility(View.VISIBLE);
                }
            } else {
                viewModel.selected_attendance = 0;
            }
        } else {
            viewModel.selected_attendance = 0;
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