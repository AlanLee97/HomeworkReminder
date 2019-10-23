package com.homeworkreminder.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.activity.AboutActivity;
import com.homeworkreminder.activity.IndexActivity;
import com.homeworkreminder.activity.MainActivity;
import com.homeworkreminder.activity.TomatoClockActivity;
import com.homeworkreminder.entity.Weather;
import com.homeworkreminder.utils.GlideImageLoader;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndexFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {
    private TextView indexTv1;
    private TextView indexTv2;
    private TextView indexTv3;
    private TextView indexTv4;
    private Button indexBtn1;
    private Button indexBtn2;
    private Button indexBtn3;
    private Button indexBtn4;

    View view = null;

//    private MyApplication app = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();

    //检查用户信息工具类
    private CheckUserInfoUtil userInfoUtil = new CheckUserInfoUtil(getContext());
    private String registerState;
    private String loginState;

    String result = "";
    Handler handler;

    //get请求的url
    String url = "https://www.apiopen.top/weatherApi?city=东莞";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IndexFragment() {
        // Required empty public constructor
    }


    public static IndexFragment newInstance(String param1, String param2) {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        checkUserState();

        showDate();

        showWeather();


        showBanner(view);

        showUsername();

        btns();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }






    private void btns() {
        indexBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        indexBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        indexBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TomatoClockActivity.class);
                startActivity(intent);
            }
        });

        indexBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);

            }
        });
    }

    private void showDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Date date = new Date();
        System.out.println("========= 日期：" + sdf.format(date));
        indexTv2.setText(sdf.format(date));
    }

    private void showWeather() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //将解析的数据显示到TextView中
                Weather weather = MyGson.parseJsonByGson(result, Weather.class);
                String wendu = weather.getData().getWendu();
                String type = weather.getData().getForecast().get(0).getType();
                System.out.println("======== 天气类型：" + type);
                System.out.println("======== 温度：" + wendu);
                indexTv3.setText(type);
                indexTv4.setText(wendu);
            }
        };

        useOkHttp3_AsyncGET(url);
    }

    private void showUsername() {
        if (loginState.equals("true")){
            //读取用户名
            String username = userInfoUtil.readUserInfo("username");
            Log.d("userinfo", "username: " + username);
            indexTv1.setText(username);
        }
    }


    private void showBanner(View view) {
        List<String> images = new ArrayList<>();
        images.add("http://www.forestry.gov.cn/uploadfile/main/2013-6/image/2013-6-19-dbdb3e3f20b644ec959960e9d8308eda.jpg");
        images.add("http://b-ssl.duitang.com/uploads/blog/201312/12/20131212143029_hNEeN.jpeg");
        images.add("http://img.zcool.cn/community/01a3d35c1d08f5a8012029ac4a3d83.jpg@1280w_1l_2o_100sh.jpg");

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initView(View view) {
        indexTv1 = (TextView) view.findViewById(R.id.index_tv_1);
        indexTv2 = (TextView) view.findViewById(R.id.index_tv_2);
        indexTv3 = (TextView) view.findViewById(R.id.index_tv_3);
        indexTv4 = (TextView) view.findViewById(R.id.index_tv_4);

        indexBtn1 = (Button) view.findViewById(R.id.index_btn_1);
        indexBtn2 = (Button) view.findViewById(R.id.index_btn_2);
        indexBtn3 = (Button) view.findViewById(R.id.index_btn_3);
        indexBtn4 = (Button) view.findViewById(R.id.index_btn_4);

    }


    /**
     * 检查用户状态
     * 通过SharedPreference将用户状态保存
     * 通过读取保存的用户状态信息，设置当前用户状态
     */
    public void checkUserState(){
        //检查注册状态
        userInfoUtil = new CheckUserInfoUtil(getContext());
        registerState = userInfoUtil.readUserInfo("register");
        Toast.makeText(getContext(), "注册状态：" + registerState, Toast.LENGTH_LONG).show();
        Log.d("userinfo", "注册状态：" + registerState);

        //检查登录状态
        loginState = userInfoUtil.readUserInfo("login");
        Toast.makeText(getContext(), "登录状态：" + loginState, Toast.LENGTH_LONG).show();
        Log.d("login", "登录状态：" + loginState);
    }



    /**
     * 使用OkHttp3请求数据--异步GET请求
     * @param url 请求的url
     */
    private void useOkHttp3_AsyncGET(String url) {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.创建请求对象
        Request request = new Request.Builder().url(url).get().build();

        //3.创建Call对象,将请求对象request作为参数
        Call call = okHttpClient.newCall(request);

        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback(){
            //请求成功时的回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "请求数据失败", Toast.LENGTH_SHORT).show();
            }
            //请求失败时的回调方法
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //异步请求需要开启子线程，使用Handler+Message的方法将数据更新到主线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获取响应的数据
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //获取消息
                        Message message = handler.obtainMessage();
                        //发送消息
                        handler.sendMessage(message);
                    }
                }).start();

                /*
                //或者使用 runOnUiThread()方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                 */
            }
        });
    }
}
