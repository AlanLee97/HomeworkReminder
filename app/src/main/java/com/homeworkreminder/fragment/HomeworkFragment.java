package com.homeworkreminder.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import com.homeworkreminder.R;
import com.homeworkreminder.activity.RegisterActivity;
import com.homeworkreminder.adapter.HomeDataRecyclerViewAdapter;
import com.homeworkreminder.adapter.TabViewPagerAdapter;
import com.homeworkreminder.adapter.MyFragmentPagerAdapter;
import com.homeworkreminder.entity.HomeData;
import com.homeworkreminder.entity.HomeworkData;
import com.homeworkreminder.utils.userUtil.CheckUserInfoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 我的作业列表界面的Fragment
 */
public class HomeworkFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    static List<Fragment> fragmentList;
    List<HomeData> homeDataList = new ArrayList<>();
    private PageViewModel pageViewModel;
    private TabViewPagerAdapter tabViewPagerAdapter;
    private ViewPager myViewPager;
    private List<View> viewList;
    private HomeDataRecyclerViewAdapter homeDataRecyclerViewAdapter;
    private TabLayout tabLayout;
    private View view;
    private RecyclerView myRecyclerView;
    private RecyclerView undoRecyclerView;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    static Fragment undoHomeworkFragment = new UndoHomeworkFragment();
    static Fragment doneHomeworkFragment = new DoneHomeworkFragment();

    String registerState;
    String loginState;

    public static HomeworkFragment newInstance(int index) {
        HomeworkFragment fragment = new HomeworkFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    /**
     * 创建视图，填充xml布局到Fragment
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return view = inflater.inflate(R.layout.homework_fragment_layout, container, false);

    }

    /**
     * 相当于Activity的onCreate()
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), Objects.requireNonNull(getActivity()).getSupportFragmentManager());
//        ViewPager viewPager = view.findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = view.findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);


        initView(view);
        addViewPager(tabLayout);
        addViewPagerFragment(tabLayout);
    }

    /**
     * 添加ViewPager
     * @param tabLayout
     */
    private void addViewPager(TabLayout tabLayout) {
        Log.i("addViewPager", "========== 调用addViewPager");
        //获取布局填充器
        LayoutInflater layoutInflater = getLayoutInflater();
        viewList = new ArrayList<>();

        View fragment_undo_homework = layoutInflater.inflate(R.layout.fragment_undo_homework, null, false);
        View fragment_done_homework = layoutInflater.inflate(R.layout.fragment_done_homework, null, false);

        //获取布局填充器
        //LayoutInflater layoutInflater = getLayoutInflater();
        //将视图添加到集合中
        viewList.add(fragment_undo_homework);
        viewList.add(fragment_done_homework);

        //初始化适配器，将视图添加到适配器中
        tabViewPagerAdapter = new TabViewPagerAdapter(viewList, getContext());
        //设置适配器
        myViewPager.setAdapter(tabViewPagerAdapter);

        //添加tab布局
        tabLayout.setupWithViewPager(myViewPager);


        //addViewPagerFragment(tabLayout);
    }

    /**
     * 在添加ViewPager中添加Fragment
     * @param tabLayout
     */
    public void addViewPagerFragment(TabLayout tabLayout) {
        Log.i("addViewPager", "========== 调用addViewPagerFragment");

        //addViewPager(tabLayout);

        fragmentList = new ArrayList<>();
        fragmentList.add(undoHomeworkFragment);
        fragmentList.add(doneHomeworkFragment);

        //适配器
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentPagerAdapter);

        tabLayout.setupWithViewPager(myViewPager);

        //解决tab标签文字不显示的问题
        int[] tabTexts = TabViewPagerAdapter.TAB_TITLES;
        for(int i = 0 ; i < tabTexts.length; i++){
            tabLayout.getTabAt(i).setText(tabTexts[i]);
        }


        //初始化第一个页面
        myViewPager.setCurrentItem(0);
    }

    /**
     * 初始化视图
     */
    private void initView(View view) {
        myViewPager = view.findViewById(R.id.view_pager);
        //myRecyclerView = view.findViewById(R.id.homework_recyclerView);
        tabLayout = view.findViewById(R.id.tabs);
    }

}
