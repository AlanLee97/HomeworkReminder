package com.homeworkreminder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;

    /**
     * 初始化FragmentPagerAdapter
     * @param fm FragmentManager
     * @param fragmentList List<Fragment>
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * 获取Fragment的位置
     * @param i 位置
     * @return Fragment的位置
     */
    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    /**
     * 获取Fragment的数量
     * @return Fragment的数量
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
