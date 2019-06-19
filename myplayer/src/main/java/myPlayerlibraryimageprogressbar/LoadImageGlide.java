package myPlayerlibraryimageprogressbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import ir.mlibs.myplayer.R;


/**
 * Created by vali on 2017-05-14.
 */

public class LoadImageGlide {
    public static void fillGlide(String url,
                                 ProgressTarget<String, Bitmap> target,
                                 final ProgressBar circularProgressBar,
                                 TextView imageTitle,
                                 int mNum,
                                 int type,
                                 final String ImageName,
                                 ImageView imageView,
                                 Context activity,
                                 final String path,
                                 String FolderNameToLoadFrom,
                                 final boolean saveToFolder,
                                 final AVLoadingIndicatorView avLoadingIndicatorView
    ) {

        final SSLSocketFactory sslSocketFactory;
        try {
            sslSocketFactory = new TLSSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        } catch (KeyManagementException ignored) {

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (type == 1) {

            circularProgressBar.setVisibility(View.VISIBLE);
            avLoadingIndicatorView.show();
            //
            //Type=1 ==> Image Is Not download
            //
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
            target.setModel(url); // update target's cache
            Glide.with(activity)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.defhome)
                    .error(R.drawable.defhome)      // optional// needs explicit transformation, because we're using a custom target
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            try {
                                avLoadingIndicatorView.hide();
                                circularProgressBar.setVisibility(View.GONE);

                            } catch (Exception ex) {

                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            try {
//                                String path = Environment.getExternalStorageDirectory() +
//                                        File.separator + ".WizLock";
                                if (saveToFolder)
                                    storeImage(resource, path, ImageName);
                            } catch (Exception ex) {
                                String s = "";
                            }

                            try {
                                circularProgressBar.setVisibility(View.GONE);
                                avLoadingIndicatorView.hide();
                            } catch (Exception ex) {

                            }
                            return false;
                        }
                    })
                    .into(target);

            imageTitle.setText(ImageName);
        } else if (type == 2) {
            try {
                circularProgressBar.setVisibility(View.GONE);
                avLoadingIndicatorView.hide();
            } catch (Exception ex) {
                String asd = "";
            }

            //
            //Type=2 ==> Image Is Downloaded
            //
            String CurrentString = Environment.getExternalStorageDirectory() +
                    File.separator + FolderNameToLoadFrom + File.separator + ImageName;

            //vali
            Uri uri = Uri.fromFile(new File(CurrentString));
            imageTitle.setText(ImageName);
            Glide.with(activity)
                    .load(uri)
                    .placeholder(R.drawable.defhome)   // optional
                    .error(R.drawable.defhome)      // optional
                    .into(imageView);

        }

    }

    static String TAG = "";

    public static void storeImage(Bitmap image, String path, String imageName) {
        File pictureFile = getOutputMediaFile(path, imageName);
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        new AndroidBmpUtil().save(image, String.valueOf(pictureFile));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(String path, String imageName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(path);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
//        String mImageName="MI_"+ timeStamp +".png";
        String mImageName = imageName;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
