package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.mlibs.notificationlibrary.R;
import ir.mlibs.sliderfragmentgallery.SliderFragmentMain;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSliderFragment extends Fragment {


    public FragmentSliderFragment() {
        // Required empty public constructor
    }


    public ArrayList<String> VIDEO_URL = new ArrayList<>();
    public ArrayList<String> IMAGE_URL = new ArrayList<>();
    public ArrayList<String> IMAGE_NAME = new ArrayList<>();
    public ArrayList<Integer> IMAGE_Type = new ArrayList<>();
    public ArrayList<Boolean> IS_VIDEO = new ArrayList<>();
    public ArrayList<Boolean> IS_APARAT = new ArrayList<>();
    public ArrayList<Boolean> IS_ONLY_SOUND = new ArrayList<>();
    View view = null;
    SliderFragmentMain sliderFragmentMain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_slider, container, false);
        Initialize();
        return view;
    }

    private void Initialize() {

        VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/nBxd2");
//        VIDEO_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
//        VIDEO_URL.add("http://aparat.com/etc/api/videooffact/videohash/Tgksl");


        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
//        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
//        IMAGE_URL.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");

        IMAGE_NAME.add("زلزله");
//        IMAGE_NAME.add("عکس شماره دو");
//        IMAGE_NAME.add("مدل");

        IMAGE_Type.add(1);
//        IMAGE_Type.add(1);
//        IMAGE_Type.add(1);

        IS_APARAT.add(true);
//        IS_APARAT.add(false);
//        IS_APARAT.add(true);

        IS_VIDEO.add(true);
//        IS_VIDEO.add(false);
//        IS_VIDEO.add(true);

        IS_ONLY_SOUND.add(false);
//        IS_ONLY_SOUND.add(true);
//        IS_ONLY_SOUND.add(false);

        sliderFragmentMain = ((SliderFragmentMain) getChildFragmentManager().findFragmentById(R.id.fragment_actionbar1));

        try {
            sliderFragmentMain.CanZoom(false);
            sliderFragmentMain.Slider_animation_type(2);
            sliderFragmentMain.SlideSpeed(4);
            sliderFragmentMain.HaveAutoSlide(false);
//            sliderFragmentMain.HaveBottomtextSwitcher(false);
//            sliderFragmentMain.HaveToptextSwitcher(false);
            sliderFragmentMain.HaveIndicators(false);
            sliderFragmentMain.CanSlideViewPager(false);
            sliderFragmentMain.ShowLeft_Arrow(false);
            sliderFragmentMain.ShowRight_Arrow(false);
			sliderFragmentMain.PositionToStart(0);
//            sliderFragmentMain.AddItems(VIDEO_URL,IMAGE_URL,IMAGE_NAME,IMAGE_Type,IS_VIDEO,IS_APARAT,IS_ONLY_SOUND);

        } catch (Exception ex) {
            String asdasd = "";
        }
    }

}
