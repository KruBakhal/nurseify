package com.nurseify.app.adapter;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurseify.app.R;

import java.util.ArrayList;
import java.util.List;

public class Nurse_FilesAdapter extends RecyclerView.Adapter<Nurse_FilesAdapter.MyHolder> {
    public List<String> list = new ArrayList<>();
    int type = 1;
    private long mLastClickTime = 0;

    public Nurse_FilesAdapter(List<String> list, int type) {
        this.list = list;
        this.type = type;
    }

    public interface PhotoFilesListner {
        void onCLick_View(int position);

        void onCLick_Download(int position);
    }

    PhotoFilesListner photoFilesListner = null;


    public Nurse_FilesAdapter(List<String> list, PhotoFilesListner itemCallback) {
        this.list = list;
        this.type = 4;
        this.photoFilesListner = itemCallback;
    }

    public Nurse_FilesAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nurse_files, parent, false);
        return new MyHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        DisplayMetrics dm = holder.img.getResources().getDisplayMetrics();
       /* int width = dm.widthPixels * 400 / 1080;
        int height = dm.widthPixels * 400 / 1080;
        holder.img.getLayoutParams().width = width;
        holder.img.getLayoutParams().height = height;
        width = dm.widthPixels * 90 / 1080;
        height = dm.widthPixels * 90 / 1080;*/
        holder.img.setImageResource(R.drawable.pdf_icon);

        holder.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 300) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                photoFilesListner.onCLick_View(position);
            }
        });
        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 300) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                photoFilesListner.onCLick_Download(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        else
            return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img, imgDownload, imgSearch;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgSearch = itemView.findViewById(R.id.imgSearchViewFile);
        }
    }
}
