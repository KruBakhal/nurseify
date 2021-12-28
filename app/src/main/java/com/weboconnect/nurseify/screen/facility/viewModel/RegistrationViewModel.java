package com.weboconnect.nurseify.screen.facility.viewModel;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.intermediate.API_ResponseCallback;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;
import com.weboconnect.nurseify.screen.facility.model.Combine_CommonModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.nurse.model.CityDatum;
import com.weboconnect.nurseify.screen.nurse.model.CityModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyDatum;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.StateModel;
import com.weboconnect.nurseify.screen.nurse.model.State_Datum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.FacilityAPI;
import com.weboconnect.nurseify.webService.RetrofitApi;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationViewModel extends ViewModel {

    public FacilityAPI backendApi;
    public MutableLiveData<ProgressUIType> showProgressBar = new MutableLiveData<>(ProgressUIType.DIMISS);
    public MutableLiveData<ErrorMessage> showErrorMsg = new MutableLiveData<>();
    public MutableLiveData<DialogStatusMessage> dialogStatus = new MutableLiveData<>(new DialogStatusMessage(DialogStatus.Dismiss, 0));
    public MutableLiveData<String> toastMesssage = new MutableLiveData<>();
    public FacilityProfile main_model;
    public FacilityProfile new_facilityModel;
    public boolean isEditMode = false;
    // setup 1
    public MutableLiveData<List<SpecialtyDatum>> listFacilityType = new MutableLiveData<>();
    public int selected_facility_type = 0;
    // setup 2
    public MutableLiveData<List<State_Datum>> list_State = new MutableLiveData<List<State_Datum>>();
    public int selected_state = 0;
    public MutableLiveData<List<CityDatum>> list_City = new MutableLiveData<>();
    public int selected_City = 0;
    // setup 3
    public MutableLiveData<List<CommonDatum>> list_emr = new MutableLiveData<>();
    public int selected_emr = 0;
    public MutableLiveData<List<CommonDatum>> list_bg_check = new MutableLiveData<>();
    public int selected_bg_check = 0;
    public MutableLiveData<List<CommonDatum>> list_nurse_credentialing = new MutableLiveData<>();
    public int selected_soft = 0;
    // setup 4

    public MutableLiveData<List<CommonDatum>> list_scheduling = new MutableLiveData<>();
    public int selected_scheduling = 0;
    public MutableLiveData<List<CommonDatum>> list_attendance = new MutableLiveData<>();
    public int selected_attendance = 0;
    public MutableLiveData<List<CommonDatum>> list_trauma = new MutableLiveData<>();
    public int selected_trauma = 0;
    public int option_call;


    public RegistrationViewModel() {
        if (backendApi == null) {
            backendApi = RetrofitClient.getInstance().getFacilityApi();
        }
    }

    public MutableLiveData<List<State_Datum>> getList_State() {
        return list_State;
    }

    public int getSelected_state() {
        return selected_state;
    }

    public MutableLiveData<String> getToastMesssage() {
        return toastMesssage;
    }

    public MutableLiveData<List<CityDatum>> getList_City() {
        return list_City;
    }

    public int getSelected_City() {
        return selected_City;
    }

    public MutableLiveData<List<SpecialtyDatum>> getFacilityType_List() {
        return listFacilityType;
    }

    public SpecialtyDatum getFacilityType_List(int pos) {
        if (listFacilityType == null || listFacilityType.getValue() == null || listFacilityType.getValue().size() == 0)
            return null;
        return listFacilityType.getValue().get(pos);

    }

    public MutableLiveData<ProgressUIType> getProgressBar() {
        return showProgressBar;
    }

    public MutableLiveData<List<CommonDatum>> getList_emr() {
        return list_emr;
    }

    public MutableLiveData<List<CommonDatum>> getList_bg_check() {
        return list_bg_check;
    }

    public MutableLiveData<List<CommonDatum>> getList_software() {
        return list_nurse_credentialing;
    }

    public MutableLiveData<List<CommonDatum>> getList_scheduling() {
        return list_scheduling;
    }

    public MutableLiveData<List<CommonDatum>> getList_attendance() {
        return list_attendance;
    }

    public MutableLiveData<List<CommonDatum>> getList_trauma() {
        return list_trauma;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public void do_DismissDialog(DialogStatusMessage dialogStatus) {
        this.dialogStatus.setValue(dialogStatus);
    }

    public MutableLiveData<DialogStatusMessage> do_DismissDialog() {
        return dialogStatus;
    }

    // fetch
    public void fetch_profile_setup_1() {
        showProgressBar.setValue(ProgressUIType.SHOW);
        backendApi.call_facility_types().enqueue(new Callback<SpecialtyModel>() {
            @Override
            public void onResponse(Call<SpecialtyModel> call, Response<SpecialtyModel> response) {
                if (response == null || response.body() == null) {
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    return;
                }
                if (!response.body().getApiStatus().equals("1")) {
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    return;
                }
                if (response.isSuccessful()) {
                    showProgressBar.setValue(ProgressUIType.DIMISS);
                    ArrayList<SpecialtyDatum> list = new ArrayList<SpecialtyDatum>();
                    list.add(new SpecialtyDatum(-1, "Select Facility Type"));
                    list.addAll(response.body().getData());
                    listFacilityType.setValue(list);
                } else {
                    showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                }
            }

            @Override
            public void onFailure(Call<SpecialtyModel> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    public void fetch_profile_setup_2(String country_id) {
        showProgressBar.setValue(ProgressUIType.SHOW);
        RetrofitApi backendApi = RetrofitClient.getInstance().getNurseRetrofitApi();
        RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data"),
                "" + country_id);
        backendApi.call_state_ID(request_id)
                .enqueue(new Callback<StateModel>() {
                    @Override
                    public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                            showErrorMsg.setValue(new ErrorMessage(Constant.API_STATUS, "Data not found !"));
                            return;
                        }
                        if (response.isSuccessful()) {
                            StateModel credentialModel = response.body();
                            List<State_Datum> list = new ArrayList<>();
                            list.add(new State_Datum("-1", "Select State"));
                            list.addAll(credentialModel.getData());
                            list_State.setValue(list);
                            showProgressBar.setValue(ProgressUIType.DIMISS);
                        } else {
                            showErrorMsg.setValue(new ErrorMessage(Constant.UnSuccessfull, "When wrong"));
                            showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                        }

                    }

                    @Override
                    public void onFailure(Call<StateModel> call, Throwable t) {
                        Log.d("TAG", "fetch_state_by_Country() onFailure: " + t.getMessage());
                        showErrorMsg.setValue(new ErrorMessage(Constant.WHEM_WRONG, "When wrong"));
                        showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    }
                });

    }

    public void fetch_state_by_City(String state_id, API_ResponseCallback apiResponseCallback) {
        apiResponseCallback.onShowProgress();
        RetrofitApi backendApi = RetrofitClient.getInstance().getNurseRetrofitApi();
        RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data"),
                "" + state_id);
        backendApi.call_city_ID(request_id)
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            apiResponseCallback.onFailed("No cities available for the selected state !");
//                            Utils.displayToast(context, "No cities available for the city !");

                            return;
                        }

                        if (response.isSuccessful()) {
                            CityModel credentialModel = response.body();
                            List<CityDatum> list = new ArrayList<>();
                            list.add(new CityDatum("-1", "Select City"));
                            list.addAll(credentialModel.getData());

                            list_City.setValue(list);

                            apiResponseCallback.onSucces(credentialModel);
                        } else {
//                            Utils.displayToast(context, "Data Not Found !");
                            apiResponseCallback.onFailed("Data Not Found !");
                        }

                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        Log.d("TAG", "No Data Found !");
//                        Utils.displayToast(context, getResources().getString(R.string.something_when_wrong));
                        apiResponseCallback.onError();
                    }
                });
    }

    public void fetch_profile_setup_3() {

        showProgressBar.setValue(ProgressUIType.SHOW);
        backendApi = RetrofitClient.getInstance().getFacilityApi();
        Observable<Combine_CommonModel> listObservable
                = Observable.zip(
                backendApi.call_facility_getmedicalrecords()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_facility_getbcheckprovider().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new Function<Throwable, CommonModel>() {
                            @Override
                            public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                                Log.d("TAG", "apply: " + throwable.getMessage());
                                return new CommonModel();
                            }
                        }),
                backendApi.call_facility_getncredentialingsoftware().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                new Function3<CommonModel, CommonModel, CommonModel,
                        Combine_CommonModel>() {
                    @NonNull
                    @Override
                    public Combine_CommonModel apply(@NonNull CommonModel specialtyModel,
                                                     @NonNull CommonModel stateModel, @NonNull CommonModel stateModel1) throws Exception {
                        return new Combine_CommonModel(specialtyModel, stateModel, stateModel1);
                    }
                });

        listObservable.subscribe(new Observer<Combine_CommonModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_CommonModel combineData) {

                if (combineData != null) {
                    set_EMR(combineData.getCommonModel_ENR());
                    set_BG(combineData.getCommonModel_BG());
                    set_SOFT(combineData.getCommonModel_SOFT());
                }

                showProgressBar.setValue(ProgressUIType.DIMISS);
            }

            private void set_EMR(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Electronic Medical Records (EMR)"));
                    listBase.addAll(commonModel_bg.getData());
                    list_emr.setValue(listBase);
                }
            }

            private void set_BG(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Background Check Provider"));
                    listBase.addAll(commonModel_bg.getData());

                    list_bg_check.setValue(listBase);
                }
            }

            private void set_SOFT(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Nurse Credentialing Software"));
                    listBase.addAll(commonModel_bg.getData());
                    list_nurse_credentialing.setValue(listBase);
                }
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

    public void fetch_profile_setup_4() {

        showProgressBar.setValue(ProgressUIType.SHOW);
        backendApi = RetrofitClient.getInstance().getFacilityApi();
        Observable<Combine_CommonModel> listObservable
                = Observable.zip(
                backendApi.call_dropdown_getnschedulingsystem()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_dropdown_get_time_attendance_system().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new Function<Throwable, CommonModel>() {
                            @Override
                            public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                                Log.d("TAG", "apply: " + throwable.getMessage());
                                return new CommonModel();
                            }
                        }),
                backendApi.call_dropdown_get_traumadesignation().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                new Function3<CommonModel, CommonModel, CommonModel,
                        Combine_CommonModel>() {
                    @NonNull
                    @Override
                    public Combine_CommonModel apply(@NonNull CommonModel specialtyModel,
                                                     @NonNull CommonModel stateModel, @NonNull CommonModel stateModel1) throws Exception {
                        return new Combine_CommonModel(specialtyModel, stateModel, stateModel1);
                    }
                });

        listObservable.subscribe(new Observer<Combine_CommonModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_CommonModel combineData) {

                if (combineData != null) {
                    set_scheduling(combineData.getCommonModel_ENR());
                    set_attendance(combineData.getCommonModel_BG());
                    set_Trauma(combineData.getCommonModel_SOFT());
                }

                showProgressBar.setValue(ProgressUIType.DIMISS);
            }

            private void set_scheduling(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Nurse Scheduling System"));
                    listBase.addAll(commonModel_bg.getData());
                    list_scheduling.setValue(listBase);
                }
            }

            private void set_attendance(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Time & Attendance System"));
                    listBase.addAll(commonModel_bg.getData());

                    list_attendance.setValue(listBase);
                }
            }

            private void set_Trauma(CommonModel commonModel_bg) {
                if (commonModel_bg != null && commonModel_bg.getData() != null && commonModel_bg.getData().size() != 0) {
                    List<CommonDatum> listBase = new ArrayList<>();
                    listBase.add(new CommonDatum(-1, "Select Trauma Designation"));
                    listBase.addAll(commonModel_bg.getData());
                    list_trauma.setValue(listBase);
                }
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

    public void perform_profile_submission(RegistrationFActivity context, int mParam1) {
        showProgressBar.setValue(ProgressUIType.SHOW);
        FacilityProfile new_facilityModel = this.new_facilityModel;
        if (isEditMode) {
            new_facilityModel = main_model;
        }
        MediaType mediatTypeStr = MediaType.parse("multipart/form-data");
        RequestBody request_1 = RequestBody.create(mediatTypeStr, "" + new SessionManager(context).get_user_register_Id());
        RequestBody request_2 = RequestBody.create(mediatTypeStr, "" + new SessionManager(context).get_facility().getFacilityId());
        RequestBody request_3 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityName());
        RequestBody request_4 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityType());
        RequestBody request_5 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityEmail());
        RequestBody request_6 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityPhone());
        RequestBody request_7 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityAddress());
        RequestBody request_8 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityCity());
        RequestBody request_9 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityState());
        RequestBody request_10 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityPostcode());
        RequestBody request_11 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getVideoEmbedUrl());
        RequestBody request_12 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getFacebook());
        RequestBody request_13 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getTwitter());
        RequestBody request_14 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getLinkedin());
        RequestBody request_15 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getInstagram());
        RequestBody request_16 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getPinterest());
        RequestBody request_17 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getTiktok());
        RequestBody request_18 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getSanpchat());
        RequestBody request_19 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilitySocial().getYoutube());
        RequestBody request_20 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityWebsite());
        RequestBody request_21 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityEmr());
        RequestBody request_22 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityEmr_Other());
        RequestBody request_23 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityBcheckProvider());
        RequestBody request_24 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getFacilityBcheckProvider_Other());
        RequestBody request_25 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getNurseCredSoft());
        RequestBody request_26 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getNurseCredSoft_other());
        RequestBody request_27 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getNurseSchedulingSys());
        RequestBody request_28 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getNurseSchedulingSys_other());
        RequestBody request_29 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getTimeAttendSys());
        RequestBody request_30 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getTimeAttendSys_other());
        RequestBody request_31 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getLicensedBeds());
        RequestBody request_32 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getTraumaDesignation());
        RequestBody request_35 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getCnoMessage());
        RequestBody request_36 = RequestBody.create(mediatTypeStr, "" + new_facilityModel.getAboutFacility());
        MultipartBody.Part request_33 = null, request_34 = null;
        if (!TextUtils.isEmpty(new_facilityModel.getCnoImage_new()) &&
                !new_facilityModel.getCnoImage_new().startsWith("http:/35.200.185.126")) {
            RequestBody img = RequestBody.create(MediaType.parse("image/*"),
                    new File(new_facilityModel.getCnoImage_new()));
            request_33 =
                    MultipartBody.Part.createFormData("cno_image",
                            new File(new_facilityModel.getCnoImage_new()).getName(), img);
        }
        if (!TextUtils.isEmpty(new_facilityModel.getFacilityLogo_new())
                && new_facilityModel.getFacilityLogo_new().startsWith("http:/35.200.185.126")) {
            RequestBody img = RequestBody.create(MediaType.parse("image/*"),
                    new File(new_facilityModel.getFacilityLogo_new()));
            request_34 =
                    MultipartBody.Part.createFormData("facility_logo",
                            new File(new_facilityModel.getFacilityLogo_new()).getName(), img);
        }

        backendApi.call_facility_profile(request_1, request_2, request_3, request_4, request_5, request_6, request_7, request_8
                , request_9, request_10, request_11, request_12, request_13, request_14, request_15, request_16, request_17,
                request_18, request_19, request_20, request_21, request_22, request_23, request_24, request_25, request_26
                , request_27, request_28, request_29, request_30, request_31, request_32, request_35, request_36, request_33, request_34)
                .enqueue(new Callback<FacilityLoginModel>() {
                    @Override
                    public void onResponse(Call<FacilityLoginModel> call, Response<FacilityLoginModel> response) {
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
                            FacilityLoginModel facilityLoginModel = response.body();
                            new SessionManager(context).setSession_IN_facility(
                                    facilityLoginModel.getData().getUserId(), facilityLoginModel.getData().getFacilityId(),
                                    facilityLoginModel.getData());
                            showProgressBar.setValue(ProgressUIType.DIMISS);

                            if (isEditMode) {
                                main_model = facilityLoginModel.getData();
                            }
                            dialogStatus.setValue(new DialogStatusMessage(DialogStatus.Done, mParam1));

                        } else {
                            showErrorMsg.setValue(new ErrorMessage(Constant.UnSuccessfull, "When wrong"));
                            showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                        }

                    }

                    @Override
                    public void onFailure(Call<FacilityLoginModel> call, Throwable t) {
                        Log.d("TAG", "perform_profile_submission() onFailure: " + t.getMessage());
                        showErrorMsg.setValue(new ErrorMessage(Constant.WHEM_WRONG, "When wrong"));
                        showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                    }
                });
    }
}
