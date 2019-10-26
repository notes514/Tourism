package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.utils.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ExhibitsItemAdapter extends RecyclerView.Adapter<ExhibitsItemAdapter.ViewHolder> {

    private List<Exhibits> objects = new ArrayList<Exhibits>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ExhibitsItemAdapter(Context context, List<Exhibits> exhibits) {
        this.context = context;
        this.objects = exhibits;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.exhibits_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // holder.scenicSpotPic.setImageResource(objects.get(position).getScenicSpotPicUrl());
        holder.ranking.setText(position+1+"");
        holder.exhibitsName.setText(objects.get(position).getExhibitsName());
        //holder.exhibitsPraisePoints.setText(objects.get(position).getExhibitsPraisePoints()+"");
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.like.setImageResource(R.drawable.ic_favorite);
                AppUtils.getToast("点赞成功！");
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView exhibitsId;
        private TextView ranking;
        private TextView exhibitsName;
        private ImageView like;
        private TextView exhibitsPraisePoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibitsId = (CardView) itemView.findViewById(R.id.exhibits_id);
            this.ranking = (TextView) itemView.findViewById(R.id.ranking);
            this.exhibitsName = (TextView) itemView.findViewById(R.id.exhibits_name);
            this.like = (ImageView) itemView.findViewById(R.id.like);
            this.exhibitsPraisePoints = (TextView) itemView.findViewById(R.id.exhibits_praise_points);
        }
    }
}
