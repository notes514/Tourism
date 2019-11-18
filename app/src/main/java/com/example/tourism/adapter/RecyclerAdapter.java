package com.example.tourism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.database.bean.TripBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    //出行人信息数据集
    private List<TripBean> tripBeanList;
    //上下文
    private Context context;
    //布局
    private LayoutInflater layoutInflater;
    //布局索引
    private int index;
    //接口实例
    private OnItemClickListener onItemClickListener;
    //存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    //记录选中的总数
    private int num = 0;

    public RecyclerAdapter(Context context, int index) {
        this.context = context;
        this.index = index;
        this.layoutInflater = LayoutInflater.from(context);
        initMap();
    }

    //初始化map集合，默认设置为false
    private void initMap() {
        if (tripBeanList == null) return;
        for (int i = 0; i < tripBeanList.size(); i++) {
            map.put(i,false);
        }
    }

    //设置信息数据
    public void setTripBeanList(List<TripBean> tripBeanList) {
        this.tripBeanList = tripBeanList;
    }

    //设置接口实例数据
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 点击item选中CheckBox
     * @param position
     */
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
            if (num == 0) return;
            num -= 1;
        } else {
            map.put(position, true);
            num += 1;
        }
        notifyItemChanged(position);
    }

    public void setCheckBox(int position, boolean flag) {
        if (flag) {
            map.put(position, flag);
            num += 1;
            num -= 1;
        } else {
            map.put(position, flag);
            if (num == 0) return;
            num -= 1;
        }
    }

    public int getNum() {
        return num;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (index == 0) {
            View view = layoutInflater.inflate(R.layout.traveler_item, parent, false);
            view.setOnClickListener(this::onClick);
            view.setOnLongClickListener(this::onLongClick);
            return new TravelerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TravelerViewHolder) {
            TripBean tripBean = tripBeanList.get(position);
            //设置TripBean实体类Tag
            holder.itemView.setTag(tripBean);
            //设置列表索引(position)Tag
            holder.itemView.setTag(R.id.tag_pos, position);
            //设置选中监听
            ((TravelerViewHolder) holder).checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                //用map集合保存
                map.put(position, isChecked);
            });
            // 设置CheckBox的状态
            if (map.get(position) == null) {
                map.put(position, false);
            }
            ((TravelerViewHolder) holder).checkBox.setChecked(map.get(position));
            ((TravelerViewHolder) holder).contacts.setText(tripBean.getTName());
            ((TravelerViewHolder) holder).idCard.setText(tripBean.getTIdentitycard());
        }
    }

    @Override
    public int getItemCount() {
        if (index == 0) {
            return tripBeanList == null ? 0 : tripBeanList.size();
        }
        return 0;
    }

    /**
     * 重写点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null && v.getTag() != null && v.getTag(R.id.tag_pos) != null) {
            onItemClickListener.onItemClickListener(v, v.getTag(), (Integer) v.getTag(R.id.tag_pos));
        }
    }

    /**
     * 重写长按事件
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        //不管显示隐藏，清空状态
        initMap();
        return tripBeanList != null && onItemClickListener.onItemLongClickListener(v, v.getTag(), (Integer) v.getTag(R.id.tag_pos));
    }

    /**
     * 返回集合给MainActivity
     * @return
     */
    public Map<Integer, Boolean> getMap() {
        return map;
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

    /**
     * 接口回调设置点击
     */
    public interface OnItemClickListener {

        /**
         * 点击事件
         * @param view
         * @param object
         */
        void onItemClickListener(View view, Object object, int itemPosition);

        /**
         * 长按事件
         * @param view
         * @param object
         * @return
         */
        boolean onItemLongClickListener(View view, Object object, int itemPosition);

    }

}
