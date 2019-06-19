package CustomCardView;

/**
 * Created by vali on 2018-06-02.
 */

import android.content.Context;

/**
 * Interface for platform specific CardView implementations.
 */
interface CardViewImpl {
    void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius,
                    float elevation, float maxElevation, int cornerFlag, int edgesFlag);

    void setRadius(CardViewDelegate cardView, float radius);

    float getRadius(CardViewDelegate cardView);

    void setElevation(CardViewDelegate cardView, float elevation);

    float getElevation(CardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(CardViewDelegate cardView, float maxElevation);

    float getMaxElevation(CardViewDelegate cardView);

    float getMinWidth(CardViewDelegate cardView);

    float getMinHeight(CardViewDelegate cardView);

    void updatePadding(CardViewDelegate cardView);

    void onCompatPaddingChanged(CardViewDelegate cardView);

    void onPreventCornerOverlapChanged(CardViewDelegate cardView);

    void setBackgroundColor(CardViewDelegate cardView, int color);
}