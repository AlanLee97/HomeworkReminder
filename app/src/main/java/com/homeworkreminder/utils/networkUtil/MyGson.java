package com.homeworkreminder.utils.networkUtil;

import android.util.Log;

import com.google.gson.Gson;

public class MyGson {

    /**
     * 使用Gson解析json数据，这个比较简单，以下是使用步骤(3步，其实前2步就算拿到数据了)
     * @param json 要解析的json
     */
    public static <T> T parseJsonByGson(String json, Class<T> t){
        //1、创建Gson对象
        Gson gson = new Gson();
        //2、调用Gson的fromJson()方法，将json转成javaBean，将要显示的数据封装到这个javaBean
        //fromJson(参数1，参数2)方法 参数1：需要解析的json 参数2：一个javaBean，接收需要封装的数据（总数据的javaBean）

        T data = (T) gson.fromJson(json, t);

        Log.d("data", "parseJsonByGson: data : " + data);

        return data;
    }
}
