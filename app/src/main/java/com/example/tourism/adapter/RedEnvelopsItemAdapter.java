package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.entity.RedEnvelops;

public class RedEnvelopsItemAdapter extends BaseAdapter {

    private List<RedEnvelops> objects = new ArrayList<RedEnvelops>();

    private Context context;
    private LayoutInflater layoutInflater;

    public RedEnvelopsItemAdapter(Context context,List<RedEnvelops> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public RedEnvelops getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.red_envelops_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((RedEnvelops)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(RedEnvelops object, ViewHolder holder) {
        holder.redName.setText(object.getRedName());
        holder.redDetail.setText(object.getRedDetail());
        //TODO implement
    }

    protected class ViewHolder {
        private TextView redName;
    private TextView redDetail;

        public ViewHolder(View view) {
            redName = (TextView) view.findViewById(R.id.redName);
            redDetail = (TextView) view.findViewById(R.id.redDetail);
        }
    }
}
