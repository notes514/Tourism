package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.TourismDetailsActivity;
import com.example.tourism.utils.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

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
        ScenicSpot scenicSpot = objects.get(position);
        holder.scenicSpotTheme.setText(objects.get(position).getScenicSpotTheme());
        holder.scenicSpotDescribe.setText(objects.get(position).getScenicSpotDescribe());
        holder.scenicSpotPrice.setText("¥:"+objects.get(position).getScenicSpotPrice());
        holder.scenicSpotId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.getToast(objects.get(position).getScenicSpotId()+"");
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("id", scenicSpot.getScenicSpotId());
                context.startActivity(intent);
            }
        });
        ImageLoader.getInstance().displayImage(RequestURL.ip_images+objects.get(position).getScenicSpotPicUrl(),
                holder.scenicSpotPic, InitApp.getOptions());

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public CardView scenicSpotId;
        public ImageView scenicSpotPic;
        public TextView scenicSpotTheme;
        public TextView scenicSpotDescribe;
        public TextView scenicSpotPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.scenicSpotId = itemView.findViewById(R.id.scenic_spot_id);
            this.scenicSpotPic = itemView.findViewById(R.id.scenic_spot_pic);
            this.scenicSpotTheme = itemView.findViewById(R.id.scenic_spot_theme);
            this.scenicSpotDescribe = itemView.findViewById(R.id.scenic_spot_describe);
            this.scenicSpotPrice = itemView.findViewById(R.id.scenic_spot_price);
        }
    }
}
