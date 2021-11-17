package com.weboconnect.nurseify.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoFilesAdapter extends RecyclerView.Adapter<PhotoFilesAdapter.MyHolder> {
    public List<String> list = new ArrayList<>();
    int type = 1;

    public PhotoFilesAdapter(List<String> list, int type) {
        this.list = list;
        this.type = type;
    }

    public PhotoFilesAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photos_files, parent, false);

        return new MyHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (type == 1) {
            Glide.with(holder.itemView.getContext())
                    .load(list.get(position)).into(holder.img);

        } else {
            holder.img.setImageResource(R.drawable.pdf_icon);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
