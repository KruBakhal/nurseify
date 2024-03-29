package com.nurseify.app.adapter;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.intermediate.ItemCallback;

import java.util.ArrayList;
import java.util.List;

public class PhotoFilesAdapter extends RecyclerView.Adapter<PhotoFilesAdapter.MyHolder> {
    private int call = 0;
    private ItemCallback itemCallback;
    public List<String> list = new ArrayList<>();
    public ArrayList<String> listPhotos = new ArrayList<>();
    int type = 1;

    public PhotoFilesAdapter(List<String> list, int type) {
        this.list = list;
        this.type = type;
    }


    public PhotoFilesAdapter(List<String> list, int type, ItemCallback itemCallback) {
        this.list = list;
        this.type = type;
        this.itemCallback = itemCallback;
    }

    public PhotoFilesAdapter(List<String> list) {
        this.list = list;
    }

    public PhotoFilesAdapter(ArrayList<String> selected_list_photos, int type, int call, ItemCallback itemCallback) {
        this.listPhotos = selected_list_photos;
        this.type = type;
        this.call = call;
        this.itemCallback = itemCallback;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photos_files, parent, false);
        return new MyHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        if (call == 0) {
            if (type == 1) {
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position)).into(holder.img);
                holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
            } else if (type == 3) {
                DisplayMetrics dm = holder.img.getResources().getDisplayMetrics();
                int width = dm.widthPixels * 400 / 1080;
                int height = dm.widthPixels * 400 / 1080;
                holder.img.getLayoutParams().width = width;
                holder.img.getLayoutParams().height = height;
                width = dm.widthPixels * 90 / 1080;
                height = dm.widthPixels * 90 / 1080;
                holder.imgDelete.getLayoutParams().width = width;
                holder.imgDelete.getLayoutParams().height = height;
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position)).into(holder.img);
                holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.imgDelete.setVisibility(View.VISIBLE);
            } else if (type == 11) {
                DisplayMetrics dm = holder.img.getResources().getDisplayMetrics();
                int width = dm.widthPixels * 400 / 1080;
                int height = dm.widthPixels * 400 / 1080;
                holder.img.getLayoutParams().width = width;
                holder.img.getLayoutParams().height = height;
                width = dm.widthPixels * 90 / 1080;
                height = dm.widthPixels * 90 / 1080;
                holder.imgDelete.getLayoutParams().width = width;
                holder.imgDelete.getLayoutParams().height = height;
                Glide.with(holder.itemView.getContext())
                        .load(list.get(position)).into(holder.img);
                holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.imgDelete.setVisibility(View.GONE);
            } else if (type == 4) {
//                Bitmap bitmap = generateImageFromPdf(holder.img.getContext(), )
                DisplayMetrics dm = holder.img.getResources().getDisplayMetrics();
                int width = dm.widthPixels * 400 / 1080;
                int height = dm.widthPixels * 400 / 1080;
                holder.img.getLayoutParams().width = width;
                holder.img.getLayoutParams().height = height;
                width = dm.widthPixels * 90 / 1080;
                height = dm.widthPixels * 90 / 1080;
                holder.imgDelete.getLayoutParams().width = width;
                holder.imgDelete.getLayoutParams().height = height;
                holder.img.setImageResource(R.drawable.pdf_icon);
                holder.imgDelete.setVisibility(View.VISIBLE);
            } else {
                holder.img.setImageResource(R.drawable.pdf_icon);
                holder.imgDelete.setVisibility(View.GONE);
            }
        } else {
            if (type == 1) {
                DisplayMetrics dm = holder.img.getResources().getDisplayMetrics();
                int width = dm.widthPixels * 300 / 1080;
                int height = dm.widthPixels * 300 / 1080;
                holder.img.getLayoutParams().width = width;
                holder.img.getLayoutParams().height = height;
                width = dm.widthPixels * 90 / 1080;
                height = dm.widthPixels * 90 / 1080;
                holder.imgDelete.getLayoutParams().width = width;
                holder.imgDelete.getLayoutParams().height = height;
                Glide.with(holder.itemView.getContext())
                        .load(listPhotos.get(position)).into(holder.img);
                holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            holder.imgDelete.setVisibility(View.VISIBLE);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCallback.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (call == 0)
            if (list == null || list.size() == 0)
                return 0;
            else
                return list.size();
        else if (listPhotos == null || listPhotos.size() == 0)
            return 0;
        else
            return listPhotos.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView img, imgDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
