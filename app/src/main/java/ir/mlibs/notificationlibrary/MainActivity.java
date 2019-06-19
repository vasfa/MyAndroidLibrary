package ir.mlibs.notificationlibrary;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

//import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Fragments.SideMenuFragment;
import ir.mlibs.myplayer.LoadMediaData;
import ir.mlibs.myupload.upload;
import ir.mlibs.varitynotification.notification;
import ir.mlibs.deeplinking.parsingdeppLinking;
import ir.mlibs.messagebox.CustomMessageBox;
import ir.mlibs.sliderfragmentgallery.Slider;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    SideMenuFragment newNavigationDrawerFragment;

    RadioButton radioB;
    LinearLayout ll_radioB;


    RelativeLayout relativeLayout;
    ImageView iv_menu;

    Button Button_StickyView;
    Button Rolet;

    Button VisibileAll;
    Button HideAll;


    Button Uploadvideogallery;
    Button Uploadvideocamera;

    Button Uploadimagegallery;
    Button Uploadimagecamera;

    Button UploadimagegalleryWithDefaultCropSetting;
    Button UploadimagecameraWithDefaultCropSetting;

    Button UploadimagegalleryShowCropSetting;
    Button UploadimagecameraShowCropSetting;

    Button UploadimagegalleryWithCustomizeCropSetting;
    Button UploadimagecameraWithCustomizeCropSetting;


    Button MPlayer;
    Button MPlayer_non_video_aparat;
    Button MPlayer_sound;
    Button MPlayer_sound_with_image;
    Button MPlayerFullScreen;
    Button MPlayerFullScreenSound;
    Button MPlayerFullScreenSound_with_image;


    Button fragment_slider_1;
    Button fragment_slider_2;


    Button RecyclerView_Style1;
    Button RecyclerView_Style2;
    Button RecyclerView_Style3;
    Button RecyclerView_Style4;
    Button RecyclerView_Style5;
    Button RecyclerView_Style6;
    Button RecyclerView_Style7;
    Button RecyclerView_Style8;

    Button Requesting_Single_Permission;
    Button Requesting_Multiple_Permission;

    Button Button_slider_style1;
    Button Button_slider_style2;
    Button Button_slider_style3;
    Button Button_slider_style4;


    Button custommessagebox_style1;
    Button custommessagebox_style2;
    Button custommessagebox_style3;
    Button custommessagebox_style4;

    Button btn_doNoting_notification;
    Button btn_openApplication_notification;
    Button btn_showsite_notification;
    Button btn_showSpacialActivity_notification;
    Button btn_openPhoneDialort_notification;
    Button btn_SendSms_notification;
    Button btn_email_notification;
    Button btn_button_notification;

    Button Image_btn_doNoting_notification;
    Button Image_btn_openApplication_notification;
    Button Image_btn_showsite_notification;
    Button Image_btn_showSpacialActivity_notification;
    Button Image_btn_openPhoneDialort_notification;
    Button Image_btn_SendSms_notification;
    Button Image_btn_email_notification;


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog(String PermissionnName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    private void requestCameraPermission() {
        Dexter.withActivity(MainActivity.this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                            String items1 = response.getPermissionName().toString().replace(".", "-");
                            String[] items = items1.split("-");
                            showSettingsDialog(items[items.length - 1]);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNavigationDrawerFragment = (SideMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_new_navigation_drawer);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_navigation_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.Drawer);
        drawerLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
        iv_menu = (ImageView) findViewById(R.id.imageView_menu);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            private float last = 0;

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

                boolean opening = arg1 > last;
                boolean closing = arg1 < last;

                if (opening) {
//                    newNavigationDrawerFragment.changeMyImage();
//                        Log.i("Drawer","opening");
                } else if (closing) {

//                        Log.i("Drawer","closing");
                } else {
                    //Log.i("Drawer","doing nothing");
                }

                last = arg1;
            }

            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            @Override
            public void onDrawerOpened(View arg0) {
            }

            @Override
            public void onDrawerClosed(View arg0) {
            }

        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(relativeLayout)) {
                    drawerLayout.closeDrawer(relativeLayout);
                } else {
                    drawerLayout.openDrawer(relativeLayout);
                }
            }
        });

        radioB = (RadioButton) findViewById(R.id.radioB);
        ll_radioB = (LinearLayout) findViewById(R.id.Linearlayout_radioB);
        ll_radioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        VisibileAll = (Button) findViewById(R.id.VisibileAll);
        VisibileAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNavigationDrawerFragment.show_hide_items(true,
                        true, true, true, true, true, true);
            }
        });
        Button_StickyView = (Button) findViewById(R.id.Button_StickyView);
        Button_StickyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StickyScrollViewActivity.class);
                startActivity(intent);
            }
        });
        Rolet = (Button) findViewById(R.id.Rolet);
        Rolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestRoletActivity.class);
                startActivity(intent);
            }
        });
        HideAll = (Button) findViewById(R.id.HideAll);
        HideAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNavigationDrawerFragment.show_hide_items(false,
                        false, false, false, false, false, false);
            }
        });

        parsingdeppLinking parsingdeppLinking = new parsingdeppLinking();

        Uploadvideogallery = (Button) findViewById(R.id.Uploadvideogallery);
        Uploadvideogallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadVideo(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "");
            }
        });
        Uploadvideocamera = (Button) findViewById(R.id.Uploadvideocamera);
        Uploadvideocamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadVideo(MainActivity.this, false, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "");
            }
        });


        Uploadimagegallery = (Button) findViewById(R.id.Uploadimagegallery);
        Uploadimagegallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImage(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "");
            }
        });
        Uploadimagecamera = (Button) findViewById(R.id.Uploadimagecamera);
        Uploadimagecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImage(MainActivity.this, false, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "");
            }
        });

        UploadimagegalleryWithDefaultCropSetting = (Button) findViewById(R.id.UploadimagegalleryWithDefaultCropSetting);
        UploadimagegalleryWithDefaultCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageWithDefaultCropSetting(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", true);
            }
        });
        UploadimagecameraWithDefaultCropSetting = (Button) findViewById(R.id.UploadimagecameraWithDefaultCropSetting);
        UploadimagecameraWithDefaultCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageWithDefaultCropSetting(MainActivity.this, false, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", false);
            }
        });


        UploadimagegalleryShowCropSetting = (Button) findViewById(R.id.UploadimagegalleryShowCropSetting);
        UploadimagegalleryShowCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageShowCropSetting(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", true);
            }
        });
        UploadimagecameraShowCropSetting = (Button) findViewById(R.id.UploadimagecameraShowCropSetting);
        UploadimagecameraShowCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageShowCropSetting(MainActivity.this, false, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", false);
            }
        });


        UploadimagegalleryWithCustomizeCropSetting = (Button) findViewById(R.id.UploadimagegalleryWithCustomizeCropSetting);
        UploadimagegalleryWithCustomizeCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageWithCustomizeCropSetting(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", true, true, 80, true, 3,
                        4, true, 40, true, 40, true, 1, true, 1, true, 1, true, Color.WHITE, true, Color.WHITE, true, Color.WHITE, true,
                        Color.WHITE, true, 1F, true, 1F, true, 1F, true, "png", true, true, true, true, true, true, true, true, true);
            }
        });
        UploadimagecameraWithCustomizeCropSetting = (Button) findViewById(R.id.UploadimagecameraWithCustomizeCropSetting);
        UploadimagecameraWithCustomizeCropSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.UploadImageWithCustomizeCropSetting(MainActivity.this, true, "uploadurl",
                        "dialog_title", "Dialog_message", "dialog_upload_title",
                        "dialog_cancel_title", "dialog_play_title", false, "", true, true, 80, true, 3,
                        4, true, 40, true, 40, true, 1, true, 1, true, 1, true, Color.WHITE, true, Color.WHITE, true, Color.WHITE, true,
                        Color.WHITE, true, 1F, true, 1F, true, 1F, true, "png", true, true, true, true, true, true, true, true, false);
            }
        });

        MPlayer = (Button) findViewById(R.id.MediaPlayer);
        MPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://aparat.com/etc/api/videooffact/videohash/nBxd2",
                        "زلزله",
                        true,
                        true,
                        false, "http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");

            }
        });
        MPlayer_non_video_aparat = (Button) findViewById(R.id.MediaPlayer_nonaparat);
        MPlayer_non_video_aparat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://pardischub.vasfa.ir/cc1.mp4",
                        "زلزله",
                        true,
                        false,
                        false, "http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");

            }
        });
        MPlayer_sound = (Button) findViewById(R.id.MediaPlayer_sound);
        MPlayer_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://pardischub.vasfa.ir/FROZENLet.mp3",
                        "زلزله",
                        false,
                        true,
                        false,
                        "");

            }
        });
        MPlayer_sound_with_image = (Button) findViewById(R.id.MediaPlayer_sound_image);
        MPlayer_sound_with_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://pardischub.vasfa.ir/FROZENLet.mp3",
                        "زلزله",
                        false,
                        true,
                        false,
                        "http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");

            }
        });
        MPlayerFullScreen = (Button) findViewById(R.id.MediaPlayerFullScreen);
        MPlayerFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://aparat.com/etc/api/videooffact/videohash/nBxd2",
                        "زلزله",
                        true,
                        true,
                        true, "http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");

