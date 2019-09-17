package com.homeworkreminder.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.homeworkreminder.adapter.UndoDataRecyclerViewAdapter;
import com.homeworkreminder.entity.HomeData;
import com.homeworkreminder.entity.HomeworkData;
import com.homeworkreminder.interfaces.MyRecyclerViewOnItemClickListener;
import com.homeworkreminder.interfaces.MyRecyclerViewOnItemLongPressListener;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 未完成作业界面的Fragment
 */
public class UndoHomeworkFragment extends Fragment {
    private RecyclerView myRecyclerView;
    private List<HomeData> homeDataList;
    private View view;
    private UndoDataRecyclerViewAdapter undoDataRecyclerViewAdapter;
    private SwipeRefreshLayout homework_undo_SwipeRefreshLayout;

    //private int headImg;
    private String nickname;
    private String date;
    private String title;
    private String content;
    private String tag;

    private MyApplication app;

    //请求的url
    private String url = "http://nibuguai.cn/index.php/index/homework/api_queryHomeworkByUidDoWith?t_user_id=";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_undo_homework, container, false);
        return view;

    }

    private void initView() {
        myRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_homework_undo_recyclerView);
        homework_undo_SwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.homework_undo_SwipeRefreshLayout);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        //initData();

        int uid = getUid();

        //拼接url
        url = url + uid;

        //addRecyclerView();
        useVolleyGET(url);

        //下拉刷新的监听事件
        homework_undo_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                homeDataList.clear();
//                initData();
                useVolleyGET(url);
            }
        });

    }

    private int getUid() {
        app = new MyApplication();
        app = (MyApplication) getActivity().getApplication();

        return app.getUserInfo().getData().get(0).getId();
    }


    /**
     * 添加RecyclerView
     * @param recyclerView
     */
    public void addRecyclerView(RecyclerView recyclerView, final List<HomeworkData.DataBean> homeworkDataBeanList){
        //设置RecyclerView的布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));

        //初始化适配器
        undoDataRecyclerViewAdapter = new UndoDataRecyclerViewAdapter(getActivity(), homeworkDataBeanList);
        //homeDataRecyclerViewAdapter = new HomeDataRecyclerViewAdapter(getActivity(), homeworkDataList);

        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置适配器
        recyclerView.setAdapter(undoDataRecyclerViewAdapter);

        //设置监听器
        undoDataRecyclerViewAdapter.setMyRecyclerViewOnItemClickListener(new MyRecyclerViewOnItemClickListener() {
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
                //getActivity().finish();

                //*/

            }
        });


        //设置长按事件监听器
        undoDataRecyclerViewAdapter.setMyRecyclerViewOnItemLongPressListener(new MyRecyclerViewOnItemLongPressListener() {
            @Override
            public void onItemLongPressListener(View view, final int position) {
                //Toast.makeText(getContext(), homeDataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                final String[] items = {"添加到已完成列表","删除"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setTitle("选择操作");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();

                        //添加item：添加到已完成列表
//                        if (items[which].equals("添加到已完成列表")){
//                            undoDataRecyclerViewAdapter.addData(position, homeworkDataBeanList.get(position));
//                        }


                        //删除item
                        if (items[which].equals("删除")){
                            undoDataRecyclerViewAdapter.removeData(position);
                        }


                    }
                });
                builder.create().show();
            }
        });
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



    private List<HomeworkData.DataBean> homeworkDataBeanList;
    private HomeworkData homeworkData;


    private String TAG = "volley";
    private String result;      //保存请求的结果
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

                        Log.d(TAG, "onResponse: response \n" + response);

                        //使用Gson解析json数据
                        parseJsonByGson(result);


                        //请求成功后addRecyclerView显示数据
                        homeworkDataBeanList = homeworkData.getData();
                        Collections.reverse(homeworkDataBeanList);

                        for (int i = 0; i < homeworkDataBeanList.size(); i++) {
                            System.out.println("数据" + i + ": " + homeworkDataBeanList.get(i).getTitle());
                        }


                        //Collections.reverse(homeworkDataBeanList);
                        
                        addRecyclerView(myRecyclerView, homeworkDataBeanList);

                        //停止刷新
                        homework_undo_SwipeRefreshLayout.setRefreshing(false);


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
