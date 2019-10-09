package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.entity.SecondaryMenu;

public class SecondaryMenuItemAdapter extends BaseAdapter {

    private List<SecondaryMenu> objects = new ArrayList<SecondaryMenu>();

    private Context context;
    private LayoutInflater layoutInflater;

    public SecondaryMenuItemAdapter(Context context, List<SecondaryMenu> secondaryMenuList) {
        this.context = context;
        this.objects = secondaryMenuList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public SecondaryMenu getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.secondary_menu_item, null);
            viewHolder = new ViewHolder();
            viewHolder.menuPic = (ImageView) convertView.findViewById(R.id.menu_pic);
            viewHolder.menuName = (TextView) convertView.findViewById(R.id.menu_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.menuPic.setImageResource(objects.get(position).menu_pic);
        viewHolder.menuName.setText(objects.get(position).menu_name);
        return convertView;
    }

    protected class ViewHolder {
        private ImageView menuPic;
        private TextView menuName;
    }
}
