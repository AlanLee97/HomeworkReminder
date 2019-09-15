package com.homeworkreminder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.homeworkreminder.R;
import com.homeworkreminder.fragment.HomeworkFragment;

import java.util.List;

/**
 * 选项卡适配器
 */
public class TabViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    public static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private Context mContext;


    public TabViewPagerAdapter(List<View> viewList, Context mContext) {
        this.viewList = viewList;
        this.mContext = mContext;
    }

    TabViewPagerAdapter(){

    }



    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


}
