package com.wall.dashboard;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wall.R;
import com.wall.base.BaseActivity;
import com.wall.model.Wallpaper;
import com.wall.utilz.AnimationUtils;
import com.wall.utilz.Constants;
import com.wall.utilz.FileUtilz;

import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class WallDetailActivity extends BaseActivity {

    private ImageView mImgWall;
    private Wallpaper.Hit mWall;
    private static final String TAG = "WallDetailActivity";
    private String mImageUrl;
    private LinearLayout mLinBottom;
    private int mWhichType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_detail);
        fullScreen();
        init();

        Glide.with(this).load(mImageUrl).placeholder(R.drawable.img_placeholder).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        }).into(mImgWall);
    }

    private void init() {
        mImgWall = (ImageView) findViewById(R.id.imgWall);
        mLinBottom = (LinearLayout) findViewById(R.id.linBottom);
        mWall = (Wallpaper.Hit) getIntent().getSerializableExtra(Constants.BUNDLE_WALL_ITEM);
        mImageUrl = mWall.getWebformatURL().replace("_640", "_960");
        AnimationUtils.animateIn(mLinBottom);
    }

    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

   public void attemptDownload(View view){
       mWhichType = 0;
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    Constants.PERMISSION_CODE
            );

            return;
        }
        downloadImage(mImageUrl, FileUtilz.getDirectory(), FileUtilz.getImageName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(mWhichType == 0)
                    downloadImage(mImageUrl, FileUtilz.getDirectory(), FileUtilz.getImageName());
                else if(mWhichType == 1){
                    shareImage();
                }
            }
        }
    }

    void downloadImage(String url, String dirPath, String fileName){
        if(isOnline(WallDetailActivity.this)) {
            AndroidNetworking.download(url, dirPath, fileName)
                    .setTag("downloadTest")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {
                            // do anything with progress
                            Log.d(TAG, "onProgress() called with: bytesDownloaded = [" + bytesDownloaded + "], totalBytes = [" + totalBytes + "]");
                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            // do anything after completion
                            Log.d(TAG, "onDownloadComplete() called");
                            showToast("Download Complete");
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d(TAG, "onError() called with: error = [" + error + "]");
                        }
                    });
        }else{
            showSnackbar(mImgWall, getString(R.string.no_internet_connection));
        }
    }

    public void onSetWallpaper(View view){
        new SetWallPaper().execute();
    }

    public void onShare(View view){
        mWhichType = 1;
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    Constants.PERMISSION_CODE
            );
            return;
        }

       shareImage();
    }

    private void shareImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(WallDetailActivity.this).load(mImageUrl).asBitmap().into(-1,-1).get();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, FileUtilz.getLocalBitmapUri(bitmap));
                    startActivity(Intent.createChooser(i, "Share Image"));
                } catch (final Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }).start();
    }

    class SetWallPaper extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.
                        with(WallDetailActivity.this).
                        load(mImageUrl).
                        asBitmap().
                        into(-1,-1).
                        get();
            } catch (final Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallDetailActivity.this);
                try {
                    wallpaperManager.setBitmap(bitmap);

                    wallpaperManager.suggestDesiredDimensions(width, height);
                    Toast.makeText(WallDetailActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
