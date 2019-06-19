package ir.mlibs.messagebox;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.io.File;

import messageboxlibraryimageprogressbar.LoadImageGlide;
import messageboxlibraryimageprogressbar.MyProgressTarget;
import messageboxlibraryimageprogressbar.OkHttpProgressGlideModule;
import messageboxlibraryimageprogressbar.ProgressTarget;

/**
 * Created by vali on 2018-03-06.
 */

public class CustomMessageBox {

    public static String FinishAndDismiss = "FinishAndDismiss";
    public static String dismiss = "dismiss";
    public static String BROADCASTS_ACTION = "BROADCASTS_ACTION";
    public static String Actions = "Actions";
    public static String SHOWACTIVITY = "SHOWACTIVITY";
    public static String DOWNLOAD = "DOWNLOAD";
    public static boolean ISCOMPLETED = false;

    static Activity mainactivity;
    static String BroadCastNames = "All";



    public static void MultiMessageButton(String Title,
                                          String Message,
                                          final Activity activity,
                                          boolean showTitle,
                                          boolean showmessage,
                                          String positiveText,
                                          String negativeText,
                                          final String positive_type,
                                          final String negative_type,
                                          int Titlegravity,
                                          int messagegravity,
                                          boolean setCancelable,
                                          final String  positive_BroadCastName,
                                          final String  positive_PackageName,
                                          final String  positive_ActivityName,
                                          final String  negative_BroadCastName,
                                          final String  negative_PackageName,
                                          final String  negative_ActivityName,
                                          boolean haveImage,
                                          String ImageUrl
    ) {

        mainactivity = activity;


        final Dialog retryDialog = new Dialog(activity);

        try {

            retryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        retryDialog.setCancelable(setCancelable);
        retryDialog.setContentView(R.layout.mainmessagebox);
        retryDialog.show();

        final Glide glide = Glide.get(activity);
        OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
        a.registerComponents(activity, glide);

        AVLoadingIndicatorView avis;
        avis = (AVLoadingIndicatorView) retryDialog.findViewById(R.id.aviLoading);
        TextView tv_pb;
        tv_pb = (TextView) retryDialog.findViewById(R.id.TextView_progressbar);
        ProgressBar pb;
        pb = (ProgressBar) retryDialog.findViewById(R.id.Progressbar_load);
        ImageView iv_message = (ImageView) retryDialog.findViewById(R.id.imageview_message);
        RelativeLayout rl_iv_message = (RelativeLayout) retryDialog.findViewById(R.id.RelativeLayout_image);
        TextView tv_Title = (TextView) retryDialog.findViewById(R.id.Title);
        TextView tv_Message = (TextView) retryDialog.findViewById(R.id.Message);
        TextView tv_positive = (TextView) retryDialog.findViewById(R.id.positive);
        TextView tv_positive_single = (TextView) retryDialog.findViewById(R.id.positive_single);
        TextView tv_negative = (TextView) retryDialog.findViewById(R.id.negative);
        LinearLayout ll_positive = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_positive);
        LinearLayout ll_negative = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_negative);

        tv_Title.setText(Title);
        tv_Title.setGravity(Titlegravity);

        tv_Message.setText(Message);
        tv_Message.setGravity(messagegravity);
        tv_Message.setMovementMethod(new ScrollingMovementMethod());


        tv_negative.setText(negativeText);
        tv_positive.setText(positiveText);
        tv_positive_single.setText(positiveText);

        if (haveImage && !ImageUrl.equals("")) {
            rl_iv_message.setVisibility(View.VISIBLE);
            ProgressTarget<String, Bitmap> target;//
            target = new MyProgressTarget<>(new BitmapImageViewTarget(iv_message), pb, iv_message, tv_pb, avis);
            LoadImageGlide.fillGlide(ImageUrl,
                    target,
                    pb,
                    tv_pb,
                    1,
                    1,
                    "",
                    null,
                    activity,
                    "",
                    ".CustomMessageBox",
                    false,
                    avis);
        }else{
            rl_iv_message.setVisibility(View.GONE);
        }

        if (!showmessage) {
            tv_Message.setVisibility(View.GONE);
        }
        if (!showTitle) {
            tv_Title.setVisibility(View.GONE);
        }

