package com.weboconnect.nurseify.screen.facility.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.weboconnect.nurseify.AppController;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.screen.facility.model.AddJobData;
import com.weboconnect.nurseify.screen.facility.model.AddJobModel;
import com.weboconnect.nurseify.screen.facility.model.Combine_CommonModel_2;
import com.weboconnect.nurseify.screen.facility.model.Facility_JobDatum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.FacilityAPI;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function8;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Job_ViewModel extends ViewModel {

    public FacilityAPI backendApi;
    public MutableLiveData<ProgressUIType> showProgressBar = new MutableLiveData<>(ProgressUIType.DIMISS);
    public MutableLiveData<ErrorMessage> showErrorMsg = new MutableLiveData<>();
    public MutableLiveData<DialogStatusMessage> dialogStatus = new MutableLiveData<>(new DialogStatusMessage(DialogStatus.Dismiss, 0));
    public MutableLiveData<String> toastMesssage = new MutableLiveData<>();

    public MutableLiveData<List<CommonDatum>> list_assignment_durations = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_senior_level = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_job_funcs = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_speciality = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_preferred_shift = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_work_loc = new MutableLiveData<>();
    public MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> list_days_of_week = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_work_cerner = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_work_medtech = new MutableLiveData<>();
    public MutableLiveData<List<CommonDatum>> list_work_epic = new MutableLiveData<>();

    public int selected_assignment_duration = 0;
    public int selected_senior_level = 0;
    public int selected_job_funcs = 0;
    public int selected_speciality = 0;

    public List<CommonDatum> getList_work_cerner() {
        return list_work_cerner.getValue();
    }

    public List<CommonDatum> getList_work_medtech() {
        return list_work_medtech.getValue();
    }

    public List<CommonDatum> getList_work_epic() {
        return list_work_epic.getValue();
    }

    public int selected_shift_duration = 0;
    public int selected_work_loc = 0;
    public List<Integer> select_daysOfWeek = new ArrayList<>();
    public String experience_year = "";
    public int selected_work_cerner = 0;
    public int selected_work_medtech = 0;
    public int selected_work_epic = 0;
    public String other_exp = "";
    public String description = "";
    public String qualifiaction = "";
    public String responsibilities = "";
    public String youtube = "";
    public boolean isActive = false;
    public ArrayList<String> selected_list_photos;
    public int hrs_rate = 5;
    public boolean isEdit = false;
    public Facility_JobDatum jobDatum;


    public Add_Job_ViewModel() {
        if (backendApi == null) {
            backendApi = RetrofitClient.getInstance().getFacilityApi();
        }
    }

    public List<CommonDatum> getList_assignment_durations() {
        return list_assignment_durations.getValue();
    }

    public List<CommonDatum> getList_senior_level() {
        return list_senior_level.getValue();
    }

    public List<CommonDatum> getList_job_funcs() {
        return list_job_funcs.getValue();
    }

    public List<CommonDatum> getList_speciality() {
        return list_speciality.getValue();
    }

    public List<CommonDatum> getList_preferred_shift() {
        return list_preferred_shift.getValue();
    }

    public List<CommonDatum> getList_work_loc() {
        return list_work_loc.getValue();
    }

    public List<HourlyRate_DayOfWeek_OptionDatum> getList_days_of_week() {
        return list_days_of_week.getValue();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<ProgressUIType> getProgressBar() {
        return showProgressBar;
    }


    public void do_DismissDialog(DialogStatusMessage dialogStatus) {
        this.dialogStatus.setValue(dialogStatus);
    }

    public MutableLiveData<DialogStatusMessage> do_DismissDialog() {
        return dialogStatus;
    }

    public MutableLiveData<String> getToastMesssage() {
        return toastMesssage;
    }

    // fetch
    public void fetch_add_job_data(AppController appController) {

        showProgressBar.setValue(ProgressUIType.SHOW);
        backendApi = RetrofitClient.getInstance().getFacilityApi();
        Observable<Combine_CommonModel_2> listObservable
                = Observable.zip(
                backendApi.call_assignment_duration()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_get_seniority_level().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new Function<Throwable, CommonModel>() {
                            @Override
                            public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                                Log.d("TAG", "apply: " + throwable.getMessage());
                                return new CommonModel();
                            }
                        }),
                backendApi.call_get_job_function().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_specialty().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_shift_duration().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_work_location().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_get_weekdays().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, HourlyRate_DayOfWeek_OptionModel>() {
                    @Override
                    public HourlyRate_DayOfWeek_OptionModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new HourlyRate_DayOfWeek_OptionModel();
                    }
                }), backendApi.call_cerner_medtech_epic_options().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                            @Override
                            public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                                Log.d("TAG", "apply: " + throwable.getMessage());
                                return new CommonModel();
                            }
                        }),
                new Function8<CommonModel, CommonModel, CommonModel, CommonModel,
                        CommonModel, CommonModel, HourlyRate_DayOfWeek_OptionModel, CommonModel,
                        Combine_CommonModel_2>() {
                    @NonNull
                    @Override
                    public Combine_CommonModel_2 apply(@NonNull CommonModel stateModel1,
                                                       @NonNull CommonModel stateModel2,
                                                       @NonNull CommonModel stateModel3,
                                                       @NonNull CommonModel stateModel4,
                                                       @NonNull CommonModel stateModel5,
                                                       @NonNull CommonModel stateModel6,
                                                       @NonNull HourlyRate_DayOfWeek_OptionModel stateModel7,
                                                       @NonNull CommonModel stateModel8
                    ) throws Exception {
                        return new Combine_CommonModel_2(stateModel1, stateModel2, stateModel3,
                                stateModel4, stateModel5,
                                stateModel6, stateModel7, stateModel8);
                    }
                });

        listObservable.subscribe(new Observer<Combine_CommonModel_2>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_CommonModel_2 combineData) {

                if (combineData != null) {
                    if (combineData.getCommonModel_assign() != null &&
                            combineData.getCommonModel_assign().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Preferred Assignment Duration"));
                        list.addAll(combineData.getCommonModel_senior().getData());
                        list_assignment_durations.setValue(list);
                        appController.setList_assignment_durations(list);
                    }

                    if (combineData.getCommonModel_senior() != null &&
                            combineData.getCommonModel_senior().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Seniority Level"));
                        list.addAll(combineData.getCommonModel_senior().getData());

                        list_senior_level.setValue(list);
                        appController.setList_senior_level(list);

                    }
                    if (combineData.getCommonModel_jobs_funcs() != null &&
                            combineData.getCommonModel_jobs_funcs().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Job Function"));
                        list.addAll(combineData.getCommonModel_jobs_funcs().getData());

                        list_job_funcs.setValue(list);
                        appController.setList_job_funcs(list);

                    }
                    if (combineData.getCommonModel_speciality() != null &&
                            combineData.getCommonModel_speciality().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Speciality"));
                        list.addAll(combineData.getCommonModel_speciality().getData());

                        list_speciality.setValue(list);

                        appController.setList_speciality(list);
                    }
                    if (combineData.getCommonModel_shift() != null &&
                            combineData.getCommonModel_shift().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Preferred Shift Duration"));
                        list.addAll(combineData.getCommonModel_shift().getData());

                        list_preferred_shift.setValue(list);
                        appController.setList_preferred_shift(list);

                    }

                    if (combineData.getCommonModel_workLoc() != null &&
                            combineData.getCommonModel_workLoc().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.add(new CommonDatum(-1, "Select Work Location"));
                        list.addAll(combineData.getCommonModel_workLoc().getData());
                        list_work_loc.setValue(list);
                        appController.setList_work_loc(list);
                    }

                    if (combineData.getCommonModel_week() != null &&
                            combineData.getCommonModel_week().getData() != null) {
                        list_days_of_week.setValue(combineData.getCommonModel_week().getData());
                        appController.setList_days_of_week(combineData.getCommonModel_week().getData());
                    }
                    if (combineData.getCommonModel_epic_medtech() != null &&
                            combineData.getCommonModel_epic_medtech().getData() != null) {
                        List<CommonDatum> list1 = new ArrayList<>();
                        List<CommonDatum> list2 = new ArrayList<>();
                        List<CommonDatum> list3 = new ArrayList<>();
                        list1.add(new CommonDatum(-1, "Select Your Experience"));
                        list1.addAll(combineData.getCommonModel_epic_medtech().getData());
                        list2.add(new CommonDatum(-1, "Select Your Experience"));
                        list2.addAll(combineData.getCommonModel_epic_medtech().getData());
                        list3.add(new CommonDatum(-1, "Select Your Experience"));
                        list3.addAll(combineData.getCommonModel_epic_medtech().getData());

                        list_work_cerner.setValue(list1);
                        list_work_medtech.setValue(list2);
                        list_work_epic.setValue(list3);

                        appController.setList_cerner(list1);
                        appController.setList_medtech(list2);
                        appController.setList_epic(list2);
                    }
                }

                showProgressBar.setValue(ProgressUIType.DIMISS);
            }


            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TAG", "fetch_profile_setup_3() onError: " + e.getMessage());
                showProgressBar.setValue(ProgressUIType.DATA_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void peform_add_job(Context context) {
        showProgressBar.setValue(ProgressUIType.SHOW);
        AddJobData addJobData = new AddJobData();
        addJobData.setPreferredAssignmentDuration("" + list_assignment_durations.getValue().get(selected_assignment_duration).getId());
        addJobData.setSeniorityLevel("" + list_senior_level.getValue().get(selected_senior_level).getId());
        addJobData.setJobFunction("" + list_job_funcs.getValue().get(selected_job_funcs).getId());
        addJobData.setPreferredSpecialty("" + list_speciality.getValue().get(selected_speciality).getId());
        addJobData.setPreferredShiftDuration("" + list_preferred_shift.getValue().get(selected_shift_duration).getId());
        addJobData.setPreferredWorkLocation("" + list_work_loc.getValue().get(selected_work_loc).getId());
        addJobData.setPreferredExperience("" + experience_year);
        addJobData.setJobOtherExp("" + other_exp);
        addJobData.setJobCernerExp("" + list_work_cerner.getValue().get(selected_work_cerner).getId());
        addJobData.setJobMeditechExp("" + list_work_medtech.getValue().get(selected_work_medtech).getId());
        addJobData.setJobEpicExp("" + list_work_epic.getValue().get(selected_work_epic).getId());
        addJobData.setPreferredHourlyPayRate("" + hrs_rate);
        addJobData.setDescription("" + description);
        addJobData.setQualifications("" + qualifiaction);
        addJobData.setResponsibilities("" + responsibilities);
        addJobData.setJobVideo("" + youtube);
        String days_str = null;
        addJobData.setActiveStatus("" + (isActive ? 1 : 0));

        for (int i = 0; i < select_daysOfWeek.size(); i++) {
            if (i == 0) {
                days_str = list_days_of_week.getValue().get(select_daysOfWeek.get(i)).getName();
            } else
                days_str = days_str + "," + list_days_of_week.getValue().get(select_daysOfWeek.get(i)).getName();
        }
        addJobData.setPreferredDaysOfTheWeek(days_str);


        MediaType mediatTypeStr = MediaType.parse("multipart/form-data");
        RequestBody request_1 = RequestBody.create(mediatTypeStr, "" + new SessionManager(context).get_user_register_Id());
        RequestBody request_2 = RequestBody.create(mediatTypeStr, "" + new SessionManager(context).get_facilityProfile().getFacilityId());
        RequestBody request_3 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredAssignmentDuration());
        RequestBody request_4 = RequestBody.create(mediatTypeStr, "" + addJobData.getSeniorityLevel());
        RequestBody request_5 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobFunction());
        RequestBody request_6 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredSpecialty());
        RequestBody request_7 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredShiftDuration());
        RequestBody request_8 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredWorkLocation());
        RequestBody request_9 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredDaysOfTheWeek());
        RequestBody request_10 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredExperience());
        RequestBody request_11 = RequestBody.create(mediatTypeStr, "" + addJobData.getPreferredHourlyPayRate());
        RequestBody request_12 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobCernerExp());
        RequestBody request_13 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobMeditechExp());
        RequestBody request_14 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobEpicExp());
        RequestBody request_15 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobOtherExp());
        RequestBody request_16 = RequestBody.create(mediatTypeStr, "" + addJobData.getDescription());
        RequestBody request_17 = RequestBody.create(mediatTypeStr, "" + addJobData.getQualifications());
        RequestBody request_18 = RequestBody.create(mediatTypeStr, "" + addJobData.getResponsibilities());
        RequestBody request_19 = RequestBody.create(mediatTypeStr, "" + addJobData.getJobVideo());
        RequestBody request_20 = RequestBody.create(mediatTypeStr, "" + addJobData.getActiveStatus());
        MultipartBody.Part[] multiPart_Pictures = null;
        Call<AddJobModel> call = null;

        if (selected_list_photos != null && selected_list_photos.size() != 0) {
            multiPart_Pictures = new MultipartBody.Part[selected_list_photos.size()];
            if (selected_list_photos != null && selected_list_photos.size() != 0)
                for (int index = 0; index < selected_list_photos.size(); index++) {
                    File file = new File(selected_list_photos.get(index));
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    multiPart_Pictures[index] = MultipartBody.Part.createFormData("job_photos[]",
                            file.getName(), body);
                }
        }
        if (multiPart_Pictures != null && multiPart_Pictures.length != 0) {
            call = backendApi.call_create_add_job(request_1, request_2, request_3, request_4,
                    request_5, request_6, request_7, request_8
                    , request_9, request_10, request_11, request_12, request_13,
                    request_14, request_15, request_16, request_17,
                    request_18, request_19, request_20, multiPart_Pictures);

        } else {
            call = backendApi.call_create_add_job(request_1, request_2, request_3, request_4, request_5, request_6, request_7, request_8
                    , request_9, request_10, request_11, request_12, request_13, request_14, request_15, request_16, request_17,
                    request_18, request_19, request_20);
        }

        call.enqueue(new Callback<AddJobModel>() {
            @Override
            public void onResponse(Call<AddJobModel> call, Response<AddJobModel> response) {
                assert response.body() != null;
                if (response == null || response.body() == null) {
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    showErrorMsg.setValue(new ErrorMessage(Constant.API_STATUS, "Data not found !"));
                    return;
                }
                if (!response.body().getApiStatus().equals("1")) {
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    Utils.displayToast(context, "" + response.body().getMessage());
                    return;
                }

                if (response.isSuccessful()) {
                    AddJobModel facilityLoginModel = response.body();
                    toastMesssage.setValue("" + facilityLoginModel.getMessage());
                    showProgressBar.setValue(ProgressUIType.DIMISS);
                    dialogStatus.setValue(new DialogStatusMessage(DialogStatus.Done, 2));
                } else {
                    showErrorMsg.setValue(new ErrorMessage(Constant.UnSuccessfull, "When wrong"));
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                }

            }

            @Override
            public void onFailure(Call<AddJobModel> call, Throwable t) {
                Log.d("TAG", "peform_add_job() onFailure: " + t.getMessage());
                showErrorMsg.setValue(new ErrorMessage(Constant.WHEM_WRONG, "When wrong"));
                showProgressBar.setValue(ProgressUIType.DATA_ERROR);
            }
        });
    }
}
