package com.geniusvjr.nbaer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.geniusvjr.nbaer.R;
import com.geniusvjr.nbaer.data.Constant;
import com.geniusvjr.nbaer.event.DrawerClickEvent;
import com.geniusvjr.nbaer.ui.activity.BaseActivity;
import com.geniusvjr.nbaer.ui.fragment.DrawerFragment;
import com.geniusvjr.nbaer.ui.fragment.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {


    private DrawerFragment mNavigationFragment;
    private BaseFragment mCurrentFragment;
    private int mCurrentDrawId = R.string.news;

    private Map<String,BaseFragment> mBaseFragmentByName = new HashMap<>();
    private Map<Integer,String> mFragmentNameByDrawerId = new HashMap<>();




    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initViews() {

        getWindow().setBackgroundDrawable(null);
        mNavigationFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp(findViewById(R.id.main_content), R.id.navigation_drawer,
                findViewById(R.id.main_drawer));
        initDrawerMap();
    }

    private void initDrawerMap() {
//        mFragmentNameByDrawerId.put(R.string.news, Main)
    }


}
