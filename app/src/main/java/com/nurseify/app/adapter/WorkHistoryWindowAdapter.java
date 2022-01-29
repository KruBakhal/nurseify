package com.nurseify.app.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nurseify.app.R;
import com.nurseify.app.common.CommonDatum;
import com.nurseify.app.screen.facility.Add_Jobs_Activity;
import com.nurseify.app.screen.facility.RegistrationFActivity;
import com.nurseify.app.screen.nurse.RegisterActivity;
import com.nurseify.app.screen.nurse.model.CernersDatum;
import com.nurseify.app.screen.nurse.model.CredentialDatum;
import com.nurseify.app.screen.nurse.model.Degree_Datum;
import com.nurseify.app.screen.nurse.model.SpecialtyDatum;
import com.nurseify.app.screen.nurse.ui.BrowseFragment;

import java.util.ArrayList;
import java.util.List;


public class WorkHistoryWindowAdapter extends RecyclerView.Adapter<WorkHistoryWindowAdapter.WorkHistoryWindowMyViewHolder> {

    private List<CommonDatum> list_Epic;
    private BrowseFragment context;
    private int type;
    public List<Degree_Datum> list_nurse_degrees = new ArrayList<>();
    public List<CredentialDatum> list_Credential = new ArrayList<>();
    Activity activity;
    private List<SpecialtyDatum> list_assignment = new ArrayList<>();
    private List<SpecialtyDatum> list_facilty_type = new ArrayList<>();
    private List<SpecialtyDatum> list_media = new ArrayList<>();

