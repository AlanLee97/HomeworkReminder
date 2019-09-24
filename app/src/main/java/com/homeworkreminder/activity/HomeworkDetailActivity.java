package com.homeworkreminder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.homeworkreminder.R;
import com.homeworkreminder.interfaces.CallbackValueToActivity;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;

public class HomeworkDetailActivity extends AppCompatActivity {
    private ImageView ivDetailHead;
    private TextView tvDetailNickname;
    private TextView tvDetailDate;
    private TextView tvDetailTitle;
    private TextView tvDetailContent;
    private TextView tvDetailTag;
    private TextView tvDetailCourse;
    private TextView tvDetailDeadtime;

    private MyApplication app;

    //设置为未做的请求地址
    private String url = "http://nibuguai.cn/index.php/index/homework/api_addHomeworkDoWith?";

    private int uid;
    private String t_hid;
    private String title;
    private String content;
    private String course;
    private String deadtime;
    private String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        app = (MyApplication) getApplication();




        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //ivDetailHead.setImageResource(headImg);
        tvDetailNickname.setText(bundle.getCharSequence("nickname"));
        tvDetailDate.setText(bundle.getCharSequence("date"));
        tvDetailTitle.setText(bundle.getCharSequence("title"));
        tvDetailContent.setText(bundle.getCharSequence("content"));
        tvDetailTag.setText(bundle.getCharSequence("tag"));
        tvDetailCourse.setText(bundle.getCharSequence("course"));
        tvDetailDeadtime.setText(bundle.getCharSequence("deadtime"));
        t_hid = (String) bundle.getCharSequence("hid");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                boolean isLogin = false;

                if (app.checkState(isLogin)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeworkDetailActivity.this);
                    builder.setTitle("作业共享提醒");
                    builder.setMessage("确认添加到我的作业列表吗？");
                    builder.setNegativeButton("不添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            useVolleyGET();
                            Toast.makeText(HomeworkDetailActivity.this, "成功添加到作业列表", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.create().show();

                }else {
                    Toast.makeText(HomeworkDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeworkDetailActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void initView() {
        ivDetailHead = (ImageView) findViewById(R.id.iv_detail_head);
        tvDetailNickname = (TextView) findViewById(R.id.tv_detail_nickname);
        tvDetailDate = (TextView) findViewById(R.id.tv_detail_date);
        tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvDetailContent = (TextView) findViewById(R.id.tv_detail_content);
        tvDetailTag = (TextView) findViewById(R.id.tv_detail_tag);
        tvDetailCourse = (TextView) findViewById(R.id.tv_detail_course);
        tvDetailDeadtime = (TextView) findViewById(R.id.tv_detail_deadtime);
    }

    public void getData(){
        title = tvDetailTitle.getText().toString();
        content = tvDetailContent.getText().toString();
        course = tvDetailCourse.getText().toString();
        deadtime = tvDetailDeadtime.getText().toString();
        tag = tvDetailTag.getText().toString();
    }

    public void useVolleyGET(){
        getData();

        uid = app.getUserInfo().getData().get(0).getId();


        url = url + "title=" + title
                + "&content=" + content
                + "&course=" + course
                + "&deadtime=" + deadtime
                + "&remind_date=" + ""
                + "&remind_time=" + ""
                + "&tag=" + tag
                + "&uid=" + uid;

        VolleyUtil.volleyGET(HomeworkDetailActivity.this, url, "102",
                new VolleyInterface(
                        HomeworkDetailActivity.this,
                        VolleyInterface.mListener,
                        VolleyInterface.mErrorListener
                ) {
                    @Override
                    public void onMySuccess(String result) {
                        Toast.makeText(HomeworkDetailActivity.this, "成功添加到我的作业列表", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        Toast.makeText(HomeworkDetailActivity.this, "添加到我的作业列表 失败", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


}
