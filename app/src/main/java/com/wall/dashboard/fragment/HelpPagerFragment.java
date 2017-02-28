package com.wall.dashboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wall.R;
import com.wall.base.BaseFragment;
import com.wall.dashboard.HelpActivity;

/**
 * Created by AND001 on 2/28/2017.
 */

public class HelpPagerFragment extends BaseFragment {

    private String[] mTitle = new String[]{"Simple","Swipe Left & Right","Download & Share"};
    private String[] mDesc = new String[]{"Simple","Swipe Left & Right","Download & Share"};
    private int[] mImages = new int[]{R.drawable.music, R.drawable.education, R.drawable.travel};
    private ImageView mImgHelp;
    private TextView mTxtTitle, mTxtDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImgHelp = (ImageView) view.findViewById(R.id.imgHelp);
        mTxtTitle = (TextView) view.findViewById(R.id.txtTitle);
        mTxtDesc = (TextView) view.findViewById(R.id.txtDesc);
        mTxtTitle.setText(mTitle[getArguments().getInt(HelpActivity.Position)]);
        mTxtDesc.setText(mDesc[getArguments().getInt(HelpActivity.Position)]);
        mImgHelp.setImageResource(mImages[getArguments().getInt(HelpActivity.Position)]);
    }
}
