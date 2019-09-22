package com.homeworkreminder.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.homeworkreminder.R;
import com.homeworkreminder.view.TomatoView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class TomatoClockActivity extends AppCompatActivity {
    TomatoView clockView;
    private QMUIRoundButton btnStart;
    private QMUIRoundButton btnStop;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato_clock);

        if(Build.VERSION.SDK_INT >= 24) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


        initView();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockView.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockView.stop();
            }
        });
    }

    private void initView() {
        clockView = findViewById(R.id.clockView);
        btnStart = (QMUIRoundButton) findViewById(R.id.btn_start);
        btnStop = (QMUIRoundButton) findViewById(R.id.btn_stop);
    }
}
