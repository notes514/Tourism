package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.MainActivity;
import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerAdapter;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.adapter.SelectionAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.TripBean;
import com.example.tourism.entity.TravellingPeopleBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;
import com.hz.android.easyadapter.EasyAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private int REQUEST_CODE_SCAN = 1;
    private List<TripBean> tripBeanList;
    private RecyclerViewAdapter adapter;
    private RecyclerAdapter rAdapter;
    private DaoManger daoManger;
    //成人数
    private int adultNumber;
    //儿童数
    private int childrenNumber;
    //出行人list集合
    private List<TravellingPeopleBean> peopleBeanList = new ArrayList<>();

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void initValidata() {
        //初始化数据库
        daoManger = DaoManger.getInstance();
        //获取景区所有数据
        tripBeanList = daoManger.getsDaoSession().getTripBeanDao().loadAll();

        adultNumber = this.getIntent().getIntExtra("adultNumber", 0);
        childrenNumber = this.getIntent().getIntExtra("childrenNumber", 0);

        setBtnComplete();

        //设置线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        linearLayoutManager.setStackFromEnd(true);//列表在底部开始展示，反转后由上面开始展示
//        linearLayoutManager.setReverseLayout(true);//列表翻转
        travelerRecyclerView.setLayoutManager(linearLayoutManager);
        rAdapter = new RecyclerAdapter(this, 0);
        bindData();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> this.finish());

        tvManually.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, NewPedestriansActivity.class);
            startActivityForResult(intent, 1);
        });

        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Object> mapTrip = new HashMap<>();
        List<TripBean> tripBeans = new ArrayList<>();
        rAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClickListener(View view, Object object, int itemPosition) {
                TripBean tripBean = (TripBean) object;
                String re = tripBean.getTIdentitycard().substring(6, 10);
                //点击事件
                rAdapter.setSelectItem(itemPosition);
                map.put(itemPosition, Integer.parseInt(re));
                mapTrip.put(itemPosition, tripBean);
                if (adultNumber + childrenNumber > rAdapter.getNum()) {
                    if (2019 - map.get(itemPosition) > 12 && adultNumber > 0) {
                        btnComplete.setText("您还需选择" + adultNumber + "位成人旅客");
                        btnComplete.setBackgroundColor(R.color.color_orange_light_colour);
                    } else if (2019 - map.get(itemPosition) <= 12 && childrenNumber > 0) {
                        btnComplete.setText("您还需选择" + childrenNumber + "位儿童旅客");
                        btnComplete.setBackgroundColor(R.color.color_orange_light_colour);
                    }

                }
                if (adultNumber + childrenNumber == rAdapter.getNum()) {
                    if (adultNumber > 0 && childrenNumber > 0 && 2019 - map.get(itemPosition) > 12) {
                        setDialog("提示", "成人旅客数量已达上限，请选择儿童旅客", "确定");
                        rAdapter.setCheckBox(itemPosition, false);
                    } else if (adultNumber > 0 && childrenNumber > 0 && 2019 - map.get(itemPosition) <= 12) {
                        setDialog("提示", "儿童旅客数量已达上限，请选择成人旅客", "确定");
                        rAdapter.setCheckBox(itemPosition, false);
                    } else {
                        btnComplete.setBackgroundColor(R.color.color_orange);
                        btnComplete.setText("完成");
                        btnComplete.setOnClickListener(v -> {
                            for (Integer key : mapTrip.keySet()) {
                                tripBeans.add((TripBean) mapTrip.get(key));
                                String res = tripBeans.get(key).getTIdentitycard().substring(6, 10);
                                if (2019 - Integer.parseInt(res) > 12) {
                                    peopleBeanList.add(new TravellingPeopleBean("成人", tripBeans.get(key).getTName(),
                                            tripBeans.get(key).getTIdentitycard(), 1));
                                } else {
                                    peopleBeanList.add(new TravellingPeopleBean("儿童", tripBeans.get(key).getTName(),
                                            tripBeans.get(key).getTIdentitycard(), 1));
                                }

                            }
                            Intent data = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("tripBeans", (Serializable) peopleBeanList);
                            data.putExtras(bundle);
                            setResult(4, data);
                            finish();
                        });
                    }
                }
                if (adultNumber + childrenNumber < rAdapter.getNum()) {
                    rAdapter.setCheckBox(itemPosition, false);
                    setDialog("提示", "旅客数量已达上限，请点击完成或重新选择", "确定");
                }
            }

            @Override
            public boolean onItemLongClickListener(View view, Object object, int itemPosition) {
                return false;
            }
        });

    }

    private void setDialog(String title, String message, String bStr){
        AlertDialog.Builder builder  = new AlertDialog.Builder(TravelerActivity.this);
        builder.setTitle(title) ;
        builder.setMessage(message) ;
        builder.setPositiveButton(bStr ,  null );
        builder.show();
    }

    @Override
    public void bindData() {
        rAdapter.setTripBeanList(tripBeanList);
        travelerRecyclerView.setAdapter(rAdapter);
        rAdapter.notifyDataSetChanged();
    }

    @SuppressLint("ResourceAsColor")
    private void setBtnComplete() {
        if (adultNumber > 0 && childrenNumber > 0) {
            btnComplete.setText("您还需选择" + adultNumber + "位成人，" + childrenNumber + "位儿童");
            btnComplete.setBackgroundColor(R.color.color_orange_light_colour);
        } else {
            btnComplete.setText("您还需选择" + adultNumber + "位成人");
            btnComplete.setBackgroundColor(R.color.color_orange_light_colour);
        }
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
