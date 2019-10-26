package com.homeworkreminder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.entity.Count;
import com.homeworkreminder.entity.Weather;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CountActivity extends AppCompatActivity {
    MyApplication app = new MyApplication();

    private TextView indexTv1;
    private TextView indexTv2;
    private TextView indexTv3;
    private TextView indexTv4;
    private TextView tvDone;
    private TextView tvUndo;
    private TextView tvTip;

    String wendu;
    String type;

    String result = "";
    Handler handler;

    //get请求的url
    String weatherUrl = "https://www.apiopen.top/weatherApi?city=东莞";
    String countHwUrl = "http://www.nibuguai.cn/index.php/index/homework/api_countHomeworkDoneState?uid=";
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        initView();

        showDate();

        getData();

        //showWeather();

        showCount();

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        indexTv3.setText(bundle.getString("type"));
        indexTv4.setText(bundle.getString("wendu"));
    }

    private void initView() {

        indexTv1 = (TextView) findViewById(R.id.index_tv_1);
        indexTv2 = (TextView) findViewById(R.id.index_tv_2);
        indexTv3 = (TextView) findViewById(R.id.index_tv_3);
        indexTv4 = (TextView) findViewById(R.id.index_tv_4);
        tvDone = (TextView) findViewById(R.id.tv_done);
        tvUndo = (TextView) findViewById(R.id.tv_undo);
        tvTip = (TextView) findViewById(R.id.tv_tip);


    }

    private void showDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Date date = new Date();
        System.out.println("========= 日期：" + sdf.format(date));
        indexTv2.setText(sdf.format(date));
    }

/*
    private void showWeather() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //将解析的数据显示到TextView中
                Weather weather = MyGson.parseJsonByGson(result, Weather.class);
                String wendu = weather.getData().getWendu();
                String type = weather.getData().getForecast().get(0).getType();
                System.out.println("======== 天气类型：" + type);
                System.out.println("======== 温度：" + wendu);
                indexTv3.setText(type);
                indexTv4.setText(wendu);
            }
        };
        useOkHttp3_AsyncGET(weatherUrl);
    }
*/


    private void showCount(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //将解析的数据显示到TextView中
                Count count = MyGson.parseJsonByGson(result, Count.class);
                int undo = count.getData().getUndo();
                int done = count.getData().getDone();
                System.out.println("======== 未完成：" + undo);
                System.out.println("======== 已完成：" + done);
                tvUndo.setText(undo + "");
                tvDone.setText(done + "");

                if (undo > 0){
                    tvTip.setText("加油喔~，赶紧把作业消灭掉~");
                }else {
                    tvTip.setText("太棒啦，作业都做完了！");
                }

            }
        };

        uid = getUid();
        countHwUrl = countHwUrl + uid;
        useOkHttp3_AsyncGET(countHwUrl);
    }

    /**
     * 使用OkHttp3请求数据--异步GET请求
     * @param url 请求的url
     */
    private void useOkHttp3_AsyncGET(String url) {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.创建请求对象
        Request request = new Request.Builder().url(url).get().build();

        //3.创建Call对象,将请求对象request作为参数
        Call call = okHttpClient.newCall(request);

        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback(){
            //请求成功时的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(CountActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
            }
            //请求失败时的回调方法
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //异步请求需要开启子线程，使用Handler+Message的方法将数据更新到主线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获取响应的数据
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //获取消息
                        Message message = handler.obtainMessage();

                        //发送消息
                        handler.sendMessage(message);

                    }
                }).start();

                /*
                //或者使用 runOnUiThread()方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                 */
            }
        });
    }



    private int getUid() {
        app = (MyApplication) getApplication();
        return app.getUserInfo().getData().get(0).getId();
    }

}
