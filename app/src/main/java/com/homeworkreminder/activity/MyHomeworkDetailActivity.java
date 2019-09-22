package com.homeworkreminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.homeworkreminder.R;
import com.homeworkreminder.utils.MyApplication;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

public class MyHomeworkDetailActivity extends AppCompatActivity {

    private QMUIRadiusImageView ivMyDetailHead;
    private TextView tvMyDetailNickname;
    private TextView tvMyDetailDate;
    private TextView tvMyDetailTitle;
    private TextView tvMyDetailContent;
    private TextView tvMyDetailDeadtime;
    private TextView tvMyDetailTag;
    private TextView tvMyDetailAlarmtime;
    private TextView tvDetailCourse;





    //private int headImg;
    private String nickname;
    private String date;
    private String title;
    private String content;
    private String course;
    private String deadtime;
    private String tag;
    private String hid;

    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_homework_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //ivDetailHead.setImageResource(headImg);
        tvMyDetailNickname.setText(bundle.getCharSequence("nickname"));
        tvMyDetailDate.setText(bundle.getCharSequence("date"));
        tvMyDetailTitle.setText(bundle.getCharSequence("title"));
        tvMyDetailContent.setText(bundle.getCharSequence("content"));
        tvMyDetailTag.setText(bundle.getCharSequence("tag"));
        tvDetailCourse.setText(bundle.getCharSequence("course"));
        tvMyDetailDeadtime.setText(bundle.getCharSequence("deadtime"));
        hid = bundle.getCharSequence("hid").toString();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
                Intent intent2 = new Intent(MyHomeworkDetailActivity.this, EditHomeworkActivity.class);

                Bundle bundle = new Bundle();


                convertDataToActivity(bundle);

                intent2.putExtras(bundle);


                startActivity(intent2);

            }
        });


    }

    private void initView() {


        ivMyDetailHead = (QMUIRadiusImageView) findViewById(R.id.iv_my_detail_head);
        tvMyDetailNickname = (TextView) findViewById(R.id.tv_my_detail_nickname);
        tvMyDetailDate = (TextView) findViewById(R.id.tv_my_detail_date);
        tvMyDetailTitle = (TextView) findViewById(R.id.tv_my_detail_title);
        tvMyDetailContent = (TextView) findViewById(R.id.tv_my_detail_content);
        tvMyDetailDeadtime = (TextView) findViewById(R.id.tv_my_detail_deadtime);
        tvMyDetailTag = (TextView) findViewById(R.id.tv_my_detail_tag);
        tvMyDetailAlarmtime = (TextView) findViewById(R.id.tv_my_detail_alarmtime);
        tvDetailCourse = (TextView) findViewById(R.id.tv_detail_course);

    }



    /**
     * 通过Bundle向Activity中传值
     */
    public void convertDataToActivity(Bundle bundle){

        getData();

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


    public void getData(){
        nickname = tvMyDetailNickname.getText().toString();
        date = tvMyDetailDate.getText().toString();
        title = tvMyDetailTitle.getText().toString();
        content = tvMyDetailContent.getText().toString();
        course = tvDetailCourse.getText().toString();
        deadtime = tvMyDetailDeadtime.getText().toString();
        tag = tvMyDetailTag.getText().toString();


    }
}
