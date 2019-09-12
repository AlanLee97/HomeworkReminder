package com.homeworkreminder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.homeworkreminder.R;
import com.homeworkreminder.adapter.DoneDataRecyclerViewAdapter;
import com.homeworkreminder.adapter.HomeDataRecyclerViewAdapter;
import com.homeworkreminder.entity.HomeData;
import com.homeworkreminder.interfaces.MyRecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 已完成作业界面的Fragment
 */
public class DoneHomeworkFragment extends Fragment {
    private RecyclerView myRecyclerView;
    private List<HomeData> homeDataList;
    private View view;
    private DoneDataRecyclerViewAdapter doneDataRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_done_homework, container, false);

        myRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_homework_done_recyclerView);

        initData();

        addRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    /**
     * 初始化数据
     */
    private void initData() {
        homeDataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HomeData homeData = new HomeData(
                    "Alan " + i,
                    R.drawable.head,
                    "2019年9月7日 20:00",
                    "Android作业",
                    "书本P15",
                    "Android");
            homeDataList.add(homeData);
        }
    }


    /**
     * 添加RecyclerView
     */
    //*
    public void addRecyclerView(){

        //设置RecyclerView的布局管理器
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));

        //初始化适配器
        doneDataRecyclerViewAdapter = new DoneDataRecyclerViewAdapter(getActivity(), homeDataList);

        //设置动画
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置适配器
        myRecyclerView.setAdapter(doneDataRecyclerViewAdapter);

        //设置监听器
        doneDataRecyclerViewAdapter.setMyRecyclerViewOnItemClickListener(new MyRecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getContext(), homeDataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

     //*/
}
