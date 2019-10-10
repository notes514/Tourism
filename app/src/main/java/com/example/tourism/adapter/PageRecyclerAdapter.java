package com.example.tourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.ui.fragment.Good;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageRecyclerAdapter extends RecyclerView.Adapter<PageRecyclerAdapter.ViewHolder> {
    private List<Good> goodList;
    private Context context;
    private LayoutInflater inflater;

    public PageRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.goods_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.headPortraitImage.setImageResource(goodList.get(position).getImagePic());
        holder.accountNameText.setText(goodList.get(position).getAccountName());
        holder.publicationTimeText.setText(goodList.get(position).getTime());
        holder.contentText.setText(goodList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_portrait_image)
        ImageView headPortraitImage;
        @BindView(R.id.account_name_text)
        TextView accountNameText;
        @BindView(R.id.publication_time_text)
        TextView publicationTimeText;
        @BindView(R.id.follow_text)
        TextView followText;
        @BindView(R.id.content_text)
        TextView contentText;
        @BindView(R.id.content_pic_gridview)
        GridView contentPicGridview;
        @BindView(R.id.collecion_image)
        ImageView collecionImage;
        @BindView(R.id.collection_number_text)
        TextView collectionNumberText;
        @BindView(R.id.forward_image)
        ImageView forwardImage;
        @BindView(R.id.forward_text)
        TextView forwardText;
        @BindView(R.id.goods_cardView)
        CardView goodsCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
