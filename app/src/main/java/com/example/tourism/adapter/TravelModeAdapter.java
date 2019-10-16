package com.example.tourism.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.TravelMode;

import java.util.ArrayList;
import java.util.List;

public class TravelModeAdapter extends RecyclerView.Adapter<TravelModeAdapter.travelModeViewHolder> {

    private List<TravelMode> travelModes;
    private List<Boolean> isClicks;
    OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public TravelModeAdapter(List<TravelMode> travelModes) {
        this.travelModes = travelModes;
        isClicks = new ArrayList<>();
        for(int i = 0;i<travelModes.size();i++){
            if(i==0) {
                isClicks.add(true);
            }else{
                isClicks.add(false);
            }
        }
    }


    @NonNull
    @Override
    public travelModeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_mode_adapter,parent,false);
        return new travelModeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull travelModeViewHolder holder, int position) {
        TravelMode travelMode = travelModes.get(position);
        holder.travelModeName.setText(travelMode.getTravelModeName());
        holder.itemView.setTag(holder.travelModeName);

        if(isClicks.get(position)){
            holder.travelModeName.setTextColor(Color.parseColor("#00a0e9"));
            holder.travelModeName.setBackgroundResource(R.drawable.text_selected);
        }else{
            holder.travelModeName.setTextColor(Color.parseColor("#000000"));
            holder.travelModeName.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        if (mOnItemClickListener!=null) {
            holder.travelModeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    mOnItemClickListener.onItemClick(position);
                    for (int i = 0; i < isClicks.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);
                    notifyDataSetChanged();
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return travelModes.size();
    }

    static class travelModeViewHolder extends RecyclerView.ViewHolder{
        TextView travelModeName;

        public travelModeViewHolder(@NonNull View itemView) {
            super(itemView);
            travelModeName = itemView.findViewById(R.id.travel_mode_name);
        }
    }
}
