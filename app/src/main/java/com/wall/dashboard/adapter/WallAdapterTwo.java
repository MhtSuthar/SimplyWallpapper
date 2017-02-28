package com.wall.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wall.R;
import com.wall.dashboard.fragment.WallListFragment;
import com.wall.model.Wallpaper;

import java.util.List;

public class WallAdapterTwo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Wallpaper.Hit> mListWall;
    private WallListFragment wallListFragment;
    private static final String TAG = "WallAdapterTwo";

    public WallAdapterTwo(Context context, List<Wallpaper.Hit> hits, WallListFragment wallListFragment) {
        this.context = context;
        this.mListWall = hits;
        this.wallListFragment = wallListFragment;
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        public ImageView imgHeader;
        public ViewHolder0(View itemView) {
            super(itemView);
            imgHeader = (ImageView) itemView.findViewById(R.id.imgHeader);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView imgGridOne, imgGridTwo;
        public ViewHolder2(View itemView) {
            super(itemView);
            imgGridOne = (ImageView) itemView.findViewById(R.id.imgGridOne);
            imgGridTwo = (ImageView) itemView.findViewById(R.id.imgGridTwo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 ;
    }

    @Override
    public int getItemCount() {
        return (mListWall.size() * 2) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View viewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wall_header, parent, false);
                return new ViewHolder0(viewHeader);
            case 1:
                View viewGrid = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wall_grid, parent, false);
                return new ViewHolder2(viewGrid);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                Glide.with(context).load(mListWall.get(position).getPreviewURL()).centerCrop().into(viewHolder0.imgHeader);
                break;

            case 1:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                Glide.with(context).load(mListWall.get(position).getPreviewURL()).centerCrop().into(viewHolder2.imgGridOne);
                Glide.with(context).load(mListWall.get(position+1).getPreviewURL()).centerCrop().into(viewHolder2.imgGridTwo);
                break;
        }
    }

}