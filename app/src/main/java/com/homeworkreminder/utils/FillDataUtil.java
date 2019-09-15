//package com.homeworkreminder.utils;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Adapter;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.gson.Gson;
//import com.homeworkreminder.activity.HomeworkDetailActivity;
//import com.homeworkreminder.adapter.UndoDataRecyclerViewAdapter;
//import com.homeworkreminder.entity.HomeworkData;
//import com.homeworkreminder.interfaces.MyRecyclerViewOnItemClickListener;
//
//import java.util.List;
//
//public class FillDataUtil {
//    private Context context;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView recyclerView;
//
//
//
//
//    //private int headImg;
//    private String nickname;
//    private String date;
//    private String title;
//    private String content;
//    private String tag;
//    private HomeworkData homeworkData;
//
//    FillDataUtil(Context context, RecyclerView recyclerView, Adapter adapter){
//        this.context = context;
//        this.recyclerView = recyclerView;
//        this.adapter = adapter;
//    }
//
//
//
//
//
//
//
//    private String TAG = "volley";
//    private String result;      //保存请求的结果
//    /**
//     * get请求
//     * @param url url
//     */
//    public void useVolleyGET(String url, final RecyclerView recyclerView) {
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        final StringRequest stringRequest = new StringRequest(
//                //参数1：请求的url
//                url,
//                //参数2：请求成功的监听事件
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        result = response;
//
//                        Log.d(TAG, "onResponse: response \n" + response);
//
//                        //使用Gson解析json数据
//                        parseJsonByGson(result);
//
//
//                        //请求成功后addRecyclerView显示数据
//                        //homeworkDataBeanList = homeworkData.getData();
//                        addRecyclerView(recyclerView, adapter);
//
//                        //停止刷新
//                        //homework_undo_SwipeRefreshLayout.setRefreshing(false);
//
//
//                        //Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                //参数3：请求失败的监听事件
//                new com.android.volley.Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //loginReturnResult.setText("请求失败");
//                        Log.d(TAG, "onErrorResponse: " + error);
//                        Toast.makeText(context.getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        //3、将请求添加到队列
//        requestQueue.add(stringRequest);
//    }
//
//    /**
//     * 使用Gson解析json数据，这个比较简单，以下是使用步骤(3步，其实前2步就算拿到数据了)
//     * @param json 要解析的json
//     */
//    public void parseJsonByGson(String json){
//        //1、创建Gson对象
//        Gson gson = new Gson();
//        //2、调用Gson的fromJson()方法，将json转成javaBean，将要显示的数据封装到这个javaBean
//        //fromJson(参数1，参数2)方法 参数1：需要解析的json 参数2：一个javaBean，接收需要封装的数据（总数据的javaBean）
//        homeworkData = gson.fromJson(result, HomeworkData.class);
//
//    }
//
//
//
//    /**
//     * 添加RecyclerView
//     * @param recyclerView
//     */
//    public <T> addRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, List<T> dataList){
//        //设置RecyclerView的布局管理器
//        recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayout.VERTICAL,false));
//
//        //初始化适配器
//        adapter = new UndoDataRecyclerViewAdapter(context.getApplicationContext(), dataList);
//        //homeDataRecyclerViewAdapter = new HomeDataRecyclerViewAdapter(getActivity(), homeworkDataList);
//
//        //设置动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        //设置适配器
//        recyclerView.setAdapter(adapter);
//
//        //设置监听器
//        /*
//        adapter.setMyRecyclerViewOnItemClickListener(new MyRecyclerViewOnItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                System.out.println("position = " + position);
//
//                //通过Bundle向Activity传递数据
//                //headImg = homeDataList.get(position).getImg();
//                //nickname = HomeworkDataBeanList.get(position).getNickname();
//                //*
//                date = homeworkDataBeanList.get(position).getDate();
//                title = homeworkDataBeanList.get(position).getTitle();
//                content = homeworkDataBeanList.get(position).getContent();
//                tag = homeworkDataBeanList.get(position).getTag();
//
//                //callbackValueToActivity.sendValue(nickname);
//                Intent intent = new Intent(context.getApplicationContext(), HomeworkDetailActivity.class);
//                Bundle bundle = new Bundle();
//
//                convertDataToActivity(bundle);
//
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//
//                //
//            }
//        });
//
//         */
//
//
//        return null;
//    }
//
//
//    /**
//     * 通过Bundle向Activity中传值
//     */
//    public void convertDataToActivity(Bundle bundle){
//        //bundle.putInt("headImg",headImg);
//        //bundle.putCharSequence("nickname", nickname);
//        bundle.putCharSequence("title", title);
//        bundle.putCharSequence("content", content);
//        bundle.putCharSequence("tag", tag);
//        bundle.putCharSequence("date", date);
//
//    }
//
//
//
//}
