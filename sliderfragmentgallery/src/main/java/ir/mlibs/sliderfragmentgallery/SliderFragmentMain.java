package ir.mlibs.sliderfragmentgallery;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import mysliderlibraryimageprogressbar.OkHttpProgressGlideModule;
import slider_custom_textview.IRANSansMobile_BoldFontTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragmentMain extends Fragment {

    ImageBean imageBean;
    ArrayList<ImageBean> image_list = new ArrayList<>();
    TextSwitcher textswitcher;
    TextSwitcher textSwitcherposition;
    ViewPagerCustomDurationFragment viewPager;
    TextView tv_title, tv_position;
    View view = null;
    PageIndicatorView pageIndicatorView;
    PageIndicatorView pageIndicatorViewInside;
    ImageView iv_next, iv_pre;

    public static SliderFragmentMain newInstance(String text) {

        SliderFragmentMain f = new SliderFragmentMain();

        Bundle b = new Bundle();
        b.putString("text", text);
        f.setArguments(b);
        return f;
    }

    public SliderFragmentMain() {
        // Required empty public constructor
    }


    public ArrayList<String> videourls = new ArrayList<String>();
    public ArrayList<String> imageurls = new ArrayList<String>();
    public ArrayList<String> imageTitle = new ArrayList<String>();
    public ArrayList<Integer> imageType = new ArrayList<Integer>();
    public ArrayList<Boolean> isVideo = new ArrayList<Boolean>();
    public ArrayList<Boolean> isAparat = new ArrayList<Boolean>();
    public ArrayList<Boolean> isOnlySound = new ArrayList<Boolean>();
    public ArrayList<Boolean> isImageZoomable = new ArrayList<Boolean>();
    public ArrayList<Boolean> ISTitleAbable = new ArrayList<Boolean>();

    public void AddItems(ArrayList<String> videourls,
                         ArrayList<String> imageurls,
                         ArrayList<String> imageTitle,
                         ArrayList<Integer> imageType,
                         ArrayList<Boolean> isVideo,
                         ArrayList<Boolean> isAparat,
                         ArrayList<Boolean> isOnlySound,
                         ArrayList<Boolean> isImageZoomable,
                         ArrayList<Boolean> ISTitleAbable
    ) {
        this.videourls = videourls;
        this.imageurls = imageurls;
        this.imageTitle = imageTitle;
        this.imageType = imageType;
        this.isVideo = isVideo;
        this.isAparat = isAparat;
        this.isOnlySound = isOnlySound;
        this.isImageZoomable = isImageZoomable;
        this.ISTitleAbable = ISTitleAbable;

        Initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_slider_main, container, false);

        return view;
    }


    public void Initialize() {
        {
//            ArrayList<String> videourls = new ArrayList<String>();
//            videourls.add("عکس شماره یک");
//            videourls.add("عکس شماره دو");
//            videourls.add("عکس شماره سه");
//
//            ArrayList<String> imageurls = new ArrayList<String>();
//            imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/6e6b25e8-20c8-41b3-bf02-edf201f67d84636560172053634812IMG_1520407795855.jpg");
//            imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/0d7c07e2-1393-42fa-8e3c-0719b111dc4e636560172491844422IMG_1520407845159.jpg");
//            imageurls.add("http://BumiGardi.vasfa.ir/images/users/userid_71/d3138039-cf3b-4067-8549-295bf496c4f9636560191780792413IMG_1520409769216.jpg");
//
//            ArrayList<String> imageTitle = new ArrayList<String>();
//            imageTitle.add("عکس شماره یک");
//            imageTitle.add("عکس شماره دو");
//            imageTitle.add("عکس شماره سه");
//
//            ArrayList<Integer> imageType = new ArrayList<Integer>();
//            imageType.add(1);
//            imageType.add(1);
//            imageType.add(1);

            Setting.HORIZONTAL_IS_TITLE_AVALABLE = ISTitleAbable;
            Setting.HORIZONTAL_IS_IMAGE_ZOOMABLE = isImageZoomable;
            Setting.HORIZONTAL_IS_ONLY_SOUND = isOnlySound;
            Setting.HORIZONTAL_IS_Aparat = isAparat;
            Setting.HORIZONTAL_IS_Video = isVideo;
            Setting.HORIZONTAL_IMAGE_Title = imageTitle;
            Setting.HORIZONTAL_IMAGE_Type = imageType;
            Setting.HORIZONTAL_IMAGE_URL = imageurls;
            Setting.HORIZONTAL_Video_URL = videourls;

            final Glide glide = Glide.get(getActivity());
            OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
            a.registerComponents(getActivity(), glide);
            iv_next = (ImageView) view.findViewById(R.id.ImageView_next);
            iv_pre = (ImageView) view.findViewById(R.id.ImageView_pre);
            textswitcher = (TextSwitcher) view.findViewById(R.id.textSwitcher);
            textSwitcherposition = (TextSwitcher) view.findViewById(R.id.textSwitcherposition);
            CircleIndicator indicator2 = (CircleIndicator) view.findViewById(R.id.indicator2);
            try {
                viewPager = (ViewPagerCustomDurationFragment) view.findViewById(R.id.view_pager);
            } catch (Exception ex) {
                String asd = "";
            }

            //Intent intent = getActivity().getIntent();
            //int position = intent.getIntExtra("id", 0);
            for (int i = 0; i < Setting.HORIZONTAL_IMAGE_URL.size(); i++) {
                imageBean = new ImageBean();
                imageBean.setImageURLPath(Setting.HORIZONTAL_IMAGE_URL.get(i));
                imageBean.setVideoURLPath(Setting.HORIZONTAL_Video_URL.get(i));
                imageBean.setIsaparat(Setting.HORIZONTAL_IS_Aparat.get(i));
                imageBean.setIsvideo(Setting.HORIZONTAL_IS_Video.get(i));
                imageBean.setIsonlySound(Setting.HORIZONTAL_IS_ONLY_SOUND.get(i));
                imageBean.setIsimageZoomable(Setting.HORIZONTAL_IS_IMAGE_ZOOMABLE.get(i));
                imageBean.setIstitleAvalable(Setting.HORIZONTAL_IS_TITLE_AVALABLE.get(i));
                image_list.add(imageBean);
            }
            NUM_ITEMS = image_list.size();
            loadAnimations();
            setFactory();
            tv_position = (TextView) view.findViewById(R.id.TextView_position);
            tv_title = (TextView) view.findViewById(R.id.TextView_title);

            textswitcher.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
//            textSwitcherposition.setText(Setting.HORIZONTAL_IMAGE_URL.size() + " / " + (position + 1));
            textSwitcherposition.setText((position + 1) + " / " + Setting.HORIZONTAL_IMAGE_URL.size());
            tv_position.setText((position + 1) + " / " + Setting.HORIZONTAL_IMAGE_URL.size());
            tv_title.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
            Setting.image_list = image_list;
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity().getBaseContext());
            viewPager.setAdapter(viewPagerAdapter);
//            viewPager.setOffscreenPageLimit(Setting.HORIZONTAL_IMAGE_URL.size());
            viewPager.setOffscreenPageLimit(1);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                    pageIndicatorView.setSelection(position);
                    pageIndicatorViewInside.setSelection(position);

                    tv_position.setText(Setting.HORIZONTAL_IMAGE_URL.size() + " / " + (position + 1));
                    textswitcher.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
//                    textSwitcherposition.setText(Setting.HORIZONTAL_IMAGE_URL.size() + " / " + (position + 1));
                    textSwitcherposition.setText((position + 1) + " / " + Setting.HORIZONTAL_IMAGE_URL.size());
                    tv_title.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));

                    if(Setting.HORIZONTAL_IS_TITLE_AVALABLE.get(position))
                    {
                        textswitcher.setVisibility(View.VISIBLE);
                    }else{
                        textswitcher.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {

                    if (Setting.ANIMATIONTYPE == 0) {

                    } else if (Setting.ANIMATIONTYPE == 1) {
                        final float normalizedposition = Math.abs(Math.abs(position) - 1);
                        page.setAlpha(normalizedposition);
                    } else if (Setting.ANIMATIONTYPE == 2) {
                        final float normalizedposition = Math.abs(Math.abs(position) - 1);
                        page.setScaleX(normalizedposition / 2 + 0.5f);
                        page.setScaleY(normalizedposition / 2 + 0.5f);
                    } else if (Setting.ANIMATIONTYPE == 3) {
                        page.setRotationY(position * -30);
                    }


                }
            });
            indicator2.setViewPager(viewPager);
            viewPagerAdapter.registerDataSetObserver(indicator2.getDataSetObserver());
            if (Setting.AutoSlide)
                SwitchPage(Setting.Delay);

            pageIndicatorView = (PageIndicatorView) view.findViewById(R.id.pageIndicatorView);
            pageIndicatorViewInside = (PageIndicatorView) view.findViewById(R.id.pageIndicatorViewInside);
            // pageIndicatorView.setCount(Setting.HORIZONTAL_IS_Aparat.size()); // specify total count of indicators
            pageIndicatorView.setSelection(0);
            pageIndicatorViewInside.setSelection(0);

            if (Setting.canSlideViewPager) {
                if (viewPager != null)
                    viewPager.setPagingEnabled(true);
            } else {
                if (viewPager != null)
                    viewPager.setPagingEnabled(false);
            }
            if (Setting.HaveTop) {
                textswitcher.setVisibility(View.VISIBLE);
            } else {
                textswitcher.setVisibility(View.GONE);
            }
            if (Setting.HaveBottom) {
                textSwitcherposition.setVisibility(View.VISIBLE);
            } else {
                textSwitcherposition.setVisibility(View.GONE);
            }
            if(Setting.HORIZONTAL_IS_TITLE_AVALABLE.get(position))
            {
                textswitcher.setVisibility(View.VISIBLE);
            }else{
                textswitcher.setVisibility(View.GONE);
            }
            if (Setting.HaveIndicator) {
                pageIndicatorView.setVisibility(View.VISIBLE);
            } else {
                pageIndicatorView.setVisibility(View.GONE);
            }
            if (Setting.HaveInsideIndicator) {
                pageIndicatorViewInside.setVisibility(View.VISIBLE);
            } else {
                pageIndicatorViewInside.setVisibility(View.GONE);
            }

            iv_pre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentPage--;
                    rightToleft = true;
                    if (currentPage <= -1) {
                        currentPage = NUM_ITEMS - 1;
                    }
                    viewPager.setCurrentItem(currentPage, true);
                }
            });

            iv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentPage++;
                    rightToleft = false;
                    if (currentPage >= NUM_ITEMS) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage, true);
                }
            });

            if (Setting.ShowLeftArrow) {
                iv_pre.setVisibility(View.VISIBLE);
            } else {
                iv_pre.setVisibility(View.GONE);
            }
            if (Setting.ShowRightArrow) {
                iv_next.setVisibility(View.VISIBLE);
            } else {
                iv_next.setVisibility(View.GONE);
            }

        }
    }

    void setFactory() {
        textswitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {

                // Create run time textView with some attributes like gravity,
                // color, etc.
                IRANSansMobile_BoldFontTextView myText = new IRANSansMobile_BoldFontTextView(getActivity());
//                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
//                        ((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int)RelativeLayout.LayoutParams.MATCH_PARENT);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.setMargins(0, 0, 10, 20);
                myText.setLayoutParams(params);
                myText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                myText.setTextSize(30);
                //myText.setLayoutParams(params);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        textSwitcherposition.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {

                // Create run time textView with some attributes like gravity,
                // color, etc.
                IRANSansMobile_BoldFontTextView myText = new IRANSansMobile_BoldFontTextView(getActivity());
//                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
//                        ((int) RelativeLayout.LayoutParams.MATCH_PARENT,(int)RelativeLayout.LayoutParams.MATCH_PARENT);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //params.setMargins(0, 0, 10, 20);
                myText.setLayoutParams(params);
                myText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                myText.setTextSize(30);
                //myText.setLayoutParams(params);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

    }

    void loadAnimations() {

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.fade_out);

        // set the animation type of textSwitcher
        textSwitcherposition.setInAnimation(in);
        textSwitcherposition.setOutAnimation(out);

        textswitcher.setInAnimation(in);
        textswitcher.setOutAnimation(out);
    }

    int position = 0;

    public void PositionToStart(int p_T_start) {
        position = p_T_start;
        currentPage=position;

    }

    public void CanZoom(Boolean ZoomAble) {
        Setting.Zoomable = ZoomAble;
    }

    public void IfIsVideo(Boolean IsVideo) {
        Setting.IsVideo = IsVideo;
    }

    public void IfIsAparat(Boolean IsAparat) {
        Setting.IsAparat = IsAparat;
    }

    public void HaveAutoSlide(Boolean AutoSlide) {
        Setting.AutoSlide = AutoSlide;
    }

    public void HaveIndicators(Boolean HaveIndicator) {
        Setting.HaveIndicator = HaveIndicator;
        if (HaveIndicator) {
            if (pageIndicatorView != null)
                pageIndicatorView.setVisibility(View.VISIBLE);
        } else {
            if (pageIndicatorView != null)
                pageIndicatorView.setVisibility(View.GONE);
        }

    }

    public void HaveInsideIndicators(Boolean HaveInsideIndicator) {
        Setting.HaveInsideIndicator = HaveInsideIndicator;
        if (HaveInsideIndicator) {
            if (pageIndicatorViewInside != null)
                pageIndicatorViewInside.setVisibility(View.VISIBLE);
        } else {
            if (pageIndicatorViewInside != null)
                pageIndicatorViewInside.setVisibility(View.GONE);
        }

    }

    public void CanSlideViewPager(Boolean canSlideViewPager) {
        Setting.canSlideViewPager = canSlideViewPager;
        if (Setting.canSlideViewPager) {
            if (viewPager != null)
                viewPager.setPagingEnabled(true);
        } else {
            if (viewPager != null)
                viewPager.setPagingEnabled(false);
        }
    }

    public void ShowLeft_Arrow(Boolean showLeft_Arrow) {
        Setting.ShowLeftArrow = showLeft_Arrow;
        if (Setting.ShowLeftArrow) {
            if (iv_pre != null)
                iv_pre.setVisibility(View.VISIBLE);
        } else {
            if (iv_pre != null)
                iv_pre.setVisibility(View.GONE);

        }
    }

    public void ShowRight_Arrow(Boolean showRight_Arrow) {
        Setting.ShowRightArrow = showRight_Arrow;
        if (Setting.ShowRightArrow) {
            if (iv_next != null)
                iv_next.setVisibility(View.VISIBLE);
        } else {
            if (iv_next != null)
                iv_next.setVisibility(View.GONE);
        }
    }

