package com.example.tourism.application;

import android.app.Application;
import android.content.Context;

import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.utils.L;
import com.example.tourism.R;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 *
 */
public class InitApp extends Application {
    public static final String TAG = "InitApp";
    private static InitApp instance = null;
    private static DisplayImageOptions options;
    private BRTBeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        AppUtils.init(this);
        DaoManger.getInstance().init(this);
        // 开启log打印
        L.enableDebugLogging(true);
        //获取单例
        beaconManager = BRTBeaconManager.getInstance(this);
        // 注册应用 APPKEY申请:http://brtbeacon.com/main/index.shtml
        beaconManager.registerApp("00000000000000000000000000000000");
        // 开启Beacon扫描服务
        beaconManager.startService();
        initImageLoader(getApplicationContext());
    }

    /**
     * 全局请求单例（单例模式）
     */
    public static InitApp getInstance() {
        return instance;
    }

    /**
     * 初始化ImageLoader
     * @param context
     */
    private void initImageLoader(Context context){
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.defaultbg) //图片正在加载，显示
                .showImageOnFail(R.drawable.defaultbg) //图片加载失败
                .showImageForEmptyUri(R.drawable.defaultbg) //加载图片的Uri为空时
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();
        //初始化 ImageLoaderConfiguration 的配置对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build();
    // 用ImageLoaderConfiguration配置对象来完成ImageLoader的初始化，单利
        ImageLoader.getInstance().init(config);
}

    /**
     * 设置DisplayImageOptions
     * @return
     */
    public static DisplayImageOptions getOptions() {
        return options;
    }

    /**
     * 创建Beacon连接需要传递此参数
     * @return IBle
     */
    public IBle getIBle() {
        return beaconManager.getIBle();
    }

    /**
     * 获取Beacon管理对象
     *
     * @return BRTBeaconManager
     */
    public BRTBeaconManager getBRTBeaconManager() {
        return beaconManager;
    }

}
