package com.homeworkreminder.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.homeworkreminder.R;
import com.homeworkreminder.entity.HomeworkID;
import com.homeworkreminder.receiver.ClockReceiver;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.TimeUtil;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建作业提醒类
 */
public class NewHomeworkActivity extends AppCompatActivity {
    MyApplication app = new MyApplication();

    private EditText etHomeworkTitle;   //标题
    private EditText etHomeworkContent; //内容
    private EditText etHomeworkTag;




    private TextView tvChooseDate;      //选择日期
    private TextView tvChooseTime;      //选择时间

    private Calendar calendar;          //日历类

    private long triggerTimeMillis;     //触发闹钟时间

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMin;
    private int mSec;



    Handler handler;

    String TAG = "NewHomeworkActivity";  //打印日志用的标签

    //保存请求url得到的结果
    String result = "空";

    //请求的url
    String url = "http://nibuguai.cn/index.php/index/homework/api_addHomeworkDoWith?";
    private String title;
    private String content;
    private String remind_time;
    private String remind_date;
    private String tag;
    private int uid;
    private int t_hid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_homework);

        //初始化视图控件
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance();

        //选择日期
        chooseDate();

        //选择时间
        chooseTime();

        //悬浮按钮，用于创建作业提醒和闹钟
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启闹钟
                startAlarm();
                //提示消息
//                Snackbar.make(view, "创建闹钟成功", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


                /**
                 * 拿到view中的数据
                 **/
                getViewData();

                uid = getUid();
                url = url + "title=" + title
                        + "&content=" + content
                        + "&remind_date=" + remind_date
                        + "&remind_time=" + remind_time
                        + "&tag=" + tag
                        + "&uid=" + uid;



                //将数据传到服务器
                useVolleyGET(url);

