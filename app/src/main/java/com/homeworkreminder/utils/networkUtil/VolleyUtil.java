package com.homeworkreminder.utils.networkUtil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.MyApplication;

import java.util.Map;


public class VolleyUtil {
    private static Context context;
    //private RequestQueue requestQueue;

//    public VolleyUtil(Context context){
//        this.context = context;
//        this.requestQueue = Volley.newRequestQueue(this.context);
//    }


    public static StringRequest stringRequest;

    public static void volleyGET(
            Context mcontext,String url,String tag,VolleyInterface vif){
        //防止重复请求，所以先取消tag标识的请求队列
        MyApplication.getHttpQueue().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.GET, url,
                vif.loadingListener(),
                vif.errorListener()
        );
        stringRequest.setTag(tag);
        MyApplication.getHttpQueue().add(stringRequest);

        //注意千万不要调用start来开启。这样写是不对的。
        //因为在源码里，当我们调用Volley.newRequestQueue()来实例化一个请求队列的时候
        //就已经使用queue.start(); 方法来开启了一个工作线程，所以我们如果多次调用
        //newRequestQueue来实例化请求队列就会多次调用start方法，这样做势必增加性能的消耗
        //所以我们一定要把volley的请求队列全局化（可以使用单例模式或在application初始化）。
        //并且我们不应当手动调用start。
//      MyApplication.getHttpQueue().start();
    }



    public static void volleyPOST(Context context,String url,String tag,final Map<String, String> params,
                                   VolleyInterface vif){
        MyApplication.getHttpQueue().cancelAll(tag);
        stringRequest = new StringRequest(url, vif.loadingListener(), vif.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getHttpQueue().add(stringRequest);
    }

}
