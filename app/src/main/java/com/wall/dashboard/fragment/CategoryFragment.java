package com.wall.dashboard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wall.R;
import com.wall.base.BaseFragment;
import com.wall.dashboard.CategoryDetailActivity;
import com.wall.dashboard.adapter.CategoryAdapter;
import com.wall.utilz.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AND001 on 2/23/2017.
 */

public class CategoryFragment extends BaseFragment {

    private static final String TAG = "CategoryFragment";
    private RecyclerView mRecyclerViewWall;
    private CategoryAdapter mCategoryAdapter;
    public int mHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wall_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setAdapter();
    }

    private void initView(View view) {
        mHeight = getHeight();
        mRecyclerViewWall = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    private void setAdapter() {
        mRecyclerViewWall.setHasFixedSize(true);
        mRecyclerViewWall.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoryAdapter = new CategoryAdapter(getContext(), getCategory(), CategoryFragment.this);
        mRecyclerViewWall.setAdapter(mCategoryAdapter);
    }

    private List<String> getCategory() {
        List<String> mList =  Arrays.asList(getResources().getStringArray(R.array.category_list));
        return mList;
    }

    public void onClickCategory(ImageView imgHeader, String category) {
        Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_CATEGORY, category);
        startActivity(intent);
    }
}
