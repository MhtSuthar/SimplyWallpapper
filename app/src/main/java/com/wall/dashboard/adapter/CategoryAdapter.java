package com.wall.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wall.R;
import com.wall.dashboard.fragment.CategoryFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> mListCategory;
    private CategoryFragment categoryFragment;
    private static final String TAG = "CategoryAdapter";
    private int[] mCategoryImg = new int[]{R.drawable.fashion, R.drawable.nature, R.drawable.background, R.drawable.music, R.drawable.people,
            R.drawable.relision, R.drawable.helth, R.drawable.place, R.drawable.animal, R.drawable.industry,
            R.drawable.food, R.drawable.computer, R.drawable.sport, R.drawable.travel, R.drawable.building, R.drawable.bussiness, R.drawable.education};

    public CategoryAdapter(Context context, List<String> hits, CategoryFragment categoryFragment) {
        this.context = context;
        this.mListCategory = hits;
        this.categoryFragment = categoryFragment;
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        public ImageView imgHeader;
        TextView mTxtCategory;
        View mView;
        public ViewHolder0(View itemView) {
            super(itemView);
            imgHeader = (ImageView) itemView.findViewById(R.id.imgHeader);
            mTxtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            mView = itemView.findViewById(R.id.viewDivider);
        }
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder0(viewHeader);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder0 viewHolder0 = (ViewHolder0) holder;
        viewHolder0.imgHeader.getLayoutParams().height = (int) (categoryFragment.mHeight/3);
        viewHolder0.imgHeader.requestLayout();
        viewHolder0.mView.getLayoutParams().height = (int) (categoryFragment.mHeight/3);
        viewHolder0.mView.requestLayout();
        viewHolder0.mTxtCategory.setText(mListCategory.get(position));
        Glide.with(context).load(mCategoryImg[position]).into(viewHolder0.imgHeader);
        viewHolder0.imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryFragment.onClickCategory(viewHolder0.imgHeader, mListCategory.get(position));
            }
        });

    }

}