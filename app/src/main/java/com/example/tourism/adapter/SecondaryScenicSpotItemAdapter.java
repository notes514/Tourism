package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.TourismDetailsActivity;
import com.example.tourism.utils.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SecondaryScenicSpotItemAdapter extends RecyclerView.Adapter<SecondaryScenicSpotItemAdapter.ViewHolder> {

    private List<ScenicSpot> objects = new ArrayList<ScenicSpot>();

    private Context context;
    private LayoutInflater layoutInflater;

    public SecondaryScenicSpotItemAdapter(Context context, List<ScenicSpot> scenicSpots) {
        this.context = context;
        this.objects = scenicSpots;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.secondary_scenic_spot_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.scenicSpotTheme.setText(objects.get(position).getScenicSpotTheme());
        holder.travelMode.setText(objects.get(position).getTourCity());
        holder.scenicSpotPrice.setText("¥:"+objects.get(position).getScenicSpotPrice());
        holder.scenicSpotState.setText(objects.get(position).getScenicSpotShop()+"");
        holder.scenicSpotId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("scenicSpotId", objects.get(position).getScenicSpotId());
                context.startActivity(intent);
            }
        });
        ImageLoader.getInstance().displayImage(RequestURL.ip_images+objects.get(position).getScenicSpotPicUrl(),
                holder.scenicSpotPic, InitApp.getOptions());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public CardView scenicSpotId;
        public ImageView scenicSpotPic;
        public TextView scenicSpotTheme;
        public TextView scenicSpotState;
        public TextView travelMode;
        public TextView scenicSpotPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.scenicSpotId = itemView.findViewById(R.id.scenic_spot_id);
            this.scenicSpotPic = itemView.findViewById(R.id.scenic_spot_pic);
            this.scenicSpotTheme = itemView.findViewById(R.id.scenic_spot_theme);
            this.travelMode = itemView.findViewById(R.id.travel_mode);
            this.scenicSpotState = itemView.findViewById(R.id.scenic_spot_state);
            this.scenicSpotPrice = itemView.findViewById(R.id.scenic_spot_price);
        }
    }
}
