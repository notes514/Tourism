package com.example.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.entity.City;
import com.example.tourism.ui.activity.LocationActivity;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CityItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<City> mBeans;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_COMPANY = 1;
    private String mLocation;



    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener){
        Log.d(TAG,"setmItemOnClickListener...");
        this.mItemOnClickListener = listener;
    }

    public interface ItemOnClickListener{
        /**
         * 传递点击的view
         * @param view
         */
        public void itemOnClickListener(View view);
    }

    public CityItemAdapter(Context context,String location){
        mContext = context;
    }

    public CityItemAdapter(Context context, ArrayList<City> beans,String location){
        mContext = context;
        mBeans = beans;
        mLocation = location;
    }

    //当列表数据发生变化时,用此方法来更新列表
    public void updateListView(ArrayList<City> beans){
        this.mBeans = beans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return TYPE_TITLE;
        } else {
            return TYPE_COMPANY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        CityViewHolder cHolder = null;
        ViewHolder mHolder = null;
        String fisrtSpell;
        switch (getItemViewType(position)){
                case TYPE_TITLE:
                    if (convertView == null) {
                        cHolder = new CityViewHolder();
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.location_item, parent, false);
                        cHolder.locationCityText = convertView.findViewById(R.id.location_city_text);
                        cHolder.popularCityList = convertView.findViewById(R.id.popular_city_list);
                        cHolder.txtTag = (TextView) convertView.findViewById(R.id.txt_tag);
                        cHolder.imgLine = (ImageView) convertView.findViewById(R.id.img_line);
                        convertView.setTag(cHolder);
                    }else {
                        cHolder = (CityViewHolder) convertView.getTag();
                    }

                    cHolder.locationCityText.setText(mLocation);
                    cHolder.locationCityText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent();
                            intent.putExtra("location",mLocation);
                            LocationActivity.getInstance().setResult(0,intent);
                            LocationActivity.getInstance().finish();
                        }
                    });
                    Log.d(TAG, "getView: ");
                    cHolder.popularCityList.setAdapter(new PopularItemAdapter(mContext, mBeans));
                    cHolder.popularCityList.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    cHolder.txtTag.setVisibility(View.VISIBLE);
                    cHolder.txtTag.setText("#");
                    cHolder.imgLine.setVisibility(View.VISIBLE);
                    break;
                case TYPE_COMPANY:
                    if (convertView == null) {
                    mHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.city_item, parent, false);
                    mHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
                    mHolder.txtTag = (TextView) convertView.findViewById(R.id.txt_tag);
                    mHolder.imgLine = (ImageView) convertView.findViewById(R.id.img_line);
                    convertView.setTag(mHolder);
                    }else {
                        mHolder = (ViewHolder) convertView.getTag();
                    }
                    convertView.setId(position);
                    mHolder.txtName.setText(mBeans.get(position).cityName);
                    fisrtSpell = mBeans.get(position).fisrtSpell.toUpperCase();
                    if (position == 1) {
                        mHolder.txtTag.setVisibility(View.VISIBLE);
                        mHolder.txtTag.setText(fisrtSpell);
                        mHolder.imgLine.setVisibility(View.VISIBLE);
                    } else {
                        if (position!=0) {
                            String lastFisrtSpell = mBeans.get(position - 1).fisrtSpell.toUpperCase();
                            if (fisrtSpell.equals(lastFisrtSpell)) {
                                mHolder.txtTag.setVisibility(View.GONE);
                                mHolder.imgLine.setVisibility(View.GONE);
                            } else {
                                mHolder.txtTag.setVisibility(View.VISIBLE);
                                mHolder.txtTag.setText(fisrtSpell);
                                mHolder.imgLine.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    break;
            }







        return convertView;
    }

    private class ViewHolder {
        private TextView txtName,txtTag;
        private ImageView imgLine;
    }

    private class CityViewHolder{
        private TextView locationCityText;
        private TextView txtTag;
        private GridView popularCityList;
        private ImageView imgLine;
    }
}
