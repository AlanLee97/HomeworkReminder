package com.homeworkreminder.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.view.WindowManager;

import com.homeworkreminder.activity.NewHomeworkActivity;
import com.homeworkreminder.service.MusicService;

/**
 * 闹钟接收器
 * 收到广播后处理：显示弹窗，播放闹铃
 */
public class ClockReceiver extends BroadcastReceiver {
    private Vibrator vibrator;  //振动器

    /**
     * 接收广播
     * @param context 上下文
     * @param intent 意图
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{500, 1000, 500, 2000}, 0);

        //接收广播
        Intent homeworkIntent = new Intent(context, NewHomeworkActivity.class);
        //String content = homeworkIntent.getStringExtra("newReminder");

        //开启闹铃
        final Intent musicIntent = new Intent(context, MusicService.class);
        if (!MusicService.isPlay){
            //开启音乐服务
            context.startService(musicIntent);
        }else {
            //停止音乐服务
            context.stopService(musicIntent);
        }

        //闹铃弹窗提醒
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("闹钟");
        alertDialog.setMessage("时间到啦");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //关闭闹钟
                vibrator.cancel();
                //停止音乐服务
                context.stopService(musicIntent);
            }
        });

        //设置弹窗
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {   //Android 8.0 以上的弹窗类型
            alertDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            alertDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }

        //显示弹窗
        alertDialog.show();

        //开启服务
        context.startService(homeworkIntent);
    }
}
