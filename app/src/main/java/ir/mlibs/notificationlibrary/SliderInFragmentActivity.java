package ir.mlibs.notificationlibrary;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ir.mlibs.sliderfragmentgallery.SliderFragmentMain;

import static ir.mlibs.sliderfragmentgallery.Setting.IsDestroyed;

public class SliderInFragmentActivity extends AppCompatActivity {
    SliderFragmentMain sliderFragmentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_in_fragment);

        Initialize();
    }

    public ArrayList<String> VIDEO_URL = new ArrayList<>();
    public ArrayList<String> IMAGE_URL = new ArrayList<>();
    public ArrayList<String> IMAGE_NAME = new ArrayList<>();
    public ArrayList<Integer> IMAGE_Type = new ArrayList<>();
    public ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
    public ArrayList<Boolean> IS_APARAT = new ArrayList<>();
    public ArrayList<Boolean> IS_ONLY_SOUND = new ArrayList<>();
    public ArrayList<Boolean> IS_Image_Zoomable = new ArrayList<>();
    public ArrayList<Boolean> IS_Title_Avalable = new ArrayList<>();

    private void Initialize() {

        IsDestroyed=false;
        VIDEO_URL.add("");
        VIDEO_URL.add("http://pardischub.vasfa.ir/FROZENLet.mp3");
        VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");


        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");

        IMAGE_NAME.add("زلزله");
        IMAGE_NAME.add("عکس شماره دو");
        IMAGE_NAME.add("مدل");


        IMAGE_Type.add(1);
        IMAGE_Type.add(1);
        IMAGE_Type.add(1);

        IS_APARAT.add(false);
        IS_APARAT.add(false);
        IS_APARAT.add(true);

        IS_VIDEO.add(false);
        IS_VIDEO.add(false);
        IS_VIDEO.add(true);

        IS_ONLY_SOUND.add(false);
        IS_ONLY_SOUND.add(true);
        IS_ONLY_SOUND.add(false);

        IS_Image_Zoomable.add(false);
        IS_Image_Zoomable.add(false);
        IS_Image_Zoomable.add(false);

        IS_Title_Avalable.add(false);
        IS_Title_Avalable.add(true);
        IS_Title_Avalable.add(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        sliderFragmentMain = (SliderFragmentMain) fragmentManager.findFragmentById(R.id.fragment_actionbar1);

        try {
            sliderFragmentMain.Slider_animation_type(2);
            sliderFragmentMain.SlideSpeed(4);
            sliderFragmentMain.HaveAutoSlide(false);
            sliderFragmentMain.HaveToShowCounter(true);
            sliderFragmentMain.HaveIndicators(false);
            sliderFragmentMain.HaveInsideIndicators(false);
            sliderFragmentMain.CanSlideViewPager(true);
            sliderFragmentMain.ShowLeft_Arrow(false);
            sliderFragmentMain.ShowRight_Arrow(false);
            sliderFragmentMain.PositionToStart(1);
            sliderFragmentMain.AddItems(VIDEO_URL
                    , IMAGE_URL
                    , IMAGE_NAME
                    , IMAGE_Type
                    , IS_VIDEO
                    , IS_APARAT
                    ,IS_ONLY_SOUND
                    ,IS_Image_Zoomable
                    ,IS_Title_Avalable
            );

        } catch (Exception ex) {
            String asdasd = "";
        }
    }
}
