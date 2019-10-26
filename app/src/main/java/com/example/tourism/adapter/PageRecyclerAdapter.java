package com.example.tourism.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.entity.TravelsBean;
import com.example.tourism.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageRecyclerAdapter extends RecyclerView.Adapter<PageRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private List<TravelsBean> travelsBeans;
    private Context context;
    private LayoutInflater inflater;
    private GoodAdapter goodAdapter;

    public PageRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    //设置数据
    public void setTravelsBeans(List<TravelsBean> travelsBeans) {
        this.travelsBeans = travelsBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.goods_layout, parent, false);
        view.setOnClickListener(this::onClick);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TravelsBean travels = travelsBeans.get(position);
        //把TravelsBean数据设置到View中
        holder.itemView.setTag(travels);
        //用户头像
        ImageLoader.getInstance().displayImage(travels.getUserPicUrl(), holder.headPortraitImage, InitApp.getOptions());
        //标题内容
        holder.tvTitleContent.setText(travels.getTitle());
        //用户姓名
        holder.accountNameText.setText(travels.getUserName());
        //标签(别称)
        holder.userTagText.setText(travels.getNickName());
        //时间
        holder.tvDate.setText(travels.getTime());
        //天数
        holder.tvDays.setText(" | " + travels.getDays());
        if (travels.getDays().length() < 1) {
            holder.tvDays.setVisibility(TextView.GONE);
        }
        //照片数量
        holder.tvPicNums.setText(" | " + travels.getPhotoNumber());
        if (travels.getPhotoNumber().length() < 1) {
            holder.tvPicNums.setVisibility(TextView.GONE);
        }
        //关系
        holder.tvRelation.setText(" | " + travels.getRelation());
        if (travels.getRelation().length() < 1) {
            holder.tvRelation.setVisibility(TextView.GONE);
        }
        //途径
        holder.cannelText.setText(travels.getChannel());
        if (travels.getChannel().length() < 1) {
            holder.cannelText.setVisibility(TextView.GONE);
        }
        //行程
        holder.tripText.setText("行程"+travels.getTrips());
        if (travels.getTrips().length() < 1) {
            holder.tripText.setVisibility(TextView.GONE);
        }
        //浏览
        holder.ivBrowse.setImageResource(R.drawable.icon_browse);
        holder.browseTextView.setText(travels.getBrowse());
        //点赞
        holder.ivFabulous.setImageResource(R.drawable.icon_fabulous);
        holder.fabulousText.setText(travels.getFoubles());
        //评论
        holder.ivComment.setImageResource(R.drawable.icon_comment_gray);
        holder.commentText.setText(travels.getComment());
        //显示图片
        goodAdapter = new GoodAdapter(context);
        goodAdapter.setPicList(travels.getPicUrl());
        holder.rvGood.setLayoutManager(new GridLayoutManager(context, 3));
        holder.rvGood.setAdapter(goodAdapter);
    }

    @Override
    public int getItemCount() {
        return travelsBeans == null ? 0 : travelsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_portrait_image)
        CircleImageView headPortraitImage;
        @BindView(R.id.account_name_text)
        TextView accountNameText;
        @BindView(R.id.user_tag_text)
        TextView userTagText;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_days)
        TextView tvDays;
        @BindView(R.id.tv_pic_nums)
        TextView tvPicNums;
        @BindView(R.id.tv_relation)
        TextView tvRelation;
        @BindView(R.id.tv_title_content)
        TextView tvTitleContent;
        @BindView(R.id.cannel_text)
        TextView cannelText;
        @BindView(R.id.trip_text)
        TextView tripText;
        @BindView(R.id.rv_good)
        RecyclerView rvGood;
//        @BindView(R.id.content_pic_gridview)
//        GridView contentPicGridview;
        @BindView(R.id.iv_browse)
        ImageView ivBrowse;
        @BindView(R.id.browse_textView)
        TextView browseTextView;
        @BindView(R.id.iv_fabulous)
        ImageView ivFabulous;
        @BindView(R.id.fabulous_text)
        TextView fabulousText;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.comment_text)
        TextView commentText;
        @BindView(R.id.goods_cardView)
        CardView goodsCardView;

        public ViewHolder(@NonNull View itemView) {
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

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Item 添加类OnItemClickListener 时间监听方法
     */
    public interface OnItemClickListener {
        /**
         * 当内部的Item发生点击的时候 调用Item点击回调方法
         * @param view    点击的View
         * @param object  回调的数据
         */
        void onItemClick(View view, Object object);
    }

}
