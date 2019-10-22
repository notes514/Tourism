package com.example.tourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.RegionType;
import com.example.tourism.entity.ScenicRegion;

import java.util.List;

public class BrowseCountryAdapter extends RecyclerView.Adapter<BrowseCountryAdapter.CountryViewHolder> {


    private List<RegionType> mList;
    private List<ScenicRegion> countries;
    private Context mContext;
    private BrowseCountry2Adapter browseCountry2Adapter;



    public BrowseCountryAdapter(List<RegionType> mList, List<ScenicRegion> countries) {
        this.mList = mList;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_country_adapter,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        RegionType region = mList.get(position);
        holder.countryName.setText(region.getRegionTypeName());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext,6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position<6){
                    return 3;
                }else {
                    return 2;
                }
            }});
        holder.country.setLayoutManager(gridLayoutManager);
        browseCountry2Adapter = new BrowseCountry2Adapter(mList,countries,position,region.getRegionTypeName());
        holder.country.setAdapter(browseCountry2Adapter);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder{
        TextView countryName;
        RecyclerView country;

        public CountryViewHolder(View itemView){
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            country = itemView.findViewById(R.id.country);
        }
    }



}
