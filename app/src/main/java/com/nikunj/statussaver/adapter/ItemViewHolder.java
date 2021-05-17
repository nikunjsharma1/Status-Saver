package com.nikunj.statussaver.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikunj.statussaver.R;


public class ItemViewHolder extends RecyclerView.ViewHolder{
    public ImageButton save, share;
    public ImageView imageView;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ivThumbnail);
        save = itemView.findViewById(R.id.save);
        share = itemView.findViewById(R.id.share);
    }
}
