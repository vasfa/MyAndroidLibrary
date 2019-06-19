package ir.mlibs.sliderfragmentgallery;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by vali on 2018-03-07.
 */

public class Setting {

    public static ArrayList<Boolean> HORIZONTAL_IS_TITLE_AVALABLE=new ArrayList<>();
    public static ArrayList<Boolean> HORIZONTAL_IS_IMAGE_ZOOMABLE=new ArrayList<>();
    public static ArrayList<Boolean> HORIZONTAL_IS_ONLY_SOUND=new ArrayList<>();
    public static ArrayList<Boolean> HORIZONTAL_IS_Aparat=new ArrayList<>();
    public static ArrayList<Boolean> HORIZONTAL_IS_Video=new ArrayList<>();
    public static ArrayList<String> HORIZONTAL_Video_URL=new ArrayList<>();
    public static ArrayList<String> HORIZONTAL_IMAGE_URL=new ArrayList<>();
    public static ArrayList<String> HORIZONTAL_IMAGE_Title=new ArrayList<>();
    public static ArrayList<Integer> HORIZONTAL_IMAGE_Type=new ArrayList<>();
    public static Integer ANIMATIONTYPE=0;
    public static boolean Zoomable=true;
    public static boolean isOnlySound=false;
    public static boolean AutoSlide=false;
    public static boolean HaveIndicator=false;
    public static boolean HaveInsideIndicator=false;
    public static boolean ShowLeftArrow=false;
    public static boolean ShowRightArrow=false;
    public static boolean canSlideViewPager=true;
    public static boolean HaveTop=false;
    public static boolean HaveBottom=false;
    public static boolean rotate=false;
    public static boolean IsDestroyed=false;
    public static int Delay=3;
    public static int mDuration=500;
    public static int StartDelay=3000;
    public static boolean RoteToLeft=true;
    public static boolean IsVideo=false;
    public static boolean IsAparat=true;
    public static boolean mcbool = true;
    public static Context mc;
    public static ArrayList<ImageBean> image_list = new ArrayList<>();
}
