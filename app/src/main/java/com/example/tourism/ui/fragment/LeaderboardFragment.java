package com.example.tourism.ui.fragment;

import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.ExhibitsItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends BaseFragment implements DefineView {

    @BindView(R.id.listView)
    ListView listView;
    private Unbinder unbinder;
    private static int exhibitionAreaId = 1;
    private List<Exhibits> exhibits;
    private ExhibitsItemAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
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
                    initListView();
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

    private void initListView(){
        adapter = new ExhibitsItemAdapter(getContext(),exhibits);
        listView.setAdapter(adapter);
    }
}
