package com.nurseify.app.screen.facility.viewModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nurseify.app.common.CommonDatum;
import com.nurseify.app.common.CommonModel;
import com.nurseify.app.databinding.DialogFilterFBinding;
import com.nurseify.app.intermediate.API_ResponseCallback;
import com.nurseify.app.screen.facility.browse.Nurse_Browse_Fragment;
import com.nurseify.app.screen.facility.model.Combine_CommonModel_3;
import com.nurseify.app.screen.facility.model.NurseModel;
import com.nurseify.app.screen.nurse.model.CityDatum;
import com.nurseify.app.screen.nurse.model.CityModel;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.nurseify.app.screen.nurse.model.StateModel;
import com.nurseify.app.screen.nurse.model.State_Datum;
import com.nurseify.app.webService.FacilityAPI;
import com.nurseify.app.webService.RetrofitApi;
import com.nurseify.app.webService.RetrofitClient;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Browse_Nurse_ViewModel extends ViewModel {

    public FacilityAPI backendApi;
    public MutableLiveData<ProgressUIType> showProgressBar = new MutableLiveData<>(ProgressUIType.DIMISS);
    public MutableLiveData<ErrorMessage> showErrorMsg = new MutableLiveData<>();
    public MutableLiveData<DialogStatusMessage> dialogStatus =
            new MutableLiveData<>();
    public MutableLiveData<String> toastMesssage = new MutableLiveData<>();

    //sads
    public MutableLiveData<List<State_Datum>> list_State = new MutableLiveData<List<State_Datum>>();
    public int selected_state = 0;
    public MutableLiveData<List<CityDatum>> list_City = new MutableLiveData<>();
    public int selected_City = 0;
    public MutableLiveData<List<CommonDatum>> list_speciality = new MutableLiveData<>();
    public List<Integer> select_speciality = new ArrayList<>();
    public MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> list_days_of_week = new MutableLiveData<>();
    public List<Integer> select_daysOfWeek = new ArrayList<>();
    public MutableLiveData<List<CommonDatum>> list_Certificate = new MutableLiveData<>();
    public List<Integer> last_days = new ArrayList<>();
    public List<Integer> last_speciality = new ArrayList<>();
    public List<Integer> selected_certificate = new ArrayList<>();


    public MutableLiveData<List<State_Datum>> getList_State() {
        return list_State;
    }

    public void setList_State(MutableLiveData<List<State_Datum>> list_State) {
        this.list_State = list_State;
    }

    public int getSelected_state() {
        return selected_state;
    }

    public void setSelected_state(int selected_state) {
        this.selected_state = selected_state;
    }

    public MutableLiveData<List<CityDatum>> getList_City() {
        return list_City;
    }

    public void setList_City(MutableLiveData<List<CityDatum>> list_City) {
        this.list_City = list_City;
    }

    public int getSelected_City() {
        return selected_City;
    }

    public void setSelected_City(int selected_City) {
        this.selected_City = selected_City;
    }

    public MutableLiveData<List<CommonDatum>> getList_speciality() {
        return list_speciality;
    }

    public void setList_speciality(MutableLiveData<List<CommonDatum>> list_speciality) {
        this.list_speciality = list_speciality;
    }

    public List<Integer> getSelect_speciality() {
        return select_speciality;
    }

    public void setSelect_speciality(List<Integer> select_speciality) {
        this.select_speciality = select_speciality;
    }

    public MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> getList_days_of_week() {
        return list_days_of_week;
    }

    public void setList_days_of_week(MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> list_days_of_week) {
        this.list_days_of_week = list_days_of_week;
    }

    public List<Integer> getSelect_daysOfWeek() {
        return select_daysOfWeek;
    }

    public void setSelect_daysOfWeek(List<Integer> select_daysOfWeek) {
        this.select_daysOfWeek = select_daysOfWeek;
    }

    public MutableLiveData<List<CommonDatum>> getList_Certificate() {
        return list_Certificate;
    }

    public void setList_Certificate(MutableLiveData<List<CommonDatum>> list_Certificate) {
        this.list_Certificate = list_Certificate;
    }

    public List<Integer> getSelected_certificate() {
        return selected_certificate;
    }

    public void setSelected_certificate(List<Integer> selected_certificate) {
        this.selected_certificate = selected_certificate;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<DialogStatusMessage> getDialogStatus() {
        return dialogStatus;
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

    //fetch
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

    public void fetch_data(Context context) {
//        showProgressBar.setValue(ProgressUIType.SHOW);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        backendApi = RetrofitClient.getInstance().getFacilityApi();
        RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data"),
                "233");
        Observable<Combine_CommonModel_3> listObservable
                = Observable.zip(
                backendApi.call_state_ID_2(request_id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, StateModel>() {
                    @Override
                    public StateModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new StateModel();
                    }
                }),
                backendApi.call_specialty()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }),
                backendApi.call_get_weekdays()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, HourlyRate_DayOfWeek_OptionModel>() {
                    @Override
                    public HourlyRate_DayOfWeek_OptionModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new HourlyRate_DayOfWeek_OptionModel();
                    }
                }),
                backendApi.call_search_for_credentials_list()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Function<Throwable, CommonModel>() {
                    @Override
                    public CommonModel apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("TAG", "apply: " + throwable.getMessage());
                        return new CommonModel();
                    }
                }), new Function4<StateModel, CommonModel, HourlyRate_DayOfWeek_OptionModel, CommonModel, Combine_CommonModel_3>() {
                    @NonNull
                    @Override
                    public Combine_CommonModel_3 apply(@NonNull StateModel stateModel, @NonNull CommonModel commonModel,
                                                       @NonNull HourlyRate_DayOfWeek_OptionModel
                                                               hourlyRate_dayOfWeek_optionModel, @NonNull CommonModel commonModel2) throws Exception {
                        return new Combine_CommonModel_3(stateModel, commonModel, hourlyRate_dayOfWeek_optionModel, commonModel2);
                    }
                });

        listObservable.subscribe(new Observer<Combine_CommonModel_3>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Combine_CommonModel_3 combineData) {
                if (combineData != null) {
                    if (combineData.getStateModel() != null &&
                            combineData.getStateModel().getData() != null) {
                        List<State_Datum> list = new ArrayList<>();
                        list.add(new State_Datum("0", "Select State"));
                        list.addAll(combineData.getStateModel().getData());
                        list_State.setValue(list);
//                        appController.setList_assignment_durations(list);
                    }
                    if (combineData.getCommonModel_specialty() != null &&
                            combineData.getCommonModel_specialty().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
//                        list.add(new CommonDatum(-1, "Select Speciality"));
                        list.addAll(combineData.getCommonModel_specialty().getData());
                        list_speciality.setValue(list);
                    }
                    if (combineData.getDayOfWeek_optionModel() != null &&
                            combineData.getDayOfWeek_optionModel().getData() != null) {
                        List<HourlyRate_DayOfWeek_OptionDatum> list = new ArrayList<>();
//                        list.add(new HourlyRate_DayOfWeek_OptionDatum("0", "Select Availability"));
                        list.addAll(combineData.getDayOfWeek_optionModel().getData());
                        list_days_of_week.setValue(list);
                    }
                    if (combineData.getCommonModel_certifcate() != null &&
                            combineData.getCommonModel_certifcate().getData() != null) {
                        List<CommonDatum> list = new ArrayList<>();
                        list.addAll(combineData.getCommonModel_certifcate().getData());
                        list_Certificate.setValue(list);
                    }
                }
                progressDialog.dismiss();
                dialogStatus.setValue(new DialogStatusMessage(DialogStatus.Done, 1));
            }

            @Override
            public void onError(@NonNull Throwable e) {
//                showProgressBar.setValue(ProgressUIType.DATA_ERROR);
                progressDialog.cancel();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void call_apply_filter(DialogFilterFBinding filterBinding, Nurse_Browse_Fragment fragment) {
        String state = "", city = "", zipcode = "", speciality = "",
                availability = "", keywords = "", bill_from = "", bill_to = "",
                certificate = "", tenure_from = "", tenure_to = "", page = "1";
        int status = 0;
        if (selected_state != 0) {
            state = list_State.getValue().get(selected_state).getIso_name();
            status++;
        }
        if (selected_City != 0) {
            city = list_City.getValue().get(selected_City).getName();
            status++;
        }

        if (!TextUtils.isEmpty(filterBinding.edZipcode.getText().toString())) {
            zipcode = filterBinding.edZipcode.getText().toString();

            status++;
        }

        if (select_speciality != null && select_speciality.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < select_speciality.size(); i++) {
                Integer pos = select_speciality.get(i);
                jsonArray.put("" + list_speciality.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());

            status++;
        }
        if (select_daysOfWeek != null && select_daysOfWeek.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < select_daysOfWeek.size(); i++) {
                Integer pos = select_daysOfWeek.get(i);
                jsonArray.put("" + list_days_of_week.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());

            status++;
        }
        if (selected_certificate != null && selected_certificate.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selected_certificate.size(); i++) {
                Integer pos = selected_certificate.get(i);
                jsonArray.put("" + list_Certificate.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());

            status++;
        }

        bill_from = String.valueOf(filterBinding.sliderBillRate.getValues().get(0));
        bill_to = String.valueOf(filterBinding.sliderBillRate.getValues().get(1));

        tenure_from = String.valueOf(filterBinding.sliderTenure.getValues().get(0));
        tenure_to = String.valueOf(filterBinding.sliderTenure.getValues().get(1));

        if (!TextUtils.isEmpty(filterBinding.edKeywords.getText().toString())) {
            keywords = filterBinding.edKeywords.getText().toString();
            status++;
        }
        MediaType mediatTypeStr = MediaType.parse("multipart/form-data");
        RequestBody request_1 = RequestBody.create(mediatTypeStr, "" + speciality);
        RequestBody request_2 = RequestBody.create(mediatTypeStr, "" + availability);
        RequestBody request_3 = RequestBody.create(mediatTypeStr, "" + keywords);
        RequestBody request_4 = RequestBody.create(mediatTypeStr, "" + bill_from);
        RequestBody request_5 = RequestBody.create(mediatTypeStr, "" + bill_to);
        RequestBody request_6 = RequestBody.create(mediatTypeStr, "" + tenure_from);
        RequestBody request_7 = RequestBody.create(mediatTypeStr, "" + tenure_to);
        RequestBody request_8 = RequestBody.create(mediatTypeStr, "" + certificate);
        RequestBody request_9 = RequestBody.create(mediatTypeStr, "" + page);
        RequestBody request_10 = RequestBody.create(mediatTypeStr, "" + state);
        RequestBody request_11 = RequestBody.create(mediatTypeStr, "" + city);
        RequestBody request_12 = RequestBody.create(mediatTypeStr, "" + zipcode);

        Call<NurseModel> call = backendApi.call_apply_filter(request_1, request_2, request_3, request_4, request_5, request_6, request_7, request_8
                , request_9, request_10, request_11, request_12);
        call.enqueue(new Callback<NurseModel>() {
            @Override
            public void onResponse(Call<NurseModel> call, Response<NurseModel> response) {

            }

            @Override
            public void onFailure(Call<NurseModel> call, Throwable t) {

            }
        });

    }
}
