package com.homeworkreminder.utils.userUtil;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class CheckUserInfoUtil {
    private Context context;

    public CheckUserInfoUtil(Context context){
        this.context = context;
    }

    /**
     * 检查用户信息 start ==================================
     */
    byte[] buffer = null;
    /**
     * 读取用户信息
     * @return 读取保存的状态
     */
    public String readUserInfo(String whatStateFileName){
        String state = "false";
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(whatStateFileName);
            buffer = new byte[fis.available()];
            fis.read(buffer);

        } catch (IOException e) {
            writeUserInfo("false", whatStateFileName);

            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                    //todo
                    state = new String(buffer);
                    Log.d("userinfo", "用户状态信息：readUserInfo: " + state + ", fileName: " + whatStateFileName);

                    return state;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return state;
    }

    /**
     * 写入状态信息
     * @param state 状态信息
     * @param fileName 要写入文件的文件名
     */
    public void writeUserInfo(String state, String fileName){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE);
            //写入状态信息
            fos.write(state.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.close();
                    //todo
                    Log.d("userinfo", "writeUserInfo：" + state +"，用户信息写入成功：" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检测用户信息 end ----------------------------------
     */

}