        ll_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (positive_type) {

                    case "FinishAndDismiss": {
                        activity.finish();
                        retryDialog.dismiss();
                    }
                    break;

                    case "dismiss": {

                        retryDialog.dismiss();
                    }
                    break;

                    case "BROADCASTS_ACTION": {
                        BroadCastNames =  positive_BroadCastName == null ? "All_no" :  positive_BroadCastName.equals("") ? "All_no" :  positive_BroadCastName;
                        if (!BroadCastNames.equals("All_no"))
                            sendBroadcast();
                        retryDialog.dismiss();
                    }
                    break;

                    case "SHOWACTIVITY": {
                        Intent intent;
                        Class myclass = null;
                        try {
                            String paths = positive_PackageName + "." + positive_ActivityName;
                            myclass = Class.forName(paths);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        intent = new Intent(activity, myclass);
                        activity.startActivity(intent);
                        retryDialog.dismiss();
                    }
                    break;

                }

            }
        });


        ll_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (negative_type) {
                    case "FinishAndDismiss": {
                        activity.finish();
                        retryDialog.dismiss();
                    }
                    break;

                    case "dismiss": {
                        retryDialog.dismiss();
                    }
                    break;

                    case "BROADCASTS_ACTION": {
                        BroadCastNames =  negative_BroadCastName == null ? "All_no" :
                                negative_BroadCastName.equals("") ? "All_no" :  negative_BroadCastName;
                        if (!BroadCastNames.equals("All_no"))
                            sendBroadcast();
                        retryDialog.dismiss();
                    }
                    break;

                    case "SHOWACTIVITY": {
                        Intent intent;
                        Class myclass = null;
                        try {
                            String paths = negative_PackageName + "." + negative_ActivityName;
                            myclass = Class.forName(paths);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        intent = new Intent(activity, myclass);
                        activity.startActivity(intent);
                        retryDialog.dismiss();
                    }
                    break;

                }
            }
        });
    }


    public static void SingleMessageButton(String Title,
                                           String Message,
                                           final Activity activity,
                                           boolean showTitle,
                                           boolean showmessage,
                                           String positiveText,
                                           final String Action_type,
                                           int Titlegravity,
                                           int messagegravity,
                                           boolean setCancelable,
                                           String BroadCastName,
                                           final String PackageName,
                                           final String ActivityName,
                                           boolean haveImage,
                                           String ImageUrl,
                                           final boolean isDownloadFile,
                                           final String DownloadUrl,
                                           final String PathFolderName,
                                           final String FileName
    ) {

        mainactivity = activity;
        BroadCastNames = BroadCastName == null ? "All_no" : BroadCastName.equals("") ? "All_no" : BroadCastName;
        final Dialog retryDialog = new Dialog(activity);
        try {

            retryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        retryDialog.setCancelable(setCancelable);
        retryDialog.setContentView(R.layout.mainmessagebox);
        retryDialog.show();

        final Glide glide = Glide.get(activity);
        OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
        a.registerComponents(activity, glide);

        AVLoadingIndicatorView avis;
        avis = (AVLoadingIndicatorView) retryDialog.findViewById(R.id.aviLoading);
        TextView tv_pb;
        tv_pb = (TextView) retryDialog.findViewById(R.id.TextView_progressbar);
        ProgressBar pb;
        final TextView tv_download;
        final ImageView iv_refresh_download;
        final LinearLayout ll_download;
        final ProgressBar pb_download;
        pb = (ProgressBar) retryDialog.findViewById(R.id.Progressbar_load);
        ll_download = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_download);
        iv_refresh_download = (ImageView) retryDialog.findViewById(R.id.imageview_refresh);
        tv_download = (TextView) retryDialog.findViewById(R.id.Title_download);
        pb_download = (ProgressBar) retryDialog.findViewById(R.id.Progressbar_download);
        ImageView iv_message = (ImageView) retryDialog.findViewById(R.id.imageview_message);
        RelativeLayout rl_iv_message = (RelativeLayout) retryDialog.findViewById(R.id.RelativeLayout_image);

        TextView tv_Title = (TextView) retryDialog.findViewById(R.id.Title);
        final TextView tv_Message = (TextView) retryDialog.findViewById(R.id.Message);
        TextView tv_positive = (TextView) retryDialog.findViewById(R.id.positive);
        final TextView tv_positive_single = (TextView) retryDialog.findViewById(R.id.positive_single);
        TextView tv_negative = (TextView) retryDialog.findViewById(R.id.negative);
        LinearLayout ll_single = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_single);
        LinearLayout ll_double = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_double);
        LinearLayout ll_positive_single = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_single_button);
        LinearLayout ll_negative = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_negative);

        tv_Title.setText(Title);
        tv_Title.setGravity(Titlegravity);

        tv_Message.setText(Message);
        tv_Message.setGravity(messagegravity);

        tv_positive.setText(positiveText);
        tv_positive_single.setText(positiveText);

        if (haveImage && !ImageUrl.equals("")) {
            rl_iv_message.setVisibility(View.VISIBLE);
            ProgressTarget<String, Bitmap> target;//
            target = new MyProgressTarget<>(new BitmapImageViewTarget(iv_message), pb, iv_message, tv_pb, avis);
            LoadImageGlide.fillGlide(ImageUrl,
                    target,
                    pb,
                    tv_pb,
                    1,
                    1,
                    "",
                    null,
                    activity,
                    "",
                    ".CustomMessageBox",
                    false,
                    avis);
        }else{
            rl_iv_message.setVisibility(View.GONE);
        }

        if (!showmessage) {
            tv_Message.setVisibility(View.GONE);
        }
        if (!showTitle) {
            tv_Title.setVisibility(View.GONE);
        }

        ll_negative.setVisibility(View.GONE);
        ll_double.setVisibility(View.GONE);
        ll_single.setVisibility(View.VISIBLE);

        ll_positive_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Action_type) {
                    case "FinishAndDismiss": {
                        activity.finish();
                        retryDialog.dismiss();
                    }
                    break;

                    case "dismiss": {
                        retryDialog.dismiss();
                    }
                    break;
                    case "DOWNLOAD": {
                        if(ISCOMPLETED)
                        {
                            retryDialog.dismiss();
                        }else{
                            AndroidNetworking.forceCancel("CustomMessageTag");
                            retryDialog.dismiss();
                        }
                    }
                    break;

                    case "BROADCASTS_ACTION": {
                        if (!BroadCastNames.equals("All_no"))
                            sendBroadcast();
                        retryDialog.dismiss();
                    }
                    break;

                    case "SHOWACTIVITY": {
                        Intent intent;
                        Class myclass = null;
                        try {
                            String paths = PackageName + "." + ActivityName;
                            myclass = Class.forName(paths);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        intent = new Intent(activity, myclass);
                        activity.startActivity(intent);
                        retryDialog.dismiss();
                    }
                    break;
                }

            }
        });
        iv_refresh_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDownloadFile)//download
                {
                    pb_download.setProgress(0);
                    tv_download.setText("%0");
                    ll_download.setVisibility(View.VISIBLE);
                    tv_download.setVisibility(View.VISIBLE);
                    iv_refresh_download.setVisibility(View.GONE);
                    tv_Message.setVisibility(View.GONE);
                    AndroidNetworking.download(DownloadUrl, Environment.getExternalStorageDirectory() +
                            File.separator + PathFolderName, FileName+"_"+Uri.parse(DownloadUrl).getLastPathSegment())
                            .setTag("CustomMessageTag")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .setDownloadProgressListener(new DownloadProgressListener() {
                                @Override
                                public void onProgress(long bytesDownloaded, long totalBytes) {
                                    // do anything with progress
                                    int progress = (int) (bytesDownloaded * 100 / totalBytes);
                                    pb_download.setProgress(progress);
                                    tv_download.setText("%"+progress);
                                }
                            })
                            .startDownload(new DownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    // do anything after completion
                                    ISCOMPLETED=true;
                                    tv_positive_single.setText("خروج");
                                    tv_Message.setVisibility(View.VISIBLE);
                                    tv_Message.setText("مسیر فایل دانلود:\n"+Environment.getExternalStorageDirectory()
                                            +File.separator + PathFolderName+File.separator+FileName+"_"+Uri.parse(DownloadUrl).getLastPathSegment());
                                }

                                @Override
                                public void onError(ANError anError) {
                                    iv_refresh_download.setVisibility(View.VISIBLE);
                                    tv_Message.setVisibility(View.VISIBLE);
                                    tv_Message.setTextColor(activity.getResources().getColor(R.color.teal_700));
                                }

                            });
                }
            }
        });
        if(isDownloadFile)//download
        {
            ll_download.setVisibility(View.VISIBLE);
            tv_download.setVisibility(View.VISIBLE);
            iv_refresh_download.setVisibility(View.GONE);
            AndroidNetworking.download(DownloadUrl, Environment.getExternalStorageDirectory() +
                    File.separator + PathFolderName,FileName+"_"+Uri.parse(DownloadUrl).getLastPathSegment())
                    .setTag("CustomMessageTag")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {
                            // do anything with progress
                            int progress = (int) (bytesDownloaded * 100 / totalBytes);
                            pb_download.setProgress(progress);
                            tv_download.setText("%"+progress);
                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            // do anything after completion
                            ISCOMPLETED=true;
                            tv_positive_single.setText("خروج");
                            tv_Message.setVisibility(View.VISIBLE);
                            tv_Message.setText("مسیر فایل دانلود:\n"+Environment.getExternalStorageDirectory()
                                    +File.separator + PathFolderName+File.separator+FileName+"_"+Uri.parse(DownloadUrl).getLastPathSegment());
                        }

                        @Override
                        public void onError(ANError anError) {
                            iv_refresh_download.setVisibility(View.VISIBLE);
                            tv_Message.setVisibility(View.VISIBLE);
                            tv_Message.setTextColor(activity.getResources().getColor(R.color.teal_700));
                        }

                    });
        }

    }


    public static void sendBroadcast() {

        Intent broadcast = new Intent();
        broadcast.putExtra("BroadCastNames",BroadCastNames);
        broadcast.setAction(BroadCastNames);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        mainactivity.sendBroadcast(broadcast);

    }
}
