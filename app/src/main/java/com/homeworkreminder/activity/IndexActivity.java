package com.homeworkreminder.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.adapter.MyFragmentPagerAdapter;
import com.homeworkreminder.entity.Weather;
import com.homeworkreminder.fragment.HomeFragment;
import com.homeworkreminder.fragment.HomeworkFragment;
import com.homeworkreminder.fragment.IndexFragment;
import com.homeworkreminder.fragment.UserFragment;
import com.homeworkreminder.utils.GlideImageLoader;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 新版首页Activity
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView tvTab1;
    private ImageView tvTab2;
    private ImageView tvTab3;
    private ImageView tvTab4;

    private TextView tabTvIndex;
    private TextView tabTvAllHw;
    private TextView tabTvMyHw;
    private TextView tabTvMy;

    Fragment fragment1 = new IndexFragment();
    Fragment fragment2 = new HomeFragment();
    Fragment fragment3 = new HomeworkFragment();
    Fragment fragment4 = new UserFragment();


    //保存登录状态
    private String loginState;
    //保存注册状态
    private String registerState;
    //检查用户信息工具类
    private CheckUserInfoUtil userInfoUtil = new CheckUserInfoUtil(IndexActivity.this);

    Integer[] selectedIcon = {
            R.drawable.icon_selected_home,
            R.drawable.icon_selected_all_hw,
            R.drawable.icon_selected_my_hw,
            R.drawable.icon_selected_my,
    };

    Integer[] unSelectedIcon = {
            R.drawable.icon_unselected_home,
            R.drawable.icon_unselected_all_hw,
            R.drawable.icon_unselected_my_hw,
            R.drawable.icon_unselected_my,
    };

    Integer[] fontColor = {
            Color.parseColor("#B6B6B6"),
            Color.parseColor("#03A9F4")
    };


    //添加ViewPager
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Log.i("IndexActivity", "======== onCreate: ");

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //获取权限
        initPermission();

        //检查用户状态
        checkUserState();

        //初始化UI
        initUI();
        //初始化底部选项卡
        initTab();

    }



    /**
     * 初始化底部选项卡
     */
    private void initTab() {
        //添加Fragment到ViewPager

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);


        //添加适配器
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);

        //初始化第一个页面
        viewPager.setCurrentItem(0);

        //设置底部tab栏的背景色
        tvTab1.setImageResource(selectedIcon[0]);
        tvTab2.setBackgroundColor(Color.WHITE);
        tvTab3.setBackgroundColor(Color.WHITE);

        //设置字体颜色
        tabTvIndex.setTextColor(fontColor[1]);
        tabTvAllHw.setTextColor(fontColor[0]);
        tabTvMyHw.setTextColor(fontColor[0]);
        tabTvMy.setTextColor(fontColor[0]);

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tvTab1 = (ImageView) findViewById(R.id.tv_tab1);
        tvTab2 = (ImageView) findViewById(R.id.tv_tab2);
        tvTab3 = (ImageView) findViewById(R.id.tv_tab3);
        tvTab4 = (ImageView) findViewById(R.id.tv_tab4);
        //加入ViewPager组件
        viewPager = findViewById(R.id.viewpager);

        tvTab1.setOnClickListener(this);
        tvTab2.setOnClickListener(this);
        tvTab3.setOnClickListener(this);
        tvTab4.setOnClickListener(this);

        tabTvIndex = (TextView) findViewById(R.id.tab_tv_index);
        tabTvAllHw = (TextView) findViewById(R.id.tab_tv_all_hw);
        tabTvMyHw = (TextView) findViewById(R.id.tab_tv_my_hw);
        tabTvMy = (TextView) findViewById(R.id.tab_tv_my);

        //加入ViewPager监听
        viewPager.addOnPageChangeListener(new MyViewPagerChangeListener());
    }



    /**
     * 监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tab1:
                //显示第一个页面
                viewPager.setCurrentItem(0);
                //设置选中的图片
                tvTab1.setImageResource(selectedIcon[0]);
                tvTab2.setImageResource(unSelectedIcon[1]);
                tvTab3.setImageResource(unSelectedIcon[2]);
                tvTab4.setImageResource(unSelectedIcon[3]);
                //设置字体颜色
                tabTvIndex.setTextColor(fontColor[1]);
                tabTvAllHw.setTextColor(fontColor[0]);
                tabTvMyHw.setTextColor(fontColor[0]);
                tabTvMy.setTextColor(fontColor[0]);

                break;
            case R.id.tv_tab2:
                //显示第二个页面
                viewPager.setCurrentItem(1);
                //设置底部tab栏的背景色
                tvTab1.setImageResource(unSelectedIcon[0]);
                tvTab2.setImageResource(selectedIcon[1]);
                tvTab3.setImageResource(unSelectedIcon[2]);
                tvTab4.setImageResource(unSelectedIcon[3]);

                //设置字体颜色
                tabTvIndex.setTextColor(fontColor[0]);
                tabTvAllHw.setTextColor(fontColor[1]);
                tabTvMyHw.setTextColor(fontColor[0]);
                tabTvMy.setTextColor(fontColor[0]);

                break;

            case R.id.tv_tab3:
                if (loginState.equals("false")){
                    startActivity(new Intent(IndexActivity.this, RegisterActivity.class));
                }else {
                    //显示第二个页面
                    viewPager.setCurrentItem(2);
                    //设置底部tab栏的背景色

                    tvTab1.setImageResource(unSelectedIcon[0]);
                    tvTab2.setImageResource(unSelectedIcon[1]);
                    tvTab3.setImageResource(selectedIcon[2]);
                    tvTab4.setImageResource(unSelectedIcon[3]);

                    //设置字体颜色
                    tabTvIndex.setTextColor(fontColor[0]);
                    tabTvAllHw.setTextColor(fontColor[0]);
                    tabTvMyHw.setTextColor(fontColor[1]);
                    tabTvMy.setTextColor(fontColor[0]);
                }
                break;


            case R.id.tv_tab4:
                if (loginState.equals("false")){
                    startActivity(new Intent(IndexActivity.this, RegisterActivity.class));
                }else {
                    //显示第四个页面
                    viewPager.setCurrentItem(3);
                    //设置底部tab栏的背景色
                    tvTab1.setImageResource(unSelectedIcon[0]);
                    tvTab2.setImageResource(unSelectedIcon[1]);
                    tvTab3.setImageResource(unSelectedIcon[2]);
                    tvTab4.setImageResource(selectedIcon[3]);

                    //设置字体颜色
                    tabTvIndex.setTextColor(fontColor[0]);
                    tabTvAllHw.setTextColor(fontColor[0]);
                    tabTvMyHw.setTextColor(fontColor[0]);
                    tabTvMy.setTextColor(fontColor[1]);
                }
                break;
            default:break;
        }
    }


    /**
     * ViewPagerChangeListener监听器
     */
    public class MyViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        //选中tap时切换背景颜色
        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case 0:
                    //显示第一个页面
                    viewPager.setCurrentItem(0);
                    //设置底部tab栏的背景色
                    tvTab1.setImageResource(selectedIcon[0]);
                    tvTab2.setImageResource(unSelectedIcon[1]);
                    tvTab3.setImageResource(unSelectedIcon[2]);
                    tvTab4.setImageResource(unSelectedIcon[3]);

                    break;
                case 1:
                    //显示第二个页面
                    viewPager.setCurrentItem(1);
                    //设置底部tab栏的背景色
                    tvTab1.setImageResource(unSelectedIcon[0]);
                    tvTab2.setImageResource(selectedIcon[1]);
                    tvTab3.setImageResource(unSelectedIcon[2]);
                    tvTab4.setImageResource(unSelectedIcon[3]);

                    break;

                case 2:
                    if (loginState.equals("false")){
                        startActivity(new Intent(IndexActivity.this, RegisterActivity.class));

                    }else {
                        //显示第三个页面
                        viewPager.setCurrentItem(2);
                        //设置底部tab栏的背景色

                        tvTab1.setImageResource(unSelectedIcon[0]);
                        tvTab2.setImageResource(unSelectedIcon[1]);
                        tvTab3.setImageResource(selectedIcon[2]);
                        tvTab4.setImageResource(unSelectedIcon[3]);
                    }
                    break;


                case 3:
                    if (loginState.equals("false")){
                        startActivity(new Intent(IndexActivity.this, RegisterActivity.class));
                    }else {
                        //显示第四个页面
                        viewPager.setCurrentItem(3);
                        //设置底部tab栏的背景色
                        tvTab1.setImageResource(unSelectedIcon[0]);
                        tvTab2.setImageResource(unSelectedIcon[1]);
                        tvTab3.setImageResource(unSelectedIcon[2]);
                        tvTab4.setImageResource(selectedIcon[3]);
                    }
                    break;
                default:break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    }


    static int count = 0;

    /**
     * 检查用户状态
     * 通过SharedPreference将用户状态保存
     * 通过读取保存的用户状态信息，设置当前用户状态
     */
    public void checkUserState(){
        count++;
        Log.i("IndexActivity", "============= checkUserState: " + count);
        //检查注册状态

        registerState = userInfoUtil.readUserInfo("register");
        Toast.makeText(this, "注册状态：" + registerState, Toast.LENGTH_LONG).show();
        Log.d("userinfo", "注册状态：" + registerState);

        //检查登录状态
        loginState = userInfoUtil.readUserInfo("login");
        Toast.makeText(this, "登录状态：" + loginState, Toast.LENGTH_LONG).show();
        Log.d("login", "登录状态：" + loginState);
    }

    /**
     * 根据用户状态跳转相应界面
     */
    public void toWhatActivity(){

        if (registerState.equals("false") && loginState.equals("false")){ //未注册，跳转到注册界面
            //if (!isRegister && !isLogin){ //未注册，跳转到注册界面
            Intent intent = new Intent(IndexActivity.this, RegisterActivity.class);
            startActivity(intent);

        }else if (loginState.equals("false") && registerState.equals("true")){//未登录，跳转到登录界面
            //}else if (isRegister && !isLogin){//未登录，跳转到登录界面
            Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
            startActivity(intent);

        }else {//已注册，已登录，跳转到个人中心
            Intent intent = new Intent(IndexActivity.this, UserActivity.class);
            startActivity(intent);
        }

    }


    /**
     * 动态权限申请 start=======================================
     */
    //权限数组
    public String[] permissions = {
            Manifest.permission. INTERNET,
            Manifest.permission. WRITE_EXTERNAL_STORAGE,
            Manifest.permission. MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission. VIBRATE,
            Manifest.permission. SYSTEM_ALERT_WINDOW
    };

    //未授权列表
    private List<String> unPermissionList = new ArrayList<>();
    //授权请求码
    private final static int mRequestCode = 200;

    /**
     * 权限申请
     */
    public void initPermission(){
        unPermissionList.clear();   //清空未申请的权限
        //将没有申请的权限放到权限列表里
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED){
                unPermissionList.add(permissions[i]);
            }
        }

        //申请权限
        if (unPermissionList.size() > 0){
            ActivityCompat.requestPermissions(this,permissions,mRequestCode);
        }

    }


    /**
     * 请求权限后回调的方法
     * @param requestCode 是我们自己定义的权限请求码
     * @param permissions 是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限
     *                     名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //是否有拒绝的权限
        boolean hasPermissionDismiss = false;
        if (mRequestCode == requestCode){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1){
                    hasPermissionDismiss = true;
                    break;
                }
            }
        }

    }



    /**
     * 动态权限申请 end------------------------------------
     */

}

