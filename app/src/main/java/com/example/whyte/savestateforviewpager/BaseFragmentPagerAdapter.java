package com.example.whyte.savestateforviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whyte on 2017/6/20.
 */

public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    protected List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fragmentManager;


    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    protected void addFragment(Fragment fragment) {
        if (fragment != null && !mFragments.contains(fragment)) {
            mFragments.add(fragment);
        }
    }

    /**
     * 初始化所有fragment，并添加进list
     */
    public abstract void initFragments();

    /**
     * 保存fragment和获取fragment使用的key
     *
     * @return
     */
    protected abstract String getKey();

    /**
     * fragment的个数，使用常量
     *
     * @return
     */
    protected abstract int getFragmentCount();

    /**
     * 页面恢复数据时恢复fragment
     */
    public void restoreFragments(Bundle savedInstanceState) {
        for (int i = 0; i < getFragmentCount(); i++) {
            Fragment fragment = fragmentManager.getFragment(savedInstanceState, getKey() + i);
            addFragment(fragment);
        }
    }

    /**
     * onSaveInstanceState时保存fragment
     *
     * @param outState
     */
    public void saveFragments(Bundle outState) {
        for (int i = 0; i < getFragmentCount(); i++) {
            if (i < mFragments.size()) {
                fragmentManager.putFragment(outState, getKey() + i, getItem(i));
            }
        }
    }

    /**
     * 移除fragment
     */
    public void clear() {
        if (fragmentManager != null) {
            for (Fragment fragment : mFragments) {
                if (fragment != null) {
                    if (fragmentManager.findFragmentByTag(fragment.getTag()) != null) {
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    }
                }
            }
        }
        mFragments.clear();
    }
}
