package com.example.tourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.MonthDayBean;
import com.hz.android.easyadapter.EasyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionAdapter extends EasyAdapter<RecyclerView.ViewHolder> {
    //上下文
    private Context context;
    //布局加载
    private LayoutInflater layoutInflater;
    //子布局缩影
    private int index;
    //攻略用户日期详情数据集
    private List<MonthDayBean> monthDayBeanList;

    public SelectionAdapter(Context context, int index) {
        this.context = context;
        this.index = index;
        this.layoutInflater = LayoutInflater.from(context);
    }

    //设置日期数据集
    public void setMonthDayBeanList(List<MonthDayBean> monthDayBeanList) {
        this.monthDayBeanList = monthDayBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (index == 0) {
            View view = layoutInflater.inflate(R.layout.tourism_details_date_item_layout, parent, false);
            return new DateViewHolder(view);
        }
        return null;
    }

    @Override
    public void whenBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //出行日期
        if (holder instanceof DateViewHolder) {
            MonthDayBean monthDayBean = monthDayBeanList.get(position);
            //设置Tag
            holder.itemView.setTag(monthDayBean);
            ((DateViewHolder) holder).tvDateWeek.setText(monthDayBean.getMonth());
            ((DateViewHolder) holder).tvPrice.setText("¥" + monthDayBean.getPrice());
            if (position == getSingleSelectedPosition()) {
                ((DateViewHolder) holder).llDetailsDate.setBackgroundResource(R.drawable.state_orange_selected);
                ((DateViewHolder) holder).tvDateWeek.setTextColor(context.getResources().getColor(R.color.color_white));
                ((DateViewHolder) holder).tvPrice.setTextColor(context.getResources().getColor(R.color.color_white));
            } else {
                ((DateViewHolder) holder).llDetailsDate.setBackgroundResource(R.color.color_white);
                ((DateViewHolder) holder).tvDateWeek.setTextColor(context.getResources().getColor(R.color.color_dark_grey_two));
                ((DateViewHolder) holder).tvPrice.setTextColor(context.getResources().getColor(R.color.color_orange));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (index == 0) {
            return monthDayBeanList == null ? 0 : monthDayBeanList.size();
        }
        return 0;
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date_week)
        TextView tvDateWeek;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_details_date)
        LinearLayout llDetailsDate;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TravelerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.contacts)
        TextView contacts;
        @BindView(R.id.id_Card)
        TextView idCard;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;

        public TravelerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
