package com.example.tourism.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BrowseRegionAdapter extends RecyclerView.Adapter<BrowseRegionAdapter.ViewHolder> implements Serializable{
    private List<Region> regions;
    private List<Boolean> isClicks;
    OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView region;

        public ViewHolder(View itemView){
            super(itemView);
            region = (TextView) itemView.findViewById(R.id.region);

        }
    }

    public BrowseRegionAdapter(List<Region> regions){
        this.regions = regions;
        isClicks = new ArrayList<>();
        for(int i = 0;i<regions.size();i++){
            if(i==0) {
                isClicks.add(true);
            }else{
                isClicks.add(false);
            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_region_adapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        Region icon = regions.get(position);
        holder.region.setText(icon.getRegionName());
        holder.itemView.setTag(holder.region);

        if(isClicks.get(position)){
            holder.region.setTextColor(Color.parseColor("#00a0e9"));
            holder.region.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            holder.region.setTextColor(Color.parseColor("#000000"));
            holder.region.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        if (mOnItemClickListener != null) {
            holder.region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    mOnItemClickListener.onItemClick(position);
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                }
            });
        }



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

    }

    @Override
    public int getItemCount() {
        return regions.size();
    }



}
