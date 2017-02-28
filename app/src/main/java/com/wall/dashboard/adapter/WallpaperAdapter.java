package com.wall.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wall.R;
import com.wall.dashboard.CategoryDetailActivity;
import com.wall.dashboard.fragment.WallListFragment;
import com.wall.model.Wallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_PHOTO = 0;
    public final int TYPE_LOAD = 1;

    private Context context;
    List<Wallpaper.Hit> mListWall;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    private WallListFragment wallListFragment;
    private CategoryDetailActivity categoryDetailActivity;


    public WallpaperAdapter(Context context, List<Wallpaper.Hit> movies, WallListFragment wallListFragment) {
        this.context = context;
        this.wallListFragment = wallListFragment;
        this.mListWall = movies;
    }

    public WallpaperAdapter(Context context, List<Wallpaper.Hit> movies, CategoryDetailActivity categoryDetailActivity) {
        this.context = context;
        this.mListWall = movies;
        this.categoryDetailActivity = categoryDetailActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == TYPE_PHOTO){
            return new WallHolder(inflater.inflate(R.layout.list_item_wall_header,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(position >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener != null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position) == TYPE_PHOTO){
            Glide.with(context).load(mListWall.get(position).getWebformatURL()).placeholder(R.drawable.img_placeholder).into(((WallHolder)holder).imgHeader);
            if(wallListFragment != null) {
                ((WallHolder)holder).imgHeader.getLayoutParams().height = (int) (wallListFragment.mHeight/3);
                ((WallHolder)holder).imgHeader.requestLayout();
            }else {
                ((WallHolder)holder).imgHeader.getLayoutParams().height = (int) (categoryDetailActivity.mHeight/3);
                ((WallHolder)holder).imgHeader.requestLayout();
            }

            ((WallHolder)holder).imgHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(wallListFragment != null)
                        wallListFragment.onClickImage(((WallHolder)holder).imgHeader, mListWall.get(position));
                    else
                        categoryDetailActivity.onClickImage(((WallHolder)holder).imgHeader, mListWall.get(position));
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mListWall.get(position).typeLoadMore == 0){
            return TYPE_PHOTO;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return mListWall.size();
    }

    /* VIEW HOLDERS */

    static class WallHolder extends RecyclerView.ViewHolder{
        ImageView imgHeader;
        public WallHolder(View itemView) {
            super(itemView);
            imgHeader=(ImageView) itemView.findViewById(R.id.imgHeader);
        }

    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}