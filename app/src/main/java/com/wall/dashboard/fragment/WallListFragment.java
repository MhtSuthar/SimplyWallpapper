package com.wall.dashboard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.wall.R;
import com.wall.base.BaseFragment;
import com.wall.dashboard.WallDetailActivity;
import com.wall.dashboard.adapter.WallpaperAdapter;
import com.wall.model.Wallpaper;
import com.wall.utilz.Constants;

import java.util.List;
import java.util.Random;

/**
 * Created by AND001 on 2/23/2017.
 */

public class WallListFragment extends BaseFragment {

    private static final String TAG = "WallListFragment";
    private RecyclerView mRecyclerViewWall;
    public int mHeight;
    private int mPageStart = 20;
    private WallpaperAdapter mWallAdapter;
    private List<Wallpaper.Hit> mWallList;
    private RelativeLayout mRelEmpty;
    private ImageView mImgEmpty;
    private int[] mImg = new int[]{R.drawable.fashion, R.drawable.nature, R.drawable.background, R.drawable.music, R.drawable.people,
            R.drawable.relision, R.drawable.helth, R.drawable.place, R.drawable.animal, R.drawable.industry,
            R.drawable.food, R.drawable.computer, R.drawable.sport, R.drawable.travel, R.drawable.building, R.drawable.bussiness, R.drawable.education};
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wall_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if(isOnline(getActivity())) {
            mRelEmpty.setVisibility(View.GONE);
            getWallpapers();
        }else{
            mProgressBar.setVisibility(View.GONE);
            mRelEmpty.setVisibility(View.VISIBLE);
            mImgEmpty.setImageResource(mImg[new Random().nextInt(mImg.length)]);
        }
    }

    private void initView(View view) {
        mHeight = getHeight();
        mRecyclerViewWall = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRelEmpty = (RelativeLayout) view.findViewById(R.id.relEmpty);
        mImgEmpty = (ImageView) view.findViewById(R.id.imgEmpty);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    void getWallpapers(){
        AndroidNetworking.get(getString(R.string.base_url))
                .addQueryParameter(Constants.KEY_PIXABAY, getString(R.string.pixabay_key))
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
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        /*manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //Log.e(TAG, "getSpanSize: "+position+"  span  "+(3 - position % 3)+"   modulo  "+(position % 3));
                return (3 - position % 3);
            }
        });*/
        mRecyclerViewWall.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerViewWall.setLayoutManager(manager);
        //mWallAdapter = new WallAdapter(getContext(), hits, WallListFragment.this);
        //mRecyclerViewWall.setAdapter(mWallAdapter);

        mWallAdapter = new WallpaperAdapter(getActivity(), mWallList, WallListFragment.this);
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
                            //telling mWallAdapter to stop calling load more as no more server data available
                            Toast.makeText(getActivity(),"No More Data Available",Toast.LENGTH_LONG).show();
                        }
                        mWallAdapter.notifyDataChanged();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError() called with: anError = [" + anError + "]");
                    }
                });
    }

    public void onClickImage(ImageView imgHeader, Wallpaper.Hit hit) {
        Intent intent = new Intent(getActivity(), WallDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_WALL_ITEM, hit);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), imgHeader, "profile");
        startActivity(intent, options.toBundle());
    }
}
