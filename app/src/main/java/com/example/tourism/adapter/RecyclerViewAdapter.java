package com.example.tourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.TravelsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<String> stringList; //攻略用户日期详情数据集
    private List<String> dDateList; //景区详情日期数据集
    private Context context;
    private int type;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
    }

    //设置攻略详情数据
    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    //设置景区详情日期数据集
    public void setdDateList(List<String> dDateList) {
        this.dDateList = dDateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == 0) {
            View view = inflater.inflate(R.layout.strategy_details_item_layout, parent, false);
            view.setOnClickListener(this::onClick);
            SViewHolder holder = new SViewHolder(view);
            return holder;
        } else if (type == 1) {
            View view = inflater.inflate(R.layout.tourism_details_date_item_layout, parent, false);
            view.setOnClickListener(this::onClick);
            DViewHolder holder = new DViewHolder(view);
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
            ((SViewHolder) holder).tvData.setText(str);
            if (position == 0) {
                ((SViewHolder) holder).ivPic.setImageResource(R.mipmap.icon_date_black);
                ((SViewHolder) holder).tvName.setText("出发日期");
            } else if (position == 1) {
                ((SViewHolder) holder).ivPic.setImageResource(R.mipmap.icon_time_black);
                //显示数据
                ((SViewHolder) holder).tvData.setText(str + "天");
                ((SViewHolder) holder).tvName.setText("出行天数");
            } else if (position == 2) {
                ((SViewHolder) holder).ivPic.setImageResource(R.mipmap.icon_wallet_black);
                //显示数据
                ((SViewHolder) holder).tvData.setText(str + "元");
                ((SViewHolder) holder).tvName.setText("人均");
            } else if (position == 3) {
                ((SViewHolder) holder).ivPic.setImageResource(R.mipmap.icon_character_black);
                ((SViewHolder) holder).tvName.setText("人物");
            } else if (position == 4) {
                ((SViewHolder) holder).ivPic.setImageResource(R.mipmap.icon_hot_ari_black);
                ((SViewHolder) holder).tvName.setText("玩法");
            } else {
                ((SViewHolder) holder).ivPic.setVisibility(View.INVISIBLE);
                ((SViewHolder) holder).tvName.setText("");
            }
        }
        if (holder instanceof DViewHolder) {
            String dData = dDateList.get(position);
            //设置Tag以便响应适配器监听点击获取相应数据
            holder.itemView.setTag(dData);
            ((DViewHolder) holder).tvDateWeek.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0) {
            return stringList == null ? 0 : stringList.size();
        }
        if (type == 1) {
            return dDateList == null ? 0 : dDateList.size();
        }
        return 0;
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

    class DViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date_week)
        TextView tvDateWeek;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_details_date)
        LinearLayout llDetailsDate;

        public DViewHolder(@NonNull View itemView) {
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
