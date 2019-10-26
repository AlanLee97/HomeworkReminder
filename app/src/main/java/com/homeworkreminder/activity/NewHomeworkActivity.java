package com.homeworkreminder.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.homeworkreminder.utils.ClockUtil;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.TimeUtil;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
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

    private EditText etHomeworkCourse;
    private EditText etHomeworkDeadtime;




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
    private String course;
    private String deadtime;
    private int uid;
    private int t_hid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_homework);

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //初始化视图控件
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance();

        //选择日期
        //chooseDate();
        ClockUtil.chooseDate(tvChooseDate, calendar, NewHomeworkActivity.this);

        //选择时间
        //chooseTime();
        ClockUtil.chooseTime(tvChooseTime, calendar, NewHomeworkActivity.this);


        //悬浮按钮，用于创建作业提醒和闹钟
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启闹钟
                //startAlarm();

                ClockUtil.startAlarm(NewHomeworkActivity.this, ClockReceiver.class,calendar);



                /**
                 * 拿到view中的数据
                 **/
                getViewData();

                if (title.equals("") ||
                content.equals("") ||
                course.equals("") ||
                deadtime.equals("") ||
                remind_date.equals("") ||
                remind_time.equals("")){
                    Toast.makeText(NewHomeworkActivity.this, "请填写相应的表单内容", Toast.LENGTH_SHORT).show();
                }else {

                    uid = getUid();
                    url = url + "title=" + title
                            + "&content=" + content
                            + "&course=" + course
                            + "&deadtime=" + deadtime
                            + "&remind_date=" + remind_date
                            + "&remind_time=" + remind_time
                            + "&tag=" + tag
                            + "&uid=" + uid;


                    //将数据传到服务器
                    useVolleyGET(url);

                    Snackbar.make(view, "创建成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //跳转到首页
                    startActivity(new Intent(NewHomeworkActivity.this, MainActivity.class));

                    finish();
                }

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

        etHomeworkCourse = (EditText) findViewById(R.id.et_homework_course);
        etHomeworkDeadtime = (EditText) findViewById(R.id.et_homework_deadtime);

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
                                String pickedTime = hourOfDay + ":" + minute;
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

                                String pickedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
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
     * get请求
     * @param url
     */
    private void useVolleyGET(String url) {
        VolleyUtil.volleyGET(NewHomeworkActivity.this, url, "105",
                new VolleyInterface(
                        NewHomeworkActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener
                ) {
                    @Override
                    public void onMySuccess(String result) {

                        //将请求的原始json数据放到EditText中
                        Toast.makeText(NewHomeworkActivity.this, "请求成功 result:" + result, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: 请求结果" + result);

                        //VolleyUtil volleyUtil = new VolleyUtil(NewHomeworkActivity.this);
                        HomeworkID homeworkID = MyGson.parseJsonByGson(result, HomeworkID.class);
                        t_hid = homeworkID.getData().get(0).getT_hid();
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                });
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
        course = etHomeworkCourse.getText().toString();
        deadtime = etHomeworkDeadtime.getText().toString();

    }
}
