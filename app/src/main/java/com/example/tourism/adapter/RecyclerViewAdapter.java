package com.example.tourism.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.StrategyDetailsBean;
import com.example.tourism.entity.TravelsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private List<TravelsBean> travelsBeans;
    private List<String> stringList;
    private Context context;
    private int type;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
    }

    //设置数据
    public void setTravelsBeans(List<TravelsBean> travelsBeans) {
        this.travelsBeans = travelsBeans;
    }

    //设置攻略详情数据
    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = inflater.inflate(R.layout.default_footer, parent, false);
            view.setOnClickListener(this::onClick);
            SViewHolder holder = new SViewHolder(view);
            return holder;
        }
        if (viewType == TYPE_TWO) {
            View view = inflater.inflate(R.layout.strategy_details_item_layout, parent, false);
            view.setOnClickListener(this::onClick);
            SViewHolder holder = new SViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SViewHolder) {
            String str = stringList.get(position);
            holder.itemView.setTag(str);
            //显示数据
            ((SViewHolder) holder).ivPic.setImageResource(R.drawable.icon_comment_black);
            ((SViewHolder) holder).tvData.setText(str);
        }
    }

    @Override
    public int getItemCount() {
        if (type == TYPE_ONE) {
            return travelsBeans == null ? 0 : travelsBeans.size();
        }
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class SViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_name)
        TextView tvName;

        public SViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 重写onClick
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Item 添加类OnItemClickListener 时间监听方法
     */
    public interface OnItemClickListener {
        /**
         * 当内部的Item发生点击的时候 调用Item点击回调方法
         *
         * @param view   点击的View
         * @param object 回调的数据
         */
        void onItemClick(View view, Object object);
    }

}
