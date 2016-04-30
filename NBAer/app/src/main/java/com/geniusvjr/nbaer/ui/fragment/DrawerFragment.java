package com.geniusvjr.nbaer.ui.fragment;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.geniusvjr.nbaer.R;
import com.geniusvjr.nbaer.app.AppService;
import com.geniusvjr.nbaer.data.Constant;
import com.geniusvjr.nbaer.event.DrawerClickEvent;
import com.geniusvjr.nbaer.ui.adapter.RecycleAdapter.DrawerAdapter;
import com.geniusvjr.nbaer.ui.fragment.base.BaseFragment;
import com.geniusvjr.nbaer.utils.BitmapUtils;
import com.geniusvjr.nbaer.utils.Blur;

import butterknife.Bind;

/**
 * Created by dream on 16/4/23.
 */
public class DrawerFragment extends BaseFragment{

    private static final int DOWNSCALE = 8;
    private static final int BLUR_RADIUS = 15;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private Bitmap mBitmap;
    private Bitmap bitmap;
    private DrawerClickEvent mDrawerClickEvent;
    @Bind(R.id.rv_drawer)
    RecyclerView mDrawerRv;
    @Bind(R.id.item_head)
    View head;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mDrawerRv.getContext());
        DrawerAdapter drawerAdapter = new DrawerAdapter(getActivity());
        mDrawerRv.setAdapter(drawerAdapter);

    }

    /**
     * 判断侧滑是否打开
     * @return
     */
    public boolean isDrawerOpen(){
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * 关闭侧滑
     */
    public void closeDrawer(){
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    /**
     * 对侧滑进行初始化设置
     * @param mContentLayout
     * @param fragmentId
     * @param drawerLayout
     */
    public void setUp(final View mContentLayout, int fragmentId, final View drawerLayout){
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = (DrawerLayout) drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(mDrawerClickEvent != null){
                    mDrawerClickEvent.setmEventResult(Constant.Result.SUCCESS);
                    AppService.getBus().post(mDrawerClickEvent);
                }
                getActivity().invalidateOptionsMenu();
                if(mBitmap != null){
                    mBitmap.recycle();
                    mBitmap = null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!isAdded()){
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(Build.VERSION.SDK_INT > 16){
                    if(mBitmap != null){
                        bitmap = Bitmap.createBitmap(mBitmap, 0, 0, (int) (mBitmap.getWidth() * slideOffset) > 0 ? (int) (mBitmap.getWidth() * slideOffset) : 1, mBitmap.getHeight());
                        BitmapDrawable bd;
                        bd = new BitmapDrawable(bitmap);
                        drawerView.setBackground(bd);
                    }else{
                        mBitmap = BitmapUtils.drawViewToBitmap(mContentLayout,
                                rootView.getWidth(), rootView.getHeight(), DOWNSCALE, "#42283593");
                        mBitmap = Blur.fastblur(mContentLayout.getContext(), mBitmap, BLUR_RADIUS);
                    }
                }
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent){
        if(Constant.Result.SUCCESS.equals(drawerClickEvent.getmEventResult())){
            return;
        }
        mDrawerClickEvent = drawerClickEvent;
        closeDrawer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_drawer;
    }
}
