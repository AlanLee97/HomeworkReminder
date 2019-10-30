package com.homeworkreminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.homeworkreminder.R;
import com.homeworkreminder.receiver.ClockReceiver;
import com.homeworkreminder.utils.ClockUtil;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;

import java.util.Calendar;

/**
 * 编辑作业的Activity
 */
public class EditHomeworkActivity extends AppCompatActivity {
    private EditText etEditHomeworkTitle;
    private EditText etEditHomeworkContent;
    private EditText etEditHomeworkDeadtime;
    private EditText etEditHomeworkTag;
    private TextView tvEditChooseDate;
    private TextView tvEditChooseTime;
    private EditText etEditHomeworkCourse;
    private String hid;
    private MyApplication app;
    private String url = "http://www.nibuguai.cn/index.php/index/homework/api_editHomeworkDoWith?id=";
    private String title;
    private String content;
    private String course;
    private String deadtime;
    private String remind_date;
    private String remind_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_homework);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        setData();

        final Calendar calendar = Calendar.getInstance();
        ClockUtil.chooseDate(tvEditChooseDate, calendar, EditHomeworkActivity.this);
        ClockUtil.chooseTime(tvEditChooseTime, calendar, EditHomeworkActivity.this);

        app = (MyApplication) getApplication();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData();
                url = url + hid
                        + "&title=" + title
                        + "&course=" + course
                        + "&content=" + content
                        + "&deadtime=" + deadtime
                        + "&remind_date=" + remind_date
                        + "&remind_time=" + remind_time;

                VolleyUtil.volleyGET(EditHomeworkActivity.this, url, "106",
                        new VolleyInterface(
                                EditHomeworkActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener
                        ) {
                            @Override
                            public void onMySuccess(String result) {
                                Toast.makeText(EditHomeworkActivity.this, "修改作业成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onMyError(VolleyError error) {
                                Toast.makeText(EditHomeworkActivity.this, "修改作业失败", Toast.LENGTH_SHORT).show();

                            }
                        });

                ClockUtil.startAlarm(EditHomeworkActivity.this, ClockReceiver.class, calendar);
            }
        });
    }

    private void getData() {
        title = etEditHomeworkTitle.getText().toString();
        content = etEditHomeworkContent.getText().toString();
        course = etEditHomeworkCourse.getText().toString();
        deadtime = etEditHomeworkDeadtime.getText().toString();
        remind_date = tvEditChooseDate.getText().toString();
        remind_time = tvEditChooseTime.getText().toString();
    }

    /**
     * 接收其他Activity传过来的数据，并显示当当前Activity
     */
    private void setData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //ivDetailHead.setImageResource(headImg);

        etEditHomeworkTitle.setText(bundle.getCharSequence("title"));
        etEditHomeworkContent.setText(bundle.getCharSequence("content"));
        etEditHomeworkDeadtime.setText(bundle.getCharSequence("deadtime"));
        etEditHomeworkCourse.setText(bundle.getCharSequence("course"));
        etEditHomeworkTag.setText(bundle.getCharSequence("tag"));

        hid = bundle.getCharSequence("hid").toString();
    }

    private void initView() {
        etEditHomeworkTitle = (EditText) findViewById(R.id.et_edit_homework_title);
        etEditHomeworkContent = (EditText) findViewById(R.id.et_edit_homework_content);
        etEditHomeworkDeadtime = (EditText) findViewById(R.id.et_edit_homework_deadtime);
        etEditHomeworkTag = (EditText) findViewById(R.id.et_edit_homework_tag);
        tvEditChooseDate = (TextView) findViewById(R.id.tv_edit_chooseDate);
        tvEditChooseTime = (TextView) findViewById(R.id.tv_edit_chooseTime);
        etEditHomeworkCourse = (EditText) findViewById(R.id.et_edit_homework_course);

    }
}
