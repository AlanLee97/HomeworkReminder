package com.homeworkreminder.utils.networkUtil;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class VolleyUtil {
    private Context context;
    public String result;

    public VolleyUtil(Context context){
        this.context = context;
    }


    /**
     * get请求
     * @param url url
     */
    public void useVolleyGET(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求的url
                url,
                //参数2：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        //将请求的原始json数据放到EditText中
                        //loginReturnResult.setText(result);

                        //使用Gson解析json数据
                        //parseJsonByGson(result);

                        Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT).show();
                    }
                },
                //参数3：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //loginReturnResult.setText("请求失败");
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }
}
