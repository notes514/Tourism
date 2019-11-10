package com.example.tourism.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dlong.rep.dlsidebar.DLSideBar;
import com.example.tourism.R;
import com.example.tourism.adapter.CityItemAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.entity.City;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.PinyinUtils;
import com.example.tourism.utils.ReadAssetsJsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

public class LocationActivity extends BaseActivity implements View.OnClickListener{

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    AMapLocationListener mLocationListener;
    String location;
    DLSideBar.OnTouchingLetterChangedListener mSBTouchListener;
    TextWatcher mTextWatcher;
    AdapterView.OnItemClickListener mItemClickListener;

    private Context mContext = this;
    private CityItemAdapter mAdapter;
    private ArrayList<City> mBeans = new ArrayList<>();

    private static LocationActivity instance;

    /** 记录当前是否是search图标 */
    private boolean IS_SEARCH_ICON = true;

    private ImageView imgSearch;
    private ImageView imgBack;
    private TextView txtTittle;
    private EditText etSearch;
    private ListView lvArea;
    private DLSideBar sbIndex;

    private void initView() {
        imgSearch = findViewById(R.id.img_search);
        imgBack = findViewById(R.id.img_back);
        txtTittle = findViewById(R.id.txt_tittle);
        etSearch = findViewById(R.id.et_search);
        lvArea = findViewById(R.id.lv_area);
        sbIndex = findViewById(R.id.sb_index);

        imgSearch.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        // 配置列表适配器
        lvArea.setVerticalScrollBarEnabled(false);
        lvArea.setFastScrollEnabled(false);
//        mAdapter = new CityItemAdapter(mContext, mBeans);
//        lvArea.setAdapter(mAdapter);
        lvArea.setOnItemClickListener(mItemClickListener);
        // 配置右侧index
        sbIndex.setOnTouchingLetterChangedListener(mSBTouchListener);
        // 配置搜索
        etSearch.addTextChangedListener(mTextWatcher);
    }

    public static LocationActivity getInstance() {
        return instance;
    }

    private void initData() {
        mBeans.clear();
        JSONArray array = ReadAssetsJsonUtil.getJSONArray(getResources().getString(R.string.area_select_json_name), mContext);
        if (null == array) array = new JSONArray();

        for (int i = 0; i < array.length(); i++) {
            City bean = new City();
            bean.cityName = array.optJSONObject(i).optString("zh");
            bean.fisrtSpell = PinyinUtils.getFirstSpell(bean.cityName.substring(0,1));
            bean.namePy = PinyinUtils.getPinYin(bean.cityName);
            mBeans.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mBeans, new City.ComparatorPY());
        // 数据更新
        if (mAdapter!=null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    public void initListen(){

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        Log.d("ss", "onLocationChanged: "+aMapLocation.getCity());
                        if (aMapLocation.getCity()!=null) {
                            location = aMapLocation.getCity();
                            location = location.substring(0, location.length() - 1);
                            Log.d("ss", "onLocationChanged: " + location);

                            mAdapter = new CityItemAdapter(mContext, mBeans,location);
                            Log.d("ss", "onLs: " + mBeans.get(0));
                            lvArea.setAdapter(mAdapter);
                        }
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };

        /**
         * 搜索监听
         */
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!"".equals(s.toString().trim())) {
                    //根据编辑框值过滤联系人并更新联系列表
                    filterContacts(s.toString().trim());
                    sbIndex.setVisibility(View.GONE);
                } else {
                    sbIndex.setVisibility(View.VISIBLE);
                    mAdapter.updateListView(mBeans);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        /**
         * 选项点击事件
         */
        mItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<City> bs = mAdapter.getList();
                City bean =bs.get(position);
                Intent data = new Intent();
                data.putExtra("location", bean.cityName);
                setResult(1, data);
                finish();
            }
        };

        mSBTouchListener = new DLSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String str) {
                // 返回的是字母A～Z
                // TODO: 2019/4/1 这里更新你的列表
                if (mBeans.size()>0){
                    for (int i=1;i<mBeans.size();i++){
                        if (mBeans.get(i).fisrtSpell.compareToIgnoreCase(str) == 0) {
                            lvArea.setSelection(i);
                            break;
                        }
                    }
                    if (str.equals("#")){
                        lvArea.setSelection(0);
                    }
                }
            }
        };
    }

    /**
     * 比对筛选
     * @param filterStr
     */
    private void filterContacts(String filterStr){
        ArrayList<City> filters = new ArrayList<>();
        //遍历数组,筛选出包含关键字的item
        for (int i = 0; i < mBeans.size(); i++) {
            //过滤的条件
            if (isStrInString(mBeans.get(i).namePy,filterStr)
                    ||mBeans.get(i).cityName.contains(filterStr)
                    ||isStrInString(mBeans.get(i).fisrtSpell,filterStr)){
                //将筛选出来的item重新添加到数组中
                City filter = mBeans.get(i);
                filters.add(filter);
            }
        }

        //将列表更新为过滤的联系人
        mAdapter.updateListView(filters);
    }

    /**
     * 判断字符串是否包含
     * @param bigStr
     * @param smallStr
     * @return
     */
    public boolean isStrInString(String bigStr,String smallStr){
        return bigStr.toUpperCase().contains(smallStr.toUpperCase());
    }

    public void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        mLocationOption.setInterval(5000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_city);
        initListen();
        initLocation();
        initView();
        initData();
        instance = this;

        sbIndex = findViewById(R.id.sb_index);
        sbIndex.setOnTouchingLetterChangedListener(mSBTouchListener);
    }



    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    /**
     * 修改当前显示
     */
    private void changeMode(){
        if (IS_SEARCH_ICON){
            imgSearch.setImageResource(R.drawable.btn_serach_selector);
            imgSearch.setVisibility(View.VISIBLE);
            etSearch.setText("");
            etSearch.setVisibility(View.GONE);
            txtTittle.setVisibility(View.VISIBLE);
        } else {
            imgSearch.setImageResource(R.mipmap.icon_date_black);
            imgSearch.setVisibility(View.INVISIBLE);
            etSearch.setVisibility(View.VISIBLE);
            etSearch.setFocusable(true);
            txtTittle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                if (IS_SEARCH_ICON){
                    finish();
                } else {
                    IS_SEARCH_ICON = true;
                    changeMode();
                }
                break;
            case R.id.img_search:
                if (IS_SEARCH_ICON){
                    IS_SEARCH_ICON = false;
                    changeMode();
                } else {
                    etSearch.setText("");
                }
                break;
        }
    }
}
