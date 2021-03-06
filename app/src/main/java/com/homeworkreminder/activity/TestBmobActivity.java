package com.homeworkreminder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.entity.bean.Person;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.util.V;

public class TestBmobActivity extends BaseActivity {
    private Button addData;
    private Button queryData;
    private Button modifyData;
    private Button deleteData;
    private TextView resultData;
    private EditText etPhoneNum;
    private EditText etSMSCode;
    private Button btnSendSMS;
    private Button btnVerifySMS;

    

    private String objectId = "a5da9c322d";
    private String phoneNum;
    private String smsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bmob);



        initView();

        add();

        query();

        modify();

        delete();
        
        sms();


    }

    private void sms() {


        System.out.println("phoneNum: " + phoneNum);
        System.out.println("smsCode: " + smsCode);
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNum = etPhoneNum.getText().toString();


                BmobSMS.requestSMSCode(phoneNum, "", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null){
                            Toast.makeText(TestBmobActivity.this, "短信发送成功，smsId:" + integer, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TestBmobActivity.this, "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        btnVerifySMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = etPhoneNum.getText().toString();
                smsCode = etSMSCode.getText().toString();

                BmobSMS.verifySmsCode(phoneNum, smsCode, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(TestBmobActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            resultData.setText("短信验证码：" + smsCode);
                        }else {
                            Toast.makeText(TestBmobActivity.this, "验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void delete() {
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person p = new Person();
                p.setObjectId(objectId);
                p.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(TestBmobActivity.this, "删除成功：" + p.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TestBmobActivity.this, "删除失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void modify() {
        modifyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person person = new Person();
                person.setAge(18);
                person.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Toast.makeText(TestBmobActivity.this, "数据更新成功：" + person.getCreatedAt(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TestBmobActivity.this, "更新失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void query() {
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Person> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(objectId, new QueryListener<Person>() {
                    @Override
                    public void done(Person person, BmobException e) {
                        if (e == null){
                            //objectId = getObjectId(person);
                            Toast.makeText(TestBmobActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                            resultData.setText(person.getName() + "\n" + person.getAge() + "\n" + objectId);

                        }else {
                            Toast.makeText(TestBmobActivity.this, "查询失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void add() {
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("AlanLee");
                person.setAge(22);
                person.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(TestBmobActivity.this, "数据添加成功：" + s, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TestBmobActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

//    public String getObjectId(Person person){
//        return person.getObjectId();
//    }

    private void initView() {
        addData = (Button) findViewById(R.id.addData);
        queryData = (Button) findViewById(R.id.queryData);
        modifyData = (Button) findViewById(R.id.modifyData);
        deleteData = (Button) findViewById(R.id.deleteData);
        resultData = (TextView) findViewById(R.id.resultData);
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        etSMSCode = (EditText) findViewById(R.id.et_sms_code);
        btnSendSMS = (Button) findViewById(R.id.sendSMS);
        btnVerifySMS = (Button) findViewById(R.id.verifySMS);

    }
}
