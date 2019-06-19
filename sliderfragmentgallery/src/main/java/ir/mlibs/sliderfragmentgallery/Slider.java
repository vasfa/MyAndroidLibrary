package ir.mlibs.sliderfragmentgallery;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by vali on 2018-03-07.
 */

public class Slider {


    public static int AnimtionType0=0;
    public static int AnimtionType1=1;
    public static int AnimtionType2=2;
    public static int AnimtionType3=3;
    public static void ShowSlideractivity(Activity activities,
                                          ArrayList<String> VIDEO_URL,
                                          ArrayList<Boolean> IS_APARAT,
                                          ArrayList<Boolean> IS_VIDEO,
                                          ArrayList<String> ImageUrl_ImageFilePaht_ImageDrawable,
                                          ArrayList<String> ImageTitle,
                                          ArrayList<Integer> type,
                                          int PositionToStart,
                                          int AnimationType,
                                          boolean ZoomAble,
                                          boolean AutoSlide,
                                          int Delay,
                                          int SlideSpeed,
                                          int StartDelay,
                                          boolean rotate,
                                          boolean RoteToLeft
    ) {

        Setting.HORIZONTAL_Video_URL = VIDEO_URL;
        Setting.HORIZONTAL_IS_Video = IS_VIDEO;
        Setting.HORIZONTAL_IS_Aparat = IS_APARAT;
        Setting.HORIZONTAL_IMAGE_Title = ImageTitle;
        Setting.HORIZONTAL_IMAGE_Type = type;
        Setting.HORIZONTAL_IMAGE_URL = ImageUrl_ImageFilePaht_ImageDrawable;
        Setting.ANIMATIONTYPE=AnimationType;
        Setting.Zoomable=ZoomAble;
        Setting.Delay=Delay;
        Setting.AutoSlide=AutoSlide;
        Setting.mDuration=SlideSpeed;
        Setting.StartDelay=StartDelay;
        Setting.rotate=rotate;
        Setting.RoteToLeft=RoteToLeft;

        Intent intent = new Intent(activities, FullScreenActivity.class);
        intent.putExtra("id", PositionToStart);
        activities.startActivity(intent);


    }
}
