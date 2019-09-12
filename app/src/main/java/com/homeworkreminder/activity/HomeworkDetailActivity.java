package com.homeworkreminder.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.homeworkreminder.R;
import com.homeworkreminder.interfaces.CallbackValueToActivity;

public class HomeworkDetailActivity extends AppCompatActivity {
    private ImageView ivDetailHead;
    private TextView tvDetailNickname;
    private TextView tvDetailDate;
    private TextView tvDetailTitle;
    private TextView tvDetailContent;
    private TextView tvDetailTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //ivDetailHead.setImageResource(headImg);
        //tvDetailNickname.setText(bundle.getCharSequence("nickname"));
        tvDetailDate.setText(bundle.getCharSequence("date"));
        tvDetailTitle.setText(bundle.getCharSequence("title"));
        tvDetailContent.setText(bundle.getCharSequence("content"));
        tvDetailTag.setText(bundle.getCharSequence("tag"));


    }

    private void initView() {
        ivDetailHead = (ImageView) findViewById(R.id.iv_detail_head);
        tvDetailNickname = (TextView) findViewById(R.id.tv_detail_nickname);
        tvDetailDate = (TextView) findViewById(R.id.tv_detail_date);
        tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvDetailContent = (TextView) findViewById(R.id.tv_detail_content);
        tvDetailTag = (TextView) findViewById(R.id.tv_detail_tag);

    }


}
