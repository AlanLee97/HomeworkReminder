package com.homeworkreminder.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.homeworkreminder.R;
import com.homeworkreminder.activity.EditUserInfoActivity;
import com.homeworkreminder.activity.IndexActivity;
import com.homeworkreminder.activity.LoginActivity;
import com.homeworkreminder.activity.MainActivity;
import com.homeworkreminder.activity.ModifyPasswordActivity;
import com.homeworkreminder.activity.RegisterActivity;
import com.homeworkreminder.activity.UserActivity;
import com.homeworkreminder.entity.UserInfo;
import com.homeworkreminder.utils.MyApplication;
import com.homeworkreminder.utils.networkUtil.MyGson;
import com.homeworkreminder.utils.networkUtil.VolleyInterface;
import com.homeworkreminder.utils.networkUtil.VolleyUtil;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;


public class UserFragment extends Fragment {

    private TextView tvUserinfoUsername;
    private TextView tvUserinfoNickname;
    private TextView tvUserinfoSchool;
    private TextView tvUserinfoMajor;
    private TextView tvUserinfoClass;
    private Button btnLogout;
    private Button btnModifyPassword;

    private String nickname;
    private String school;
    private String major;
    private String clazz;

    String registerState;
    String loginState;





    String url = "http://www.nibuguai.cn/index.php/index/user/api_getUserInfoById?id=";
    int uid;

    MyApplication app = new MyApplication();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        checkUserState();

        //getUserData();

        //showData();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                getData();
                Intent intent = new Intent(getContext(), EditUserInfoActivity.class);
                Bundle bundle = new Bundle();

                convertDataToActivity(bundle);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        modifyPassword();

        logout();
    }

    // TODO: Rename method, update argument and hook method into UI event
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



    private void modifyPassword() {
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyPasswordActivity.class));

            }
        });
    }


    private String TAG = "user_state";

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUserInfoUtil userInfoUtil = new CheckUserInfoUtil(getContext());
                userInfoUtil.writeUserInfo("false", "register");
                userInfoUtil.writeUserInfo("false", "login");
                userInfoUtil.writeUserInfo("", "uid");
                //app.setSTATE_LOGIN(false);
                startActivity(new Intent(getContext(), IndexActivity.class));

                //finish();
            }
        });
    }

    private void initView(View view) {

        tvUserinfoUsername = (TextView) view.findViewById(R.id.tv_userinfo_username);
        tvUserinfoNickname = (TextView) view.findViewById(R.id.tv_userinfo_nickname);
        tvUserinfoSchool = (TextView) view.findViewById(R.id.tv_userinfo_school);
        tvUserinfoMajor = (TextView) view.findViewById(R.id.tv_userinfo_major);
        tvUserinfoClass = (TextView) view.findViewById(R.id.tv_userinfo_class);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnModifyPassword = (Button) view.findViewById(R.id.btn_modifyPassword);

    }



    /**
     * 通过Bundle向Activity中传值
     */
    public void convertDataToActivity(Bundle bundle){
        //bundle.putInt("headImg",headImg);
        bundle.putCharSequence("nickname", nickname);
        bundle.putCharSequence("school", school);
        bundle.putCharSequence("major", major);
        bundle.putCharSequence("class", clazz);


    }

    public void getData(){
        nickname = tvUserinfoNickname.getText().toString();
        school = tvUserinfoSchool.getText().toString();
        major = tvUserinfoMajor.getText().toString();
        clazz = tvUserinfoClass.getText().toString();

    }


    //*
    private void checkUserState() {
        CheckUserInfoUtil checkUserInfoUtil = new CheckUserInfoUtil(getActivity());
        List<String> userStates = checkUserInfoUtil.checkUserState();

        registerState = userStates.get(0);
        loginState = userStates.get(1);

        System.out.println("============= registerState：" + registerState);
        System.out.println("============= loginState：" + loginState);

        if (loginState.equals("true")){
            getUserData(checkUserInfoUtil);
        }else {
            startActivity(new Intent(getActivity(), RegisterActivity.class));
            //getActivity().finish();
        }



    }

     //*/

    //*
    public void getUserData(CheckUserInfoUtil checkUserInfoUtil){

        String localUserInfo = checkUserInfoUtil.getLocalUserInfo();

        System.out.println("============ localUserInfo：" + localUserInfo);
        UserInfo userInfo = MyGson.parseJsonByGson(localUserInfo, UserInfo.class);

        String username = userInfo.getData().get(0).getUsername();
        String nickname = userInfo.getData().get(0).getNickname();
        String school = userInfo.getData().get(0).getSchool();
        String major = userInfo.getData().get(0).getMajor();
        String classX = userInfo.getData().get(0).getClassX();

        System.out.println("username = " + username);

        tvUserinfoUsername.setText(username);
        tvUserinfoNickname.setText(nickname);
        tvUserinfoSchool.setText(school);
        tvUserinfoMajor.setText(major);
        tvUserinfoClass.setText(classX);

        Toast.makeText(getActivity(), "个人数据获取成功", Toast.LENGTH_SHORT).show();

    }

     //*/
}
