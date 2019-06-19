package ir.mlibs.myupload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

import ir.mlibs.myplayer.LoadMediaData;

public class UploadVideoActivity extends AppCompatActivity {

    private static final int VIDEO_GALLERY = 1;
    private static final int VIDEO_CAPTURE = 2;
    Uri videoUri;
    String Path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);

        if (utilitys.isGallery) {
            requestStoragePermission();

        } else {
            try {
                requestStorageandcameraPermission();
            } catch (Exception ex) {
                String asd = "";
            }

        }
    }


    public void startRecordingVideo() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File mediaFile = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");
            videoUri = Uri.fromFile(mediaFile);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60); //10 sec
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5242880L);//5*1024*1024=5MiB
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            try {
                startActivityForResult(intent, VIDEO_CAPTURE);
            } catch (Exception ex) {
                int i = 0;


            }
        } catch (Exception ex) {
            String asd = "";
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode == VIDEO_GALLERY) {
            if (data != null && !data.equals("")) {
                Uri selectedImageUri = data.getData();
                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);
                if (selectedImagePath != null) {

                    Path = selectedImagePath;
//                    MultiMessageButton("Title",
//                            "Message",
//                            UploadVideoActivity.this,
//                            true,
//                            true,
//                            "upload",
//                            "cancel",
//                            "play",
//                            Gravity.RIGHT,
//                            Gravity.RIGHT,
//                            false);
                    MultiMessageButton(utilitys.DialogTitle,
                            utilitys.DialogMessage,
                            UploadVideoActivity.this,
                            utilitys.showdialogtitle,
                            utilitys.showdialogmessage,
                            utilitys.DialogUploadTitle,
                            utilitys.DialogcancelTitle,
                            utilitys.DialogplayTitle,
                            Gravity.RIGHT,
                            Gravity.RIGHT,
                            utilitys.isdialogcancelable);
                }
            } else {
                finish();
            }
        }
        if (requestCode == VIDEO_CAPTURE) {

            if (resultCode == RESULT_OK) {

                Uri uu = getLastPhotoOrVideo(UploadVideoActivity.this);

                Path = uu.getPath();
                //play(Path);
                MultiMessageButton(utilitys.DialogTitle,
                        utilitys.DialogMessage,
                        UploadVideoActivity.this,
                        utilitys.showdialogtitle,
                        utilitys.showdialogmessage,
                        utilitys.DialogUploadTitle,
                        utilitys.DialogcancelTitle,
                        utilitys.DialogplayTitle,
                        Gravity.RIGHT,
                        Gravity.RIGHT,
                        utilitys.isdialogcancelable);
            } else {
                finish();
            }
        }
    }

    Uri getLastPhotoOrVideo(Context context) {
        String[] columns = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED};

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);

        cursor.close();

        return Uri.fromFile(new File(path));
    }


    public void MultiMessageButton(String Title,
                                   String Message,
                                   final Activity activity,
                                   boolean showTitle,
                                   boolean showmessage,
                                   String positiveText,
                                   String negativeText,
                                   String ShowOrPlaytext,
                                   int Titlegravity,
                                   int messagegravity,
                                   boolean setCancelable
    ) {


        final Dialog retryDialog = new Dialog(activity);

        try {

            retryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        retryDialog.setCancelable(setCancelable);
        retryDialog.setContentView(R.layout.mmessagebox);
        retryDialog.show();

        TextView tv_Title = (TextView) retryDialog.findViewById(R.id.Title);
        TextView tv_Message = (TextView) retryDialog.findViewById(R.id.Message);
        TextView tv_positive = (TextView) retryDialog.findViewById(R.id.positive);
        TextView tv_negative = (TextView) retryDialog.findViewById(R.id.negative);
        TextView tv_show = (TextView) retryDialog.findViewById(R.id.textviewshow);
        LinearLayout ll_positive = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_positive);
        LinearLayout ll_negative = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_negative);
        LinearLayout ll_show = (LinearLayout) retryDialog.findViewById(R.id.linearLayout_show);

        tv_Title.setText(Title);
        tv_Title.setGravity(Titlegravity);

        tv_Message.setText(Message);
        tv_Message.setGravity(messagegravity);


        tv_negative.setText(negativeText);
        tv_positive.setText(positiveText);
        tv_show.setText(ShowOrPlaytext);

        if (!showmessage) {
            tv_Message.setVisibility(View.GONE);
        }
        if (!showTitle) {
            tv_Title.setVisibility(View.GONE);
        }

        ll_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryDialog.dismiss();
                upload();
            }
        });


        ll_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                retryDialog.dismiss();
            }
        });

        ll_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadMediaData.ShowMedia(UploadVideoActivity.this,
                        Path,
                        "",
                        true,
                        false,false,"http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
            }
        });
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.setType("video/*");
                            startActivityForResult(galleryIntent, VIDEO_GALLERY);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
