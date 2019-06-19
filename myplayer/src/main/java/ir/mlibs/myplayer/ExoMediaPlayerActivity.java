package ir.mlibs.myplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.net.URI;

public class ExoMediaPlayerActivity extends AppCompatActivity {


    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    ImageView iv_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_media_player);

        initialize();
    }

    private void initialize() {
        try{

            iv_play=findViewById(R.id.Imageview_play);
            exoPlayerView=findViewById(R.id.videoView_Play);
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer= ExoPlayerFactory.newSimpleInstance(ExoMediaPlayerActivity.this,trackSelector);

            Uri videoUri=Uri.parse("http://pardischub.vasfa.ir/FROZENLet.mp3");
            DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(videoUri,dataSourceFactory,extractorsFactory,null,null);


            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

            exoPlayerView.setSystemUiVisibility(View.VISIBLE);


        }catch (Exception ex)
        {

        }
    }
}
