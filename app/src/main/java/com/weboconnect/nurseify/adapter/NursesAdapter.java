package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.DialogInviteNurseBinding;
import com.weboconnect.nurseify.databinding.ItemNursesBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.MessageFacilityActivity;
import com.weboconnect.nurseify.screen.facility.NurseDetailsActivity;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.facility.model.OfferedJobNurseModel;
import com.weboconnect.nurseify.screen.facility.model.OfferedJobNurse_Datum;
import com.weboconnect.nurseify.screen.facility.model.Offered_Job_F_Model;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NursesAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private List<NurseDatum> listPostedJob;
    private List<NurseDatum> copy_contactList = new ArrayList<>();
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    public boolean isLoaderVisible = false;
    Activity activity;
    private long mLastClickTime = 0;

    public interface NurseListener {
        void onClick_Msg(NurseDatum model, int position);

        void onClick_Like(NurseDatum model, int position);

        void onClick_Hire(NurseDatum model, int position);
    }

    NurseListener postedListener;

    public NursesAdapter(Activity activity, List<NurseDatum> listPostedJob, NurseListener postedListener) {
        this.activity = activity;
        this.listPostedJob = listPostedJob;
        this.postedListener = postedListener;
        this.copy_contactList = listPostedJob;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:

                return new ViewHolder(ItemNursesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == listPostedJob.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int pos) {
        holder.onBind(pos);
    }

    public void remove_Job(int position) {
        listPostedJob.remove(position);
        notifyItemRemoved(position);
    }

    public void addItems(List<NurseDatum> postItems) {
        listPostedJob.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        listPostedJob.add(new NurseDatum());
        notifyItemInserted(listPostedJob.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = listPostedJob.size() - 1;
        NurseDatum item = getItem(position);
        if (item != null) {
            listPostedJob.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void clear() {
        listPostedJob.clear();
        notifyDataSetChanged();
    }

    NurseDatum getItem(int position) {
        if (listPostedJob == null || listPostedJob.size() == 0)
            return null;
        return listPostedJob.get(position);
    }

    @Override
    public int getItemCount() {
        if (listPostedJob == null || listPostedJob.size() == 0)
            return 0;

        return listPostedJob.size();
    }

    class ViewHolder extends BaseViewHolder {
        ItemNursesBinding itemView;
        private ProgressDialog progressDialog;
        public int selected_job = 0;
        List<OfferedJobNurse_Datum> listPostedJobs = new ArrayList<>();

        public ViewHolder(@NonNull ItemNursesBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        @Override
        public void onBind(int position) {
            NurseDatum model = listPostedJob.get(position);
            Glide.with(itemView.imgProfile.getContext()).load(model.getNurseLogo()).placeholder(R.drawable.person)
                    .error(R.drawable.person).into(itemView.imgProfile);
            itemView.tvName.setText(model.getFirstName() + " " + model.getLastName());
            itemView.tvDescription.setText(model.getSummary());
            if (model.getRating() != null && !TextUtils.isEmpty(model.getRating().getOverAll()))
                itemView.tvRating.setText(model.getRating().getOverAll());
            String rate = model.getHourlyPayRate();
            if (TextUtils.isEmpty(rate))
                rate = "0";
            itemView.tvRate.setText("$ " + rate + "/Hr");

            itemView.layItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    postedListener.onClick_Hire(model, position);
                }
            });
            itemView.layHire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    check_open_inviteBox(itemView.layHire.getContext(), model, position);
                }
            });
            itemView.layMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    activity.startActivity(new Intent(activity, MessageFacilityActivity.class)
                            .putExtra("sender_id", model.getUserId())
                            .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model)));
                }
            });
        }

        private void check_open_inviteBox(Context context, NurseDatum nurseDatum, int position) {
            if (!Utils.isNetworkAvailable(context)) {
                Utils.displayToast(context, context.getResources().getString(R.string.no_internet));
                return;
            }
            Utils.displayToast(context, null); // to cancel toast if showing on screen
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            String user_id = new SessionManager(context).get_user_register_Id();
            RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), nurseDatum.getUserId());
            RequestBody user_id2 = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(context).get_facilityProfile().getFacilityId());

            Call<OfferedJobNurseModel> call = RetrofitClient.getInstance().getFacilityApi()
                    .call_offer_job_to_nurse_dropdown(user_id1, user_id2);

            call.enqueue(new Callback<OfferedJobNurseModel>() {
                @Override
                public void onResponse(Call<OfferedJobNurseModel> call, Response<OfferedJobNurseModel> response) {
                    try {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            Utils.displayToast(context, response.body().getMessage());
                            progressDialog.dismiss();
                            return;
                        }
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            OfferedJobNurseModel jobModel = response.body();
//                        Utils.displayToast(context, jobModel.getMessage());
                            open_Invite_dialog(jobModel.getData(), context, nurseDatum);

                        } else {
                            Utils.displayToast(context, "Yet, no jobs created !");
                            progressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<OfferedJobNurseModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("TAG", "onFailure: " + t.getMessage());
                }
            });


        }

        private void open_Invite_dialog(List<OfferedJobNurse_Datum> data, Context context, NurseDatum model) {

            listPostedJobs.add(new OfferedJobNurse_Datum("-1", "Choose Job/Assignment"));
            listPostedJobs.addAll(data);
            DialogInviteNurseBinding inviteNurseBinding = DialogInviteNurseBinding.inflate(activity.getLayoutInflater(), null, false);
            final Dialog dialog = new Dialog(context, R.style.AlertDialog);
            dialog.setContentView(inviteNurseBinding.getRoot());
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setCancelable(false);
            inviteNurseBinding.tvTitle.setText("Make an offer to " + model.getFirstName() + " " + model.getLastName());
            inviteNurseBinding.tv1.setText(new SessionManager(context).get_facilityProfile().getFacilityName());
            inviteNurseBinding.layDone.setVisibility(View.VISIBLE);
            inviteNurseBinding.layDetail.setVisibility(View.GONE);
            inviteNurseBinding.tvSucces.setVisibility(View.GONE);
            inviteNurseBinding.layJobs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptionPopup(context, inviteNurseBinding.view2, 2, inviteNurseBinding.img2
                            , inviteNurseBinding.tv2, listPostedJobs, selected_job, new ItemCallback() {
                                @Override
                                public void onClick(int position) {
                                    selected_job = position;
                                    String f_name = listPostedJobs.get(position).getContent().getName();
                                    OfferedJobNurse_Datum jobDatum = listPostedJobs.get(position);
                                    inviteNurseBinding.tv2
                                            .setText("" + f_name + " - " + listPostedJobs.get(position)
                                                    .getContent().getSpecialty());
                                    inviteNurseBinding.layDone.setVisibility(View.VISIBLE);
                                    inviteNurseBinding.layDetail.setVisibility(View.GONE);
                                    inviteNurseBinding.tvSucces.setVisibility(View.GONE);
                                    if (position == 0)
                                        return;
                                    inviteNurseBinding.tv3.setText("Hello " + f_name);
                                    inviteNurseBinding.tv4.setText(f_name + " would like to book you for the assignment below.");
                                    inviteNurseBinding.tvFacilityName.setText("Facility Name: " + f_name);
                                    inviteNurseBinding.tvlocation.setText("Location : " + jobDatum.getContent().getLocation());
                                    inviteNurseBinding.tvSpecialty.setText("Specialty : " + jobDatum.getContent().getSpecialty());
                                    inviteNurseBinding.tvStartDate.setText("Start Date : " + jobDatum.getContent().getJobDetail().getStartDate());
                                    inviteNurseBinding.tvAssignmentDuration.setText("Duration : " + jobDatum.getContent().getJobDetail().getDuration());
                                    inviteNurseBinding.tvShiftDuration.setText("Shift : " + jobDatum.getContent().getSpecialty());
                                    inviteNurseBinding.tvWorkDays.setText("Work Days : " + jobDatum.getContent().getJobDetail().getWorkdays());
                                    inviteNurseBinding.tv5.setText(Html.fromHtml("" + jobDatum.getContent().getTerms()));
                                    inviteNurseBinding.layDetail.setVisibility(View.VISIBLE);
                                    inviteNurseBinding.layDone.setVisibility(View.VISIBLE);
                                    inviteNurseBinding.tvSucces.setVisibility(View.GONE);
                                }
                            });
                    Utils.onClickEvent(v);
                }
            });

            inviteNurseBinding.layDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected_job == 0) {
                        Utils.displayToast(context, "First Select Job/Assignment !");
                        return;
                    }
                    make_offer(context, dialog, listPostedJobs.get(selected_job), inviteNurseBinding
                            , model);

                    Utils.onClickEvent(v);
                }
            });

            inviteNurseBinding.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Utils.onClickEvent(v);
                }
            });


            dialog.show();

        }

        private void make_offer(Context context, Dialog dialog, OfferedJobNurse_Datum facility_jobDatum,
                                DialogInviteNurseBinding inviteNurseBinding, NurseDatum model) {
            if (!Utils.isNetworkAvailable(itemView.layHire.getContext())) {
                Utils.displayToast(context, context.getResources().getString(R.string.no_internet));
                return;
            }
//        showProgress();
            inviteNurseBinding.layProgress.setVisibility(View.VISIBLE);
            inviteNurseBinding.progressBar.setVisibility(View.VISIBLE);
            inviteNurseBinding.tvMsg.setText("Please Wait");
            String user_id = new SessionManager(context).get_facilityProfile().getUserId();
            RequestBody nurseReques = RequestBody.create(MediaType.parse("multipart/form-data"), model.getNurseId());
            RequestBody facilityReques = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
            RequestBody jo_id = RequestBody.create(MediaType.parse("multipart/form-data"), "" + facility_jobDatum.getJobId());


            Call<Offered_Job_F_Model> call = RetrofitClient.getInstance().getFacilityApi()
                    .call_send_offer(nurseReques, facilityReques, jo_id);

            call.enqueue(new Callback<Offered_Job_F_Model>() {
                @Override
                public void onResponse(Call<Offered_Job_F_Model> call, Response<Offered_Job_F_Model> response) {
                    if (response == null || response.body() == null) {
                        inviteNurseBinding.layProgress.setVisibility(View.GONE);
                        inviteNurseBinding.progressBar.setVisibility(View.GONE);
                        inviteNurseBinding.tvMsg.setText("Make an Offer");
                        Utils.displayToast(context, "" + response.message());
                        return;
                    }
                    if (!response.body().getApiStatus().equals("1")) {
                        inviteNurseBinding.layProgress.setVisibility(View.GONE);
                        inviteNurseBinding.progressBar.setVisibility(View.GONE);
                        inviteNurseBinding.tvMsg.setText("Make an Offer");
                        Utils.displayToast(context, "" + response.body().getMessage());
                        return;
                    }
                    if (response.isSuccessful()) {
                        inviteNurseBinding.tvMsg.setVisibility(View.GONE);
                        inviteNurseBinding.layProgress.setVisibility(View.GONE);
                        inviteNurseBinding.progressBar.setVisibility(View.GONE);
                        inviteNurseBinding.tvMsg.setText("Make an Offer");
                        inviteNurseBinding.tvSucces.setVisibility(View.VISIBLE);
                        inviteNurseBinding.layDetail.setVisibility(View.GONE);
                        inviteNurseBinding.layDone.setVisibility(View.GONE);
                        if (listPostedJobs != null && listPostedJobs.size() != 0)
                            listPostedJobs.remove(selected_job);
                        selected_job = 0;
                        inviteNurseBinding.tv2.setText("");
                        Utils.displayToast(context, response.body().getMessage());

                        Log.d("TAG", "onResponse: " + response.body().getData().toString());

                    } else {
                        inviteNurseBinding.layProgress.setVisibility(View.GONE);
                        inviteNurseBinding.progressBar.setVisibility(View.GONE);
                        inviteNurseBinding.tvMsg.setText("Make an Offer");
                        Utils.displayToast(context, context.getResources().getString(R.string.something_when_wrong));
                        dialog.dismiss();
                    }

//                    dismissProgress();
                }

                @Override
                public void onFailure(Call<Offered_Job_F_Model> call, Throwable t) {
                    inviteNurseBinding.layProgress.setVisibility(View.GONE);
                    inviteNurseBinding.progressBar.setVisibility(View.GONE);
                    inviteNurseBinding.tvMsg.setText("Make an Offer");
                    Utils.displayToast(context, context.getResources().getString(R.string.something_when_wrong));
                }
            });
        }

        private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                     TextView tvState, List<OfferedJobNurse_Datum> cityData, int selected_City,
                                     ItemCallback itemCallback) {
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
            popup.setHeight(activity.getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
            RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
            img1.setRotation(-180);
            View finalImg = img1;
            popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    finalImg.setRotation(0);
                }
            });

            JobsAdapter parentChildAdapter = new JobsAdapter(this, type
                    , selected_City, cityData, new JobsAdapter.JobsAdapterInterface() {
                @Override
                public void onCLickItem(int position, int ty) {
                    itemCallback.onClick(position);

                    popup.dismiss();
                }
            });
            recyclerView.setAdapter(parentChildAdapter);
            recyclerView.scrollToPosition(selected_City);
            popup.showAsDropDown(v, 0, 0);
        }

        @Override
        public int getCurrentPosition() {
            return super.getCurrentPosition();
        }

        @Override
        protected void clear() {

        }
    }

    private Filter fRecords;

    @Override
    public Filter getFilter() {

        if (fRecords == null) {
            fRecords = new RecordFilter1();
        }
        return fRecords;
    }

    public class RecordFilter1 extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults oReturn = new FilterResults();
            final ArrayList<NurseDatum> results = new ArrayList<>();
            if (listPostedJob != null)
                if (constraint != null && !TextUtils.isEmpty(constraint)) {
                    if (copy_contactList != null && copy_contactList.size() > 0) {
                        for (NurseDatum g : copy_contactList) {

                            try {
                                if (g.getFirstName().toLowerCase()
                                        .startsWith(constraint.toString().toLowerCase())) {
                                    results.add(g);
                                }
                            } catch (Exception exception) {
                                Log.d("TAG", "performFiltering: " + exception.getMessage());
                            }
                        }
                    }
                    oReturn.values = results;
                } else {
                    oReturn.count = copy_contactList.size();
                    oReturn.values = copy_contactList;
                }
            return oReturn;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listPostedJob = (ArrayList<NurseDatum>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
