package com.example.tourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.ScenicSpot;

import java.util.List;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.SpotViewHolder> {

    private List<ScenicSpot> scenicSpots;

    public SpotAdapter(List<ScenicSpot> scenicSpots) {
        this.scenicSpots = scenicSpots;
    }

    @NonNull
    @Override
    public SpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_adapter,parent,false);
        return new SpotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotViewHolder holder, int position) {
        ScenicSpot scenicSpot = scenicSpots.get(position);
        holder.spotTitle.setText(scenicSpot.getScenicSpotTheme());
        holder.spotPic.setImageResource(R.drawable.defaultbg);
        holder.spotStart.setText("出发:"+scenicSpot.getStartLand());
        holder.spotEnd.setText("终点:"+scenicSpot.getEndLand());
        holder.spotPrice.setText(scenicSpot.getScenicSpotPrice()+"起/人");
    }

    @Override
    public int getItemCount() {
        return scenicSpots.size();
    }

    static class SpotViewHolder extends RecyclerView.ViewHolder {

         ImageView spotPic;
         TextView spotTitle;
         TextView spotStart;
         TextView spotEnd;
         TextView spotPrice;




        public SpotViewHolder(@NonNull View itemView) {
            super(itemView);
            spotPic = (ImageView) itemView.findViewById(R.id.spot_pic);
            spotTitle = (TextView) itemView.findViewById(R.id.spot_title);
            spotStart = (TextView) itemView.findViewById(R.id.spot_start);
            spotEnd = (TextView) itemView.findViewById(R.id.spot_end);
            spotPrice = (TextView) itemView.findViewById(R.id.spot_price);
        }
    }

}
