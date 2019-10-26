package com.homeworkreminder.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.homeworkreminder.R;


public class MyApplication extends Application {
    //ImageLoader显示图片过程中的参数
    private static DisplayImageOptions mLoaderOptions;
    //Volley网络请求的队列对象
    private static RequestQueue mQueue;

    private CheckUserInfoUtil userInfoUtil;

    private UserInfo userInfo;

    private boolean STATE_LOGIN = false;
    private boolean STATE_REGISTER = false;

    public boolean isSTATE_LOGIN() {
        return STATE_LOGIN;
    }

    public void setSTATE_LOGIN(boolean STATE_LOGIN) {
        this.STATE_LOGIN = STATE_LOGIN;
    }

    public boolean isSTATE_REGISTER() {
        return STATE_REGISTER;
    }

    public void setSTATE_REGISTER(boolean STATE_REGISTER) {
        this.STATE_REGISTER = STATE_REGISTER;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }



    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ImageLoader
        initImageLoader(getApplicationContext());
        //初始化Volley的队列对象
        mQueue = Volley.newRequestQueue(getApplicationContext());

        getUserInfoFromSharedPreference();

        //app_checkUserState();


    }

    public static void initImageLoader(Context context) {
        //初始化一个ImageLoaderConfiguration配置对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.
                Builder(context).
                denyCacheImageMultipleSizesInMemory().
                threadPriority(Thread.NORM_PRIORITY - 2).
                diskCacheFileNameGenerator(new Md5FileNameGenerator()).
                tasksProcessingOrder(QueueProcessingType.FIFO).
                build();
        //用ImageLoaderConfiguration配置对象完成ImageLoader的初始化，单例
        ImageLoader.getInstance().init(config);
        //示图片过程中的参数
        mLoaderOptions = new DisplayImageOptions.Builder().
                showImageOnLoading(R.drawable.no_image).//正加载，显示no_image
                showImageOnFail(R.drawable.no_image).//加载失败时
                showImageForEmptyUri(R.drawable.no_image).//加载的Uri为空
                imageScaleType(ImageScaleType.EXACTLY_STRETCHED).
                cacheInMemory(true).//是否进行缓冲
                cacheOnDisk(true).
                considerExifParams(true).
                build();
    }



    public static RequestQueue getHttpQueue() {
        return mQueue;
    }

    public static DisplayImageOptions getLoaderOptions() {
        return mLoaderOptions;
    }
    //Volley请求，传入消息编号Tag。将request请求放入队列中缓存
    public static void addRequest(Request request, Object tag) {
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    // 通过消息编号在队列中删除请求
    public static void removeRequest(Object tag) {
        mQueue.cancelAll(tag);
    }


    private String TAG = "MyApplication";
    public void getUserInfoFromSharedPreference(){
        userInfoUtil = new CheckUserInfoUtil(getApplicationContext());
        String userinfo = userInfoUtil.readUserInfo("userinfo");
        Log.d(TAG, "getUserInfoFromSharedPreference: " + userinfo);

//        VolleyUtil volleyUtil = new VolleyUtil(getApplicationContext());
        if (!userinfo.equals("false")){
            UserInfo userInfo1 = MyGson.parseJsonByGson(userinfo,UserInfo.class);
            Log.d(TAG, "getUserInfoFromSharedPreference: userInfo1:" + userInfo1);
            setUserInfo(userInfo1);
        }
    }




    public boolean checkState(boolean isLogin){
        CheckUserInfoUtil checkUserInfoUtil = new CheckUserInfoUtil(getApplicationContext());
        String loginState = checkUserInfoUtil.readUserInfo("login");
        if (loginState.equals("true")){
            isLogin = true;
        }else {
            isLogin = false;
        }

        return isLogin;
    }

}
