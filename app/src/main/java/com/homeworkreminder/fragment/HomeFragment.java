package com.homeworkreminder.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 首页界面的Fragment
 */
public class HomeFragment extends Fragment  {
    //private ImageView imageView;

    private HomeDataRecyclerViewAdapter homeDataRecyclerViewAdapter;
    private RecyclerView myRecyclerView;
    //private int headImg;
    private String nickname;
    private String date;
    private String title;
    private String content;
    private String tag;

    private String user;
    private List<HomeworkData.DataBean> homeworkDataBeanList;
    private HomeworkData homeworkData;

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
        initData();




        String url = "http://nibuguai.cn/index.php/index/homework/getHomework";

        useVolleyGET(url);

        //addRecyclerView(myRecyclerView);


    }




    /**
     * 初始化视图
     */
    private void initView(View view) {
        myRecyclerView = view.findViewById(R.id.recyclerView);


    }



    List<HomeData> homeDataList = new ArrayList<>();
    List<HomeworkData> homeworkDataList = new ArrayList<>();
    List<HomeworkData.DataBean> HomeworkDataBeanList = new ArrayList<>();

    /**
     * 初始化数据
     */
    private void initData() {

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

        //设置监听器
        homeDataRecyclerViewAdapter.setMyRecyclerViewOnItemClickListener(new MyRecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                System.out.println("position = " + position);

                //通过Bundle向Activity传递数据
                //headImg = homeDataList.get(position).getImg();
                //nickname = HomeworkDataBeanList.get(position).getNickname();
                //*
                date = homeworkDataBeanList.get(position).getDate();
                title = homeworkDataBeanList.get(position).getTitle();
                content = homeworkDataBeanList.get(position).getContent();
                tag = homeworkDataBeanList.get(position).getTag();

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


    private CallbackValueToActivity callbackValueToActivity;

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
        //bundle.putCharSequence("nickname", nickname);
        bundle.putCharSequence("title", title);
        bundle.putCharSequence("content", content);
        bundle.putCharSequence("tag", tag);
        bundle.putCharSequence("date", date);

    }


    private String TAG = "volley";

    private String result;
    /**
     * get请求
     * @param url url
     */
    public void useVolleyGET(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求的url
                url,
                //参数2：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        //将请求的原始json数据放到EditText中
                        //loginReturnResult.setText(result);

                        Log.d(TAG, "onResponse: response \n" + response);

                        //使用Gson解析json数据
                        parseJsonByGson(result);


                        homeworkDataBeanList = homeworkData.getData();
                        addRecyclerView(myRecyclerView, homeworkDataBeanList);

                        //Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                },
                //参数3：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //loginReturnResult.setText("请求失败");
                        Log.d(TAG, "onErrorResponse: " + error);
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }

    /**
     * 使用Gson解析json数据，这个比较简单，以下是使用步骤(3步，其实前2步就算拿到数据了)
     * @param json 要解析的json
     */
    public void parseJsonByGson(String json){
        //1、创建Gson对象
        Gson gson = new Gson();
        //2、调用Gson的fromJson()方法，将json转成javaBean，将要显示的数据封装到这个javaBean
        //fromJson(参数1，参数2)方法 参数1：需要解析的json 参数2：一个javaBean，接收需要封装的数据（总数据的javaBean）
        homeworkData = gson.fromJson(result, HomeworkData.class);

    }
}
