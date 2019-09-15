package com.t.testalarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NewAlarmActivity extends AppCompatActivity {
    private TextView tvChooseDate;
    private TextView tvChooseTime;
    private Button btnAlarm;

    private Calendar calendar;

    private long triggerTimeMillis;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
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

        chooseDate();

        chooseTime();

        startAlarm();



    }

    private void startAlarm() {
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerTimeMillis = calendar.getTimeInMillis();

                Intent intent = new Intent(NewAlarmActivity.this, ReminderActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        NewAlarmActivity.this,
                        0,
                        intent,
                        0);

                //设定闹钟
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeMillis, pendingIntent);
            }
        });
    }

    /**
     * 选择时间
     */
    private void chooseTime() {
        tvChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NewAlarmActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String pickedTime = "时间：" + hourOfDay + ":" + minute;
                                tvChooseTime.setText(pickedTime);
                                Toast.makeText(NewAlarmActivity.this, "选择的时间" + pickedTime, Toast.LENGTH_SHORT).show();
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
                calendar = Calendar.getInstance();

                //使用DatePickerDialog的构造方法创建DatePickerDialog日期选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewAlarmActivity.this,
                        new DatePickerDialog.OnDateSetListener(){

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String pickedDate = "" + year + "年" + month + "月" + dayOfMonth + "日";
                                tvChooseDate.setText(pickedDate);
                                Toast.makeText(NewAlarmActivity.this, "选择了日期" + pickedDate, Toast.LENGTH_SHORT).show();
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

    private void initView() {
        tvChooseDate = (TextView) findViewById(R.id.tv_chooseDate);
        tvChooseTime = (TextView) findViewById(R.id.tv_chooseTime);


        btnAlarm = (Button) findViewById(R.id.btn_alarm);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
