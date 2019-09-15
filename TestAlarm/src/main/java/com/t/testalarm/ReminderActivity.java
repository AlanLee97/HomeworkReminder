package com.t.testalarm;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("闹钟");
        alertDialog.setMessage("闹钟时间到");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );
        alertDialog.show();
    }
}
