package ir.mlibs.sliderfragmentgallery;

/**
 * Created by Vn on 09/11/2016.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

public class ViewPagerCustomDurationFragment extends ViewPager {

    public ViewPagerCustomDurationFragment(Context context) {
        super(context);
        this.isPagingEnabled = true;
        postInitViewPager();
    }

    public ViewPagerCustomDurationFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isPagingEnabled = true;
        postInitViewPager();
    }

    private ScrollerCustomDuration mScroller = null;

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }

    private boolean isPagingEnabled;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isPagingEnabled) {
            return super.onInterceptTouchEvent(ev);
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isPagingEnabled) {
            return super.onTouchEvent(ev);
        }

        return false;
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


}
