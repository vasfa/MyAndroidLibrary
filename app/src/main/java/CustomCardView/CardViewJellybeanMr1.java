package CustomCardView;

/**
 * Created by vali on 2018-06-02.
 */
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class CardViewJellybeanMr1 extends CardViewEclairMr1 {

    @Override
    public void initStatic() {
        OptRoundRectDrawableWithShadow.sRoundRectHelper
                = new OptRoundRectDrawableWithShadow.RoundRectHelper() {
            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                      Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }
}
