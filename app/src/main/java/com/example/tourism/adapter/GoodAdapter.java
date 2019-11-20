package com.example.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.BigImaActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {
    private List<String> picList;
    private Context context;
    private LayoutInflater inflate;

    public GoodAdapter(Context context) {
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.image_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pic = picList.get(position);
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(context, pic, holder.imageView);
        holder.imageView.setOnClickListener(v -> AppUtils.getToast("你点击了第" + position + "张图片"));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BigImaActivity.class);
                intent.putExtra("pic",pic);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
