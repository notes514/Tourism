package com.example.tourism.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.entity.DateBean;
import com.example.tourism.entity.MonthBean;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    private List<MonthBean> monthList;
    private Context context;
    private LayoutInflater inflater;

    public MonthAdapter(Context context, List<MonthBean> monthList) {
        this.context = context;
        this.monthList = monthList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMonthList(List<MonthBean> monthList) {
        this.monthList = monthList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_calender_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvCalTitle.setText(monthList.get(position).getTitle());
        GridLayoutManager manager = new GridLayoutManager(context, 7) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setAutoMeasureEnabled(true);
        DateAdapter adapter = new DateAdapter(context);
        //设置数据
        adapter.setDateList(monthList.get(position).getList());
        adapter.setClickListener((parentPos, pos, object) -> {
            if (childClickListener != null) {
                DateBean dateBean = (DateBean) object;
                String dateStr = monthList.get(parentPos).getTitle() + dateBean.getDate() + "日";
                childClickListener.onMonthClick(parentPos, pos, dateStr);
            }
        });
        holder.rvCal.setLayoutManager(manager);
        holder.rvCal.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return monthList == null ? 0 : monthList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCalTitle;
        private RecyclerView rvCal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCalTitle = (TextView) itemView.findViewById(R.id.tv_cal_title);
            rvCal = (RecyclerView) itemView.findViewById(R.id.rv_cal);
        }
    }

    private static OnMonthChildClickListener childClickListener;

    public interface OnMonthChildClickListener {
        void onMonthClick(int parentPos, int pos, Object object);
    }

    public void setChildClickListener(OnMonthChildClickListener childClickListener) {
        MonthAdapter.childClickListener = childClickListener;
    }


}
