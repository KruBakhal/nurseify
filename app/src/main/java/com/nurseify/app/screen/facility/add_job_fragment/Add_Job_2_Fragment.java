package com.nurseify.app.screen.facility.add_job_fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nurseify.app.R;
import com.nurseify.app.adapter.PhotoFilesAdapter;
import com.nurseify.app.databinding.ActivityAddJob2Binding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.JobPhoto;
import com.nurseify.app.screen.facility.viewModel.Add_Job_ViewModel;
import com.nurseify.app.screen.facility.viewModel.DialogStatus;
import com.nurseify.app.screen.facility.viewModel.DialogStatusMessage;
import com.nurseify.app.screen.facility.viewModel.ProgressUIType;
import com.nurseify.app.screen.nurse.model.ResponseModel;
import com.nurseify.app.utils.FileUtils;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Job_2_Fragment extends Fragment {
    String id;
    ActivityAddJob2Binding binding;
    View view;
    private Add_Job_ViewModel viewModel;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private PhotoFilesAdapter photoFilesAdapter;

    public Add_Job_2_Fragment() {

    }

    public static Add_Job_2_Fragment newInstance(int param1, String param2) {
        Add_Job_2_Fragment fragment = new Add_Job_2_Fragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_add_job2, null, false);
        viewModel = new ViewModelProvider(requireActivity()).get(Add_Job_ViewModel.class);
        setAdapter();
        observer_view();
        addText_view();
        setData();
        click();
        return view = binding.getRoot();
    }

    private void setData() {
        viewModel.hrs_rate = 5;
        viewModel.description = "";
        viewModel.qualifiaction = "";
        viewModel.responsibilities = "";
        viewModel.youtube = "";
        viewModel.isActive = false;
        viewModel.selected_list_photos = new ArrayList<>();
        setAdapter();
        binding.rvPhotos.setVisibility(View.GONE);
        try {
            if (viewModel.isEdit) {
                binding.textView3.setText("Edit Job");
                binding.distanceSlider.setProgress(Integer.parseInt(viewModel.jobDatum.getPreferredHourlyPayRate()));
                if (!TextUtils.isEmpty(viewModel.jobDatum.getDescription())) {
                    binding.edDescription.setText(Html.fromHtml(viewModel.jobDatum.getDescription()));
                }
                if (!TextUtils.isEmpty(viewModel.jobDatum.getResponsibilities())) {
                    binding.edResponsibilities.setText(Html.fromHtml(viewModel.jobDatum.getResponsibilities()));
                }
                if (!TextUtils.isEmpty(viewModel.jobDatum.getQualifications())) {
                    binding.edQualifications.setText(Html.fromHtml(viewModel.jobDatum.getQualifications()));
                }
                if (!TextUtils.isEmpty(viewModel.jobDatum.getJobVideo())) {
                    binding.edYoutube.setText(viewModel.jobDatum.getJobVideo());
                }
                setupPhotoData();
                if (viewModel.jobDatum.getActive().equals(1)) {
                    binding.checkBox.setChecked(true);
                } else {
                    binding.checkBox.setChecked(false);
                }

            }
        } catch (Exception exception) {
            Log.d("TAG", "setData: " + exception.getMessage());
        }
    }

    private void setupPhotoData() {
        if (viewModel.jobDatum.getJobPhotos() != null && viewModel.jobDatum.getJobPhotos().size() != 0) {
            /*if (viewModel.edit_list_photos == null) {
                viewModel.edit_list_photos = new ArrayList<>();
            }
            viewModel.edit_list_photos.addAll(viewModel.jobDatum.getJobPhotos());*/
            viewModel.selected_list_photos = new ArrayList<>();
            for (JobPhoto jobPhoto : viewModel.jobDatum.getJobPhotos()) {
                viewModel.selected_list_photos.add(jobPhoto.getName());
            }
            binding.rvPhotos.setVisibility(View.VISIBLE);
            if (photoFilesAdapter != null) {
                setAdapter();
                photoFilesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void addText_view() {
        binding.distanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvRate.setText("$ " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.distanceSlider.setProgress(5);
        binding.edDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                /*if (s == null || s.length() == 0)
                    binding.edDescription.setError(null);
                else if (!Utils.patternAlphabetNumbersSpace.matcher(s.toString()).find()) {
                    binding.edDescription.setError("Description Can Contain Alphabets, Numbers, Space !");
                } else {
                    binding.edDescription.setError(null);
                }*/
            }
        });
        binding.edQualifications.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                /*if (s == null || s.length() == 0)
                    binding.edQualifications.setError(null);
                else if (!Utils.patternAlphabetNumbersSpace.matcher(s.toString()).find()) {
                    binding.edQualifications.setError("Qualifications Can Contain Alphabets, Numbers, Space !");
                } else {
                    binding.edQualifications.setError(null);
                }*/
            }
        });
        binding.edResponsibilities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                /*if (s == null || s.length() == 0)
                    binding.edResponsibilities.setError(null);
                else if (!Utils.patternAlphabetNumbersSpace.matcher(s.toString()).find()) {
                    binding.edResponsibilities.setError("Description Can Contain Alphabets, Numbers, Space !");
                } else {
                    binding.edResponsibilities.setError(null);
                }*/
            }
        });
        binding.edYoutube.addTextChangedListener(new TextWatcher() {
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
                        binding.edYoutube.setError("Enter Valid Youtube/Vinmo URL In Proper Format !");
                    }
                } else {
                    binding.edYoutube.setError(null);
                }
            }
        });

    }

    private void observer_view() {
        binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Cancel, mParam1));

            }
        });
        binding.layAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);

                if (!checkReadExternal()) {
//                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE});
                    requestPermission();
                    return;
                }

                if (viewModel.selected_list_photos != null && viewModel.selected_list_photos.size() > 3) {
                    Utils.displayToast(getContext(), "Max 4 photos can be upload only !");
                    return;
                }

                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpg", "image/png", "image/jpeg"});
                resultLauncherPhotos.launch(intent);
                Utils.onClickEvent(v);
            }


        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            String desc = "";
            String qual = "";
            String respo = "";
            String youtube = "";

            @Override
            public void onClick(View v) {
                if (check_validation()) {
                    viewModel.description = desc;
                    viewModel.qualifiaction = qual;
                    viewModel.responsibilities = respo;
                    viewModel.youtube = youtube;
                    viewModel.isActive = binding.checkBox.isChecked();
                    viewModel.hrs_rate = binding.distanceSlider.getProgress();
                    viewModel.peform_add_job(getContext());
                }
            }

            private boolean check_validation() {
                desc = binding.edDescription.getText().toString();
                qual = binding.edQualifications.getText().toString();
                respo = binding.edResponsibilities.getText().toString();
                youtube = binding.edYoutube.getText().toString();
                if (TextUtils.isEmpty(desc)) {
                    binding.edDescription.setError("Enter Description First !");
                    return false;
                }
                /*if (!(Utils.patternAlphabetNumbersSpace.matcher(desc).find())) {
                    binding.edDescription.setError("Description Can Contain Alphabets, Numbers, Space !");
                    return false;
                }*/
                if (TextUtils.isEmpty(respo)) {
                    binding.edQualifications.setError("Enter Responsibilities First !");
                    return false;
                }
                /*if (!(Utils.patternAlphabetNumbersSpace.matcher(respo).find())) {
                    binding.edQualifications.setError("Responsibilities Can Contain Alphabets, Numbers, Space !");
                    return false;
                }*/
                if (TextUtils.isEmpty(qual)) {
                    binding.edQualifications.setError("Enter Qualifications First !");
                    return false;
                }
                /*if (!(Utils.patternAlphabetNumbersSpace.matcher(qual).find())) {
                    binding.edQualifications.setError("Qualifications Can Contain Alphabets, Numbers, Space !");
                    return false;
                }*/
                if (!TextUtils.isEmpty(youtube) && !(Utils.patternYoutubeURL.matcher(youtube).find())) {
                    binding.edYoutube.setError("Enter Youtube/Vinmo URL In Proper Format !");
                    return false;
                }
                return true;
            }
        });
    }

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    ActivityResultLauncher<Intent> resultLauncherPhotos = registerForActivityResult(
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
                        //e.printStackTrace();
                        Log.d("TAG", ": " + e.getMessage());
//                        crashlytics.recordException(e);
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
                        if ((sis > 5 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(getContext(), "Select File less than equal to 5 MB");
                            return;
                        }
                    } catch (NumberFormatException exception) {
                        Log.d("TAG", ": " + exception.getMessage());
//                        crashlytics.recordException(exception);
                    }
//                    } catch (Exception e) {
//                    Utils.displayToast(getContext(), "uri " + data.getData());
//                    Utils.displayToast(getContext(), "file " + file.getAbsolutePath());
//                    Log.d("TAG", "resultLauncherPhotos: " + e.getMessage());
//                    crashlytics.recordException();

//                        return;
//                    }
                    if (file != null) {
                        if (viewModel.selected_list_photos == null) {
                            viewModel.selected_list_photos = new ArrayList<>();
                            setAdapter();
                        }
                        viewModel.selected_list_photos.add(file.getAbsolutePath());
                        binding.rvPhotos.setVisibility(View.VISIBLE);
                        if (photoFilesAdapter != null) {
                            setAdapter();
                            photoFilesAdapter.notifyDataSetChanged();
                        }
                    }
                }

            });

    private void setAdapter() {
        if (viewModel.isEdit) {
            photoFilesAdapter = new PhotoFilesAdapter(viewModel.selected_list_photos, 1, 1
                    , new ItemCallback() {
                @Override
                public void onClick(int position) {
                    if (!TextUtils.isEmpty(viewModel.selected_list_photos.get(position))
                            && viewModel.selected_list_photos.get(position).startsWith("http://")) {
                        String pos = getAsserId(viewModel.jobDatum.getJobPhotos(),
                                viewModel.selected_list_photos.get(position));
                        if (!TextUtils.isEmpty(pos)) {
                            String asserId = viewModel.jobDatum.getJobPhotos().get(Integer.parseInt(pos)).getAssetId();
                            performDelete(asserId, viewModel.jobDatum.getJobId(), position, pos);
                        }
                    } else {
                        if (viewModel.selected_list_photos != null && position < viewModel.selected_list_photos.size()) {
                            viewModel.selected_list_photos.remove(position);
                            setAdapter();
                        }
                    }
                }

                private String getAsserId(List<JobPhoto> jobPhotos, String s) {
                    for (int i = 0; i < jobPhotos.size(); i++) {
                        JobPhoto jobPhoto = jobPhotos.get(i);
                        if (jobPhoto.getName().equals(s)) {
                            return "" + i;
                        }
                    }
                    return null;
                }

                private void performDelete(String assetId, String job_id, int position, String pos) {
                    if (!Utils.isNetworkAvailable(getContext())) {
                        Utils.displayToast(getContext(), getContext().getResources().getString(R.string.no_internet));
                        return;
                    }
                    viewModel.showProgressBar.setValue(ProgressUIType.SHOW);

                    RequestBody job_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), job_id);
                    RequestBody assetId1 = RequestBody.create(MediaType.parse("multipart/form-data"), assetId);

                    Call<ResponseModel> call = RetrofitClient.getInstance().getFacilityApi()
                            .call_remove_Asset_image(job_id1, assetId1);

                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            assert response.body() != null;
                            if (!response.body().getApiStatus().equals("1")) {
                                Utils.displayToast(getContext(), "" + response.body().getMessage());
                                viewModel.showProgressBar.setValue(ProgressUIType.DIMISS);
                                return;
                            }
                            if (response.isSuccessful()) {
                                viewModel.showProgressBar.setValue(ProgressUIType.DIMISS);
                                Utils.displayToast(getContext(), "Item data deleted !");
                                viewModel.selected_list_photos.remove(position);
                                if (viewModel.jobDatum.getJobPhotos() != null
                                        && viewModel.jobDatum.getJobPhotos().size() != 0) {
                                    List<JobPhoto> list = viewModel.jobDatum.getJobPhotos();
                                    int a = Integer.parseInt(pos);
                                    list.remove(a);
                                    viewModel.jobDatum.setJobPhotos(list);
                                }
                                setAdapter();
                            } else {
                                viewModel.showProgressBar.setValue(ProgressUIType.DIMISS);
                                Utils.displayToast(getContext(), "Failed to delete item data");
                            }
                            viewModel.showProgressBar.setValue(ProgressUIType.DIMISS);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            viewModel.showProgressBar.setValue(ProgressUIType.DIMISS);
                            Log.e("TAG" + "getNurseProfile", t.toString());
                        }
                    });

                }
            });
            binding.rvPhotos.setAdapter(photoFilesAdapter);
        } else {
            photoFilesAdapter = new PhotoFilesAdapter(viewModel.selected_list_photos, 1);
            binding.rvPhotos.setAdapter(photoFilesAdapter);

        }
    }

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


}