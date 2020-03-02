package com.example.tourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.ScenicRegion;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter {
    List<ScenicRegion> scenicRegions;

    OnItemClickListener mOnItemClickListener;
    int type;

    public SearchListAdapter(int type,List<ScenicRegion> scenicRegions) {
        this.type = type;
        this.scenicRegions = scenicRegions;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (type==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_adapter,parent,false);
            return new SearchListViewHolder(view);
        }else if (type==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item,parent,false);
            return new HistoryListViewHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScenicRegion scenicRegion = scenicRegions.get(position);
        if (holder instanceof SearchListViewHolder) {
            ((SearchListViewHolder) holder).searchHint.setText(scenicRegion.getRegionName());
            if (mOnItemClickListener != null) {
                ((SearchListViewHolder) holder).searchHint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //在TextView的地方进行监听点击事件，并且实现接口
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }
        }else if (holder instanceof HistoryListViewHolder){
            ((HistoryListViewHolder) holder).historyName.setText(scenicRegion.getRegionName());
            if (mOnItemClickListener != null) {
                ((HistoryListViewHolder) holder).historyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //在TextView的地方进行监听点击事件，并且实现接口
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return scenicRegions.size();
    }

    static class SearchListViewHolder extends RecyclerView.ViewHolder{
        TextView searchHint;

        public SearchListViewHolder(@NonNull View itemView) {
            super(itemView);
            searchHint = itemView.findViewById(R.id.search_hint);
        }
    }

    static class HistoryListViewHolder extends RecyclerView.ViewHolder {
        Button historyName;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            historyName = itemView.findViewById(R.id.history_name);
        }
    }
}
