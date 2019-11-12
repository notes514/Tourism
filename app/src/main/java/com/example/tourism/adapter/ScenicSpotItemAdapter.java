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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScenicSpotItemAdapter extends RecyclerView.Adapter<ScenicSpotItemAdapter.ViewHolder> {

    private List<ScenicSpot> objects = new ArrayList<ScenicSpot>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ScenicSpotItemAdapter(Context context, List<ScenicSpot> scenicSpots) {
        this.context = context;
        this.objects = scenicSpots;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void loadMore(List<ScenicSpot> scenicSpots){
        if (scenicSpots != null) {
            objects = scenicSpots;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_recommend_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScenicSpot scenicSpot = objects.get(position);
        //显示数据
        ImageLoader.getInstance().displayImage(RequestURL.ip_images + scenicSpot.getScenicSpotPicUrl(),
                holder.ivTourismPic, InitApp.getOptions());
        if (scenicSpot.getTravelMode() == 0) {
            holder.tvTravelMode.setText("国内游");
        } else if (scenicSpot.getTravelMode() == 1) {
            holder.tvTravelMode.setText("出境游");
        } else if (scenicSpot.getTravelMode() == 2) {
            holder.tvTravelMode.setText("自由行");
        } else if (scenicSpot.getTravelMode() == 3) {
            holder.tvTravelMode.setText("跟团游");
        } else if (scenicSpot.getTravelMode() == 4) {
            holder.tvTravelMode.setText("主题游");
        } else if (scenicSpot.getTravelMode() == 5) {
            holder.tvTravelMode.setText("周边游");
        } else if (scenicSpot.getTravelMode() == 6) {
            holder.tvTravelMode.setText("一日游");
        } else if (scenicSpot.getTravelMode() == 7) {
            holder.tvTravelMode.setText("定制游");
        }
        holder.tvContentTheme.setText(scenicSpot.getScenicSpotTheme());
        holder.tvExplainOne.setText("环球影城");
        holder.tvExplainTwo.setText("精选酒店");
        int price = (int) (scenicSpot.getScenicSpotPrice() + 0);
        holder.tvPrice.setText(price + "起");
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TourismDetailsActivity.class);
            intent.putExtra("scenicSpotId", scenicSpot.getScenicSpotId());
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tourism_pic)
        ImageView ivTourismPic;
        @BindView(R.id.tv_travel_mode)
        TextView tvTravelMode;
        @BindView(R.id.tv_content_theme)
        TextView tvContentTheme;
        @BindView(R.id.tv_explain_one)
        TextView tvExplainOne;
        @BindView(R.id.tv_explain_two)
        TextView tvExplainTwo;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