    public WorkHistoryWindowAdapter(Activity context, int type, List<Object> list_nurse_degrees,
                                    WorkHistoryWindowInterface parentInterface) {
        this.type = type;
        if (list_nurse_degrees instanceof List) {
            if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof Degree_Datum)) {
                this.list_nurse_degrees = ((List<Degree_Datum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CredentialDatum)) {
                this.list_Credential = ((List<CredentialDatum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof SpecialtyDatum)) {
                if (type == 6)
                    this.list_assignment = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));
                else if (type == 7)
                    this.list_facilty_type = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));
                else if (type == 8)
                    this.list_media = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CommonDatum)) {
                if (type == 2)
                    this.list_Epic = (List<CommonDatum>) list_nurse_degrees.get(0);
                else if (type == 3)
                    this.list_Epic = ((List<CommonDatum>) list_nurse_degrees.get(0));
                else if (type == 4)
                    this.list_Epic = ((List<CommonDatum>) list_nurse_degrees.get(0));


            }
        }
        this.activity = context;
        this.parentInterface = parentInterface;
    }

    public WorkHistoryWindowAdapter(Activity context, int type, int type1, List<Degree_Datum> list_nurse_degrees,
                                    WorkHistoryWindowInterface parentInterface) {
        this.type = type;
        this.list_nurse_degrees = list_nurse_degrees;
        this.activity = context;
        this.parentInterface = parentInterface;
    }

    WorkHistoryWindowInterface parentInterface;

    public WorkHistoryWindowAdapter(BrowseFragment context, int type, List<Object> list_nurse_degrees, WorkHistoryWindowInterface parentInterface) {
        this.type = type;
        if (list_nurse_degrees instanceof List) {
            if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof Degree_Datum)) {
                this.list_nurse_degrees = ((List<Degree_Datum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CernersDatum)) {
                this.list_nurse_degrees = ((List<Degree_Datum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof CredentialDatum)) {
                this.list_Credential = ((List<CredentialDatum>) list_nurse_degrees.get(0));
            } else if (((List) list_nurse_degrees).size() > 0
                    && (((List) list_nurse_degrees.get(0)).get(0) instanceof SpecialtyDatum)) {
                if (type == 6)
                    this.list_assignment = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));
                else if (type == 7)
                    this.list_facilty_type = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));
                else if (type == 8)
                    this.list_media = ((List<SpecialtyDatum>) list_nurse_degrees.get(0));


            }
        }
        this.context = context;
        this.parentInterface = parentInterface;
    }


    public interface WorkHistoryWindowInterface {
        void onCLickItem(int i, int position);
    }

    public class WorkHistoryWindowMyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lay_item;

        public WorkHistoryWindowMyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvText);
            lay_item = view.findViewById(R.id.lay_item);
        }
    }


    @Override
    public WorkHistoryWindowMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_dropdown, parent, false);

        return new WorkHistoryWindowMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkHistoryWindowMyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Context context1 = null;
        boolean isNotSelected = true;
        int selectedPos = 0;
        if (type >= 1 && type <= 5) {
            if (activity instanceof RegisterActivity) {
                RegisterActivity activity = (RegisterActivity) this.activity;
                context1 = activity;
                if (type == 1) {
                    Degree_Datum movie = list_nurse_degrees.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.selected_nurse_degree;
                } else if (type == 2) {
                    Degree_Datum movie = list_nurse_degrees.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.selected_nurse_cerner;
                } else if (type == 3) {
                    Degree_Datum movie = list_nurse_degrees.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.selected_nurse_meditech;
                } else if (type == 4) {
                    Degree_Datum movie = list_nurse_degrees.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.selected_nurse_epic;
                } else if (type == 5) {
                    CredentialDatum movie = list_Credential.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.selected_Credential;
                }
            } else {
                Add_Jobs_Activity activity = (Add_Jobs_Activity) this.activity;
                context1 = activity;
                if (type == 2) {
                    CommonDatum movie = list_Epic.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.viewModel.selected_work_cerner;
                } else if (type == 3) {
                    CommonDatum movie = list_Epic.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.viewModel.selected_work_medtech;
                } else if (type == 4) {
                    CommonDatum movie = list_Epic.get(position);
                    holder.title.setText(movie.getName());
                    selectedPos = activity.viewModel.selected_work_epic;
                }
            }
        } else if (activity instanceof RegistrationFActivity) {
            SpecialtyDatum movie = null;
            if (type == 7) {
                movie = (SpecialtyDatum) list_facilty_type.get(position);
                selectedPos = ((RegistrationFActivity) activity).viewModel.selected_facility_type;
            }
            if (movie == null)
                return;
            holder.title.setText(movie.getName());

        } else if (type == 6 || type == 7 || type == 8) {
            BrowseFragment activity = (BrowseFragment) context;
            SpecialtyDatum movie = null;
            context1 = activity.getContext();
            if (type == 6) {
                movie = (SpecialtyDatum) list_assignment.get(position);
                selectedPos = activity.selected_open_assignment_type;
            } else if (type == 7) {

                movie = (SpecialtyDatum) list_facilty_type.get(position);
                selectedPos = activity.selected_facility_type;
            } else if (type == 8) {
                movie = (SpecialtyDatum) list_media.get(position);
                selectedPos = activity.selected_electronic_medical_records;
            }
            if (movie == null)
                return;
            holder.title.setText(movie.getName());

        }

        if (selectedPos == position) {
            isNotSelected = false;
        } else {
            isNotSelected = true;
        }
        if (!isNotSelected) {
            holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.lay_item.setBackground(null);
            holder.title.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.gray));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentInterface.onCLickItem(position, type);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (activity instanceof Add_Jobs_Activity) {
            Add_Jobs_Activity activity = (Add_Jobs_Activity) this.activity;
            if (list_Epic != null && list_Epic.size() != 0) {
                return list_Epic.size();
            }

        } else if (type == 1) {
            if (list_nurse_degrees == null || list_nurse_degrees.size() == 0)
                return 0;
            return list_nurse_degrees.size();
        } else if (type == 5) {
            if (list_Credential == null || list_Credential.size() == 0)
                return 0;
            return list_Credential.size();
        } else if (type == 6 || type == 7 || type == 8) {
            if (type == 6)
                if (list_assignment != null || list_assignment.size() != 0)
                    return this.list_assignment.size();
                else
                    return 0;
            else if (type == 7)
                if (list_facilty_type != null || list_facilty_type.size() != 0)
                    return this.list_facilty_type.size();
                else
                    return 0;
            else if (type == 8)
                if (list_media != null || list_media.size() != 0)
                    return this.list_media.size();
                else
                    return 0;
        } else {
            if (list_nurse_degrees == null || list_nurse_degrees.size() == 0)
                return 0;
            return list_nurse_degrees.size();
        }
        return 0;
    }
}