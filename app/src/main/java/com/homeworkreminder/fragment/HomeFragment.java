package com.homeworkreminder.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homeworkreminder.R;
import com.homeworkreminder.activity.HomeworkDetailActivity;
import com.homeworkreminder.adapter.HomeDataRecyclerViewAdapter;
import com.homeworkreminder.entity.HomeworkData;
import com.homeworkreminder.interfaces.CallbackValueToActivity;
import com.homeworkreminder.interfaces.MyRecyclerViewOnItemClickListener;
import com.homeworkreminder.entity.HomeData;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 首页界面的Fragment
 */
public class HomeFragment extends Fragment  {
    //private ImageView imageView;

    private HomeDataRecyclerViewAdapter homeDataRecyclerViewAdapter;
    private RecyclerView myRecyclerView;
    private SwipeRefreshLayout homeSwipeRefreshLayout;



    //private int headImg;
    private String nickname;
    private String date;
    private String title;
    private String content;
    private String tag;
    private String course;
    private String deadtime;
    private String hid;

    private String user;
    private List<HomeworkData.DataBean> homeworkDataBeanList;
    private HomeworkData homeworkData;
    //请求的url
    private String url;
    private CallbackValueToActivity callbackValueToActivity;

    /**
     * 这里要填充布局文件
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.one_fragment_layout, null);
        //填充布局文件
        return inflater.inflate(R.layout.home_fragment_layout, null);
    }

    /**
     * 这个相当于Activity的onCreate方法
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);

        url = "http://nibuguai.cn/index.php/index/homework/api_queryHomeworkDoWith";

        useVolleyGET(url);

        //addRecyclerView(myRecyclerView);

        //下拉刷新的监听事件
        homeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                useVolleyGET(url);
            }
        });


    }

    /**
     * 初始化视图
     */
    private void initView(View view) {
        myRecyclerView = view.findViewById(R.id.recyclerView);
        homeSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_SwipeRefreshLayout);
    }

    /**
     * 添加RecyclerView
     * @param recyclerView
     */
    public void addRecyclerView(RecyclerView recyclerView, final List<HomeworkData.DataBean> homeworkDataBeanList){
        //设置RecyclerView的布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));

        //初始化适配器
        homeDataRecyclerViewAdapter = new HomeDataRecyclerViewAdapter(getActivity(), homeworkDataBeanList);
        //homeDataRecyclerViewAdapter = new HomeDataRecyclerViewAdapter(getActivity(), homeworkDataList);

        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置适配器
        recyclerView.setAdapter(homeDataRecyclerViewAdapter);

        //设置单击事件监听器
        homeDataRecyclerViewAdapter.setMyRecyclerViewOnItemClickListener(new MyRecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                System.out.println("position = " + position);

                //通过Bundle向Activity传递数据
                //headImg = homeDataList.get(position).getImg();
                nickname = homeworkDataBeanList.get(position).getUsername();
                //*
                date = homeworkDataBeanList.get(position).getDate();
                title = homeworkDataBeanList.get(position).getTitle();
                content = homeworkDataBeanList.get(position).getContent();
                tag = homeworkDataBeanList.get(position).getTag();
                course = homeworkDataBeanList.get(position).getCourse();
                deadtime = homeworkDataBeanList.get(position).getDeadtime();

                hid = homeworkDataBeanList.get(position).getId() + "";


                //callbackValueToActivity.sendValue(nickname);
                Intent intent = new Intent(getActivity(), HomeworkDetailActivity.class);
                Bundle bundle = new Bundle();

                convertDataToActivity(bundle);

                intent.putExtras(bundle);
                startActivity(intent);

                 //*/
            }
        });
    }

    /**
     * Fragment与Activity关联的回调方法
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //callbackValueToActivity = (CallbackValueToActivity) context;

    }


    /**
     * 通过Bundle向Activity中传值
     */
    public void convertDataToActivity(Bundle bundle){
        //bundle.putInt("headImg",headImg);
        bundle.putCharSequence("nickname", nickname);
        bundle.putCharSequence("title", title);
        bundle.putCharSequence("content", content);
        bundle.putCharSequence("tag", tag);
        bundle.putCharSequence("date", date);
        bundle.putCharSequence("course", course);
        bundle.putCharSequence("deadtime", deadtime);
        bundle.putCharSequence("hid", hid);

    }


    /**
     * get请求
     * @param url url
     */
    public void useVolleyGET(String url) {
        VolleyUtil.volleyGET(getContext(), url, "104", new VolleyInterface(
                getActivity(), VolleyInterface.mListener, VolleyInterface.mErrorListener
        ) {
            @Override
            public void onMySuccess(String result) {

                //使用Gson解析json数据
                homeworkData = MyGson.parseJsonByGson(result, HomeworkData.class);


                //请求成功后addRecyclerView显示数据
                homeworkDataBeanList = HomeFragment.this.homeworkData.getData();

                addRecyclerView(myRecyclerView, homeworkDataBeanList);

                //停止刷新
                homeSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
