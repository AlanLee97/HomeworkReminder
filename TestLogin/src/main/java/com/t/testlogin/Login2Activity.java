package com.t.testlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Login2Activity extends AppCompatActivity {
    private String result;

    private Button btn_login;
    private EditText password;
    private EditText username;
    private TextView returnResult;




    private String url = "http://192.168.43.24/index.php/index/User/registerDoWith?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);




        btn_login = (Button) findViewById(R.id.btn_login);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        returnResult = (TextView) findViewById(R.id.return_result);




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inp_username = username.getText().toString();
                String inp_password = password.getText().toString();

                useVolleyPost(url, inp_username, inp_password);

                System.out.println("url: " + url);
            }
        });




    }





    private void useVolleyPost(String url, final String username, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(
                //参数1：请求方法
                Request.Method.POST,
                //参数2：请求的url
                url,
                //参数3：请求成功的监听事件
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result = response;
                        //将请求的原始json数据放到EditText中
                        returnResult.setText(result);

                        System.out.println("result:" + result);
                        Toast.makeText(Login2Activity.this, "请求成功", Toast.LENGTH_SHORT).show();

                        //使用Gson解析json数据
                        //parseJsonByGson(result);

                    }
                },
                //参数4：请求失败的监听事件
                new com.android.volley.Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login2Activity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }){//StringRequest方法体的左大括号

            //StringRequest方法体中重写getParams()方法
            //设置请求参数的方法
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求参数信息
                Map<String, String> map = new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }

        };//StringRequest方法体的右大括号

        //3、将请求添加到队列
        requestQueue.add(stringRequest);
    }

    /**
     * 使用Gson解析json数据，这个比较简单，以下是使用步骤(3步，其实前2步就算拿到数据了)
     * @param json 要解析的json
     */
    public void parseJsonByGson(String json){
        //1、创建Gson对象
        Gson gson = new Gson();
        //2、调用Gson的fromJson()方法，将json转成javaBean，将要显示的数据封装到这个javaBean
        //fromJson(参数1，参数2)方法 参数1：需要解析的json 参数2：一个javaBean，接收需要封装的数据（总数据的javaBean）

        //Weather weather = gson.fromJson(result, Weather.class);

        //3、将数据显示到TextView中

    }
}
