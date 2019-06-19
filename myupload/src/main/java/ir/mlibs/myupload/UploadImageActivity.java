package ir.mlibs.myupload;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ir.mlibs.myplayer.LoadMediaData;
import ir.mlibs.myupload.clib.BitmapHelper;
import ir.mlibs.myupload.clib.CameraIntentHelper;
import ir.mlibs.myupload.clib.CameraIntentHelperCallback;

import static ir.mlibs.myupload.utilitys.CROPPATH;
import static ir.mlibs.myupload.utilitys.CROPUSE;
import static java.security.AccessController.getContext;

public class UploadImageActivity extends AppCompatActivity {
    private static final int GALLARY_REQUEST = 1;
    private static final int VIDEO_CAPTURE = 2;
    Uri imageUri;
    String Path = "";


    // This tag is used for error or debug log.
    private static final String TAG_TAKE_PICTURE = "TAKE_PICTURE";
    // This is the request code when start camera activity use implicit intent.
    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    // This output image file uri is used by camera app to save taken picture.
    private Uri outputImgUri;
    // Save the camera taken picture in this folder.
    private File pictureSaveFolderPath;

    // Save imageview currently displayed picture index in all camera taken pictures..
    private int currentDisplayImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        if (utilitys.isGallery) {
            requestStoragePermission();

        } else {
            try {
                requestStorageandcameraPermission();
            } catch (Exception ex) {
                String asd = "";
            }

        }
        if (CROPUSE) {
            IntentFilter filter_POSITIVE = new IntentFilter();
            filter_POSITIVE.addAction("BROADCAST_ACTION_CROPIMAGE");
            filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(broadcastReceiver, filter_POSITIVE);
        }

    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic1.jpg");
        //imageCounter++;
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        //Statics.image=imageUri;
        startActivityForResult(intent, 3);
    }

    public void startRecordingVideo() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    // Log.i(TAG, "IOException");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, VIDEO_CAPTURE);
                }
            }
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////            File mediaFile = new File(
////                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pic.jpg");
////            intent.putExtra(MediaStore.EXTRA_OUTPUT,
////                    Uri.fromFile(mediaFile));
////            imageUri = Uri.fromFile(mediaFile);
//            startActivityForResult(intent, VIDEO_CAPTURE);

        } catch (Exception ex) {
            String asd = "";
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        Path = "file:" + image.getAbsolutePath();
        return image;
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
        if (requestCode == GALLARY_REQUEST) {
            if (data != null && !data.equals("")) {
                if (data == null) {
//                    sendBroadcast("response", "");
                    finish();
                } else {
                    Uri selectedImageUri = data.getData();
                    // MEDIA GALLERY
                    String selectedImagePath = getPath(selectedImageUri);
                    if (selectedImagePath != null) {

                        Path = selectedImagePath;
                        if (CROPUSE) {

                            Intent intent = new Intent(UploadImageActivity.this, CropActivity.class);
                            intent.putExtra("IMAGE", Path);
                            startActivity(intent);
                        } else {
                            MultiMessageButton(utilitys.DialogTitle,
                                    utilitys.DialogMessage,
                                    UploadImageActivity.this,
                                    utilitys.showdialogtitle,
                                    utilitys.showdialogmessage,
                                    utilitys.DialogUploadTitle,
                                    utilitys.DialogcancelTitle,
                                    utilitys.DialogplayTitle,
                                    Gravity.RIGHT,
                                    Gravity.RIGHT,
                                    utilitys.isdialogcancelable);
                        }


                    }
                }

            } else {
                finish();
            }
        } else {
            if (resultCode == -1)
                mCameraIntentHelper.onActivityResult(requestCode, resultCode, data);
            else {

                finish();
            }
        }
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
        final ImageView iv_image = (ImageView) retryDialog.findViewById(R.id.Imageview_Main);


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

                iv_image.setVisibility(View.VISIBLE);
//                iv_image.setImageURI(Uri.parse(Path));
                File f = new File(Path);
                if (f.exists()) {
                    Picasso.with(UploadImageActivity.this).load(f).into(iv_image);
                } else {
                    String asd = "";
                }

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
                            startActivityForResult(galleryIntent, GALLARY_REQUEST);

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {


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

                            setupCameraIntentHelper();
                            if (mCameraIntentHelper != null) {
                                mCameraIntentHelper.startCameraIntent();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
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
        broadcast.putExtra("BroadCastNames", utilitys.broadcastTitle);
        broadcast.setAction(utilitys.broadcastTitle);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcast);

    }

    CameraIntentHelper mCameraIntentHelper;
    String MainCroppath = "";

    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper(this, new CameraIntentHelperCallback() {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                //messageView.setText(getString(R.string.activity_camera_intent_photo_uri_found) + photoUri.toString());

                Bitmap photo = BitmapHelper.readBitmap(UploadImageActivity.this, photoUri);
                if (photo != null) {

                    Path = String.valueOf(photoUri);
                    Path = Path.toString().replace("file:///", "/");
                    if (CROPUSE) {



                        Intent intent = new Intent(UploadImageActivity.this, CropActivity.class);
                        intent.putExtra("IMAGE", Path);
                        startActivity(intent);
                    } else {
                        MultiMessageButton(utilitys.DialogTitle,
                                utilitys.DialogMessage,
                                UploadImageActivity.this,
                                utilitys.showdialogtitle,
                                utilitys.showdialogmessage,
                                utilitys.DialogUploadTitle,
                                utilitys.DialogcancelTitle,
                                utilitys.DialogplayTitle,
                                Gravity.RIGHT,
                                Gravity.RIGHT,
                                utilitys.isdialogcancelable);
                    }


//                    UCrop.of(photoUri, Uri.fromFile(outDirs))
//                            .start(UploadImageActivity.this);
                }
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {
                BitmapHelper.deleteImageWithUriIfExists(photoUri, UploadImageActivity.this);
            }

            @Override
            public void onSdCardNotMounted() {
                //Toast.makeText(getApplicationContext(), getString(R.string.error_sd_card_not_mounted), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCanceled() {
                // Toast.makeText(getApplicationContext(), getString(R.string.warning_camera_intent_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCouldNotTakePhoto() {
                //Toast.makeText(getApplicationContext(), getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPhotoUriNotFound() {
//                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_not_found));
                // Toast.makeText(getApplicationContext(), getString(R.string.activity_camera_intent_photo_uri_not_found), Toast.LENGTH_LONG).show();
            }

            @Override
            public void logException(Exception e) {
                //Toast.makeText(getApplicationContext(), getString(R.string.error_sth_went_wrong), Toast.LENGTH_LONG).show();
//                Log.d(getClass().getName(), e.getMessage());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (mCameraIntentHelper != null) {
            mCameraIntentHelper.onSaveInstanceState(savedInstanceState);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCameraIntentHelper.onRestoreInstanceState(savedInstanceState);
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            unregisterReceiver(broadcastReceiver);
            String TYPES = intent.getStringExtra("BroadCastNames");
            if (TYPES.equals("BROADCAST_ACTION_CROPIMAGE")) {
                Path = CROPPATH;
                MultiMessageButton(utilitys.DialogTitle,
                        utilitys.DialogMessage,
                        UploadImageActivity.this,
                        utilitys.showdialogtitle,
                        utilitys.showdialogmessage,
                        utilitys.DialogUploadTitle,
                        utilitys.DialogcancelTitle,
                        utilitys.DialogplayTitle,
                        Gravity.RIGHT,
                        Gravity.RIGHT,
                        utilitys.isdialogcancelable);
            }

        }
    };


}
