package com.example.tourism.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
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
import com.example.tourism.entity.ExhibitsPic;
import com.example.tourism.entity.FabulousDetails;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.NearbyActivity;
import com.example.tourism.ui.activity.ShowExhibitsDetialActivity;
import com.example.tourism.ui.fragment.LeaderboardDetailFragment;
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

    public void orderByCommentCount(List<Exhibits> exhibits){
        Collections.sort(exhibits, new Comparator<Exhibits>() {
            @Override
            public int compare(Exhibits exhibits, Exhibits t1) {
                return t1.getExhibitsCommentList().size() - exhibits.getExhibitsCommentList().size();
            }
        });
        notifyDataSetChanged();
    }

    public void orderByLikeCount(List<Exhibits> exhibits){
        Collections.sort(exhibits, new Comparator<Exhibits>() {
            @Override
            public int compare(Exhibits exhibits, Exhibits t1) {
                return t1.getLikeCount() - exhibits.getLikeCount();
            }
        });
        notifyDataSetChanged();
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
        Exhibits exhibits = objects.get(position);
        holder.exhibitsName.setText(exhibits.getExhibitsName());
        holder.exhibitionAreaName.setText(AppUtils.getStringArray(R.array.exhibition_area)[exhibits.getExhibitionAreaId()-1]);
        holder.exhibitsAuthor.setText(AppUtils.getString(R.string.exhibits_author)+exhibits.getExhibitsAuthor());
        holder.teamMembers.setText(AppUtils.getString(R.string.team_members)+exhibits.getTeamMembers());
        //holder.exhibitsCommentCount.setText(exhibits.getExhibitsCommentList().size()+"");
        holder.likeCount.setText(exhibits.getLikeCount()+"");
        ImageLoader.getInstance().displayImage(RequestURL.ip_images+exhibits.getExhibitsPicList().get(0).getExhibitsPicUrl(),
                holder.exhibitsPic, InitApp.getOptions());
        holder.exhibitsId.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowExhibitsDetialActivity.class);
            intent.putExtra("exhibitsId",objects.get(position).getExhibitsId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView exhibitsId;
        private ImageView exhibitsPic;
        private TextView exhibitsName;
        private TextView exhibitionAreaName;
        private TextView exhibitsAuthor;
        //private TextView exhibitsCommentCount;
        private TextView likeCount;
        private TextView teamMembers;

        public ViewHolder(View view) {
            super(view);
            exhibitsId = (CardView) view.findViewById(R.id.exhibits_id);
            exhibitsPic = (ImageView) view.findViewById(R.id.exhibits_pic);
            exhibitsName = (TextView) view.findViewById(R.id.exhibits_name);
            exhibitionAreaName = (TextView) view.findViewById(R.id.exhibition_area_name);
            exhibitsAuthor = (TextView) view.findViewById(R.id.exhibits_author);
            //exhibitsCommentCount = (TextView) view.findViewById(R.id.exhibits_comment_count);
            likeCount = (TextView) view.findViewById(R.id.likeCount);
            teamMembers = (TextView) view.findViewById(R.id.team_members);
        }
    }
}
