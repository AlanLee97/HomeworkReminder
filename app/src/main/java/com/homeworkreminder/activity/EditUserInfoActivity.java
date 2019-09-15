package com.homeworkreminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.homeworkreminder.R;

public class EditUserInfoActivity extends AppCompatActivity {

    private EditText etEditNickname;
    private EditText etEditSchool;
    private TextView etEditMajor;
    private EditText etEditClass;
    private Button btnEditUserinfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);


        initView();

    }

    private void initView() {
        etEditNickname = (EditText) findViewById(R.id.et_edit_nickname);
        etEditSchool = (EditText) findViewById(R.id.et_edit_school);
        etEditMajor = (TextView) findViewById(R.id.et_edit_major);
        etEditClass = (EditText) findViewById(R.id.et_edit_class);
        btnEditUserinfo = (Button) findViewById(R.id.btn_edit_userinfo);
    }
}
