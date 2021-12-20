package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.LanguageDatum;
import com.weboconnect.nurseify.screen.nurse.model.LeaderRolesData;
import com.weboconnect.nurseify.screen.nurse.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionMyViewHolder> {

    private final List<LeaderRolesData> list_LeaderShipRole;
    private final boolean edit_mode;
    public List<QuestionModel> list_QuestionModels = new ArrayList<>();
    RegisterActivity context;

    public QuestionAdapter(RegisterActivity context, List<QuestionModel> list_QuestionModels,
                           List<LeaderRolesData> list_LeaderShipRole, boolean edit_mode, QuestionInterface parentInterface) {
        this.list_QuestionModels = list_QuestionModels;
        this.context = context;
        this.list_LeaderShipRole = list_LeaderShipRole;
        this.parentInterface = parentInterface;
        this.edit_mode = edit_mode;
    }

    QuestionInterface parentInterface;


    public interface QuestionInterface {

        void onCLickItem(int position, int yes);
    }

    public class QuestionMyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ques, tv_lang, tv_ques1;
        public View btnNo, btnYes;
        ImageView img1;
        public View lay_leader, lay_ques, lay_lang;

        public QuestionMyViewHolder(View view) {
            super(view);
            tv_ques = (TextView) view.findViewById(R.id.tv_ques);
            btnYes = view.findViewById(R.id.btnYes);
            btnNo = view.findViewById(R.id.btnNo);
            lay_leader = view.findViewById(R.id.lay_leader);
            lay_ques = view.findViewById(R.id.lay_ques);
            tv_ques1 = view.findViewById(R.id.tv_ques1);
            img1 = view.findViewById(R.id.img1);
            lay_lang = view.findViewById(R.id.lay_lang);
            tv_lang = view.findViewById(R.id.tv_lang);
        }
    }

    @Override
    public QuestionMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questions, parent, false);

        return new QuestionMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionMyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        QuestionModel questionModel = list_QuestionModels.get(position);
        holder.tv_ques.setText(questionModel.getQuestion());
        if (position == 2) {
            if (list_QuestionModels.get(1).getAnswer() != 2 && list_QuestionModels.get(1).getAnswer() == 1) {
                holder.lay_leader.setVisibility(View.VISIBLE);
                holder.lay_ques.setVisibility(View.GONE);
                holder.tv_ques1.setText("" + questionModel.getQuestion());
                if (context.selected_leadership == 0) {
                    holder.tv_lang.setText("");
                } else
                    holder.tv_lang.setText("" + list_LeaderShipRole.get(context.selected_leadership).getName());
            } else {
                holder.lay_leader.setVisibility(View.GONE);
                holder.lay_ques.setVisibility(View.GONE);
            }
            holder.lay_lang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOptionPopup_RoleInterested(context, holder.tv_lang, holder.img1, list_LeaderShipRole, new ItemCallback() {
                        @Override
                        public void onClick(int position) {
                            context.selected_leadership = position;
                            if (position == 0) {
                                holder.tv_lang.setText("");
                            } else
                                holder.tv_lang.setText("" + list_LeaderShipRole.get(position).getName());
                        }
                    });
                }

            });
        }
        if (questionModel.isAnyCheck()) {
            if (questionModel.getAnswer() == 1) {
                holder.btnYes.setBackgroundResource(R.drawable.radio_selected);
                holder.btnNo.setBackgroundResource(R.drawable.radio_unselected);
            } else {
                holder.btnYes.setBackgroundResource(R.drawable.radio_unselected);
                holder.btnNo.setBackgroundResource(R.drawable.radio_selected);
            }
        } else {
            holder.btnYes.setBackgroundResource(R.drawable.radio_unselected);
            holder.btnNo.setBackgroundResource(R.drawable.radio_unselected);
        }

        holder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 1) {
                    context.selected_leadership = 0;
                }
                questionModel.setAnswer(1);
                list_QuestionModels.set(position, questionModel);
                parentInterface.onCLickItem(position, 1);
                notifyDataSetChanged();

            }
        });
        holder.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 1) {
                    context.selected_leadership = 0;
                }
                questionModel.setAnswer(0);
                list_QuestionModels.set(position, questionModel);
                parentInterface.onCLickItem(position, 0);
                notifyDataSetChanged();
            }
        });


    }

    private void showOptionPopup_RoleInterested(RegisterActivity context, View view1, ImageView img1,
                                                List<LeaderRolesData> list_nurse_degrees,
                                                ItemCallback itemCallback) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(context.getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        RoleInteresetWindowAdapter adapter_degree;
        adapter_degree = new RoleInteresetWindowAdapter(context,
                list_nurse_degrees, 2, 2,
                new RoleInteresetWindowAdapter.RoleInterface() {
                    @Override
                    public void onCLickItem(int i, int position) {
                        itemCallback.onClick(i);
                        popup.dismiss();
                    }
                });


        recyclerView.setAdapter(adapter_degree);
        popup.showAsDropDown(view1, 0, 0);
    }

    @Override
    public int getItemCount() {

        if (list_QuestionModels == null || list_QuestionModels.size() == 0)
            return 0;
        return list_QuestionModels.size();

    }
}