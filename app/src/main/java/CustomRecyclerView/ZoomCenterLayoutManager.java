package CustomRecyclerView;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * Created by vali on 2018-06-03.
 */

import android.content.Context;
        import android.graphics.PointF;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.LinearSmoothScroller;
        import android.support.v7.widget.RecyclerView;
        import android.util.DisplayMetrics;
import android.view.View;

/**
 * This extension to LinearLayoutManager allows the smoothScroll speed to be adjusted, additionally
 * it provides a custom implementation of the LinearSmoothScroller. The extension of
 * LinearSmoothScroller provides an additional snap preference, CENTER, this aligns the center of
 * the child's View with the center of the parent's View.
 */
public class ZoomCenterLayoutManager extends LinearLayoutManager {

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 0.9f;

    private SmoothScroller smoothScroller;

    private int anchor;

    public ZoomCenterLayoutManager(Context context) {
        super(context);

        smoothScroller = new SmoothScroller(context);
    }

    public ZoomCenterLayoutManager(Context context, int spanCount, int orientation,
                               boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        smoothScroller = new SmoothScroller(context);
    }

    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

    public void setScrollSpeed(float scrollSpeed) {
        smoothScroller.setScrollSpeed(scrollSpeed);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView,
                                       RecyclerView.State state, final int position) {
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class SmoothScroller extends LinearSmoothScroller {

        private static final float MILLISECONDS_PER_INCH = 250f;

        /**
         * Use in calculating the scroll speed.
         * The time (in ms) it takes to scroll an inch.
         */
        private float milliSecondsPerInch = -1;

        public SmoothScroller(Context context) {
            super(context);
        }

        public void setScrollSpeed(float scrollSpeed) {
            this.milliSecondsPerInch = scrollSpeed;
        }

        /**
         * Controls the direction in which smoothScroll looks for a list item.
         * Use our custom LinearLayoutManager to calculate PointF.
         *
         * @param targetPosition
         *
         * @return PointF, a class holding two float coordinates
         */
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return ZoomCenterLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            switch (anchor) {
                case SnappingRecyclerView.START:
                    return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, SNAP_TO_START);
                case SnappingRecyclerView.END:
                    return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, SNAP_TO_END);
                case SnappingRecyclerView.CENTER:
                default:
                    return ((boxStart + boxEnd) / 2) - ((viewStart + viewEnd) / 2);
            }
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return (milliSecondsPerInch > 0 ? milliSecondsPerInch : MILLISECONDS_PER_INCH) / displayMetrics.densityDpi;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

            float midpoint = getWidth() / 2.f;
            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;
        } else {
            return 0;
        }

    }
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            float midpoint = getHeight() / 2.f;
            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;
        } else {
            return 0;
        }
    }
}