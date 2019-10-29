package com.example.tourism.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tourism.R;
import com.example.tourism.entity.ExhibitsComment;
import com.example.tourism.widget.CircleImageView;
import android.widget.TextView;
import android.widget.ImageView;

public class ExhibitsCommentItemsAdapter extends BaseAdapter {
    private List<ExhibitsComment> objects;
    private Context context;
    private LayoutInflater layoutInflater;

    public ExhibitsCommentItemsAdapter(Context context,List<ExhibitsComment> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ExhibitsComment getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.exhibits_comment_items, null);
            viewHolder = new ViewHolder();
            viewHolder.textview = (TextView) convertView.findViewById(R.id.textview);
            viewHolder.textview2 = (TextView) convertView.findViewById(R.id.textview2);
            viewHolder.commentator = (TextView) convertView.findViewById(R.id.commentator);
            viewHolder.commentcontent = (TextView) convertView.findViewById(R.id.exhibits_comment_content);
            viewHolder.browseTextView = (TextView) convertView.findViewById(R.id.browse_textView);
            viewHolder.commentPraisePoints = (TextView) convertView.findViewById(R.id.comment_praise_points);
            viewHolder.commentText = (TextView) convertView.findViewById(R.id.comment_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textview.setText("展品评价(22)");
        viewHolder.textview2.setText("查看全部 >");
        viewHolder.commentcontent.setText(objects.get(position).getExhibitsCommentContent());
        viewHolder.commentPraisePoints.setText(objects.get(position).getCommentPraisePoints()+"");
        return convertView;
    }

    protected class ViewHolder {
        private TextView textview;
        private TextView textview2;
        private CircleImageView userPic;
        private TextView commentator;
        private TextView commentcontent;
        private ImageView ivBrowse;
        private TextView browseTextView;
        private ImageView ivFabulous;
        private TextView commentPraisePoints;
        private ImageView ivComment;
        private TextView commentText;

    }
}
