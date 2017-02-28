package com.wall.utilz;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AND001 on 2/24/2017.
 */

public class FileUtilz {

    public static File getFile() {
        File sdCard = new File(Environment.getExternalStorageDirectory() + "/SimplyWall");
        if (!sdCard.exists())
            sdCard.mkdirs();

        File pdfFile = new File(sdCard, "certificate.pdf");
        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }

    public static String getDirectory() {
        File sdCard = new File(Environment.getExternalStorageDirectory() + "/SimplyWall");
        if (!sdCard.exists())
            sdCard.mkdirs();

        return sdCard.getAbsolutePath();
    }

    public static String getImageName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        timeStamp = "SimplyWall_"+timeStamp+".jpg";
        return timeStamp;
    }

    public static Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "SimplyWall_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();

            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
