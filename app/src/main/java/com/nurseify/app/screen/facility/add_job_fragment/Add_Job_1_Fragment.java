package com.nurseify.app.screen.facility.add_job_fragment;

import static com.nurseify.app.utils.Utils.dayOfMonth2;
import static com.nurseify.app.utils.Utils.dayOfMonth3;
import static com.nurseify.app.utils.Utils.formatter;
import static com.nurseify.app.utils.Utils.monthOfYear2;
import static com.nurseify.app.utils.Utils.monthOfYear3;
import static com.nurseify.app.utils.Utils.patternExp;
import static com.nurseify.app.utils.Utils.year2;
import static com.nurseify.app.utils.Utils.year3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.nurseify.app.AppController;
import com.nurseify.app.R;
import com.nurseify.app.adapter.CommonDropDownAdapter;
import com.nurseify.app.adapter.HourlyRateWindowAdapter;
import com.nurseify.app.adapter.SpecialtyAdapter;
import com.nurseify.app.adapter.WorkHistoryWindowAdapter;
import com.nurseify.app.common.CommonDatum;
import com.nurseify.app.databinding.ActivityAddJob1Binding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.Add_Jobs_Activity;
import com.nurseify.app.screen.facility.viewModel.Add_Job_ViewModel;
import com.nurseify.app.screen.facility.viewModel.DialogStatus;
import com.nurseify.app.screen.facility.viewModel.DialogStatusMessage;
import com.nurseify.app.screen.facility.viewModel.ProgressUIType;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.nurseify.app.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Add_Job_1_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    ActivityAddJob1Binding binding;
    View view;
    Add_Job_ViewModel viewModel;
    private SpecialtyAdapter daysOfWeekAdapter;
    private String date1;

    public Add_Job_1_Fragment() {

    }

    public static Add_Job_1_Fragment newInstance(int param1, String param2) {
        Add_Job_1_Fragment fragment = new Add_Job_1_Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_add_job1, null, false);
        viewModel = new ViewModelProvider(requireActivity()).get(Add_Job_ViewModel.class);
        observer_view();
        setData();
        click();
        return view = binding.getRoot();
    }

    private void setData() {
        if (viewModel.isFirstTime) {
            viewModel.isFirstTime = false;
            dayOfMonth2 = 0;
            monthOfYear2 = 0;
            year2 = 0;
            dayOfMonth3 = 0;
            monthOfYear3 = 0;
            year3 = 0;
        }
        AppController appController = (AppController) getActivity().getApplication();
        if (!checkDataEmpty()) {
            viewModel.fetch_add_job_data(appController);
        } else {
            viewModel.list_assignment_durations.setValue(appController.getList_assignment_durations());
            viewModel.list_senior_level.setValue(appController.getList_senior_level());
            viewModel.list_job_funcs.setValue(appController.getList_job_funcs());
            viewModel.list_preferred_shift_duration.setValue(appController.getList_preferred_shift());
            viewModel.list_speciality.setValue(appController.getList_speciality());
            viewModel.list_work_loc.setValue(appController.getList_work_loc());
            viewModel.list_days_of_week.setValue(appController.getList_days_of_week());
            viewModel.list_work_cerner.setValue(appController.getList_cerner());
            viewModel.list_work_medtech.setValue(appController.getList_medtech());
            viewModel.list_work_epic.setValue(appController.getList_epic());
            viewModel.list_preferred_shift.setValue(appController.getList__preferred_shift());
            binding.edExperience.setText(viewModel.experience_year);
            binding.edOther.setText(viewModel.other_exp);
            edit_portion();
        }
        setAdapter();


    }

    private void edit_portion() {
        try {
            if (viewModel.isEdit) {
                binding.textView3.setText("Edit Job");
                if (checkItemInList(viewModel.jobDatum.getPreferredAssignmentDuration(),
                        Collections.singletonList(viewModel.getList_assignment_durations()))) {
                    viewModel.selected_assignment_duration = getIndexFromList(viewModel.jobDatum.getPreferredAssignmentDuration(), Collections.singletonList(viewModel.getList_assignment_durations()));
                    binding.spinnerAssignment.setText("" + viewModel.jobDatum.getPreferredAssignmentDurationDefinition());
                }
                if (checkItemInList(String.valueOf(viewModel.jobDatum.getSeniorityLevel()), Collections.singletonList(viewModel.getList_senior_level()))) {
                    viewModel.selected_senior_level = getIndexFromList(String.valueOf(viewModel.jobDatum.getSeniorityLevel()), Collections.singletonList(viewModel.getList_senior_level()));
                    binding.spinnerSeniorityLevel.setText("" + viewModel.jobDatum.getSeniorityLevelDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getJobFunction(), Collections.singletonList(viewModel.getList_job_funcs()))) {
                    viewModel.selected_job_funcs = getIndexFromList(viewModel.jobDatum.getJobFunction(), Collections.singletonList(viewModel.getList_job_funcs()));
                    binding.spinnerJobFuncs.setText("" + viewModel.jobDatum.getJobFunctionDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getPreferredSpecialty(), Collections.singletonList(viewModel.getList_speciality()))) {
                    viewModel.selected_speciality = getIndexFromList(viewModel.jobDatum.getPreferredSpecialty(), Collections.singletonList(viewModel.getList_speciality()));
                    binding.tvSpecialty.setText("" + viewModel.jobDatum.getPreferredSpecialtyDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getPreferredShiftDuration(), Collections.singletonList(viewModel.getList_preferred_shift_duration()))) {
                    viewModel.selected_shift_duration = getIndexFromList(viewModel.jobDatum.getPreferredShiftDuration(), Collections.singletonList(viewModel.getList_preferred_shift_duration()));
                    binding.spinnerShiftDuration.setText("" + viewModel.jobDatum.getPreferredShiftDurationDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getPreferredWorkLocation(), Collections.singletonList(viewModel.getList_work_loc()))) {
                    viewModel.selected_work_loc = getIndexFromList(viewModel.jobDatum.getPreferredWorkLocation(), Collections.singletonList(viewModel.getList_work_loc()));
                    binding.spinnerWorkLoc.setText("" + viewModel.jobDatum.getPreferredWorkLocationDefinition());
                }
                binding.edExperience.setText("" + viewModel.jobDatum.getPreferredExperience());
                binding.edOther.setText("" + viewModel.jobDatum.getJobOtherExp());

                if (!TextUtils.isEmpty(viewModel.jobDatum.getPreferredDaysOfTheWeek())) {
                    setupSelection_DaysOfWeeks_ByModelData(viewModel.jobDatum.getPreferredDaysOfTheWeek().split(","));
                    daysOfWeekAdapter.notifyDataSetChanged();
                }

                if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0) {
                    binding.tvWeeksDays.setVisibility(View.GONE);
                    binding.rvWeeksDays.setVisibility(View.VISIBLE);
                } else {
                    binding.tvWeeksDays.setVisibility(View.VISIBLE);
                    binding.rvWeeksDays.setVisibility(View.GONE);
                }
                if (checkItemInList(viewModel.jobDatum.getJobCernerExp(), Collections.singletonList(viewModel.getList_work_cerner()))) {
                    viewModel.selected_work_cerner = getIndexFromList(viewModel.jobDatum.getJobCernerExp(), Collections.singletonList(viewModel.getList_work_cerner()));
                    binding.tvCenter.setText("" + viewModel.jobDatum.getJobCernerExpDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getJobMeditechExp(),
                        Collections.singletonList(viewModel.getList_work_cerner()))) {
                    viewModel.selected_work_medtech = getIndexFromList(viewModel.jobDatum.getJobMeditechExp(),
                            Collections.singletonList(viewModel.getList_work_cerner()));
                    binding.tvMeditech.setText("" + viewModel.jobDatum.getJobMeditechExpDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getJobEpicExp(),
                        Collections.singletonList(viewModel.getList_work_medtech()))) {
                    viewModel.selected_work_epic = getIndexFromList(viewModel.jobDatum.getJobEpicExp(),
                            Collections.singletonList(viewModel.getList_work_medtech()));
                    binding.tvEpic.setText("" + viewModel.jobDatum.getJobEpicExpDefinition());
                }
                if (checkItemInList(viewModel.jobDatum.getPreferredShift(),
                        Collections.singletonList(viewModel.getList_preferred_shift()))) {
                    viewModel.selected_preferred_shift = getIndexFromList(viewModel.jobDatum.getPreferredShift(),
                            Collections.singletonList(viewModel.getList_preferred_shift()));
                    binding.spinnerPreferredShift.setText("" + viewModel.jobDatum.getPreferredShiftDefinition());
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date str1 = simpleDateFormat.parse(viewModel.jobDatum.getStartDate());
                    Date str2 = simpleDateFormat.parse(viewModel.jobDatum.getEndDate());
                    viewModel.date1 = simpleDateFormat1.format(str1);
                    viewModel.date2 = simpleDateFormat1.format(str2);

                    DateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
                    year2 = Integer.parseInt(formatter.format(str1.getTime()));
                    formatter = new SimpleDateFormat("MM", Locale.getDefault());
                    monthOfYear2 = Integer.parseInt(formatter.format(str1.getTime()));
                    formatter = new SimpleDateFormat("dd", Locale.getDefault());
                    dayOfMonth2 = Integer.parseInt(formatter.format(str1.getTime()));

                    formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
                    year3 = Integer.parseInt(formatter.format(str2.getTime()));
                    formatter = new SimpleDateFormat("MM", Locale.getDefault());
                    monthOfYear3 = Integer.parseInt(formatter.format(str2.getTime()));
                    formatter = new SimpleDateFormat("dd", Locale.getDefault());
                    dayOfMonth3 = Integer.parseInt(formatter.format(str2.getTime()));
                    monthOfYear2--;
                    monthOfYear3--;

                    binding.tvStartDate.setText(simpleDateFormat2.format(str1));
                    binding.tvEndDate.setText(simpleDateFormat2.format(str2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception exception) {
            Log.d("TAG", "setData: " + exception.getMessage());
        }
    }

    private void setupSelection_DaysOfWeeks_ByModelData(String[] specialty) {
        viewModel.select_daysOfWeek.clear();
        for (int i = 0; i < viewModel.list_days_of_week.getValue().size(); i++) {
            HourlyRate_DayOfWeek_OptionDatum data = viewModel.list_days_of_week.getValue().get(i);
            for (int j = 0; j < specialty.length; j++) {
                Log.d("TAG1", "setupSelectionSpecialtyByModelData: " + "" + data.getId() +
                        " " + ((String) specialty[j]));
                if (("" + data.getId()).equals(((String) specialty[j]))) {
                    viewModel.select_daysOfWeek.add(i);
                }
            }
        }
    }


    private int getIndexFromList(String id, List<Object> list1) {
        int pos = 0;
        if (list1 instanceof List) {
            if (((List) list1).size() > 0 && (((List) list1.get(0)).get(0) instanceof CommonDatum)) {
                List<CommonDatum> list;
                list = ((List<CommonDatum>) list1.get(0));
                for (int i = 0; i < list.size(); i++) {
                    CommonDatum specialtyDatum = list.get(i);
                    if (specialtyDatum.getId().toString().equals(id)) {
                        pos = i;
                    }
                }
            } else if (((List) list1).size() > 0 && (((List) list1.get(0)).get(0) instanceof HourlyRate_DayOfWeek_OptionDatum)) {
                List<HourlyRate_DayOfWeek_OptionDatum> list;
                list = ((List<HourlyRate_DayOfWeek_OptionDatum>) list1.get(0));
                for (int i = 0; i < list.size(); i++) {
                    HourlyRate_DayOfWeek_OptionDatum specialtyDatum = list.get(i);
                    HourlyRate_DayOfWeek_OptionDatum sdas = (HourlyRate_DayOfWeek_OptionDatum) specialtyDatum;
                    if (sdas.getId().toString().equals(id)) {
                        pos = i;
                    }
                }
            }
        }
        return pos;
    }

    private boolean checkItemInList(String id, List<Object> list1) {
        if (list1 instanceof List) {
            if (((List) list1).size() > 0 && (((List) list1.get(0)).get(0) instanceof CommonDatum)) {
                List<CommonDatum> list;
                list = ((List<CommonDatum>) list1.get(0));
                for (int i = 0; i < list.size(); i++) {
                    CommonDatum specialtyDatum = list.get(i);
                    if (specialtyDatum.getId().toString().equals(id)) {
                        return true;
                    }
                }
            } else if (((List) list1).size() > 0 && (((List) list1.get(0)).get(0) instanceof HourlyRate_DayOfWeek_OptionDatum)) {
                List<HourlyRate_DayOfWeek_OptionDatum> list;
                list = ((List<HourlyRate_DayOfWeek_OptionDatum>) list1.get(0));
                for (int i = 0; i < list.size(); i++) {
                    HourlyRate_DayOfWeek_OptionDatum specialtyDatum = list.get(i);
                    HourlyRate_DayOfWeek_OptionDatum sdas = (HourlyRate_DayOfWeek_OptionDatum) specialtyDatum;
                    if (sdas.getId().toString().equals(id)) {
                        return true;
                    }
                }
            }
        }
        /*for (Object specialtyDatum : list) {
            if (specialtyDatum instanceof CommonDatum) {
                CommonDatum sdas = (CommonDatum) specialtyDatum;
                if (sdas.getId().toString().equals(id)) {
                    return true;
                }
            } else if (specialtyDatum instanceof HourlyRate_DayOfWeek_OptionDatum) {
                HourlyRate_DayOfWeek_OptionDatum sdas = (HourlyRate_DayOfWeek_OptionDatum) specialtyDatum;
                if (sdas.getId().toString().equals(id)) {
                    return true;
                }
            }
        }*/
        return false;
    }

    private void setAdapter() {
        Collections.sort(viewModel.select_daysOfWeek, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? -1 : 1;
            }
        });
        daysOfWeekAdapter = new SpecialtyAdapter(getContext(), viewModel.select_daysOfWeek,
                viewModel.list_days_of_week.getValue(), 3, 3, new SpecialtyAdapter.SpecialtyListener() {
            @Override
            public void onClickItem(int position) {
                if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0 && position < viewModel.select_daysOfWeek.size()) {
                    viewModel.select_daysOfWeek.remove(position);
                    if (daysOfWeekAdapter != null)
                        daysOfWeekAdapter.notifyDataSetChanged();
                    if (binding.tvWeeksDays != null && binding.rvWeeksDays != null)
                        if (viewModel.select_daysOfWeek == null || viewModel.select_daysOfWeek.size() == 0) {
                            binding.tvWeeksDays.setVisibility(View.VISIBLE);
                            binding.rvWeeksDays.setVisibility(View.GONE);
                        } else {
                            binding.tvWeeksDays.setVisibility(View.GONE);
                            binding.rvWeeksDays.setVisibility(View.VISIBLE);
                        }
                }
            }
        });
        binding.rvWeeksDays.setAdapter(daysOfWeekAdapter);

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
        popup.setHeight(getActivity().getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
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
        parentChildAdapter = new HourlyRateWindowAdapter((Add_Jobs_Activity) getActivity(),
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

    private boolean checkDataEmpty() {
        AppController appController = (AppController) getActivity().getApplication();
        int status = 0;
        if (appController.getList_assignment_durations() != null
                && appController.getList_assignment_durations().size() != 0) {
            status++;
        }
        if (appController.getList_senior_level() != null
                && appController.getList_senior_level().size() != 0) {
            status++;
        }
        if (appController.getList_job_funcs() != null
                && appController.getList_job_funcs().size() != 0) {
            status++;
        }
        if (appController.getList_speciality() != null
                && appController.getList_speciality().size() != 0) {
            status++;
        }
        if (appController.getList_preferred_shift() != null
                && appController.getList_preferred_shift().size() != 0) {
            status++;
        }
        if (appController.getList_work_loc() != null
                && appController.getList_work_loc().size() != 0) {
            status++;
        }
        if (appController.getList_days_of_week() != null
                && appController.getList_days_of_week().size() != 0) {
            status++;
        }
        if (appController.getList_cerner() != null
                && appController.getList_cerner().size() != 0) {
            status++;
        }
        if (appController.getList_medtech() != null
                && appController.getList_medtech().size() != 0) {
            status++;
        }
        if (appController.getList_epic() != null
                && appController.getList_epic().size() != 0) {
            status++;
        }
        if (appController.getList__preferred_shift() != null
                && appController.getList__preferred_shift().size() != 0) {
            status++;
        }
        if (status == 11) {
            return true;
        } else
            return false;
    }

    private void observer_view() {
        binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    setAdapter();
                    binding.layProgress.setVisibility(View.GONE);
                    edit_portion();
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    private void click() {
        binding.edExperience.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s == null || s.length() == 0)
                    binding.edExperience.setError(null);
                else if (!patternExp.matcher(s.toString()).find()) {
                    binding.edExperience.setError("Preferred Experience Can Contain Numbers And Max Length Is 5 !");
                } else {
                    binding.edExperience.setError(null);
                }
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Close, mParam1));

            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_validation()) {
                    viewModel.experience_year = binding.edExperience.getText().toString();

                    if (!TextUtils.isEmpty(binding.edOther.getText().toString()))
                        viewModel.other_exp = binding.edOther.getText().toString();
                    else
                        viewModel.other_exp = "";

                    viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Dismiss, mParam1));
                }
            }

            private boolean check_validation() {
                if (viewModel.selected_assignment_duration == 0) {
                    Utils.displayToast(getContext(), "Please, Select Preferred Assignment Duration First !");
                    return false;
                }
                if (viewModel.selected_senior_level == 0) {
                    Utils.displayToast(getContext(), "Please, Select Seniority Level First !");
                    return false;
                }

                if (viewModel.selected_job_funcs == 0) {
                    Utils.displayToast(getContext(), "Please, Select Job Function !");
                    return false;
                }
                if (viewModel.selected_speciality == 0) {
                    Utils.displayToast(getContext(), "Please, Select Preferred Specialty !");
                    return false;
                }
                if (viewModel.selected_shift_duration == 0) {
                    Utils.displayToast(getContext(), "Please, Select Preferred Shift Duration !");
                    return false;
                }
                if (viewModel.selected_work_loc == 0) {
                    Utils.displayToast(getContext(), "Please, Select Preferred Work Location !");
                    return false;
                }
                if (viewModel.selected_preferred_shift == 0) {
                    Utils.displayToast(getContext(), "Please, Select Your Preferred Shift !");
                    return false;
                }
                if (TextUtils.isEmpty(viewModel.date1)) {
                    Utils.displayToast(getContext(), "Select Start Date");
                    return false;
                }
                if (TextUtils.isEmpty(viewModel.date2)) {
                    Utils.displayToast(getContext(), "Select End Date");
                    return false;
                }
                if (TextUtils.isEmpty(binding.edExperience.getText().toString())) {
                    Utils.displayToast(getContext(), "Enter Experience Years First !");
                    return false;
                }
                if (viewModel.select_daysOfWeek == null || viewModel.select_daysOfWeek.size() == 0) {
                    Utils.displayToast(getContext(), "Please, Select Preferred Day Of The Week First !");
                    return false;
                }
                if (viewModel.selected_work_cerner == 0) {
                    Utils.displayToast(getContext(), "Please, Select Your Cerner Experience !");
                    return false;
                }
                if (viewModel.selected_work_medtech == 0) {
                    Utils.displayToast(getContext(), "Please, Select Your Meditech Experience !");
                    return false;
                }
                if (viewModel.selected_work_epic == 0) {
                    Utils.displayToast(getContext(), "Please, Select Your Epic Experience !");
                    return false;
                }
                return true;
            }
        });
        binding.layAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view1, 1, binding.img1, binding.spinnerAssignment,
                        viewModel.list_assignment_durations.getValue(), viewModel.selected_assignment_duration
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_assignment_duration = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.laySeniorityLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view2, 2, binding.img2, binding.spinnerSeniorityLevel,
                        viewModel.list_senior_level.getValue(), viewModel.selected_senior_level
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_senior_level = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layJobFuncs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view3, 3, binding.img3, binding.spinnerJobFuncs,
                        viewModel.list_job_funcs.getValue(), viewModel.selected_job_funcs
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_job_funcs = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.laySpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view4, 4, binding.img4, binding.tvSpecialty,
                        viewModel.list_speciality.getValue(), viewModel.selected_speciality
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_speciality = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view5, 5, binding.img5, binding.spinnerShiftDuration,
                        viewModel.list_preferred_shift_duration.getValue(), viewModel.selected_shift_duration
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_shift_duration = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layPrefferedShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view15, 15, binding.img15, binding.spinnerPreferredShift,
                        viewModel.list_preferred_shift.getValue(), viewModel.selected_preferred_shift
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_preferred_shift = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.imgStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);

                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = null;
                if (dayOfMonth2 == 0 && year2 == 0 && monthOfYear2 == 0) {
                    dpd = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    binding.tvStartDate.setText(formatter.format((monthOfYear + 1)) + "-" + formatter.format(dayOfMonth) + "-" +
                                            year);
                                    viewModel.date1 = year + "-" + formatter.format((monthOfYear + 1)) + "-" +
                                            formatter.format(dayOfMonth);
                                    dayOfMonth2 = dayOfMonth;
                                    monthOfYear2 = monthOfYear;
                                    year2 = year;
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

                } else {
                    dpd = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    binding.tvStartDate.setText(formatter.format((monthOfYear + 1)) + "-" + formatter.format(dayOfMonth) + "-" +
                                            year);
                                    viewModel.date1 = year + "-" + formatter.format((monthOfYear + 1)) + "-" +
                                            formatter.format(dayOfMonth);
                                    dayOfMonth2 = dayOfMonth;
                                    monthOfYear2 = monthOfYear;
                                    year2 = year;
                                }
                            },
                            year2, monthOfYear2, dayOfMonth2);
                }
                dpd.show();

                Utils.onClickEvent(v);
            }
        });
        binding.imgEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);

                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = null;
                if (dayOfMonth3 == 0 && year3 == 0 && monthOfYear3 == 0) {
                    dpd = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
                                    try {
                                        Date d1 = sdf.parse(dayOfMonth + "/" + monthOfYear + "/" + year);
                                        Date d2 = sdf.parse(dayOfMonth2 + "/" + monthOfYear2 + "/" + year2);
                                        if (d1.getTime() <= d2.getTime()) {
                                            Utils.displayToast(getContext(), "Please Select Proper Valid Date, It Must Be After Start Date  ");
                                            return;
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    binding.tvEndDate.setText(formatter.format((monthOfYear + 1)) + "-" + formatter.format(dayOfMonth) + "-" +
                                            year);
                                    viewModel.date2 = year + "-" + formatter.format((monthOfYear + 1)) + "-" +
                                            formatter.format(dayOfMonth);
                                    dayOfMonth3 = dayOfMonth;
                                    monthOfYear3 = monthOfYear;
                                    year3 = year;
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

                } else {
                    dpd = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
                                    try {
                                        Date d1 = sdf.parse(dayOfMonth + "/" + monthOfYear + "/" + year);
                                        Date d2 = sdf.parse(dayOfMonth2 + "/" + monthOfYear2 + "/" + year2);
                                        if (d1.getTime() <= d2.getTime()) {
                                            Utils.displayToast(getContext(), "Please Select Proper Valid Date, It Must Be After Effective Date  ");
                                            return;
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    binding.tvEndDate.setText(formatter.format((monthOfYear + 1)) + "-" + formatter.format(dayOfMonth) + "-" +
                                            year);
                                    viewModel.date2 = year + "-" + formatter.format((monthOfYear + 1)) + "-" +
                                            formatter.format(dayOfMonth);
                                    dayOfMonth3 = dayOfMonth;
                                    monthOfYear3 = monthOfYear;
                                    year3 = year;
                                }
                            },
                            year3, monthOfYear3, dayOfMonth3);
                }
                dpd.show();

                Utils.onClickEvent(v);
            }
        });

        binding.layWorkLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view6, 6, binding.img6, binding.spinnerWorkLoc,
                        viewModel.list_work_loc.getValue(), viewModel.selected_work_loc
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_work_loc = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup_Weeks(getContext(), 4, binding.view8, binding.img8, binding.tvWeeksDays,
                        viewModel.list_days_of_week.getValue(), viewModel.select_daysOfWeek
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                if (viewModel.select_daysOfWeek == null)
                                    viewModel.select_daysOfWeek = new ArrayList<>();
                                if (position < viewModel.select_daysOfWeek.size() && viewModel.select_daysOfWeek.contains(position)) {
                                    viewModel.select_daysOfWeek.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    viewModel.select_daysOfWeek.add(position);
                                binding.tvWeeksDays.setVisibility(View.GONE);
                                binding.rvWeeksDays.setVisibility(View.VISIBLE);

                                setAdapter();
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);

                showOptionPopup_Degrees(getContext(), 2, binding.view9, binding.img9,
                        Collections.singletonList(viewModel.list_work_cerner.getValue()),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_work_cerner = position;
                                if (position == 0) {
                                    binding.tvCenter.setText("");
                                } else
                                    binding.tvCenter.setText("" + viewModel.list_work_cerner.getValue()
                                            .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layMeditech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);

                showOptionPopup_Degrees(getContext(), 3, binding.view10, binding.img10,
                        Collections.singletonList(viewModel.list_work_medtech.getValue()),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_work_medtech = position;
                                if (position == 0) {
                                    binding.tvMeditech.setText("");
                                } else
                                    binding.tvMeditech.setText("" + viewModel.list_work_medtech.getValue()
                                            .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });

        binding.layEpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);

                showOptionPopup_Degrees(getContext(), 4, binding.view11, binding.img11,
                        Collections.singletonList(viewModel.list_work_epic.getValue()),
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_work_epic = position;
                                if (position == 0) {
                                    binding.tvEpic.setText("");
                                } else
                                    binding.tvEpic.setText("" + viewModel.list_work_epic.getValue()
                                            .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
    }

    private void showOptionPopup_Degrees(Context context, int type, View view1, ImageView img1,
                                         List<Object> list,
                                         ItemCallback itemCallback) {
        if (list == null || list.size() == 0) {
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
        adapter_degree = new WorkHistoryWindowAdapter((Add_Jobs_Activity) getActivity(), type,
                list,
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

    private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                 TextView tvEmr, List<CommonDatum> cityData, int selected_City, ItemCallback
                                         itemCallback) {
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
                = new CommonDropDownAdapter((Add_Jobs_Activity) getActivity()
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

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
}