//                LoadMediaData.ShowMedia(MainActivity.this,
//                        "http://pardischub.vasfa.ir/FROZENLet.mp3",
//                        "زلزله",
//                        false,
//                        true,
//                        true,"http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");

//                LoadMediaData.ShowMedia(MainActivity.this,
//                        "http://pardischub.vasfa.ir/cc1.mp4",
//                        "زلزله",
//                        true,
//                        false,
//                        true,"http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
                //
            }
        });
        MPlayerFullScreenSound = (Button) findViewById(R.id.MediaPlayerFullScreenSound);
        MPlayerFullScreenSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://pardischub.vasfa.ir/FROZENLet.mp3",
                        "زلزله",
                        false,
                        true,
                        true,
                        "");

            }
        });
        MPlayerFullScreenSound_with_image = (Button) findViewById(R.id.MediaPlayerFullScreenSound_Image);
        MPlayerFullScreenSound_with_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadMediaData.ShowMedia(MainActivity.this,
                        "http://pardischub.vasfa.ir/FROZENLet.mp3",
                        "زلزله",
                        false,
                        true,
                        true,
                        "http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");

            }
        });
        fragment_slider_1 = (Button) findViewById(R.id.fragment_slider_1);
        fragment_slider_2 = (Button) findViewById(R.id.fragment_slider_2);

        fragment_slider_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FragmentSliderinFragmentActivity.class);
                startActivity(intent);
            }
        });

        fragment_slider_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SliderInFragmentActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView_Style1 = (Button) findViewById(R.id.RecyclerView_Style1);
        RecyclerView_Style2 = (Button) findViewById(R.id.RecyclerView_Style2);
        RecyclerView_Style3 = (Button) findViewById(R.id.RecyclerView_Style3);
        RecyclerView_Style4 = (Button) findViewById(R.id.RecyclerView_Style4);
        RecyclerView_Style5 = (Button) findViewById(R.id.RecyclerView_Style5);
        RecyclerView_Style6 = (Button) findViewById(R.id.RecyclerView_Style6);
        RecyclerView_Style7 = (Button) findViewById(R.id.RecyclerView_Style7);
        RecyclerView_Style8 = (Button) findViewById(R.id.RecyclerView_Style8);

        RecyclerView_Style1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView_Style2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewBottomToTopActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView_Style3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewSwitchingBottmToTopActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView_Style4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewSwitchingTopToBottomActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView_Style5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewCenterActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView_Style6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewBothSideActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView_Style7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewCenterTwoActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView_Style8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewCenterThreeActivity.class);
                startActivity(intent);
            }
        });


        Requesting_Multiple_Permission = (Button) findViewById(R.id.Requesting_Multiple_Permission);
        Requesting_Single_Permission = (Button) findViewById(R.id.Requesting_Single_Permission);

        Requesting_Single_Permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestCameraPermission();
                } catch (Exception ex) {
                    String asd = "";
                }

            }
        });
        Requesting_Multiple_Permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestStoragePermission();
                } catch (Exception ex) {
                    String asd = "";
                }

            }
        });


        Button_slider_style1 = (Button) findViewById(R.id.Button_slider_style1);
        Button_slider_style1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
                ArrayList<Boolean> IS_APARAT = new ArrayList<>();
                ArrayList<String> imageurls = new ArrayList<String>();
                ArrayList<String> VIDEO_URL = new ArrayList<>();
                ArrayList<String> imageTitle = new ArrayList<String>();
                ArrayList<Integer> imageType = new ArrayList<Integer>();

                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");


                imageTitle.add("عکس شماره یک");
                imageTitle.add("عکس شماره دو");
                imageTitle.add("عکس شماره سه");


                imageType.add(1);
                imageType.add(1);
                imageType.add(1);

                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/nBxd2");
                VIDEO_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");

                IS_APARAT.add(true);
                IS_APARAT.add(false);
                IS_APARAT.add(true);

                IS_VIDEO.add(true);
                IS_VIDEO.add(false);
                IS_VIDEO.add(true);

                Slider.ShowSlideractivity(MainActivity.this, VIDEO_URL, IS_APARAT, IS_VIDEO, imageurls, imageTitle, imageType, 0, Slider.AnimtionType0, true, true, 4, 3000, 3000, true, false);

            }
        });
        Button_slider_style2 = (Button) findViewById(R.id.Button_slider_style2);
        Button_slider_style2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
                ArrayList<Boolean> IS_APARAT = new ArrayList<>();
                ArrayList<String> imageurls = new ArrayList<String>();
                ArrayList<String> VIDEO_URL = new ArrayList<>();
                ArrayList<String> imageTitle = new ArrayList<String>();
                ArrayList<Integer> imageType = new ArrayList<Integer>();

                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");


                imageTitle.add("عکس شماره یک");
                imageTitle.add("عکس شماره دو");
                imageTitle.add("عکس شماره سه");


                imageType.add(1);
                imageType.add(1);
                imageType.add(1);

                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/nBxd2");
                VIDEO_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");

                IS_APARAT.add(true);
                IS_APARAT.add(false);
                IS_APARAT.add(true);

                IS_VIDEO.add(true);
                IS_VIDEO.add(false);
                IS_VIDEO.add(true);

                Slider.ShowSlideractivity(MainActivity.this, VIDEO_URL, IS_APARAT, IS_VIDEO, imageurls, imageTitle, imageType, 0, Slider.AnimtionType1, false, false, 4, 2000, 3000, false, false);

            }
        });
        Button_slider_style3 = (Button) findViewById(R.id.Button_slider_style3);
        Button_slider_style3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
                ArrayList<Boolean> IS_APARAT = new ArrayList<>();
                ArrayList<String> imageurls = new ArrayList<String>();
                ArrayList<String> VIDEO_URL = new ArrayList<>();
                ArrayList<String> imageTitle = new ArrayList<String>();
                ArrayList<Integer> imageType = new ArrayList<Integer>();

                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");


                imageTitle.add("عکس شماره یک");
                imageTitle.add("عکس شماره دو");
                imageTitle.add("عکس شماره سه");


                imageType.add(1);
                imageType.add(1);
                imageType.add(1);

                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/nBxd2");
                VIDEO_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");

                IS_APARAT.add(true);
                IS_APARAT.add(false);
                IS_APARAT.add(true);

                IS_VIDEO.add(true);
                IS_VIDEO.add(false);
                IS_VIDEO.add(true);

                Slider.ShowSlideractivity(MainActivity.this, VIDEO_URL, IS_APARAT, IS_VIDEO, imageurls, imageTitle, imageType, 0, Slider.AnimtionType2, true, false, 4, 1000, 3000, true, false);

            }
        });
        Button_slider_style4 = (Button) findViewById(R.id.Button_slider_style4);
        Button_slider_style4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
                ArrayList<Boolean> IS_APARAT = new ArrayList<>();
                ArrayList<String> imageurls = new ArrayList<String>();
                ArrayList<String> VIDEO_URL = new ArrayList<>();
                ArrayList<String> imageTitle = new ArrayList<String>();
                ArrayList<Integer> imageType = new ArrayList<Integer>();

                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");


                imageTitle.add("عکس شماره یک");
                imageTitle.add("عکس شماره دو");
                imageTitle.add("عکس شماره سه");


                imageType.add(1);
                imageType.add(1);
                imageType.add(1);

                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/nBxd2");
                VIDEO_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
                VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");

                IS_APARAT.add(true);
                IS_APARAT.add(false);
                IS_APARAT.add(true);

                IS_VIDEO.add(true);
                IS_VIDEO.add(false);
                IS_VIDEO.add(true);

                Slider.ShowSlideractivity(MainActivity.this, VIDEO_URL, IS_APARAT, IS_VIDEO, imageurls, imageTitle, imageType, 0, Slider.AnimtionType3, false, true, 4, 500, 3000, true, true);

            }
        });
        // ----------------------------------------------------------------------------

        custommessagebox_style1 = (Button) findViewById(R.id.custommessagebox_style1);
        custommessagebox_style1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String BROADCAST_ACTION_NAME_POSITIVE = "CALLMAINPOSITIVE";
                IntentFilter filter_POSITIVE = new IntentFilter();
                filter_POSITIVE.addAction(BROADCAST_ACTION_NAME_POSITIVE);
                filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
                registerReceiver(broadcastReceiver, filter_POSITIVE);

                CustomMessageBox.SingleMessageButton("سلام", "خوش آمدید به برنامه خودتون!", MainActivity.this,
                        true, true, "ممنون", CustomMessageBox.BROADCASTS_ACTION, Gravity.RIGHT, Gravity.RIGHT, true
                        , BROADCAST_ACTION_NAME_POSITIVE, "", "", false, "", false, "", "", "");
            }
        });

        custommessagebox_style2 = (Button) findViewById(R.id.custommessagebox_style2);
        custommessagebox_style2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomMessageBox.SingleMessageButton("Hi!", "GO TO TestActivity!", MainActivity.this,
                        true, true, "OK", CustomMessageBox.SHOWACTIVITY, Gravity.LEFT, Gravity.LEFT, true
                        , "", "ir.vasfa.notificationlibrary",
                        "TestActivity", false, "", false, "", "", "");
            }
        });

        custommessagebox_style3 = (Button) findViewById(R.id.custommessagebox_style3);
        custommessagebox_style3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BROADCAST_ACTION_NAME_POSITIVE = "CALLMAINPOSITIVE";
                IntentFilter filter_POSITIVE = new IntentFilter();
                filter_POSITIVE.addAction(BROADCAST_ACTION_NAME_POSITIVE);
                filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
                registerReceiver(broadcastReceiver, filter_POSITIVE);

                String BROADCAST_ACTION_NAME_NEGETIVE = "CALLMAINNEGETIVE";
                IntentFilter filter_NEGETIVE = new IntentFilter();
                filter_NEGETIVE.addAction(BROADCAST_ACTION_NAME_NEGETIVE);
                filter_NEGETIVE.addCategory(Intent.CATEGORY_DEFAULT);
                registerReceiver(broadcastReceiver, filter_NEGETIVE);

                CustomMessageBox.MultiMessageButton("TITLE",
                        "MESSAGE",
                        MainActivity.this,
                        true,
                        true,
                        "YES",
                        "NO",
                        CustomMessageBox.BROADCASTS_ACTION,
                        CustomMessageBox.BROADCASTS_ACTION,
                        Gravity.LEFT,
                        Gravity.LEFT,
                        false,
                        BROADCAST_ACTION_NAME_POSITIVE,
                        "",
                        "",
                        BROADCAST_ACTION_NAME_NEGETIVE,
                        "",
                        "",
                        true,
                        "http://ghesegoo.rahkarno.ir/urls/images/index.jpg");


            }
        });
        custommessagebox_style4 = (Button) findViewById(R.id.custommessagebox_style4);
        custommessagebox_style4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomMessageBox.SingleMessageButton("دانلود",
                        "خطا در دانلود فایل",
                        MainActivity.this,
                        true,
                        false,
                        "لغو دانلود",
                        CustomMessageBox.DOWNLOAD,
                        Gravity.RIGHT,
                        Gravity.RIGHT,
                        true,
                        "",
                        "",
                        "",
                        false,
                        "",
                        true,
                        "http://ipv4.download.thinkbroadband.com/5MB.zip",
                        "notificationFolder",
                        "20180620");
            }
        });

        final notification notification = new notification();
        btn_doNoting_notification = (Button) findViewById(R.id.doNoting_notification);
        btn_doNoting_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 5000);



                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(7), "");

            }
        });
        btn_openApplication_notification = (Button) findViewById(R.id.openApplication_notification);
        btn_openApplication_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(1), "");
            }
        });

        btn_showsite_notification = (Button) findViewById(R.id.showsite_notification);
        btn_showsite_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(2), "");
            }
        });

        btn_showSpacialActivity_notification = (Button) findViewById(R.id.showSpacialActivity_notification);
        btn_showSpacialActivity_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(3), "");
            }
        });

        btn_openPhoneDialort_notification = (Button) findViewById(R.id.openPhoneDialort_notification);
        btn_openPhoneDialort_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(4), "");
            }
        });

        btn_SendSms_notification = (Button) findViewById(R.id.SendSms_notification);
        btn_SendSms_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(5), "");
            }
        });

        btn_email_notification = (Button) findViewById(R.id.email_notification);
        btn_email_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] EmailTo = {"v.naeimabadi@gmail.com"};
                notification.All_Notification_Those_Are_Only_Text(MainActivity.this, Fake_notificationData(6), "");
            }
        });

        //image
        final String Image_URL = "http://maroofshow.com/image/950620-mohammad.jpg";
        Image_btn_doNoting_notification = (Button) findViewById(R.id.Image_doNoting_notification);
        Image_btn_doNoting_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(7), "");
            }
        });
        Image_btn_openApplication_notification = (Button) findViewById(R.id.Image_openApplication_notification);
        Image_btn_openApplication_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(1), "");
            }
        });

        Image_btn_showsite_notification = (Button) findViewById(R.id.Image_showsite_notification);
        Image_btn_showsite_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(2), "");
            }
        });

        Image_btn_showSpacialActivity_notification = (Button) findViewById(R.id.Image_showSpacialActivity_notification);
        Image_btn_showSpacialActivity_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(3), "");
            }
        });

        Image_btn_openPhoneDialort_notification = (Button) findViewById(R.id.Image_openPhoneDialort_notification);
        Image_btn_openPhoneDialort_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(4), "");
            }
        });

        Image_btn_SendSms_notification = (Button) findViewById(R.id.Image_SendSms_notification);
        Image_btn_SendSms_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(5), "");
            }
        });

        Image_btn_email_notification = (Button) findViewById(R.id.Image_email_notification);
        Image_btn_email_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification.All_Notification_Those_Are_Image(MainActivity.this, Fake_notificationData(6), "");

            }
        });

    }


    public void showNewalarm1() {

        CustomMessageBox.SingleMessageButton("چطوری1", "این پیغام برای شما می باشد!1", MainActivity.this,
                true, true, "سپاس1", CustomMessageBox.dismiss, Gravity.CENTER, Gravity.CENTER, true
                , "", "", "", false, "", false, "", "", "");

    }

    public void showNewalarm2() {

        CustomMessageBox.SingleMessageButton("چطوری2", "این پیغام برای شما می باشد!2", MainActivity.this,
                true, true, "سپاس2", CustomMessageBox.dismiss, Gravity.CENTER, Gravity.CENTER, true
                , "", "", "", false, "", false, "", "", "");

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            unregisterReceiver(broadcastReceiver);
            String TYPES = intent.getStringExtra("BroadCastNames");
            if (TYPES.equals("CALLMAINPOSITIVE")) {
                showNewalarm1();
            } else if (TYPES.equals("CALLMAINNEGETIVE")) {
                showNewalarm2();
            }

        }
    };


    @Override
    public void onBackPressed() {

        if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }
    }


    public String Fake_notificationData(int type) {
//        AndroidDataItems dataItems = new AndroidDataItems();
//        dataItems.DS = new ArrayList<>();
//        dataItems.DB = new ArrayList<>();
//
//        //this is default
//        dataItems.DS.add("0");
//        dataItems.DS.add(type + "");
//        dataItems.DS.add("btn_doNoting_notification");
//        dataItems.DS.add("small");
//        dataItems.DS.add("big");
//        dataItems.DS.add("تایید");
//        dataItems.DS.add("انصراف");
//
//
//        //this is default
//        dataItems.DB.add(true);
//        dataItems.DB.add(true);
//        dataItems.DB.add(true);
//        dataItems.DB.add(true);
//
//        switch (type) {
//            case 1: {
//                dataItems.DS.add("ir.vasfa.notificationlibrary");
//            }
//            break;
//            case 2: {
//                dataItems.DS.add("www.google.com");
//            }
//            break;
//            case 3: {
//                dataItems.DS.add("ir.vasfa.notificationlibrary");
//                dataItems.DS.add("TestActivity");
//            }
//            break;
//            case 4: {
//                dataItems.DS.add("09195884906");
//            }
//            break;
//            case 5: {
//                dataItems.DS.add("09195884906");
//                dataItems.DS.add("this is from notification");
//            }
//            break;
//            case 6: {
//                dataItems.DS.add("v.naeimabadi@gmail.com;vasfacompany@gmail.com");
//                dataItems.DS.add("vasfa@gmail.com;company@gmail.com");
//                dataItems.DS.add("Notification");
//                dataItems.DS.add("this is notificaiton");
//                dataItems.DS.add("Notificaiton1");
//            }
//            break;
//            case 8: {
//                dataItems.DS.add("download file url");
//                dataItems.DS.add("file name");
//            }
//            break;
//        }


//        String resJson = "";
//        final ObjectMapper om = new ObjectMapper();
//        try {
//            resJson = om.writer().withDefaultPrettyPrinter()
//                    .writeValueAsString(dataItems);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return type+"";
    }
}
