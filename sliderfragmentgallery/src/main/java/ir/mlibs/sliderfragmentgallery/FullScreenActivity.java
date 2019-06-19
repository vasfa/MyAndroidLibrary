package ir.mlibs.sliderfragmentgallery;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import mysliderlibraryimageprogressbar.OkHttpProgressGlideModule;
import slider_custom_textview.IRANSansMobile_BoldFontTextView;

public class FullScreenActivity extends AppCompatActivity {

    ImageBean imageBean;
    ArrayList<ImageBean> image_list = new ArrayList<>();
    TextSwitcher textswitcher;
    TextSwitcher textSwitcherposition;
    ViewPagerCustomDurationFragment viewPager;
    TextView tv_title, tv_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);


        final Glide glide = Glide.get(this);
        OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
        a.registerComponents(this, glide);

        textswitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        textSwitcherposition = (TextSwitcher) findViewById(R.id.textSwitcherposition);
        CircleIndicator indicator2 = (CircleIndicator) findViewById(R.id.indicator2);
        viewPager = (ViewPagerCustomDurationFragment) findViewById(R.id.view_pager);
        Intent intent = getIntent();
        int position = intent.getIntExtra("id", 0);
        for (int i = 0; i < Setting.HORIZONTAL_IMAGE_URL.size(); i++) {
            imageBean = new ImageBean();
            imageBean.setImageURLPath(Setting.HORIZONTAL_IMAGE_URL.get(i));
            imageBean.setVideoURLPath(Setting.HORIZONTAL_Video_URL.get(i));
            imageBean.setIsaparat(Setting.HORIZONTAL_IS_Aparat.get(i));
            imageBean.setIsvideo(Setting.HORIZONTAL_IS_Video.get(i));
            image_list.add(imageBean);
        }
        NUM_ITEMS = image_list.size();
        loadAnimations();
        setFactory();
        tv_position = (TextView) findViewById(R.id.TextView_position);
        tv_title = (TextView) findViewById(R.id.TextView_title);

        textswitcher.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
//        textSwitcherposition.setText(Setting.HORIZONTAL_IMAGE_URL.size()+" / "+(position+1));
        textSwitcherposition.setText((position + 1) + " / " + Setting.HORIZONTAL_IMAGE_URL.size());
        tv_position.setText(Setting.HORIZONTAL_IMAGE_URL.size() + " / " + (position + 1));
        tv_title.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
        Setting.image_list=image_list;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getBaseContext());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(Setting.HORIZONTAL_IMAGE_URL.size());
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage=position;
                tv_position.setText(Setting.HORIZONTAL_IMAGE_URL.size() + " / " + (position + 1));
                textswitcher.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
//                textSwitcherposition.setText(Setting.HORIZONTAL_IMAGE_URL.size()+" / "+(position+1));
                textSwitcherposition.setText((position + 1) + " / " + Setting.HORIZONTAL_IMAGE_URL.size());
                tv_title.setText(Setting.HORIZONTAL_IMAGE_Title.get(position));
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
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


                LinearLayout RL = (LinearLayout) findViewById(R.id.full_screen_layout);
                RL.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) RL.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                RL.setLayoutParams(params);
//                RL.setScaleX(sx);
//                RL.setScaleY(sy);
                //  RL.setLayoutParams(rel_btn);

            } else {


                final float scale = getResources().getDisplayMetrics().density;
                int size = Integer.parseInt(getString(R.string.sizeOfVideo));
                int pixels = (int) (size * scale + 0.5f);

                LinearLayout RL = (LinearLayout) findViewById(R.id.full_screen_layout);
                RL.getLayoutParams().height = pixels;

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) RL.getLayoutParams();
                params.height = (int) (size * metrics.density);
                RL.setLayoutParams(params);
            }
        } catch (Exception ex) {

        }
    }

    void setFactory() {
        textswitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {

                // Create run time textView with some attributes like gravity,
                // color, etc.
                IRANSansMobile_BoldFontTextView myText = new IRANSansMobile_BoldFontTextView(FullScreenActivity.this);
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
                IRANSansMobile_BoldFontTextView myText = new IRANSansMobile_BoldFontTextView(FullScreenActivity.this);
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
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);

        // set the animation type of textSwitcher
        textSwitcherposition.setInAnimation(in);
        textSwitcherposition.setOutAnimation(out);

        textswitcher.setInAnimation(in);
        textswitcher.setOutAnimation(out);
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
                    runOnUiThread(new Runnable() {
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
                }else{//TO RIght
                    runOnUiThread(new Runnable() {
                        public void run() {
                            isusing = true;
                            currentPage--;
                            rightToleft = true;
                            if (currentPage <= -1) {
                                currentPage = NUM_ITEMS-1;
                            }
                            viewPager.setCurrentItem(currentPage, true);
                        }
                    });
                }

            } else {
                // As the TimerTask run on a separate thread from UI thread we have
                // to call runOnUiThread to do work on UI thread.
                runOnUiThread(new Runnable() {
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
