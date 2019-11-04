package com.example.tourism.ui.fragment;

import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.ExhibitsItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardDetailFragment extends BaseFragment implements DefineView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private int exhibitionAreaId;
    private List<Exhibits> exhibits;
    private ExhibitsItemAdapter adapter;

    public LeaderboardDetailFragment(int exhibitionAreaId) {
        this.exhibitionAreaId = exhibitionAreaId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initListener();
        bindData();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        queryByExhibitionArea();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

    private void queryByExhibitionArea(){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("exhibitionAreaId",exhibitionAreaId+"");
        Call<ResponseBody> exhibitsCall = api.getASync("queryByExhibitionArea",hashMap);
        exhibitsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
                    Log.d("@@@",data);
                    JSONObject jsonObject = new JSONObject(data);
                    exhibits = RetrofitManger.getInstance().getGson().fromJson(
                            jsonObject.getString("ONE_DETAIL"),
                           new TypeToken<List<Exhibits>>(){}.getType());
                    Log.d("@@@", exhibits.size()+"");
                    Collections.sort(exhibits, new Comparator<Exhibits>() {
                        @Override
                        public int compare(Exhibits exhibits, Exhibits t1) {
                            return t1.getLikeCount()-exhibits.getLikeCount();
                        }
                    });
                    initRecyclerView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("@@@","请求失败！");
                Log.d("@@@",t.getMessage());
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new ExhibitsItemAdapter(getContext(),exhibits);
        recyclerView.setAdapter(adapter);
    }
}
