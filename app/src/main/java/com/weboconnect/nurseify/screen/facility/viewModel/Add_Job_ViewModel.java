package com.weboconnect.nurseify.screen.facility.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.weboconnect.nurseify.AppController;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.screen.facility.model.Combine_CommonModel;
import com.weboconnect.nurseify.screen.facility.model.Combine_CommonModel_2;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel.HourlyRate_DayOfWeek_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationDatum;
import com.weboconnect.nurseify.webService.FacilityAPI;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.schedulers.Schedulers;

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

    public int selected_assignment_duration = 0;
    public int selected_senior_level = 0;
    public int selected_job_funcs = 0;
    public int selected_speciality = 0;
    public int selected_shift_duration = 0;
    public int selected_work_loc = 0;
    public List<Integer> select_daysOfWeek = new ArrayList<>();


    public Add_Job_ViewModel() {
        if (backendApi == null) {
            backendApi = RetrofitClient.getInstance().getFacilityApi();
        }
    }

    public MutableLiveData<List<CommonDatum>> getList_assignment_durations() {
        return list_assignment_durations;
    }

    public MutableLiveData<List<CommonDatum>> getList_senior_level() {
        return list_senior_level;
    }

    public MutableLiveData<List<CommonDatum>> getList_job_funcs() {
        return list_job_funcs;
    }

    public MutableLiveData<List<CommonDatum>> getList_speciality() {
        return list_speciality;
    }

    public MutableLiveData<List<CommonDatum>> getList_preferred_shift() {
        return list_preferred_shift;
    }

    public MutableLiveData<List<CommonDatum>> getList_work_loc() {
        return list_work_loc;
    }

    public MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> getList_days_of_week() {
        return list_days_of_week;
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
                }),
                new Function7<CommonModel, CommonModel, CommonModel, CommonModel,
                        CommonModel, CommonModel, HourlyRate_DayOfWeek_OptionModel,
                        Combine_CommonModel_2>() {
                    @NonNull
                    @Override
                    public Combine_CommonModel_2 apply(@NonNull CommonModel stateModel1,
                                                       @NonNull CommonModel stateModel2,
                                                       @NonNull CommonModel stateModel3,
                                                       @NonNull CommonModel stateModel4,
                                                       @NonNull CommonModel stateModel5,
                                                       @NonNull CommonModel stateModel6,
                                                       @NonNull HourlyRate_DayOfWeek_OptionModel stateModel7
                    ) throws Exception {
                        return new Combine_CommonModel_2(stateModel1, stateModel2, stateModel3,
                                stateModel4, stateModel5,
                                stateModel6, stateModel7);
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
}
