package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.ContactsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.contacts_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.newly_added)
    Button newlyAdded;
    private List<ContactsBean> contactsBeans;
    private RecyclerViewAdapter adapter;
    private DaoManger daoManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView(){

    }

    @Override
    public void initValidata() {
        daoManger = DaoManger.getInstance();
        contactsBeans = daoManger.getsDaoSession().getContactsBeanDao().loadAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);//列表在底部开始展示，反转后由上面开始展示
        linearLayoutManager.setReverseLayout(true);//列表翻转
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(this,7);
        bindData();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());
        newlyAdded.setOnClickListener(view -> {
            Intent intent = new Intent(ContactActivity.this,AddContactActivity.class);
            startActivityForResult(intent, 1);
        });
        adapter.setOnItemClickListener((view, object) -> {
            ContactsBean contacts = (ContactsBean) object;
            Intent data = new Intent();
            data.putExtra("cName", contacts.getCName());
            data.putExtra("cCellPhone", contacts.getCtellPhone());
            data.putExtra("cQq", contacts.getCQQ());
            data.putExtra("cWechat", contacts.getCWechat());
            setResult(3, data);
            Log.d("hlahfahfoahf", "执行了！！！ ");
            finish();
        });
    }

    @Override
    public void bindData() {
        adapter.setContactsBeanList(contactsBeans);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            contactsBeans = daoManger.getsDaoSession().getContactsBeanDao().loadAll();
            bindData();
        }
    }
}
