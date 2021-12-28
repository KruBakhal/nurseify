package com.weboconnect.nurseify.screen.facility.dialog;

import static com.weboconnect.nurseify.utils.Utils.patternAlphabetNumbers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.weboconnect.nurseify.databinding.DialogProfileSetup5Binding;
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
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSetupDialog_5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSetupDialog_5 extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private DialogProfileSetup5Binding setup1Binding;
    private RoundedImageView imgProfile;
    private RegistrationViewModel viewModel;
    private FacilityProfile model;
    private String facility_profile;


    public ProfileSetupDialog_5() {
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
    public static ProfileSetupDialog_5 newInstance(int param1, String param2) {
        ProfileSetupDialog_5 fragment = new ProfileSetupDialog_5();
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
        setup1Binding = DialogProfileSetup5Binding.inflate(getLayoutInflater(), container, false);
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

        setup1Binding.edFb.addTextChangedListener(new GenericTextWatcher(setup1Binding.edFb, "Facebook"));
        setup1Binding.edTweet.addTextChangedListener(new GenericTextWatcher(setup1Binding.edTweet, "Twitter"));
        setup1Binding.edLinkin.addTextChangedListener(new GenericTextWatcher(setup1Binding.edLinkin, "Linkin"));
        setup1Binding.edInsta.addTextChangedListener(new GenericTextWatcher(setup1Binding.edInsta, "Instagram"));
        setup1Binding.edPinterest.addTextChangedListener(new GenericTextWatcher(setup1Binding.edPinterest, "Pinterest"));
        setup1Binding.edTiktok.addTextChangedListener(new GenericTextWatcher(setup1Binding.edTiktok, "Tik Tok"));
        setup1Binding.edSnap.addTextChangedListener(new GenericTextWatcher(setup1Binding.edSnap, "Snapchat"));
        setup1Binding.edYoutube.addTextChangedListener(new GenericTextWatcher(setup1Binding.edYoutube, "Youtube"));


    }

    private void setData() {

        if (check_any_list_empty()) {
            viewModel.fetch_profile_setup_4();
        } else {
            if (model.getFacilitySocial() == null)
                return;
            if (!TextUtils.isEmpty(model.getFacilitySocial().getFacebook()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getFacebook());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getInstagram()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getInstagram());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getLinkedin()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getLinkedin());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getPinterest()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getPinterest());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getSanpchat()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getSanpchat());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getTiktok()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getTiktok());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getTwitter()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getTwitter());
            if (!TextUtils.isEmpty(model.getFacilitySocial().getYoutube()))
                setup1Binding.edFb.setText(model.getFacilitySocial().getYoutube());

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
                    String you, twitter, snap, tik, linkin, insta, fab, pinterset;
                    you = setup1Binding.edYoutube.getText().toString();
                    twitter = setup1Binding.edTweet.getText().toString();
                    snap = setup1Binding.edSnap.getText().toString();
                    tik = setup1Binding.edTiktok.getText().toString();
                    linkin = setup1Binding.edLinkin.getText().toString();
                    insta = setup1Binding.edInsta.getText().toString();
                    fab = setup1Binding.edFb.getText().toString();
                    pinterset = setup1Binding.edPinterest.getText().toString();


                    facilityProfile.getFacilitySocial().setYoutube(you);
                    facilityProfile.getFacilitySocial().setInstagram(insta);
                    facilityProfile.getFacilitySocial().setFacebook(fab);
                    facilityProfile.getFacilitySocial().setLinkedin(linkin);
                    facilityProfile.getFacilitySocial().setPinterest(pinterset);
                    facilityProfile.getFacilitySocial().setSanpchat(snap);
                    facilityProfile.getFacilitySocial().setTiktok(tik);
                    facilityProfile.getFacilitySocial().setTwitter(twitter);
                    if (viewModel.isEditMode) {
                        viewModel.main_model = facilityProfile;
                    } else {
                        viewModel.new_facilityModel = facilityProfile;
                    }
                    if (Utils.isNetworkAvailable(getContext())) {
                        viewModel.perform_profile_submission((RegistrationFActivity) getActivity(), mParam1);
                    } else {
                        Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
                    }
                }
            }

            private boolean checkValidation() {
                if (!get_view_status(setup1Binding.edTweet, "Twitter")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edTiktok, "Twitter")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edSnap, "Snapchat")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edLinkin, "Linkin")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edFb, "Facebook")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edPinterest, "Pinterest")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edInsta, "Instagram")) {
                    return false;
                } else if (!get_view_status(setup1Binding.edYoutube, "Youtube")) {
                    return false;
                }
                return true;
            }

            private boolean get_view_status(EditText view, String name) {
                if (!TextUtils.isEmpty(view.getText().toString())) {
                    if (!(Patterns.WEB_URL.matcher(view.getText().toString()).find())) {
                        view.setError("Enter " + name + " Valid Link In Proper Format !");
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


    }


    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    private class GenericTextWatcher implements TextWatcher {

        private EditText view;
        String name;

        private GenericTextWatcher(EditText view, String name) {
            this.view = view;
            this.name = name;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                if (!(Patterns.WEB_URL.matcher(s).find())) {
                    view.setError("Enter " + name + " valid link in proper format !");
                }
            } else {
                view.setError(null);
            }

        }
    }
}