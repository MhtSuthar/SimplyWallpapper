package com.wall.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wall.R;
import com.wall.base.BaseActivity;
import com.wall.dashboard.adapter.WallpaperAdapter;
import com.wall.model.Wallpaper;
import com.wall.utilz.Constants;

import java.util.List;
import java.util.Random;

public class CategoryDetailActivity extends BaseActivity {

    private String mCategory;
    private static final String TAG = "CategoryDetailActivity";
    private RecyclerView mRecyclerViewWall;
    private WallpaperAdapter mWallAdapter;
    public int mHeight;
    private int mPageStart = 20;
    private List<Wallpaper.Hit> mWallList;
    private RelativeLayout mRelEmpty;
    private ImageView mImgEmpty;
    private int[] mImg = new int[]{R.drawable.fashion, R.drawable.nature, R.drawable.background, R.drawable.music, R.drawable.people,
            R.drawable.relision, R.drawable.helth, R.drawable.place, R.drawable.animal, R.drawable.industry,
            R.drawable.food, R.drawable.computer, R.drawable.sport, R.drawable.travel, R.drawable.building, R.drawable.bussiness, R.drawable.education};
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wall_list);
        fullScreen();
        init();
        mCategory = getIntent().getStringExtra(Constants.BUNDLE_CATEGORY);
        if(isOnline(this)) {
            getWallpapers();
        }else{
            mProgressBar.setVisibility(View.GONE);
            mRelEmpty.setVisibility(View.VISIBLE);
            mImgEmpty.setImageResource(mImg[new Random().nextInt(mImg.length)]);
        }
    }

    private void init() {
        mHeight = getHeight();
        mRecyclerViewWall = (RecyclerView) findViewById(R.id.recycler_view);
        mRelEmpty = (RelativeLayout) findViewById(R.id.relEmpty);
        mImgEmpty = (ImageView) findViewById(R.id.imgEmpty);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    void getWallpapers(){
        AndroidNetworking.get(getString(R.string.base_url))
                .addQueryParameter(Constants.KEY_PIXABAY, getString(R.string.pixabay_key))
                .addQueryParameter(Constants.CATEGORY, mCategory)
                .addQueryParameter(Constants.PER_PAGE, ""+mPageStart)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Wallpaper.class, new ParsedRequestListener<Wallpaper>() {
                    @Override
                    public void onResponse(Wallpaper users) {
                        mProgressBar.setVisibility(View.GONE);
                        mWallList = users.getHits();
                        setAdapter();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError() called with: anError = [" + anError + "]");
                    }
                });

    }

    private void setAdapter() {
        mRecyclerViewWall.setHasFixedSize(true);
        mRecyclerViewWall.setLayoutManager(new LinearLayoutManager(this));
        mWallAdapter = new WallpaperAdapter(this, mWallList, CategoryDetailActivity.this);
        mWallAdapter.setLoadMoreListener(new WallpaperAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mRecyclerViewWall.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = mWallList.size() - 1;

                        Log.d(TAG, "run() called   "+index);
                        loadMore(index);
                    }
                });
            }
        });
        mRecyclerViewWall.setAdapter(mWallAdapter);
    }

    private void loadMore(int index) {
        Wallpaper.Hit hit = new Wallpaper.Hit();
        hit.typeLoadMore = -1;
        mWallList.add(hit);
        mWallAdapter.notifyItemInserted(mWallList.size()-1);
        mPageStart = mPageStart + 20;
        AndroidNetworking.get(getString(R.string.base_url))
                .addQueryParameter(Constants.KEY_PIXABAY, getString(R.string.pixabay_key))
                .addQueryParameter(Constants.CATEGORY, mCategory)
                .addQueryParameter(Constants.PER_PAGE, ""+mPageStart)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Wallpaper.class, new ParsedRequestListener<Wallpaper>() {
                    @Override
                    public void onResponse(Wallpaper users) {
                        mWallList.remove(mWallList.size()-1);
                        List<Wallpaper.Hit> result = users.getHits();
                        if(result.size()>0){
                            //add loaded data
                            mWallList.clear();
                            mWallList.addAll(result);
                        }else{//result size 0 means there is no more data available at server
                            mWallAdapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            Toast.makeText(CategoryDetailActivity.this,"No More Data Available",Toast.LENGTH_LONG).show();
                        }
                        mWallAdapter.notifyDataChanged();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError() called with: anError = [" + anError + "]");
                    }
                });
    }

    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void onClickImage(ImageView imgHeader, Wallpaper.Hit hit) {
        Intent intent = new Intent(this, WallDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_WALL_ITEM, hit);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imgHeader, "profile");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        }else
            startActivity(intent);
    }
}
