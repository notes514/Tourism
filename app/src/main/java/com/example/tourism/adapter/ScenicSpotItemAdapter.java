package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.ScenicSpot;

public class ScenicSpotItemAdapter extends RecyclerView.Adapter<ScenicSpotItemAdapter.ViewHolder> {

    private List<ScenicSpot> objects = new ArrayList<ScenicSpot>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ScenicSpotItemAdapter(Context context, List<ScenicSpot> scenicSpots) {
        this.context = context;
        this.objects = scenicSpots;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.scenic_spot_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.scenicSpotPic.setImageResource(objects.get(position).getScenicSpotPicUrl());
        holder.scenicSpotName.setText(objects.get(position).getScenicSpotTheme());
    }


    @Override
    public int getItemCount(

    )
    {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView scenicSpotPic;
        public TextView scenicSpotName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.scenicSpotPic = itemView.findViewById(R.id.scenicSpot_pic);
            this.scenicSpotName = itemView.findViewById(R.id.scenicSpot_name);
        }
    }
}
