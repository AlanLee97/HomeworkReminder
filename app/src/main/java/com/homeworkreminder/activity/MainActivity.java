package com.homeworkreminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.homeworkreminder.R;
import com.homeworkreminder.fragment.HomeFragment;
import com.homeworkreminder.fragment.SettingFragment;
import com.homeworkreminder.fragment.HomeworkFragment;
import com.homeworkreminder.service.MusicService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;            //Fragment管理器
    private FragmentTransaction fragmentTransaction;    //Fragment事务
    private ImageView iv_user_head;

    //获取当前的Fragment
    private Fragment mFragment = new HomeFragment();

    //导航栏选项名称数组
    String[] titles = {"首页", "我的作业列表", "设置", "注册", "登录"};

    //Fragment数组
    Fragment[] fragments = {
            new HomeFragment(),
            new HomeworkFragment(),
            new SettingFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        //获取左侧抽屉头像控件
        View headView = navigationView.getHeaderView(0);
        headView.findViewById(R.id.iv_user_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, UserActivity.class));

            }
        });

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



}
