package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.TripBean;
import com.example.tourism.entity.Constant;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelerActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tv_scan_documents)
    TextView tvScanDocuments;
    @BindView(R.id.tv_manually)
    TextView tvManually;
    @BindView(R.id.traveler_recyclerView)
    RecyclerView travelerRecyclerView;

    private int REQUEST_CODE_SCAN = 1;
    private List<TripBean> tripBeanList;
    private RecyclerViewAdapter adapter;
    private DaoManger daoManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveler);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        daoManger = DaoManger.getInstance();
        tripBeanList = daoManger.getsDaoSession().getTripBeanDao().loadAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);//列表在底部开始展示，反转后由上面开始展示
        linearLayoutManager.setReverseLayout(true);//列表翻转
        travelerRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(this,8);
        bindData();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> this.finish());

//        tvScanDocuments.setOnClickListener(view -> {
//            Intent intent = new Intent(TravelerActivity.this, CaptureActivity.class);
//            ZxingConfig config = new ZxingConfig();
//            config.setPlayBeep(false);//是否播放扫描声音 默认为true
//            config.setShake(true);//是否震动 默认为true
//            config.setShowFlashLight(true);//是否全屏扫描 默认为true 设为false则只会在扫描框中扫描
//            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
//            startActivityForResult(intent, REQUEST_CODE_SCAN);
//        });

        tvManually.setOnClickListener(view ->
        {
            Intent intent = new Intent(this,NewPedestriansActivity.class);
            startActivityForResult(intent, 1);
        });

        adapter.setOnItemClickListener((view, object) -> {
            TripBean tripBean = (TripBean) object;
            Intent data = new Intent();
            data.putExtra("tName", tripBean.getTName());
            data.putExtra("tIdentityCard", tripBean.getTIdentitycard());
            setResult(4, data);
            this.finish();
        });

    }

    @Override
    public void bindData() {
        adapter.setTripBeanList(tripBeanList);
        travelerRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            tripBeanList = daoManger.getsDaoSession().getTripBeanDao().loadAll();
            bindData();
        }
    }
}
