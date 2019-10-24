package com.example.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.ui.activity.ActivitySpotActivity;
import com.example.tourism.entity.RegionType;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class BrowseCountry2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ScenicRegion> cc;
    private ScenicRegion country;
    private Context context;
    private int COUNTRY_TYPE1=1;
    private int COUNTRY_TYPE2=2;
    private int position;

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public BrowseCountry2Adapter(List<RegionType> regionTypes, List<ScenicRegion> countries, int position, String name) {
        //this.countries = countries;

        cc = new ArrayList<>();
        for (int i=0;i<regionTypes.size();i++) {
            switch (regionTypes.get(i).getRegionTypeId()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    if (position<=regionTypes.size()){
                        for (int j = 0; j < countries.size(); j++) {
                            country = countries.get(j);
                            if (country.getRegionDescribe().contains(name)&&!cc.contains(country)) {
                                cc.add(country);
                            }
                            Log.d(TAG, "BrowseCountry2Adapter: "+country.getRegionDescribe());
                        }
                    }
                    break;
            }
        }
    }

    class Country2ViewHolder extends RecyclerView.ViewHolder{
        TextView countryName;

        public Country2ViewHolder(View itemView){
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
        }
    }

    class Country3ViewHolder extends RecyclerView.ViewHolder{
        TextView countryName;
        ImageView countryPic;

        public Country3ViewHolder(View itemView){
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPic = itemView.findViewById(R.id.country_pic);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_country3_adapter,parent,false);
            return new Country3ViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_country2_adapter,parent,false);
            return new Country2ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScenicRegion country = cc.get(position);
        if (holder instanceof Country3ViewHolder){
            ImageLoader.getInstance().displayImage(RequestURL.ip_images+country.getRegionPicUrl(),((Country3ViewHolder) holder).countryPic, InitApp.getOptions());
            ((Country3ViewHolder) holder).countryName.setText(country.getRegionName());
            ((Country3ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                    Intent intent = new Intent(view.getContext(), ActivitySpotActivity.class);
                    intent.putExtra("country", country);
                    view.getContext().startActivity(intent);
                }
            });
        }else if (holder instanceof Country2ViewHolder){
            ((Country2ViewHolder) holder).countryName.setText(country.getRegionName());
            ((Country2ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                    Intent intent = new Intent(view.getContext(), ActivitySpotActivity.class);
                    intent.putExtra("country", country);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cc.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position<6){
            return COUNTRY_TYPE1;
        }else {
            return COUNTRY_TYPE2;
        }
    }



}