//                String url2 = "http://www.nibuguai.cn/index.php/index/homework/api_setDoneHomework?";
//                url2 = url2 + "t_user_id=" + uid;
//                url2 = url2 + "&t_hid=" + t_hid;
//                useVolleyGET2(url2);



                Snackbar.make(view, "创建成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




                //跳转到首页
                startActivity(new Intent(NewHomeworkActivity.this, MainActivity.class));

                finish();
            }
        });



        //useOkHttp3_POST_KV(url);

    }

    private int getUid() {
        app = (MyApplication) getApplication();
        return app.getUserInfo().getData().get(0).getId();
    }

    /**
     * 开启闹钟
     */
    private void startAlarm() {
        long tmpTime = TimeUtil.transformateFromDateToMilis(mYear, mMonth, mDay, mHour, mMin);
        triggerTimeMillis = tmpTime - calendar.getTimeInMillis();

        Log.d("setTime", "tmpTime:" + tmpTime);
        Log.d("setTime", "currentTime:" + calendar.getTimeInMillis());

        Log.d("setTime", "triggerTimeMillis:" + triggerTimeMillis);

        Intent intent = new Intent(NewHomeworkActivity.this, ClockReceiver.class);
        //intent.putExtra("newReminder", "newReminder的值");

        //获取广播
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                NewHomeworkActivity.this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                );

        //设定闹钟
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + triggerTimeMillis, pendingIntent);

        Log.d("setTime", "triggerAtMillis: " + (calendar.getTimeInMillis() + triggerTimeMillis));
    }


    /**
     * 初始化视图
     */
    private void initView() {
        tvChooseDate = (TextView) findViewById(R.id.tv_chooseDate);
        tvChooseTime = (TextView) findViewById(R.id.tv_chooseTime);
        etHomeworkTag = (EditText) findViewById(R.id.et_homework_tag);

        etHomeworkTitle = (EditText) findViewById(R.id.et_homework_title);
        etHomeworkContent = (EditText) findViewById(R.id.et_homework_content);

    }


    /**
     * 选择时间
     */
    private void chooseTime() {
        tvChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NewHomeworkActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String pickedTime = "时间：" + hourOfDay + ":" + minute;
                                tvChooseTime.setText(pickedTime);

                                mHour = hourOfDay;
                                mMin = minute;

                                Log.d("setTime", "mHour: " + mHour);
                                Log.d("setTime", "mMin: " + mMin);


                                Toast.makeText(NewHomeworkActivity.this, "选择的时间" + pickedTime, Toast.LENGTH_SHORT).show();
                            }
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                );

                timePickerDialog.show();
            }
        });
    }

    /**
     * 选择日期
     */
    private void chooseDate() {
        tvChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar = Calendar.getInstance();

                //使用DatePickerDialog的构造方法创建DatePickerDialog日期选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewHomeworkActivity.this,
                        new DatePickerDialog.OnDateSetListener(){

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;

                                Log.d("setTime", "mYear: " + mYear);
                                Log.d("setTime", "mMonth: " + mMonth);
                                Log.d("setTime", "mDay: " + mDay);

                                String pickedDate = "" + year + "年" + month + "月" + dayOfMonth + "日";
                                tvChooseDate.setText(pickedDate);
                                Toast.makeText(NewHomeworkActivity.this, "选择了日期" + pickedDate, Toast.LENGTH_SHORT).show();
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                //设置最小的日期为 当前日期
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                //显示选择日期的读对话框
                datePickerDialog.show();

            }
        });
    }


    /**
     * 使用OkHttp3请求数据--POST请求,提交键值对
     * @param url 请求的url
     */
    private void useOkHttp3_POST_KV(String url) {
        /**
         * 拿view中的数据
         **/
        String title = etHomeworkTitle.getText().toString();
        String content = etHomeworkContent.getText().toString();
        String remind_time = tvChooseTime.getText().toString();
        String remind_date = tvChooseDate.getText().toString();

        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.1 准备请求体（这里放请求的参数）
        RequestBody requestBody = new FormBody.Builder()
                .add("title",title)
                .add("content",content)
                .add("remind_time",remind_time)
                .add("remind_date",remind_date)
                .build();

        //2.2 创建请求对象
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //3.创建Call对象,将请求对象request作为参数
        Call call = okHttpClient.newCall(request);

        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback(){
            //请求成功时的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(NewHomeworkActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
            }
            //请求失败时的回调方法
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //*
                //异步请求需要开启子线程，使用Handler+Message的方法将数据更新到主线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获取响应的数据,注意response.body().string()的string()不能调用两次以上，不然会保存
                            result = response.body().string();

                            Log.d("result", "result = " + result);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //获取消息
                        Message message = handler.obtainMessage();
                        //发送消息
                        handler.sendMessage(message);
                    }
                }).start();

                Toast.makeText(NewHomeworkActivity.this, "请求成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NewHomeworkActivity.this, MainActivity.class);
                startActivity(intent);

                //*/



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


    /**
     * post请求
     * @param url url
     */
    private void useVolleyPOST(String url) {
        //获取view中的数据
        getViewData();



        System.out.println("title:" + title);
        System.out.println("content:" + content);
        System.out.println("remind_time:" + remind_time);
        System.out.println("remind_date:" + remind_date);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求方法
                com.android.volley.Request.Method.POST,
                //参数2：请求的url
                url,
                //参数3：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;

                        Log.d(TAG, "发送数据到服务器：" + response);


                        //System.out.println("result:" + result);
                        Toast.makeText(NewHomeworkActivity.this, "newHomework请求成功" + result, Toast.LENGTH_SHORT).show();

                        //跳转到主界面
                        //Intent intent = new Intent(NewHomeworkActivity.this, MainActivity.class);
                        //startActivity(intent);



                    }
                },
                //参数4：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewHomeworkActivity.this, "newHomework请求失败", Toast.LENGTH_SHORT).show();
                    }
                }){//StringRequest方法体的左大括号

            //StringRequest方法体中重写getParams()方法
            //设置请求参数的方法
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求参数信息
                Map<String, String> map = new HashMap<>();
                map.put("title",title);
                map.put("content",content);
                map.put("remind_date",remind_date);
                map.put("remind_time",remind_time);
                return map;
            }

        };//StringRequest方法体的右大括号

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }


    /**
     * get请求
     * @param url
     */
    private void useVolleyGET(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求的url
                url,
                //参数2：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        //将请求的原始json数据放到EditText中
                        Toast.makeText(NewHomeworkActivity.this, "请求成功 result:" + result, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: 请求结果" + response);

                        VolleyUtil volleyUtil = new VolleyUtil(NewHomeworkActivity.this);
                        HomeworkID homeworkID = volleyUtil.parseJsonByGson(result, HomeworkID.class);
                        t_hid = homeworkID.getData().get(0).getT_hid();
                    }
                },
                //参数3：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewHomeworkActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }

    /**
     * get请求
     * @param url
     */
    private void useVolleyGET2(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求的url
                url,
                //参数2：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        //将请求的原始json数据放到EditText中
                        Toast.makeText(NewHomeworkActivity.this, "请求成功 result:" + result, Toast.LENGTH_SHORT).show();

                    }
                },
                //参数3：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewHomeworkActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }

    /**
     * 拿view中的数据
     **/
    public void getViewData(){

        title = etHomeworkTitle.getText().toString();
        content = etHomeworkContent.getText().toString();
        tag = etHomeworkTag.getText().toString();
        remind_time = tvChooseTime.getText().toString();
        remind_date = tvChooseDate.getText().toString();

    }
}
