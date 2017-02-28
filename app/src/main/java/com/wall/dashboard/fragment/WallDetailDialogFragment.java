package com.wall.dashboard.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wall.R;
import com.wall.model.Wallpaper;
import com.wall.utilz.Constants;


/**
 * Created by AND001 on 2/1/2017.
 */

public class WallDetailDialogFragment extends DialogFragment {

    private static final String TAG = "WallDetailDialogFragment";
    private Wallpaper.Hit mWall;
    private String mImageUrl;
    private ImageView mImgWall;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDialog();
        return inflater.inflate(R.layout.dialog_wall_detail, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImgWall = (ImageView) view.findViewById(R.id.imgWall);
        mWall = (Wallpaper.Hit) getArguments().getSerializable(Constants.BUNDLE_WALL_ITEM);
        mImageUrl = mWall.getWebformatURL().replace("_640", "_960");
        Glide.with(this).load(mImageUrl).placeholder(R.drawable.img_placeholder).into(mImgWall);
    }

    private void initDialog() {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.ThemeDialogZoomAnimation;
        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        wmlp.gravity = Gravity.FILL_HORIZONTAL;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
