package com.example.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.entity.City;
import com.example.tourism.ui.activity.LocationActivity;

import java.util.ArrayList;

public class PopularItemAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<City> mBeans;

    public PopularItemAdapter(Context context, ArrayList<City> beans){
        mContext = context;
        mBeans = beans;
    }

    //当列表数据发生变化时,用此方法来更新列表
    public void updateListView(ArrayList<City> beans){
        this.mBeans = beans;
        notifyDataSetChanged();
    }

    // 获得当前列表数据
    public ArrayList<City> getList(){
        return mBeans;
    }

    @Override
    public int getCount() {
        return mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popular_item, parent, false);
            mHolder.popularName = (TextView) convertView.findViewById(R.id.popular_name);
            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setId(position);
        mHolder.popularName.setText(mBeans.get(position).cityName);
        mHolder.popularName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("location",mBeans.get(position).cityName);
                LocationActivity.getInstance().setResult(0,intent);
                LocationActivity.getInstance().finish();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView popularName;
    }
}
