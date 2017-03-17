package com.wall.dashboard;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.wall.R;
import com.wall.dashboard.fragment.HelpPagerFragment;
import com.wall.utilz.Constants;
import com.wall.utilz.customviews.CirclePageIndicator;


/**
 * Created by ubuntu on 15/9/16.
 */
public class HelpActivity extends AppCompatActivity {

    public final static String Position = "pos";
    private ViewPager mViewPager;
    private CirclePageIndicator mIndicator;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        fullScreen();
        sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCE, MODE_PRIVATE);
        init();
    }

    public void init(){
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setRadius(10);
        mIndicator.setStrokeColor(getResources().getColor(R.color.colorPrimary));
        mIndicator.setFillColor(getResources().getColor(R.color.colorAccent));
        setPagerAdapter();
    }

    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public void onNextClick(View view){
        if(mViewPager.getCurrentItem() != 2){
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.KEY_HELP_SCREEN_APPEAR, true);
            editor.commit();
            finish();
        }
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }
            @Override
            public Fragment getItem(int position) {
                HelpPagerFragment fragment = new HelpPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Position, position);
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }
}
