package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.ContactsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.contacts_recyclerView)
    SwipeRecyclerView recyclerView;
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
            finish();
        });

        recyclerView.setItemViewSwipeEnabled(true);//侧滑删除 默认关闭
        recyclerView.setLongPressDragEnabled(true);//拖拽排序 默认关闭
        recyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            //此方法在item拖拽交换位置时被调用
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                // 第一个参数是要交换为之的Item，第二个是目标位置的Item。
                int adapterPosition = viewHolder.getAdapterPosition();
                int adapterPosition1 = viewHolder1.getAdapterPosition();
                // swap交换数据，并更新adapter。
                Collections.swap(contactsBeans, adapterPosition, adapterPosition1);
                adapter.notifyItemMoved(adapterPosition, adapterPosition1);
                return true; //返回true,表示数据交换成功,Itemview可以交换位置
            }
            //此方法在item在侧滑删除时被调用
            @Override
            public void onItemDismiss(RecyclerView.ViewHolder viewHolder) {
                //从数据源移除该item对应的数据，并刷新Adapter
                int position = viewHolder.getAdapterPosition();
                daoManger.getsDaoSession().getContactsBeanDao().deleteByKey(contactsBeans.get(position).getContactsid());
                contactsBeans.remove(position);
                adapter.notifyItemRemoved(position);
            }
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
