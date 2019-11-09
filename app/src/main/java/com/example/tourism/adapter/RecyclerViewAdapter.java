package com.example.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.MonthDayBean;
import com.example.tourism.entity.Order;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.OrderCancelLayoutActivity;
import com.example.tourism.ui.activity.TourismDetailsActivity;
import com.example.tourism.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    //攻略用户日期详情数据集
    private List<String> stringList;
    //景区详情日期数据集
    private List<MonthDayBean> monthDayBeanList;
    //订单信息数据集
    private List<Order> orderList;
    //行程信息数据集
    private List<ScenicSpot> scenicSpotList;
    //行程信息数据集
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
    public void setMonthDayBeanList(List<MonthDayBean> monthDayBeanList) {
        this.monthDayBeanList = monthDayBeanList;
    }

    //设置订单信息数据集
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    //设置行程信息数据集
    public void setScenicSpotList(List<ScenicSpot> scenicSpotList) {
        this.scenicSpotList = scenicSpotList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == 0) {
            View view = inflater.inflate(R.layout.strategy_details_item_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new SViewHolder(view);
        } else if (type == 1) {
            View view = inflater.inflate(R.layout.tourism_details_date_item_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new DViewHolder(view);
        } else if (type == 2) {
            View view = inflater.inflate(R.layout.item_all_order_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new AllOrderViewHolder(view);
        } else if (type == 3) {
            View view = inflater.inflate(R.layout.item_trip_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new TripViewHolder(view);
        } else if (type == 4) {
            View view = inflater.inflate(R.layout.item_seach_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new SeachViewHolder(view);
        } else if (type == 5) {
            View view = inflater.inflate(R.layout.item_recommend_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new RecommendViewHolder(view);
        } else if (type == 6) {
            View view = inflater.inflate(R.layout.item_seach_details_layout, parent, false);
            view.setOnClickListener(this::onClick);
            return new SeachDetailsViewHolder(view);
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
            MonthDayBean monthDayBean = monthDayBeanList.get(position);
            //设置Tag以便响应适配器监听点击获取相应数据
            holder.itemView.setTag(monthDayBean);
            ((DViewHolder) holder).tvDateWeek.setText(monthDayBean.getMonth());
            ((DViewHolder) holder).tvPrice.setText("¥" + monthDayBean.getPrice());
            holder.itemView.setClickable(false);
            if (position == 0) {
                ((DViewHolder) holder).llDetailsDate.setBackgroundResource(R.drawable.state_orange_selected);
                ((DViewHolder) holder).tvDateWeek.setTextColor(context.getResources().getColor(R.color.color_white));
                ((DViewHolder) holder).tvPrice.setTextColor(context.getResources().getColor(R.color.color_white));
            }
            ((DViewHolder) holder).llDetailsDate.setOnClickListener(v -> {
                ((DViewHolder) holder).llDetailsDate.setBackgroundResource(R.drawable.state_orange_selected);
                ((DViewHolder) holder).tvDateWeek.setTextColor(context.getResources().getColor(R.color.color_white));
                ((DViewHolder) holder).tvPrice.setTextColor(context.getResources().getColor(R.color.color_white));
            });

        }
        if (holder instanceof AllOrderViewHolder) {
            Order order = orderList.get(position);
            holder.itemView.setTag(order);
            ((AllOrderViewHolder) holder).tvOrderContent.setText(order.getOrderContent());
            if (order.getOrderState() == 0) {
                ((AllOrderViewHolder) holder).tvOrderState.setText("订单取消");
            } else if (order.getOrderState() == 1) {
                ((AllOrderViewHolder) holder).tvOrderState.setText("待付款");
            } else if (order.getOrderState() == 2) {
                ((AllOrderViewHolder) holder).tvOrderState.setText("待消费");
            } else if (order.getOrderState() == 3) {
                ((AllOrderViewHolder) holder).tvOrderState.setText("待评价");
            } else if (order.getOrderState() == 4) {
                ((AllOrderViewHolder) holder).tvOrderState.setText("退款中");
            }
            ((AllOrderViewHolder) holder).tvDate.setText("出发日期 " + order.getDepartDate());
            ((AllOrderViewHolder) holder).tvTripDay.setText("行程天数 " + order.getDepartDays());
            ((AllOrderViewHolder) holder).tvTripInformtion.setText("出行信息 " + order.getTirpInformation());
            ((AllOrderViewHolder) holder).tvPrice.setText("¥" + order.getOrderPrice());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, OrderCancelLayoutActivity.class);
                intent.putExtra("orderId", order.getOrderId());
                context.startActivity(intent);
            });
        }
        if (holder instanceof TripViewHolder) {
            ScenicSpot scenicSpot = scenicSpotList.get(position);
            holder.itemView.setTag(scenicSpot);
            if (position == 0) ((TripViewHolder) holder).viewTop.setVisibility(View.VISIBLE);
            else ((TripViewHolder) holder).viewTop.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + scenicSpot.getScenicSpotPicUrl(),
                    ((TripViewHolder) holder).ivTourismPic, InitApp.getOptions());
            ((TripViewHolder) holder).tvTourismContent.setText(scenicSpot.getScenicSpotTheme());
            if (scenicSpot.getTravelMode() == 0) {
                ((TripViewHolder) holder).tvTripInformtion.setText("国内游");
            } else if (scenicSpot.getTravelMode() == 1) {
                ((TripViewHolder) holder).tvTripInformtion.setText("出境游");
            } else if (scenicSpot.getTravelMode() == 2) {
                ((TripViewHolder) holder).tvTripInformtion.setText("自由行");
            } else if (scenicSpot.getTravelMode() == 3) {
                ((TripViewHolder) holder).tvTripInformtion.setText("跟团游");
            } else if (scenicSpot.getTravelMode() == 4) {
                ((TripViewHolder) holder).tvTripInformtion.setText("主题游");
            } else if (scenicSpot.getTravelMode() == 5) {
                ((TripViewHolder) holder).tvTripInformtion.setText("周边游");
            } else if (scenicSpot.getTravelMode() == 6) {
                ((TripViewHolder) holder).tvTripInformtion.setText("一日游");
            } else if (scenicSpot.getTravelMode() == 7) {
                ((TripViewHolder) holder).tvTripInformtion.setText("定制游");
            }
            ((TripViewHolder) holder).tvPrice.setText(scenicSpot.getScenicSpotPrice() + "");
            //去预定
            ((TripViewHolder) holder).btnReserve.setOnClickListener(v -> {
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("scenicSpotId", scenicSpot.getScenicSpotId());
                context.startActivity(intent);
            });
        }
        if (holder instanceof SeachViewHolder) {
            ScenicSpot scenicSpot = scenicSpotList.get(position);
            holder.itemView.setTag(scenicSpot);
            ((SeachViewHolder) holder).ivSeach.setImageResource(R.mipmap.icon_leaf);
            ((SeachViewHolder) holder).tvContent.setText(scenicSpot.getScenicSpotTheme());
            ((SeachViewHolder) holder).tvPrice.setText(scenicSpot.getScenicSpotPrice() + "");
        }
        if (holder instanceof RecommendViewHolder) {
            ScenicSpot scenicSpot = scenicSpotList.get(position);
            holder.itemView.setTag(scenicSpot);
            //显示数据
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + scenicSpot.getScenicSpotPicUrl(),
                    ((RecommendViewHolder) holder).ivTourismPic, InitApp.getOptions());
            if (scenicSpot.getTravelMode() == 0) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("国内游");
            } else if (scenicSpot.getTravelMode() == 1) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("出境游");
            } else if (scenicSpot.getTravelMode() == 2) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("自由行");
            } else if (scenicSpot.getTravelMode() == 3) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("跟团游");
            } else if (scenicSpot.getTravelMode() == 4) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("主题游");
            } else if (scenicSpot.getTravelMode() == 5) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("周边游");
            } else if (scenicSpot.getTravelMode() == 6) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("一日游");
            } else if (scenicSpot.getTravelMode() == 7) {
                ((RecommendViewHolder) holder).tvTravelMode.setText("定制游");
            }
            ((RecommendViewHolder) holder).tvContentTheme.setText(scenicSpot.getScenicSpotTheme());
            ((RecommendViewHolder) holder).tvExplainOne.setText("环球影城");
            ((RecommendViewHolder) holder).tvExplainTwo.setText("精选酒店");
            int price = (int) (scenicSpot.getScenicSpotPrice() + 0);
            ((RecommendViewHolder) holder).tvPrice.setText(price + "起");
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("scenicSpotId", scenicSpot.getScenicSpotId());
                context.startActivity(intent);
            });
        }
        if (holder instanceof SeachDetailsViewHolder) {
            ScenicSpot scenicSpot = scenicSpotList.get(position);
            holder.itemView.setTag(scenicSpot);
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + scenicSpot.getScenicSpotPicUrl(),
                    ((SeachDetailsViewHolder) holder).ivTourismPic, InitApp.getOptions());
            if (scenicSpot.getTravelMode() == 0) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("国内游");
            } else if (scenicSpot.getTravelMode() == 1) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("出境游");
            } else if (scenicSpot.getTravelMode() == 2) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("自由行");
            } else if (scenicSpot.getTravelMode() == 3) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("跟团游");
            } else if (scenicSpot.getTravelMode() == 4) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("主题游");
            } else if (scenicSpot.getTravelMode() == 5) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("周边游");
            } else if (scenicSpot.getTravelMode() == 6) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("一日游");
            } else if (scenicSpot.getTravelMode() == 7) {
                ((SeachDetailsViewHolder) holder).tvTravelMode.setText("定制游");
            }
            ((SeachDetailsViewHolder) holder).tvEndLand.setText(scenicSpot.getStartLand() + "出发");
            ((SeachDetailsViewHolder) holder).tvTourismContent.setText(scenicSpot.getScenicSpotTheme());
            ((SeachDetailsViewHolder) holder).tvScore.setText(scenicSpot.getScenicSpotScore() + "分");
            ((SeachDetailsViewHolder) holder).tvNumber.setText(scenicSpot.getScenicSpotSold() + "人已出行");
            ((SeachDetailsViewHolder) holder).tvTourCity.setText(scenicSpot.getTourCity());
            ((SeachDetailsViewHolder) holder).tvExplain.setText(scenicSpot.getScenicSpotExplain());
            int price = (int) (scenicSpot.getScenicSpotPrice() + 0);
            ((SeachDetailsViewHolder) holder).tvPrice.setText(price + "");
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("scenicSpotId", scenicSpot.getScenicSpotId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0) {
            return stringList == null ? 0 : stringList.size();
        }
        if (type == 1) {
            return monthDayBeanList == null ? 0 : monthDayBeanList.size();
        }
        if (type == 2) {
            return orderList == null ? 0 : orderList.size();
        }
        if (type == 3 || type == 4 || type == 5 || type == 6) {
            return scenicSpotList == null ? 0 : scenicSpotList.size();
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

    class AllOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_content)
        TextView tvOrderContent;
        @BindView(R.id.tv_order_state)
        TextView tvOrderState;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_trip_day)
        TextView tvTripDay;
        @BindView(R.id.tv_trip_informtion)
        TextView tvTripInformtion;
        @BindView(R.id.iv_forward)
        ImageView ivForward;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public AllOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TripViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tourism_pic)
        ImageView ivTourismPic;
        @BindView(R.id.tv_tourism_content)
        TextView tvTourismContent;
        @BindView(R.id.tv_trip_informtion)
        TextView tvTripInformtion;
        @BindView(R.id.tv_symbol)
        TextView tvSymbol;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_reserve)
        Button btnReserve;
        @BindView(R.id.view_top)
        View viewTop;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SeachViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_seach)
        CircleImageView ivSeach;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public SeachViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tourism_pic)
        ImageView ivTourismPic;
        @BindView(R.id.tv_travel_mode)
        TextView tvTravelMode;
        @BindView(R.id.tv_content_theme)
        TextView tvContentTheme;
        @BindView(R.id.tv_explain_one)
        TextView tvExplainOne;
        @BindView(R.id.tv_explain_two)
        TextView tvExplainTwo;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SeachDetailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tourism_pic)
        ImageView ivTourismPic;
        @BindView(R.id.tv_travel_mode)
        TextView tvTravelMode;
        @BindView(R.id.tv_end_land)
        TextView tvEndLand;
        @BindView(R.id.tv_tourism_content)
        TextView tvTourismContent;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_explain)
        TextView tvExplain;
        @BindView(R.id.tv_tour_city)
        TextView tvTourCity;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public SeachDetailsViewHolder(@NonNull View itemView) {
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
