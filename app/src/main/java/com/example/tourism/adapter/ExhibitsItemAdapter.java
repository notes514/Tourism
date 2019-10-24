package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.entity.Exhibits;

public class ExhibitsItemAdapter extends BaseAdapter {

    private List<Exhibits> objects = new ArrayList<Exhibits>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ExhibitsItemAdapter(Context context, List<Exhibits> exhibits) {
        this.context = context;
        this.objects = exhibits;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Exhibits getItem(int position) {
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
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.exhibits_item, null);
            viewHolder.exhibitsId = (TextView) convertView.findViewById(R.id.exhibits_id);
            viewHolder.exhibitsName = (TextView) convertView.findViewById(R.id.exhibits_name);
            viewHolder.exhibitsPraisePoints = (TextView) convertView.findViewById(R.id.exhibits_praise_points);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.exhibitsId.setText((position+1)+"");
        viewHolder.exhibitsName.setText(objects.get(position).getExhibitsName());
        viewHolder.exhibitsPraisePoints.setText(objects.get(position).getExhibitsPraisePoints()+"");
        return convertView;
    }

    protected class ViewHolder {
        private TextView exhibitsId;
        private TextView exhibitsName;
        private TextView exhibitsPraisePoints;
    }
}