//    public void IsonlyMusic(Boolean IsonlyMusic) {
//        Setting.isOnlySound = IsonlyMusic;
//
//    }

    public void HaveToShowTitle(Boolean ShowTitle) {
        Setting.HaveTop = ShowTitle;
        if (ShowTitle) {
            if (textswitcher != null)
                textswitcher.setVisibility(View.VISIBLE);
        } else {
            if (textswitcher != null)
                textswitcher.setVisibility(View.GONE);
        }

    }

    public void HaveToShowCounter(Boolean ShowCounter) {
        Setting.HaveBottom = ShowCounter;
        if (ShowCounter) {
            if (textSwitcherposition != null)
                textSwitcherposition.setVisibility(View.VISIBLE);
        } else {
            if (textSwitcherposition != null)
                textSwitcherposition.setVisibility(View.GONE);
        }



    }

    public void SlideSpeed(int Delay) {
        Setting.Delay = Delay;
    }

    public void Slider_animation_type(int Types) {
        Setting.ANIMATIONTYPE = Types;
    }

    Timer timer = new Timer();
    static int NUM_ITEMS = 6;
    public static boolean rightToleft = true;
    public static int currentPage = 0;
    public static boolean isusing = false;

    public void SwitchPage(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        switchPageTask = new SwitchPageTask();
        timer.schedule(switchPageTask, Setting.StartDelay, seconds * 1000); // delay in milliseconds

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
        if (switchPageTask != null)
            switchPageTask.cancel();
    }

    SwitchPageTask switchPageTask;

    class SwitchPageTask extends TimerTask {
        @Override
        public void run() {

            if (Setting.rotate) {
                if (Setting.RoteToLeft) {//TO Left
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            isusing = true;
                            currentPage++;
                            rightToleft = false;
                            if (currentPage >= NUM_ITEMS) {
                                currentPage = 0;
                            }
                            viewPager.setCurrentItem(currentPage, true);
                        }
                    });
                } else {//TO RIght
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            isusing = true;
                            currentPage--;
                            rightToleft = true;
                            if (currentPage <= -1) {
                                currentPage = NUM_ITEMS - 1;
                            }
                            viewPager.setCurrentItem(currentPage, true);
                        }
                    });
                }

            } else {
                // As the TimerTask run on a separate thread from UI thread we have
                // to call runOnUiThread to do work on UI thread.
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        isusing = true;
                        if (currentPage >= NUM_ITEMS) {
                            //currentPage = 1;
                            rightToleft = false;
                            currentPage = NUM_ITEMS;
                        } else if (currentPage <= 0) {
                            rightToleft = true;
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage, true);
                        if (rightToleft) {
                            if (currentPage != NUM_ITEMS - 1)
                                currentPage++;
                            else {
                                rightToleft = false;
                                currentPage--;
                            }
                        } else
                            currentPage--;
                    }
                });
            }

        }
    }
}