//                            String items1=response.getPermissionName().toString().replace(".","-");
//                            String[] items=items1.split("-");
//                            showSettingsDialog(items[items.length-1]);

                            List<PermissionDeniedResponse> pp = report.getDeniedPermissionResponses();
                            String permisionsNeed = "";
                            for (int i = 0; i < pp.size(); i++) {

                                String items1 = pp.get(i).getPermissionName().toString().replace(".", "-");
                                String[] items = items1.split("-");
                                if (i == 0)
                                    permisionsNeed = (items[items.length - 1]);
                                else
                                    permisionsNeed += " , " + (items[items.length - 1]);
                            }
                            showSettingsDialog(permisionsNeed);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void requestStorageandcameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            startRecordingVideo();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
//                            String items1=response.getPermissionName().toString().replace(".","-");
//                            String[] items=items1.split("-");
//                            showSettingsDialog(items[items.length-1]);

                            List<PermissionDeniedResponse> pp = report.getDeniedPermissionResponses();
                            String permisionsNeed = "";
                            for (int i = 0; i < pp.size(); i++) {

                                String items1 = pp.get(i).getPermissionName().toString().replace(".", "-");
                                String[] items = items1.split("-");
                                if (i == 0)
                                    permisionsNeed = (items[items.length - 1]);
                                else
                                    permisionsNeed += " , " + (items[items.length - 1]);
                            }
                            showSettingsDialog(permisionsNeed);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog(String PermissionnName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadVideoActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs " + PermissionnName + " permission. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void upload() {

        if (utilitys.UploadUrl == null || utilitys.UploadUrl.equals("")) {
            //    UsermessageBox("ارسال با موفقیت انجام نشد. مجددا تلاش نمایید!");
            return;
        }
        if (Path.equals("")) {

            //    UsermessageBox("ارسال با موفقیت انجام نشد. مجددا تلاش نمایید!");

            return;
        }

        File fileChecker = new File(Path);
        long filesize = fileChecker.length();

        if (filesize <= 0) {
//            UsermessageBox("حجم فیلم ارسالی صفر می باشد.فیلمی با حجم بیشتر از صفر انتخاب کنید!");
            return;
        }

        // load data file
        String fileDuration = "00:00";
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        try {

            metaRetriever.setDataSource(Path);

            String out = "";
            // get mp3 info

            // convert duration to minute:seconds
            String duration =
                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            long dur = Long.parseLong(duration);
            fileDuration = String.valueOf((dur % 60000) / 1000);

            // close object
            metaRetriever.release();
        } catch (Exception ex) {
            metaRetriever.release();
//            UsermessageBox("مدت زمان فیلم ارسالی صفر می باشد.فیلم با مدت زمان بیشتری انتخاب نمایید!");

            return;
        }

        File file = new File(Path);
        try {
            AndroidNetworking.upload(utilitys.UploadUrl)
                    .addMultipartFile("video", file)
                    .addMultipartParameter("GUID", "SP_Guid")
                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            // do anything with progress
                            //TV.setText("%" + ((int) ((100 * bytesUploaded) / totalBytes)) + "");
                            int progress = ((int) ((100 * bytesUploaded) / totalBytes));
                            //mProgressDialog.setProgress((int) ((100 * bytesUploaded) / totalBytes));

                        }
                    })
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {

                        }

                        @Override
                        public void onError(ANError ANError) {
                            // UsermessageBox("فیلم مورد نظر ارسال نشد.مجددا ارسال نمایید!");

                        }
                    });
        } catch (Exception ex) {

        }
    }

    public void sendBroadcast() {

        Intent broadcast = new Intent();
        broadcast.putExtra("BroadCastNames",utilitys.broadcastTitle);
        broadcast.setAction(utilitys.broadcastTitle);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcast);

    }
}
