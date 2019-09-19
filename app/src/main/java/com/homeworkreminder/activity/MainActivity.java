package com.homeworkreminder.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.fragment.HomeFragment;
import com.homeworkreminder.fragment.SettingFragment;
import com.homeworkreminder.fragment.HomeworkFragment;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;            //Fragment管理器
    private FragmentTransaction fragmentTransaction;    //Fragment事务
    private ImageView iv_user_head;

    //获取当前的Fragment
    private Fragment mFragment = new HomeFragment();

    //导航栏选项名称数组
    String[] titles = {"首页", "我的作业列表", "设置", "注册", "登录", "测试Bmob", "测试QMUI"};

    //Fragment数组
    Fragment[] fragments = {
            new HomeFragment(),
            new HomeworkFragment(),
            new SettingFragment()
    };

    private MyApplication app = (MyApplication) getApplication();

    //保存登录状态
    private String loginState;
    //保存注册状态
    private String registerState;
    //检查用户信息工具类
    private CheckUserInfoUtil userInfoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //悬浮按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MainActivity.this, NewHomeworkActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);         //左侧抽屉
        NavigationView navigationView = findViewById(R.id.nav_view);    //左侧导航栏
        //NavigationView navigationView = null;    //左侧导航栏
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        /**
         * ======================= 上面是系统创建的内容 =============================
         */

        //initView();

        fragmentManager = this.getSupportFragmentManager();
        //切换Fragment
        switchFragment("首页");

        //检查用户状态
        checkUserState();

        //获取导航栏左侧的头像和用户名控件的点击事件，并显示当前用户名
        getLeftImgAndUsername(navigationView);


//        String TAG = "user_state";
//        Log.d(TAG, "MainActivity>registerState: " + MyApplication.registerState);
//        Log.d(TAG, "MainActivity>loginState: " + MyApplication.loginState);

    }

    /**
     * 检查用户状态
     * 通过SharedPreference将用户状态保存
     * 通过读取保存的用户状态信息，设置当前用户状态
     */
    public void checkUserState(){
        //检查注册状态
        userInfoUtil = new CheckUserInfoUtil(getApplicationContext());
        registerState = userInfoUtil.readUserInfo("register");
        Toast.makeText(this, "注册状态：" + registerState, Toast.LENGTH_LONG).show();
        Log.d("userinfo", "注册状态：" + registerState);

        //检查登录状态
        loginState = userInfoUtil.readUserInfo("login");
        Toast.makeText(this, "登录状态：" + loginState, Toast.LENGTH_LONG).show();
        Log.d("login", "登录状态：" + loginState);
    }


    /**
     * 获取左侧导航栏的头像和用户名的点击事件
     * 根据用户状态显示当前用户的用户名
     * @param navigationView NavigationView
     */
    public void getLeftImgAndUsername(NavigationView navigationView){
        //获取左侧抽屉用户头像控件
        View headView = navigationView.getHeaderView(0);
        headView.findViewById(R.id.iv_user_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据用户状态跳转相应界面
                toWhatActivity();
            }
        });

        //获取左侧抽屉用户名控件，并设置显示当前用户的用户名
        View headViewUsername = navigationView.getHeaderView(0);
        TextView nav_header_username = headViewUsername.findViewById(R.id.nav_header_username);
        if (loginState.equals("true")){
            //读取用户名
            String username = userInfoUtil.readUserInfo("username");
            Log.d("userinfo", "username: " + username);
            nav_header_username.setText(username);
        }
        //点击用户名的事件
        nav_header_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWhatActivity();
            }
        });
    }

    /**
     * 根据用户状态跳转相应界面
     */
    public void toWhatActivity(){
        if (registerState.equals("false") && loginState.equals("false")){ //未注册，跳转到注册界面
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

        }else if (loginState.equals("false") && registerState.equals("true")){//未登录，跳转到登录界面
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }else {//已注册，已登录，跳转到个人中心
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }

    }

    /*
    private void initView() {
        iv_user_head = findViewById(R.id.iv_user_head);
    }
     */

    /**
     * 按下手机返回键
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 创建右上角菜单按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 菜单按钮选中时
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String TAG = "MainActivity";    //打印日志的标签

    /**
     * 左侧导航栏选中时
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("Main", "onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, "MenuItem: " + id);


        String title = item.getTitle().toString();

        if (title.equals("注册")) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        if (title.equals("登录")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (title.equals("测试Bmob")) {
            Intent intent = new Intent(MainActivity.this, TestBmobActivity.class);
            startActivity(intent);
        }

        if (title.equals("测试QMUI")) {
            Intent intent = new Intent(MainActivity.this, TestQMUIActivity.class);
            startActivity(intent);
        }

        //切换Fragment的方法
        switchFragment(title);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换Fragment
     */
    public void switchFragment(String title) {
        //获取FragmentManager
        fragmentManager = this.getSupportFragmentManager();
        //开启事务
        fragmentTransaction = fragmentManager.beginTransaction();

        //找到上一个显示的Fragment（第一次显示为null），将它隐藏，显示当前要显示的Fragment
        Fragment findFragment = fragmentManager.findFragmentByTag(title);
        if (findFragment != null) {
            //隐藏之前的fragment，显示当前的fragment
            fragmentTransaction.hide(mFragment).show(findFragment);
            mFragment = findFragment;
        } else {
            //添加Fragment到RelativeLayout布局中
            for (int i = 0; i < fragments.length; i++) {
                if (title.equals(titles[i])) {   //选中选项时才添加Fragment
                    //隐藏掉Fragment
                    fragmentTransaction.hide(mFragment);
                    //将fragment添加到布局
                    fragmentTransaction.add(R.id.relativeLayout, fragments[i], titles[i]);
                    mFragment = fragments[i];
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();
        //设置标题
        this.getSupportActionBar().setTitle(title);

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
        if (hasPermissionDismiss){
            Toast.makeText(this, "请手动开启权限", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 动态权限申请 end------------------------------------
     */



}
