package com.example.tourism.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.DateBean;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private List<DateBean> dateList;
    private Context context;
    private LayoutInflater inflater;

    public DateAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDateList(List<DateBean> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_date_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateBean de = dateList.get(position);
        holder.itemView.setTag(R.id.tag_parent_pos, de.getParentPos());
        holder.itemView.setTag(R.id.tag_pos, position);
        holder.itemView.setTag(de);
        int date = de.getDate();
        int type = de.getType();

        if (type == 1) {//留白
            holder.mTvDate.setText("");
            holder.mTvDesc.setText("");
            holder.itemView.setClickable(false);
        } else if (type == 0) {//日常
            holder.mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            holder.mTvDate.setTextColor(date == 77 ? ContextCompat.getColor(context, R.color.color_blue_85) : ContextCompat.getColor(context, R.color.color_black_2c));
            holder.mTvDesc.setText(de.getDesc());

            int mod = position % 7;
            if (mod == 5 || mod == 6) {
                holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_red));
                holder.mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.color_red));
            }
        } else if (type == 3) {//日常选中
            holder.mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            holder.mTvDesc.setText(de.getDesc());
            holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            holder.mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            holder.mLlDate.setBackgroundResource(R.drawable.state_selected_blue_85);
        } else if (type == 4) {//今天之前的日期
            holder.itemView.setClickable(false);
            holder.mTvDate.setText(String.valueOf(de.getDate()));
            holder.mTvDesc.setText(de.getDesc());
            holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_black_cc));
            holder.mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.color_black_cc));
        }  else if (type == 8) {//单选
            holder.mTvDate.setText(date == 77 ? "今天" : String.valueOf(de.getDate()));
            holder.mTvDesc.setText(de.getDesc());
            holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            holder.mTvDesc.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            holder.mLlDate.setBackgroundResource(R.drawable.state_selected_blue_85);
        }

    }

    @Override
    public int getItemCount() {
        return dateList == null ? 0 : dateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLlDate;
        private TextView mTvDate;
        private TextView mTvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mLlDate = (LinearLayout) itemView.findViewById(R.id.ll_date);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                if (view != null && view.getTag(R.id.tag_parent_pos) != null && view.getTag(R.id.tag_pos) != null &&
                    view.getTag() != null) {
                    clickListener.onDateClick((Integer) view.getTag(R.id.tag_parent_pos), (Integer) view.getTag(R.id.tag_pos),
                            view.getTag());
                }
            }
        }
    }

    private static OnDateClickListener clickListener;

    public interface OnDateClickListener {
        void onDateClick(int parentPos, int pos, Object object);
    }

    public void setClickListener(OnDateClickListener clickListener) {
        DateAdapter.clickListener = clickListener;
    }

}
