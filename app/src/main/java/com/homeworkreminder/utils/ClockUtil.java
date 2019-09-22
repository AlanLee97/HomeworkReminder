package com.homeworkreminder.utils;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.homeworkreminder.activity.NewHomeworkActivity;
import com.homeworkreminder.receiver.ClockReceiver;

import java.util.Calendar;

public class ClockUtil {
//    private static Calendar calendar;
    private Context context;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private static int mHour;
    private static int mMin;
    private static long triggerTimeMillis;


    /**
     * 选择日期
     */
    public static void chooseDate(final TextView textView, final Calendar calendar, final Context context){

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar = Calendar.getInstance();

                //使用DatePickerDialog的构造方法创建DatePickerDialog日期选择器
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener(){

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;

                                String pickedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                textView.setText(pickedDate);
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
     * 选择时间
     */
    public static void chooseTime(final TextView textView, final Calendar calendar, final Context context){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String pickedTime = hourOfDay + ":" + minute;
                                textView.setText(pickedTime);

                                mHour = hourOfDay;
                                mMin = minute;
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
     * 开启闹钟
     */
    public static void startAlarm(Context context, Class receiverClass,  Calendar calendar) {
        long tmpTime = TimeUtil.transformateFromDateToMilis(mYear, mMonth, mDay, mHour, mMin);
        triggerTimeMillis = tmpTime - calendar.getTimeInMillis();

        Intent intent = new Intent(context, receiverClass);
        //intent.putExtra("newReminder", "newReminder的值");

        //获取广播
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        //设定闹钟
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + triggerTimeMillis, pendingIntent);

        Log.d("setTime", "triggerAtMillis: " + (calendar.getTimeInMillis() + triggerTimeMillis));
    }
}
