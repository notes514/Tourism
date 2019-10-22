package com.example.tourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.entity.ScenicSpot;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder> {
    List<ScenicRegion> scenicRegions;

    OnItemClickListener mOnItemClickListener;

    public SearchListAdapter(List<ScenicRegion> scenicRegions) {
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
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_adapter,parent,false);
        return new SearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {
        ScenicRegion scenicRegion = scenicRegions.get(position);
        holder.searchHint.setText(scenicRegion.getRegionName());
        if (mOnItemClickListener!=null) {
            holder.searchHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    mOnItemClickListener.onItemClick(position);
                }
            });
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
}
