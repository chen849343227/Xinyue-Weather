package com.chen.xinyueweather;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chen.xinyueweather.api.RetrofitService;
import com.chen.xinyueweather.dao.CityDao;
import com.chen.xinyueweather.dao.bean.City;
import com.chen.xinyueweather.dao.bean.CityManage;
import com.chen.xinyueweather.dao.greendao.DaoMaster;
import com.chen.xinyueweather.dao.greendao.DaoSession;
import com.chen.xinyueweather.injector.components.ApplicationComponent;
import com.chen.xinyueweather.injector.components.DaggerApplicationComponent;
import com.chen.xinyueweather.injector.modules.ApplicationModule;
import com.chen.xinyueweather.rxbus.RxBus;
import com.chen.xinyueweather.utils.PermissionUtils;
import com.chen.xinyueweather.utils.ToastUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author along
 * @date create:17-10-10
 * @Description
 */
public class AndroidApplication extends Application {
    private static final String TAG = "AndroidApplication";

    private static final String DB_NAME = "location.db";
    private DaoSession mDaoSession;
    public static AndroidApplication sApplication;
    private static ApplicationComponent sAppComponent;

    private RxBus mRxBus = new RxBus();
    private CityDao cityDao = new CityDao(this);

    /**
     * 用户当前选中的城市
     */
    public static int currentCity = 0;
    /**
     * 保存用户选择的城市
     */
    public static List<CityManage> mCityManages = new ArrayList<>();
    /**
     * 热门城市
     */
    public static final List<String> HOT_CITYS = Arrays.asList("北京", "上海", "天津", "南京", "西安", "武汉", "杭州", "成都", "广州", "深圳");

    /**
     * 保存城市信息
     */
    public static List<City> sCityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        initCopyDatabase(this, "data/data/" + this.getPackageName() + "/databases/", DB_NAME);
        initDatabase();
        initInjector();
        initConfig();
        initData();
    }


    public static Context getContext() {
        return sApplication;
    }


    /**
     * 初始化注射器
     */
    private void initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, mDaoSession, mRxBus, cityDao))
                .build();
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weather.db");
        Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
    }

    /**
     * 将数据库copy到手机里
     *
     * @param context
     * @param filePath
     * @param fileName
     */
    public void initCopyDatabase(Context context, String filePath, String fileName) {
        File oldfile = new File(filePath, fileName);
        if (oldfile.exists()) {
            Log.i(TAG, "数据库文件已存在");
            return;
        }
        File file = new File(filePath, fileName);
        Logger.e(file.toString());
        if (file.exists()) {
            Log.i(TAG, "数据库文件已存在");
            return;
        }
        file.getParentFile().mkdir();
        InputStream in;
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int len;
            in = context.getAssets().open(fileName);
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
            out.flush();
            in.close();
            Log.i(TAG, "数据库初始化完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
        // 初始化网络配置
        RetrofitService.init();
        // Toast
        ToastUtils.init(this);
    }

    private void initData() {
        mDaoSession.getCityManageDao().queryBuilder().rx().list()
                .subscribe(cityManageList -> {
                    mCityManages.clear();
                    mCityManages.addAll(cityManageList);
                });
        //初始化热门城市
        for (int i = 0; i < AndroidApplication.HOT_CITYS.size(); i++) {
            AndroidApplication.sCityList.add(cityDao.getCityByArea(AndroidApplication.HOT_CITYS.get(i)));
        }
        //获取退出时app选中的城市的position
        SharedPreferences sharedPreferences = this.getSharedPreferences("cityIndex", MODE_PRIVATE);
        AndroidApplication.currentCity = sharedPreferences.getInt("index", 0);
    }


    /**
     * @return
     */
    public static ApplicationComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

}
