package com.example.tourism.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.MainActivity.user;

public class PersonalDataActivity extends BaseActivity implements DefineView, View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    @BindView(R.id.btn_personal_Logout)
    Button btnPersonalLogout;
    private Context context;
    private LinearLayout llDate, llTime;
    private TextView tvDate, tvTime;
    private int year, month, day, hour, minute;
    //在TextView上显示的字符
    private StringBuffer date, time;

    @BindView(R.id.user_head_portrait)
    CircleImageView userHeadPortrait;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_eamil)
    TextView tvEamil;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        initListener();
        initDateTime();
        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initView();


        //设置圆形图像
        Glide.with(this).load(R.drawable.personal_blue_bj)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(userHeadPortrait);

        if (user == null) {
            btnPersonalLogout.setVisibility(View.GONE);
        }else if (user != null){
            tvUsername.setText(user.getUserAccountName());
            tvEamil.setText(user.getEmail());
            tvAddress.setText(user.getNagaiAddr());
            tvSex.setText(user.getUserSex());
            tvTel.setText(user.getUserTellphone());
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + user.getUserPicUrl(),
                    userHeadPortrait, InitApp.getOptions());
            btnPersonalLogout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        llDate = (LinearLayout) findViewById(R.id.ll_date);
        tvDate = (TextView) findViewById(R.id.tv_date);
        llDate.setOnClickListener(this);
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.personal_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, month - 1, day, this);
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }
    @OnClick(R.id.btn_personal_Logout)
    public void onViewClicked() {
        SharedPreferences sharedPreferences = getSharedPreferences("Userdata",Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        user = null;
        RequestURL.vUserId = "";
        finish();
    }
}
