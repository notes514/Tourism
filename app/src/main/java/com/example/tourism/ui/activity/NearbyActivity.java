package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NearbyActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        unbinder = ButterKnife.bind(this, this);
        //setSupportActionBar(toolbar);
        setActionBar(toolbar);
        //显示返回按钮 禁用标题
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    protected int getStatusBarHeight() {
        return super.getStatusBarHeight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
