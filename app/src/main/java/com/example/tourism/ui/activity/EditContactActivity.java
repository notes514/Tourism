package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.ContactsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.contacts_pic)
    CircleImageView contactsPic;
    @BindView(R.id.contacts)
    EditText contacts;
    @BindView(R.id.et_tell)
    EditText etTell;
    @BindView(R.id.et_tell2)
    EditText etName;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private String contactsN = "";
    private String contactsTell = "";
    final Intent intent = getIntent();
//    private List<ContactsBean> contactsBeans;
    private DaoManger daoManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {
        Bundle bundle = this.getIntent().getExtras();
        contactsN = bundle.getString("id");
        contactsTell = bundle.getString("tell");
        contacts.setText(contactsN);
        etTell.setText(contactsTell);
        daoManger = DaoManger.getInstance();


    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());

        customToolbar.setOnRightButtonClickLister(() -> {
//            ContactsBean contactsBean = new ContactsBean();
//            daoManger.getsDaoSession().getContactsBeanDao().update(contactsBean);
            finish();
            AppUtils.getToast("编辑成功");
        });
    }

    @Override
    public void bindData() {

    }
}
