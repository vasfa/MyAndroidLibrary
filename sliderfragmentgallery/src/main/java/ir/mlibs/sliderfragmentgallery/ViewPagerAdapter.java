package ir.mlibs.sliderfragmentgallery;

/**
 * Created by vali on 2018-03-13.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;


/**
 * Created by komal yogi
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    View viewLayout = null;


    ArrayList<ImageBean> image_list = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;
    TouchImageViewFragment imageDisplay;
    ImageView iv_full_image_without_zoom;
    AVLoadingIndicatorView avis;
    ProgressBar pb;
    TextView tv;

    //these are for video and voice
    LinearLayout ll_Linearlayout_main_videoView;
    VideoView myVideoView;
    String Video_Sound_Url = "";
    LinearLayout ll_SmoothProgressBar;
    //    SeekBar seekBar;
    DiscreteSeekBar seekBar;
    ImageView iv_pause, iv_play, iv_main;
    ImageView iv_off, iv_on, iv_repeat;
    int lastvolume = 0;
    private AudioManager audioManager = null;
    boolean newplay = true;
    TextView tv_durationleft, tv_total_duration;
    Boolean isvideo = false;
    Boolean isAparat = false;
    boolean isstarted = false;
    boolean isCanceled = false;
    String MainURL = "";
    MediaPlayer mediaPlayer;
    Handler mHandler;
    int mediaFileLengthInMilliseconds;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    String Title = "";
    LinearLayout ll_videoView;
    Boolean islooping = false;
    int pos = 1;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.image_list = Setting.image_list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return image_list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageVideoSoundFragment.init(position);
    }


}
