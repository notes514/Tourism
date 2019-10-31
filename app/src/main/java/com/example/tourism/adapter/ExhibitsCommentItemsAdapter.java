package com.example.tourism.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ExhibitsComment;
import com.example.tourism.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.widget.ListAdapter;
import android.widget.ListView;
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
            viewHolder.userPic = (CircleImageView) convertView.findViewById(R.id.user_pic);
            viewHolder.commentator = (TextView) convertView.findViewById(R.id.commentator);
            viewHolder.commentcontent = (TextView) convertView.findViewById(R.id.exhibits_comment_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.commentator.setText(objects.get(position).getUserId());
//        ImageLoader.getInstance().displayImage(RequestURL.ip_images+objects.get(position).getUserId(),
//                viewHolder.userPic, InitApp.getOptions());
        viewHolder.commentcontent.setText(objects.get(position).getExhibitsCommentContent());
        //viewHolder.commentPraisePoints.setText(objects.get(position).getCommentPraisePoints()+"");
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    //解决NestedScrollView嵌套Listview显示不全问题解决
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter ecitemadapter = listView.getAdapter();
        if (ecitemadapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = ecitemadapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = ecitemadapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (ecitemadapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    protected class ViewHolder {
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
