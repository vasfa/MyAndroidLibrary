package New_Custom_textview;

/**
 * Created by Vn on 28/03/2016.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class IRANSansMobile_BoldFontTextView extends android.support.v7.widget.AppCompatTextView {


    public IRANSansMobile_BoldFontTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(),
                "fonts/IRANSansMobile(FaNum)_Bold.ttf");
        this.setTypeface(face);
    }

    public IRANSansMobile_BoldFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),
                "fonts/IRANSansMobile(FaNum)_Bold.ttf");
        this.setTypeface(face);
    }

    public IRANSansMobile_BoldFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),
                "fonts/IRANSansMobile(FaNum)_Bold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
