package com.wall.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.wall.R;
import com.wall.base.BaseActivity;
import com.wall.dashboard.fragment.CategoryFragment;
import com.wall.dashboard.fragment.WallListFragment;
import com.wall.utilz.Constants;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private ViewPager mViewPager;
    public WallListFragment mWallListFragment;
    public CategoryFragment mCategoryFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCE, MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(Constants.KEY_HELP_SCREEN_APPEAR, false))
            startActivity(new Intent(this, HelpActivity.class));
        fullScreen();
        init();
        setPagerAdapter();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 1:
                        mWallListFragment  = new WallListFragment();
                        return mWallListFragment;
                    case 0:
                        mCategoryFragment = new CategoryFragment();
                        return mCategoryFragment;
                }
               return null;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
    }
}
