package com.geniusvjr.nbaer.ui.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusvjr.nbaer.app.AppService;

import butterknife.ButterKnife;

/**
 * Created by dream on 16/4/23.
 */
public abstract class BaseFragment extends Fragment {

    private int mTaskId;
    protected View rootView;
    protected abstract void initViews();
    protected abstract int getContentViewId();

    public int getmTaskId() {
        return mTaskId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId = getActivity().getTaskId();
//        AppService.getInstance().getBus().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        AppService.getInstance().getBus().unregister(this);
    }
}
