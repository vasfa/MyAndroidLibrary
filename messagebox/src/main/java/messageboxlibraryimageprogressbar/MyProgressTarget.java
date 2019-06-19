package messageboxlibraryimageprogressbar;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by vali on 2017-05-16.
 */

public class MyProgressTarget<Z> extends ProgressTarget<String, Z> {
    private final TextView text;
    private final ProgressBar progress;
    private final AVLoadingIndicatorView avLoadingIndicatorView;
    private final ImageView image;

    public MyProgressTarget(Target<Bitmap> target, ProgressBar progress, ImageView image, TextView text,AVLoadingIndicatorView avLoadingIndicatorView) {
        super((Target<Z>) target);
        this.progress = progress;
        this.image = image;
        this.text = text;
        this.avLoadingIndicatorView=avLoadingIndicatorView;
    }

    @Override
    public void onProgress(long bytesRead, long expectedLength) {
        //progress.setIndeterminate(false);
        progress.setProgress((int) (100 * bytesRead / expectedLength));
        image.setImageLevel((int) (10000 * bytesRead / expectedLength));
        text.setText(String.format("%.2f/%.2f MB\n %.1f%%",
                bytesRead / 1e6, expectedLength / 1e6, 100f * bytesRead / expectedLength));
        text.setText(String.format("%.1f%%", 100f * bytesRead / expectedLength));
        if ((int) (100 * bytesRead / expectedLength) == 100) {
            text.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
            avLoadingIndicatorView.hide();
        }

    }

    @Override
    public float getGranualityPercentage() {
        return 0.1f; // this matches the format string for #text below
    }

}